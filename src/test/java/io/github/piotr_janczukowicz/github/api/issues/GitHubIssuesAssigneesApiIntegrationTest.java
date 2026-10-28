package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssuesAssigneesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ASSIGNEE = "hubot";
    private static final String ASSIGNEES_PATH = "/repos/octocat/Hello-World/assignees";
    private static final String CHECK_PATH = "/repos/octocat/Hello-World/assignees/hubot";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAssignees_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ASSIGNEES_PATH,
                okJson(listJsonBytes(GitHubIssuesAssigneesApiIntegrationTest.class, "simple-user-octocat.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().assignees().listAssignees(OWNER, REPO).items())
                    .hasSize(1);
        }

        HTTP.verifyRequested(GET, ASSIGNEES_PATH);
    }

    @Test
    void checkUserCanBeAssigned_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, CHECK_PATH, noContent());

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().assignees().checkUserCanBeAssigned(OWNER, REPO, ASSIGNEE))
                    .isTrue();
        }
    }

    @Test
    void checkUserCanBeAssigned_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, CHECK_PATH, notFound());

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().assignees().checkUserCanBeAssigned(OWNER, REPO, ASSIGNEE))
                    .isFalse();
        }
    }
}
