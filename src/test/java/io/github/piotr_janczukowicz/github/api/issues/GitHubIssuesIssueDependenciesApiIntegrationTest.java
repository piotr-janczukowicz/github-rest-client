package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssuesIssueDependenciesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;
    private static final String BLOCKED_BY_PATH = "/repos/octocat/Hello-World/issues/1309/dependencies/blocked_by";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listDependenciesBlockedBy_shouldReturnIssues_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BLOCKED_BY_PATH, okJson(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .issueDependencies()
                            .listDependenciesBlockedBy(OWNER, REPO, ISSUE_NUMBER)
                            .items())
                    .hasSize(1);
        }
    }

    @Test
    void addBlockedByDependency_shouldReturnIssue_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, BLOCKED_BY_PATH, createdJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .issueDependencies()
                            .addBlockedByDependency(
                                    OWNER, REPO, ISSUE_NUMBER, IssuesCatalogFixtures.addBlockedByDependencyPayload())
                            .number())
                    .isEqualTo(ISSUE_NUMBER);
        }
    }
}
