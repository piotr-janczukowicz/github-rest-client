package io.github.piotr_janczukowicz.github.api.repositories;

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
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposRulesApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
class GitHubRepositoriesRulesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BRANCH = "main";
    private static final long RULESET_ID = 42L;
    private static final long VERSION_ID = 1L;

    private static final String BRANCH_RULES_PATH = "/repos/octocat/Hello-World/rules/branches/main";
    private static final String REPO_RULESETS_PATH = "/repos/octocat/Hello-World/rulesets";
    private static final String REPO_RULESET_PATH = "/repos/octocat/Hello-World/rulesets/42";
    private static final String REPO_RULESET_HISTORY_PATH = "/repos/octocat/Hello-World/rulesets/42/history";
    private static final String REPO_RULESET_VERSION_PATH = "/repos/octocat/Hello-World/rulesets/42/history/1";

    private static final JsonType<List<GitHubRepositoryRuleset>> RULESETS_TYPE =
            JsonTypes.listOf(GitHubRepositoryRuleset.class);
    private static final JsonType<List<GitHubRepositoryRuleDetailed>> RULES_DETAILED_TYPE =
            JsonTypes.listOf(GitHubRepositoryRuleDetailed.class);
    private static final JsonType<List<GitHubRulesetVersion>> RULESET_VERSIONS_TYPE =
            JsonTypes.listOf(GitHubRulesetVersion.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getBranchRules_shouldReturnRules_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                BRANCH_RULES_PATH,
                okJson(listJsonBytes(
                        GitHubRepositoriesRulesApiIntegrationTest.class, "repository-rule-detailed.json")));

        final GitHubPage<GitHubRepositoryRuleDetailed> page;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            page = api.api().getBranchRules(OWNER, REPO, BRANCH);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedBranchRules());
        HTTP.verifyRequested(GET, BRANCH_RULES_PATH);
        HTTP.verifyQueryParam(GET, BRANCH_RULES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, BRANCH_RULES_PATH, "per_page", "100");
    }

    @Test
    void getBranchRules_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(BRANCH_RULES_PATH, "repository-rule-detailed.json");

        final GitHubPage<GitHubRepositoryRuleDetailed> first;
        final Optional<GitHubPage<GitHubRepositoryRuleDetailed>> second;
        final Optional<GitHubPage<GitHubRepositoryRuleDetailed>> third;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            first = api.api().getBranchRules(OWNER, REPO, BRANCH);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedBranchRules());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedBranchRules());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, BRANCH_RULES_PATH, 2);
    }

    @Test
    void getBranchRules_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                BRANCH_RULES_PATH,
                okJson(listJsonBytes(
                        GitHubRepositoriesRulesApiIntegrationTest.class, "repository-rule-detailed.json")));

        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            api.api().getBranchRules(OWNER, REPO, BRANCH, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, BRANCH_RULES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, BRANCH_RULES_PATH, "per_page", "10");
    }

    @Test
    void getRepoRulesets_shouldReturnRulesets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESETS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));

        final GitHubPage<GitHubRepositoryRuleset> page;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            page = api.api().getRepoRulesets(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRulesets());
        HTTP.verifyRequested(GET, REPO_RULESETS_PATH);
        HTTP.verifyQueryParam(GET, REPO_RULESETS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_RULESETS_PATH, "per_page", "100");
    }

    @Test
    void getRepoRulesets_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESETS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));

        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            api.api()
                    .getRepoRulesets(
                            OWNER,
                            REPO,
                            GitHubGetRepoRulesetsFilterBuilder.builder()
                                    .includesParents(true)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, REPO_RULESETS_PATH, "includes_parents", "true");
        HTTP.verifyQueryParam(GET, REPO_RULESETS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_RULESETS_PATH, "per_page", "100");
    }

    @Test
    void getRepoRulesets_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(REPO_RULESETS_PATH, "repository-ruleset.json");

        final GitHubPage<GitHubRepositoryRuleset> first;
        final Optional<GitHubPage<GitHubRepositoryRuleset>> second;
        final Optional<GitHubPage<GitHubRepositoryRuleset>> third;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            first = api.api().getRepoRulesets(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedRulesets());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedRulesets());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, REPO_RULESETS_PATH, 2);
    }

    @Test
    void getRepoRulesets_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESETS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));

        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            api.api()
                    .getRepoRulesets(
                            OWNER, REPO, GitHubGetRepoRulesetsFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, REPO_RULESETS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, REPO_RULESETS_PATH, "per_page", "10");
    }

    @Test
    void createRepoRuleset_shouldReturnRuleset_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_RULESETS_PATH,
                createdJson(jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));
        final GitHubCreateRepoRulesetPayload payload = RepositoriesCatalogFixtures.createRepoRulesetPayload();

        final GitHubRepositoryRuleset ruleset;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            ruleset = api.api().createRepoRuleset(OWNER, REPO, payload);
        }

        assertThat(ruleset).isEqualTo(expectedRuleset());
        HTTP.verifyRequested(POST, REPO_RULESETS_PATH);
        HTTP.verifyHeader(POST, REPO_RULESETS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, REPO_RULESETS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getRepoRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESET_PATH,
                okJson(jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));

        final Optional<GitHubRepositoryRuleset> ruleset;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            ruleset = api.api().getRepoRuleset(OWNER, REPO, RULESET_ID);
        }

        assertThat(ruleset).contains(expectedRuleset());
        HTTP.verifyRequested(GET, REPO_RULESET_PATH);
    }

    @Test
    void getRepoRuleset_shouldSendIncludesParents_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESET_PATH,
                okJson(jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));

        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            api.api()
                    .getRepoRuleset(
                            OWNER,
                            REPO,
                            RULESET_ID,
                            GitHubGetRepoRulesetFilterBuilder.builder()
                                    .includesParents(false)
                                    .build());
        }

        HTTP.verifyQueryParam(GET, REPO_RULESET_PATH, "includes_parents", "false");
    }

    @Test
    void getRepoRuleset_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_RULESET_PATH, notFound());

        final Optional<GitHubRepositoryRuleset> ruleset;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            ruleset = api.api().getRepoRuleset(OWNER, REPO, RULESET_ID);
        }

        assertThat(ruleset).isEmpty();
        HTTP.verifyRequested(GET, REPO_RULESET_PATH);
    }

    @Test
    void updateRepoRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                REPO_RULESET_PATH,
                okJson(jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));
        final GitHubUpdateRepoRulesetPayload payload = RepositoriesCatalogFixtures.updateRepoRulesetPayload();

        final GitHubRepositoryRuleset ruleset;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            ruleset = api.api().updateRepoRuleset(OWNER, REPO, RULESET_ID, payload);
        }

        assertThat(ruleset).isEqualTo(expectedRuleset());
        HTTP.verifyRequested(PUT, REPO_RULESET_PATH);
        HTTP.verifyHeader(PUT, REPO_RULESET_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, REPO_RULESET_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateRepoRuleset_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(
                PUT,
                REPO_RULESET_PATH,
                okJson(jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json")));

        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            api.api().updateRepoRuleset(OWNER, REPO, RULESET_ID, null);
        }

        HTTP.verifyRequested(PUT, REPO_RULESET_PATH);
    }

    @Test
    void deleteRepoRuleset_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_RULESET_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            api.api().deleteRepoRuleset(OWNER, REPO, RULESET_ID);
        }

        HTTP.verifyRequested(DELETE, REPO_RULESET_PATH);
    }

    @Test
    void getRepoRulesetHistory_shouldReturnVersions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESET_HISTORY_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "ruleset-version.json")));

        final GitHubPage<GitHubRulesetVersion> page;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            page = api.api().getRepoRulesetHistory(OWNER, REPO, RULESET_ID);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRulesetVersions());
        HTTP.verifyRequested(GET, REPO_RULESET_HISTORY_PATH);
        HTTP.verifyQueryParam(GET, REPO_RULESET_HISTORY_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_RULESET_HISTORY_PATH, "per_page", "100");
    }

    @Test
    void getRepoRulesetHistory_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(REPO_RULESET_HISTORY_PATH, "ruleset-version.json");

        final GitHubPage<GitHubRulesetVersion> first;
        final Optional<GitHubPage<GitHubRulesetVersion>> second;
        final Optional<GitHubPage<GitHubRulesetVersion>> third;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            first = api.api().getRepoRulesetHistory(OWNER, REPO, RULESET_ID);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedRulesetVersions());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedRulesetVersions());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, REPO_RULESET_HISTORY_PATH, 2);
    }

    @Test
    void getRepoRulesetHistory_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESET_HISTORY_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "ruleset-version.json")));

        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            api.api().getRepoRulesetHistory(OWNER, REPO, RULESET_ID, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, REPO_RULESET_HISTORY_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, REPO_RULESET_HISTORY_PATH, "per_page", "10");
    }

    @Test
    void getRepoRulesetVersion_shouldReturnVersion_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULESET_VERSION_PATH,
                okJson(jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "ruleset-version-with-state.json")));

        final Optional<GitHubRulesetVersionWithState> version;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            version = api.api().getRepoRulesetVersion(OWNER, REPO, RULESET_ID, VERSION_ID);
        }

        assertThat(version).contains(expectedRulesetVersionWithState());
        HTTP.verifyRequested(GET, REPO_RULESET_VERSION_PATH);
    }

    @Test
    void getRepoRulesetVersion_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_RULESET_VERSION_PATH, notFound());

        final Optional<GitHubRulesetVersionWithState> version;
        try (final GitHubApiWireMockSupport.ReposRulesApiClient api = reposRulesApi(HTTP)) {
            version = api.api().getRepoRulesetVersion(OWNER, REPO, RULESET_ID, VERSION_ID);
        }

        assertThat(version).isEmpty();
        HTTP.verifyRequested(GET, REPO_RULESET_VERSION_PATH);
    }

    private void stubPagedList(final String path, final String fixture) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, fixture))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, fixture)));
    }

    private static List<GitHubRepositoryRuleset> expectedRulesets() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json"),
                RULESETS_TYPE);
    }

    private static GitHubRepositoryRuleset expectedRuleset() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-ruleset.json"),
                GitHubRepositoryRuleset.class);
    }

    private static List<GitHubRepositoryRuleDetailed> expectedBranchRules() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "repository-rule-detailed.json"),
                RULES_DETAILED_TYPE);
    }

    private static List<GitHubRulesetVersion> expectedRulesetVersions() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "ruleset-version.json"),
                RULESET_VERSIONS_TYPE);
    }

    private static GitHubRulesetVersionWithState expectedRulesetVersionWithState() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoriesRulesApiIntegrationTest.class, "ruleset-version-with-state.json"),
                GitHubRulesetVersionWithState.class);
    }
}
