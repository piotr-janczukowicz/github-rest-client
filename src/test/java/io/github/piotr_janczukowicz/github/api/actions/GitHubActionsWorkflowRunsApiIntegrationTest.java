package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsWorkflowRunsApiIntegrationTest {

    private static final String RUN_PATH = "/repos/octocat/Hello-World/actions/runs/1";
    private static final String RUN_LOGS_PATH = "/repos/octocat/Hello-World/actions/runs/1/logs";
    private static final String RUNS_PATH = "/repos/octocat/Hello-World/actions/runs";
    private static final String RERUN_PATH = "/repos/octocat/Hello-World/actions/runs/1/rerun";
    private static final URI LOGS_LOCATION = URI.create("https://example.com/run-logs.zip");

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getWorkflowRun_shouldReturnRun_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RUN_PATH,
                okJson(jsonBytes(GitHubActionsWorkflowRunsApiIntegrationTest.class, "workflow-run.json")));

        final GitHubWorkflowRun run;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            run = actions.api()
                    .workflowRuns()
                    .getWorkflowRun("octocat", "Hello-World", 1L)
                    .orElseThrow();
        }

        assertThat(run).isEqualTo(ActionsCatalogFixtures.workflowRun());
        HTTP.verifyRequested(GET, RUN_PATH);
    }

    @Test
    void getReviewsForRun_shouldReturnApprovals_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RUN_PATH + "/approvals",
                okJson(listJsonBytes(GitHubActionsWorkflowRunsApiIntegrationTest.class, "environment-approvals.json")));

        final List<GitHubEnvironmentApprovals> approvals;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            approvals = actions.api().workflowRuns().getReviewsForRun("octocat", "Hello-World", 1L);
        }

        assertThat(approvals).containsExactly(ActionsCatalogFixtures.environmentApprovals());
    }

    @Test
    void downloadWorkflowRunLogs_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(RUN_LOGS_PATH, LOGS_LOCATION.toString(), HttpStatus.FOUND.code());

        final URI location;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            location = actions.api().workflowRuns().downloadWorkflowRunLogs("octocat", "Hello-World", 1L);
        }

        assertThat(location).isEqualTo(LOGS_LOCATION);
    }

    @Test
    void deleteWorkflowRunLogs_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, RUN_LOGS_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().workflowRuns().deleteWorkflowRunLogs("octocat", "Hello-World", 1L);
        }

        HTTP.verifyRequested(DELETE, RUN_LOGS_PATH);
    }

    @Test
    void reRunWorkflow_shouldSendPost_whenPayloadOmitted() throws Exception {
        HTTP.stub(POST, RERUN_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().workflowRuns().reRunWorkflow("octocat", "Hello-World", 1L);
        }

        HTTP.verifyRequested(POST, RERUN_PATH);
    }

    @Test
    void getWorkflowRunUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RUN_PATH + "/timing",
                okJson(jsonBytes(GitHubActionsWorkflowRunsApiIntegrationTest.class, "workflow-run-usage.json")));

        final GitHubWorkflowRunUsage usage;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            usage = actions.api()
                    .workflowRuns()
                    .getWorkflowRunUsage("octocat", "Hello-World", 1L)
                    .orElseThrow();
        }

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.workflowRunUsage());
    }

    @Test
    void listWorkflowRunsForRepo_shouldReturnRuns_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RUNS_PATH,
                okJson(jsonBytes(GitHubActionsWorkflowRunsApiIntegrationTest.class, "workflow-runs.json")));

        final GitHubPage<GitHubWorkflowRun> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().workflowRuns().listWorkflowRunsForRepo("octocat", "Hello-World");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.workflowRuns().workflowRuns());
        HTTP.verifyQueryParam(GET, RUNS_PATH, "page", "1");
    }
}
