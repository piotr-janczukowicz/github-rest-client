package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubRepositoriesRuleSuitesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long RULE_SUITE_ID = 1L;
    private static final String REPO_RULE_SUITES_PATH = "/repos/octocat/Hello-World/rulesets/rule-suites";
    private static final String REPO_RULE_SUITE_PATH = "/repos/octocat/Hello-World/rulesets/rule-suites/1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesRuleSuitesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getRepoRuleSuites_shouldReturnSuites_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("git-hub-rule-suite-list-item.json") + "]"));

        final GitHubPage<GitHubRuleSuiteListItem> page = callApi(api -> api.getRepoRuleSuites(OWNER, REPO));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEqualTo(List.of(RepositoriesCatalogFixtures.ruleSuiteListItem()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com" + REPO_RULE_SUITES_PATH + "?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void getRepoRuleSuite_shouldReturnSuite_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("rule-suite.json")));

        final Optional<GitHubRuleSuite> suite = callApi(api -> api.getRepoRuleSuite(OWNER, REPO, RULE_SUITE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(suite).contains(RepositoriesCatalogFixtures.ruleSuite());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + REPO_RULE_SUITE_PATH);
        });
    }

    @Test
    void getRepoRuleSuite_shouldSendIncludeMetadataQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("rule-suite.json")));
        final GitHubGetRepoRuleSuiteFilter filter = RepositoriesCatalogFixtures.getRepoRuleSuiteFilter();

        callApi(api -> api.getRepoRuleSuite(OWNER, REPO, RULE_SUITE_ID, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + REPO_RULE_SUITE_PATH + "?include_metadata=true");
        });
    }

    @Test
    void getRepoRuleSuites_shouldSendFilterAndPageQuery_whenFilterAndRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("git-hub-rule-suite-list-item.json") + "]"));
        final GitHubGetRepoRuleSuitesFilter filter = GitHubGetRepoRuleSuitesFilterBuilder.builder()
                .ref("refs/heads/main")
                .build();

        callApi(api -> api.getRepoRuleSuites(OWNER, REPO, filter, GitHubPageNumberRequest.of(2, 10)));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + REPO_RULE_SUITES_PATH
                            + "?ref=refs%2Fheads%2Fmain&page=2&per_page=10");
            softly.assertThat(request.uri().getQuery()).doesNotContain("repository_name");
        });
    }

    @FunctionalInterface
    private interface RuleSuitesApiAction<T> {
        T apply(GitHubRepositoriesRuleSuitesApi api) throws Exception;
    }

    private <T> T callApi(final RuleSuitesApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubRepositoriesRuleSuitesApi(TestClients.caller(client)));
        }
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesRuleSuitesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
