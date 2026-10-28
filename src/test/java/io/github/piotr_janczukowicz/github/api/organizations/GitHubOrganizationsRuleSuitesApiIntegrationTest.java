package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuite;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteListItem;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsRuleSuitesApiIntegrationTest {

    private static final String ORG = "my-org";
    private static final long RULE_SUITE_ID = 1L;

    private static final String ORG_RULE_SUITES_PATH = "/orgs/my-org/rulesets/rule-suites";
    private static final String ORG_RULE_SUITE_PATH = "/orgs/my-org/rulesets/rule-suites/1";

    private static final JsonType<List<GitHubRuleSuiteListItem>> RULE_SUITES_TYPE =
            JsonTypes.listOf(GitHubRuleSuiteListItem.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnSuites_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_RULE_SUITES_PATH, okJson(listJsonBytes(GitHubRuleSuite.class, "rule-suite.json")));

        final GitHubPage<GitHubRuleSuiteListItem> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().ruleSuites().list(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRuleSuites());
        HTTP.verifyRequested(GET, ORG_RULE_SUITES_PATH);
        HTTP.verifyQueryParam(GET, ORG_RULE_SUITES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_RULE_SUITES_PATH, "per_page", "100");
    }

    @Test
    void list_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(GET, ORG_RULE_SUITES_PATH, okJson(listJsonBytes(GitHubRuleSuite.class, "rule-suite.json")));

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .ruleSuites()
                    .list(
                            ORG,
                            GitHubListOrgRuleSuitesFilterBuilder.builder()
                                    .ref("refs/heads/main")
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, ORG_RULE_SUITES_PATH, "ref", "refs/heads/main");
        HTTP.verifyQueryParam(GET, ORG_RULE_SUITES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_RULE_SUITES_PATH, "per_page", "100");
    }

    @Test
    void list_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(ORG_RULE_SUITES_PATH);

        final GitHubPage<GitHubRuleSuiteListItem> first;
        final Optional<GitHubPage<GitHubRuleSuiteListItem>> second;
        final Optional<GitHubPage<GitHubRuleSuiteListItem>> third;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            first = organizations.api().ruleSuites().list(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedRuleSuites());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedRuleSuites());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_RULE_SUITES_PATH, 2);
    }

    @Test
    void list_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, ORG_RULE_SUITES_PATH, okJson(listJsonBytes(GitHubRuleSuite.class, "rule-suite.json")));

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .ruleSuites()
                    .list(ORG, GitHubListOrgRuleSuitesFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, ORG_RULE_SUITES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, ORG_RULE_SUITES_PATH, "per_page", "10");
    }

    @Test
    void get_shouldReturnSuite_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_RULE_SUITE_PATH, okJson(jsonBytes(GitHubRuleSuite.class, "rule-suite.json")));

        final Optional<GitHubRuleSuite> suite;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            suite = organizations.api().ruleSuites().get(ORG, RULE_SUITE_ID);
        }

        assertThat(suite).contains(expectedRuleSuite());
        HTTP.verifyRequested(GET, ORG_RULE_SUITE_PATH);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_RULE_SUITE_PATH, notFound());

        final Optional<GitHubRuleSuite> suite;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            suite = organizations.api().ruleSuites().get(ORG, RULE_SUITE_ID);
        }

        assertThat(suite).isEmpty();
        HTTP.verifyRequested(GET, ORG_RULE_SUITE_PATH);
    }

    private void stubPagedList(final String path) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubRuleSuite.class, "rule-suite.json"))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRuleSuite.class, "rule-suite.json")));
    }

    private static List<GitHubRuleSuiteListItem> expectedRuleSuites() {
        return CODEC.decode(listJsonBytes(GitHubRuleSuite.class, "rule-suite.json"), RULE_SUITES_TYPE);
    }

    private static GitHubRuleSuite expectedRuleSuite() {
        return CODEC.decode(jsonBytes(GitHubRuleSuite.class, "rule-suite.json"), GitHubRuleSuite.class);
    }
}
