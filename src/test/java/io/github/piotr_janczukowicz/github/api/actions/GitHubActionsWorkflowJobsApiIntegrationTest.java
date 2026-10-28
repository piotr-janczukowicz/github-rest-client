package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpStatus.FOUND;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsWorkflowJobsApiIntegrationTest {

    private static final String JOB_PATH = "/repos/octocat/Hello-World/actions/jobs/3";
    private static final String JOB_LOGS_PATH = "/repos/octocat/Hello-World/actions/jobs/3/logs";
    private static final String JOB_STEP_LOGS_PATH = "/repos/octocat/Hello-World/actions/jobs/3/steps/1/logs";
    private static final String RUN_JOBS_PATH = "/repos/octocat/Hello-World/actions/runs/1/jobs";
    private static final URI LOGS_LOCATION = URI.create("https://example.com/job-logs.txt");

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getJobForWorkflowRun_shouldReturnJob_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, JOB_PATH, okJson(jsonBytes(GitHubActionsWorkflowJobsApiIntegrationTest.class, "job.json")));

        final GitHubJob job;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            job = actions.api()
                    .workflowJobs()
                    .getJobForWorkflowRun("octocat", "Hello-World", 3L)
                    .orElseThrow();
        }

        assertThat(job).isEqualTo(ActionsCatalogFixtures.job());
        HTTP.verifyRequested(GET, JOB_PATH);
    }

    @Test
    void downloadJobLogsForWorkflowRun_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(JOB_LOGS_PATH, LOGS_LOCATION.toString(), FOUND.code());

        final URI location;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            location = actions.api().workflowJobs().downloadJobLogsForWorkflowRun("octocat", "Hello-World", 3L);
        }

        assertThat(location).isEqualTo(LOGS_LOCATION);
    }

    @Test
    void downloadStepLogsForWorkflowRunJob_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(JOB_STEP_LOGS_PATH, LOGS_LOCATION.toString(), FOUND.code());

        final URI location;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            location = actions.api().workflowJobs().downloadStepLogsForWorkflowRunJob("octocat", "Hello-World", 3L, 1);
        }

        assertThat(location).isEqualTo(LOGS_LOCATION);
    }

    @Test
    void listJobsForWorkflowRun_shouldReturnJobs_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, RUN_JOBS_PATH, okJson(jsonBytes(GitHubActionsWorkflowJobsApiIntegrationTest.class, "jobs.json")));

        final GitHubPage<GitHubJob> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().workflowJobs().listJobsForWorkflowRun("octocat", "Hello-World", 1L);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.jobs().jobs());
        HTTP.verifyQueryParam(GET, RUN_JOBS_PATH, "page", "1");
    }
}
