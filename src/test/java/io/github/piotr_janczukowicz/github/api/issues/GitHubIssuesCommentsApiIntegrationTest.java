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
class GitHubIssuesCommentsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;
    private static final long COMMENT_ID = 1L;
    private static final String COMMENT_PATH = "/repos/octocat/Hello-World/issues/comments/1";
    private static final String ISSUE_COMMENTS_PATH = "/repos/octocat/Hello-World/issues/1309/comments";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMMENT_PATH,
                okJson(jsonBytes(GitHubIssuesCommentsApiIntegrationTest.class, "issue-comment.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().comments().getComment(OWNER, REPO, COMMENT_ID))
                    .isPresent();
        }
    }

    @Test
    void createComment_shouldReturnComment_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ISSUE_COMMENTS_PATH,
                createdJson(jsonBytes(GitHubIssuesCommentsApiIntegrationTest.class, "issue-comment.json")));
        final GitHubCreateCommentPayload payload = IssuesCatalogFixtures.createCommentPayload();

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .comments()
                            .createComment(OWNER, REPO, ISSUE_NUMBER, payload)
                            .body())
                    .isNotBlank();
        }

        HTTP.verifyRequested(POST, ISSUE_COMMENTS_PATH);
    }

    @Test
    void listComments_shouldReturnComments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ISSUE_COMMENTS_PATH,
                okJson(listJsonBytes(GitHubIssuesCommentsApiIntegrationTest.class, "issue-comment.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .comments()
                            .listComments(OWNER, REPO, ISSUE_NUMBER)
                            .items())
                    .hasSize(1);
        }
    }
}
