package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssuesSubIssuesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;
    private static final String PARENT_PATH = "/repos/octocat/Hello-World/issues/1309/parent";
    private static final String SUB_ISSUES_PATH = "/repos/octocat/Hello-World/issues/1309/sub_issues";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getParent_shouldReturnIssue_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PARENT_PATH, okJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().subIssues().getParent(OWNER, REPO, ISSUE_NUMBER))
                    .isPresent();
        }
    }

    @Test
    void addSubIssue_shouldReturnIssue_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, SUB_ISSUES_PATH, createdJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .subIssues()
                            .addSubIssue(OWNER, REPO, ISSUE_NUMBER, IssuesCatalogFixtures.addSubIssuePayload())
                            .number())
                    .isEqualTo(ISSUE_NUMBER);
        }
    }
}
