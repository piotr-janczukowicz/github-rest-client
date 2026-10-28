package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleset;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersion;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersionWithState;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsRulesApiIntegrationTest {

    private static final String ORG = "my-org";
    private static final long RULESET_ID = 42L;
    private static final long VERSION_ID = 1L;

    private static final String ORG_RULESETS_PATH = "/orgs/my-org/rulesets";
    private static final String ORG_RULESET_PATH = "/orgs/my-org/rulesets/42";
    private static final String ORG_RULESET_HISTORY_PATH = "/orgs/my-org/rulesets/42/history";
    private static final String ORG_RULESET_VERSION_PATH = "/orgs/my-org/rulesets/42/history/1";

    private static final JsonType<List<GitHubRepositoryRuleset>> RULESETS_TYPE =
            JsonTypes.listOf(GitHubRepositoryRuleset.class);
    private static final JsonType<List<GitHubRulesetVersion>> RULESET_VERSIONS_TYPE =
            JsonTypes.listOf(GitHubRulesetVersion.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRulesets_shouldReturnRulesets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_RULESETS_PATH,
                okJson(listJsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json")));

        final GitHubPage<GitHubRepositoryRuleset> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().rules().getRulesets(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRulesets());
        HTTP.verifyRequested(GET, ORG_RULESETS_PATH);
        HTTP.verifyQueryParam(GET, ORG_RULESETS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_RULESETS_PATH, "per_page", "100");
    }

    @Test
    void getRulesets_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ORG_RULESETS_PATH,
                okJson(listJsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json")));

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .rules()
                    .getRulesets(
                            ORG,
                            GitHubListOrgRulesetsFilterBuilder.builder()
                                    .targets("branch,tag")
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, ORG_RULESETS_PATH, "targets", "branch,tag");
        HTTP.verifyQueryParam(GET, ORG_RULESETS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_RULESETS_PATH, "per_page", "100");
    }

    @Test
    void getRulesets_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(ORG_RULESETS_PATH, GitHubRepositoryRuleset.class, "repository-ruleset.json");

        final GitHubPage<GitHubRepositoryRuleset> first;
        final Optional<GitHubPage<GitHubRepositoryRuleset>> second;
        final Optional<GitHubPage<GitHubRepositoryRuleset>> third;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            first = organizations.api().rules().getRulesets(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedRulesets());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedRulesets());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_RULESETS_PATH, 2);
    }

    @Test
    void getRulesets_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                ORG_RULESETS_PATH,
                okJson(listJsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json")));

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .rules()
                    .getRulesets(ORG, GitHubListOrgRulesetsFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, ORG_RULESETS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, ORG_RULESETS_PATH, "per_page", "10");
    }

    @Test
    void createRuleset_shouldReturnRuleset_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_RULESETS_PATH,
                createdJson(jsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json")));
        final GitHubCreateOrgRulesetPayload payload = OrganizationsCatalogFixtures.createOrgRulesetPayload();

        final GitHubRepositoryRuleset ruleset;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            ruleset = organizations.api().rules().createRuleset(ORG, payload);
        }

        assertThat(ruleset).isEqualTo(expectedRuleset());
        HTTP.verifyRequested(POST, ORG_RULESETS_PATH);
        HTTP.verifyHeader(POST, ORG_RULESETS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_RULESETS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_RULESET_PATH, okJson(jsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json")));

        final Optional<GitHubRepositoryRuleset> ruleset;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            ruleset = organizations.api().rules().getRuleset(ORG, RULESET_ID);
        }

        assertThat(ruleset).contains(expectedRuleset());
        HTTP.verifyRequested(GET, ORG_RULESET_PATH);
    }

    @Test
    void getRuleset_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_RULESET_PATH, notFound());

        final Optional<GitHubRepositoryRuleset> ruleset;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            ruleset = organizations.api().rules().getRuleset(ORG, RULESET_ID);
        }

        assertThat(ruleset).isEmpty();
        HTTP.verifyRequested(GET, ORG_RULESET_PATH);
    }

    @Test
    void updateRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, ORG_RULESET_PATH, okJson(jsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json")));
        final GitHubUpdateOrgRulesetPayload payload = OrganizationsCatalogFixtures.updateOrgRulesetPayload();

        final GitHubRepositoryRuleset ruleset;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            ruleset = organizations.api().rules().updateRuleset(ORG, RULESET_ID, payload);
        }

        assertThat(ruleset).isEqualTo(expectedRuleset());
        HTTP.verifyRequested(PUT, ORG_RULESET_PATH);
        HTTP.verifyHeader(PUT, ORG_RULESET_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ORG_RULESET_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateRuleset_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(PUT, ORG_RULESET_PATH, okJson(jsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json")));

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().rules().updateRuleset(ORG, RULESET_ID, null);
        }

        HTTP.verifyRequested(PUT, ORG_RULESET_PATH);
    }

    @Test
    void deleteRuleset_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_RULESET_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().rules().deleteRuleset(ORG, RULESET_ID);
        }

        HTTP.verifyRequested(DELETE, ORG_RULESET_PATH);
    }

    @Test
    void getRulesetHistory_shouldReturnVersions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_RULESET_HISTORY_PATH,
                okJson(listJsonBytes(GitHubRulesetVersion.class, "ruleset-version.json")));

        final GitHubPage<GitHubRulesetVersion> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().rules().getRulesetHistory(ORG, RULESET_ID);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRulesetVersions());
        HTTP.verifyRequested(GET, ORG_RULESET_HISTORY_PATH);
        HTTP.verifyQueryParam(GET, ORG_RULESET_HISTORY_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_RULESET_HISTORY_PATH, "per_page", "100");
    }

    @Test
    void getRulesetHistory_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(ORG_RULESET_HISTORY_PATH, GitHubRulesetVersion.class, "ruleset-version.json");

        final GitHubPage<GitHubRulesetVersion> first;
        final Optional<GitHubPage<GitHubRulesetVersion>> second;
        final Optional<GitHubPage<GitHubRulesetVersion>> third;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            first = organizations.api().rules().getRulesetHistory(ORG, RULESET_ID);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedRulesetVersions());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedRulesetVersions());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_RULESET_HISTORY_PATH, 2);
    }

    @Test
    void getRulesetHistory_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                ORG_RULESET_HISTORY_PATH,
                okJson(listJsonBytes(GitHubRulesetVersion.class, "ruleset-version.json")));

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().rules().getRulesetHistory(ORG, RULESET_ID, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, ORG_RULESET_HISTORY_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, ORG_RULESET_HISTORY_PATH, "per_page", "10");
    }

    @Test
    void getRulesetVersion_shouldReturnVersion_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_RULESET_VERSION_PATH,
                okJson(jsonBytes(GitHubRulesetVersionWithState.class, "ruleset-version-with-state.json")));

        final Optional<GitHubRulesetVersionWithState> version;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            version = organizations.api().rules().getRulesetVersion(ORG, RULESET_ID, VERSION_ID);
        }

        assertThat(version).contains(expectedRulesetVersionWithState());
        HTTP.verifyRequested(GET, ORG_RULESET_VERSION_PATH);
    }

    @Test
    void getRulesetVersion_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_RULESET_VERSION_PATH, notFound());

        final Optional<GitHubRulesetVersionWithState> version;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            version = organizations.api().rules().getRulesetVersion(ORG, RULESET_ID, VERSION_ID);
        }

        assertThat(version).isEmpty();
        HTTP.verifyRequested(GET, ORG_RULESET_VERSION_PATH);
    }

    private void stubPagedList(final String path, final Class<?> anchor, final String fixture) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(anchor, fixture))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(anchor, fixture)));
    }

    private static List<GitHubRepositoryRuleset> expectedRulesets() {
        return CODEC.decode(listJsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json"), RULESETS_TYPE);
    }

    private static GitHubRepositoryRuleset expectedRuleset() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryRuleset.class, "repository-ruleset.json"), GitHubRepositoryRuleset.class);
    }

    private static List<GitHubRulesetVersion> expectedRulesetVersions() {
        return CODEC.decode(listJsonBytes(GitHubRulesetVersion.class, "ruleset-version.json"), RULESET_VERSIONS_TYPE);
    }

    private static GitHubRulesetVersionWithState expectedRulesetVersionWithState() {
        return CODEC.decode(
                jsonBytes(GitHubRulesetVersionWithState.class, "ruleset-version-with-state.json"),
                GitHubRulesetVersionWithState.class);
    }
}
