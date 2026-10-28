package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposRuleSuitesApi;
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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRepositoriesRuleSuitesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long RULE_SUITE_ID = 1L;

    private static final String REPO_RULE_SUITES_PATH = "/repos/octocat/Hello-World/rulesets/rule-suites";
    private static final String REPO_RULE_SUITE_PATH = "/repos/octocat/Hello-World/rulesets/rule-suites/1";

    private static final JsonType<List<GitHubRuleSuiteListItem>> RULE_SUITES_TYPE =
            JsonTypes.listOf(GitHubRuleSuiteListItem.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRepoRuleSuites_shouldReturnSuites_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULE_SUITES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, "rule-suite.json")));

        final GitHubPage<GitHubRuleSuiteListItem> page;
        try (final GitHubApiWireMockSupport.ReposRuleSuitesApiClient api = reposRuleSuitesApi(HTTP)) {
            page = api.api().getRepoRuleSuites(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRuleSuites());
        HTTP.verifyRequested(GET, REPO_RULE_SUITES_PATH);
        HTTP.verifyQueryParam(GET, REPO_RULE_SUITES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_RULE_SUITES_PATH, "per_page", "100");
    }

    @Test
    void getRepoRuleSuites_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULE_SUITES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, "rule-suite.json")));

        try (final GitHubApiWireMockSupport.ReposRuleSuitesApiClient api = reposRuleSuitesApi(HTTP)) {
            api.api()
                    .getRepoRuleSuites(
                            OWNER,
                            REPO,
                            GitHubGetRepoRuleSuitesFilterBuilder.builder()
                                    .ref("refs/heads/main")
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, REPO_RULE_SUITES_PATH, "ref", "refs/heads/main");
        HTTP.verifyQueryParam(GET, REPO_RULE_SUITES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_RULE_SUITES_PATH, "per_page", "100");
    }

    @Test
    void getRepoRuleSuites_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(REPO_RULE_SUITES_PATH, "rule-suite.json");

        final GitHubPage<GitHubRuleSuiteListItem> first;
        final Optional<GitHubPage<GitHubRuleSuiteListItem>> second;
        final Optional<GitHubPage<GitHubRuleSuiteListItem>> third;
        try (final GitHubApiWireMockSupport.ReposRuleSuitesApiClient api = reposRuleSuitesApi(HTTP)) {
            first = api.api().getRepoRuleSuites(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedRuleSuites());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedRuleSuites());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, REPO_RULE_SUITES_PATH, 2);
    }

    @Test
    void getRepoRuleSuites_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULE_SUITES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, "rule-suite.json")));

        try (final GitHubApiWireMockSupport.ReposRuleSuitesApiClient api = reposRuleSuitesApi(HTTP)) {
            api.api()
                    .getRepoRuleSuites(
                            OWNER, REPO, GitHubGetRepoRuleSuitesFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, REPO_RULE_SUITES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, REPO_RULE_SUITES_PATH, "per_page", "10");
    }

    @Test
    void getRepoRuleSuite_shouldReturnSuite_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_RULE_SUITE_PATH,
                okJson(jsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, "rule-suite.json")));

        final Optional<GitHubRuleSuite> suite;
        try (final GitHubApiWireMockSupport.ReposRuleSuitesApiClient api = reposRuleSuitesApi(HTTP)) {
            suite = api.api().getRepoRuleSuite(OWNER, REPO, RULE_SUITE_ID);
        }

        assertThat(suite).contains(expectedRuleSuite());
        HTTP.verifyRequested(GET, REPO_RULE_SUITE_PATH);
    }

    @Test
    void getRepoRuleSuite_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_RULE_SUITE_PATH, notFound());

        final Optional<GitHubRuleSuite> suite;
        try (final GitHubApiWireMockSupport.ReposRuleSuitesApiClient api = reposRuleSuitesApi(HTTP)) {
            suite = api.api().getRepoRuleSuite(OWNER, REPO, RULE_SUITE_ID);
        }

        assertThat(suite).isEmpty();
        HTTP.verifyRequested(GET, REPO_RULE_SUITE_PATH);
    }

    private void stubPagedList(final String path, final String fixture) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, fixture))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, fixture)));
    }

    private static List<GitHubRuleSuiteListItem> expectedRuleSuites() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, "rule-suite.json"),
                RULE_SUITES_TYPE);
    }

    private static GitHubRuleSuite expectedRuleSuite() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoriesRuleSuitesApiIntegrationTest.class, "rule-suite.json"),
                GitHubRuleSuite.class);
    }
}
