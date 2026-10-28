package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
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
class GitHubIssuesEventsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String EVENTS_PATH = "/repos/octocat/Hello-World/issues/events";
    private static final String EVENT_PATH = "/repos/octocat/Hello-World/issues/events/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listEventsForRepo_shouldReturnEvents_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                EVENTS_PATH,
                okJson(listJsonBytes(GitHubIssuesEventsApiIntegrationTest.class, "issue-event.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().events().listEventsForRepo(OWNER, REPO).items())
                    .hasSize(1);
        }
    }

    @Test
    void getEvent_shouldReturnEvent_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, EVENT_PATH, okJson(jsonBytes(GitHubIssuesEventsApiIntegrationTest.class, "issue-event.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().events().getEvent(OWNER, REPO, 1L)).isPresent();
        }
    }
}
