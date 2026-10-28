package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.deployments.DeploymentsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubDeployment;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsWorkflowRunsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String WORKFLOW = "ci.yml";
    private static final URI LOGS_LOCATION = URI.create("https://example.com/logs.zip");

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsWorkflowRunsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void deleteWorkflowRun_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteWorkflowRun(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/repos/octocat/Hello-World/actions/runs/1");
    }

    @Test
    void getWorkflowRun_shouldReturnRun_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-run.json")));

        final GitHubWorkflowRun run = api().getWorkflowRun(OWNER, REPO, 1L).orElseThrow();

        assertThat(run).isEqualTo(ActionsCatalogFixtures.workflowRun());
    }

    @Test
    void getWorkflowRun_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubWorkflowRun> result = api().getWorkflowRun(OWNER, REPO, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void getWorkflowRun_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-run.json")));

        api().getWorkflowRun(
                        OWNER,
                        REPO,
                        1L,
                        GitHubGetWorkflowRunFilterBuilder.builder()
                                .excludePullRequests(true)
                                .build());

        assertThat(captureSingleRequest().uri()).hasParameter("exclude_pull_requests", "true");
    }

    @Test
    void getReviewsForRun_shouldReturnApprovals_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("environment-approvals.json") + "]"));

        final List<GitHubEnvironmentApprovals> approvals = api().getReviewsForRun(OWNER, REPO, 1L);

        assertThat(approvals).containsExactly(ActionsCatalogFixtures.environmentApprovals());
    }

    @Test
    void approveWorkflowRun_shouldSendPost_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().approveWorkflowRun(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/approve");
    }

    @Test
    void getWorkflowRunAttempt_shouldReturnAttempt_whenFilterOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-run.json")));

        final GitHubWorkflowRun attempt =
                api().getWorkflowRunAttempt(OWNER, REPO, 1L, 2L).orElseThrow();

        assertThat(attempt).isEqualTo(ActionsCatalogFixtures.workflowRun());
    }

    @Test
    void getWorkflowRunAttempt_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubWorkflowRun> result = api().getWorkflowRunAttempt(OWNER, REPO, 1L, 2L);

        assertThat(result).isEmpty();
    }

    @Test
    void getWorkflowRunAttempt_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-run.json")));

        api().getWorkflowRunAttempt(
                        OWNER,
                        REPO,
                        1L,
                        2L,
                        GitHubGetWorkflowRunAttemptFilterBuilder.builder()
                                .excludePullRequests(true)
                                .build());

        assertThat(captureSingleRequest().uri()).hasParameter("exclude_pull_requests", "true");
    }

    @Test
    void downloadWorkflowRunAttemptLogs_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any())).thenReturn(redirectResponse(HttpStatus.FOUND.code(), LOGS_LOCATION.toString()));

        final URI location = api().downloadWorkflowRunAttemptLogs(OWNER, REPO, 1L, 2L);

        assertThat(location).isEqualTo(LOGS_LOCATION);
    }

    @Test
    void cancelWorkflowRun_shouldSendPost_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().cancelWorkflowRun(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/cancel");
    }

    @Test
    void reviewCustomGatesForRun_shouldSendPost_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().reviewCustomGatesForRun(OWNER, REPO, 1L, ActionsCatalogFixtures.reviewCustomGatesForRunPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo(
                        "POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/deployment_protection_rule");
    }

    @Test
    void forceCancelWorkflowRun_shouldSendPost_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().forceCancelWorkflowRun(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/force-cancel");
    }

    @Test
    void downloadWorkflowRunLogs_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any())).thenReturn(redirectResponse(HttpStatus.FOUND.code(), LOGS_LOCATION.toString()));

        final URI location = api().downloadWorkflowRunLogs(OWNER, REPO, 1L);

        assertThat(location).isEqualTo(LOGS_LOCATION);
    }

    @Test
    void deleteWorkflowRunLogs_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteWorkflowRunLogs(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/repos/octocat/Hello-World/actions/runs/1/logs");
    }

    @Test
    void getPendingDeploymentsForRun_shouldReturnDeployments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("pending-deployment.json") + "]"));

        final List<GitHubPendingDeployment> deployments = api().getPendingDeploymentsForRun(OWNER, REPO, 1L);

        assertThat(deployments).containsExactly(ActionsCatalogFixtures.pendingDeployment());
    }

    @Test
    void reviewPendingDeploymentsForRun_shouldReturnDeployments_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("deployment.json") + "]"));

        final List<GitHubDeployment> deployments = api().reviewPendingDeploymentsForRun(
                        OWNER, REPO, 1L, ActionsCatalogFixtures.reviewPendingDeploymentsForRunPayload());

        assertThat(deployments).containsExactly(DeploymentsCatalogFixtures.deployment());
    }

    @Test
    void reRunWorkflow_shouldSendPost_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().reRunWorkflow(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/rerun");
    }

    @Test
    void reRunWorkflow_shouldSendPayload_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().reRunWorkflow(OWNER, REPO, 1L, ActionsCatalogFixtures.reRunWorkflowPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/rerun");
    }

    @Test
    void reRunWorkflowFailedJobs_shouldSendPost_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().reRunWorkflowFailedJobs(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/rerun-failed-jobs");
    }

    @Test
    void reRunWorkflowFailedJobs_shouldSendPayload_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().reRunWorkflowFailedJobs(OWNER, REPO, 1L, ActionsCatalogFixtures.reRunWorkflowFailedJobsPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/runs/1/rerun-failed-jobs");
    }

    @Test
    void getWorkflowRunUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-run-usage.json")));

        final GitHubWorkflowRunUsage usage =
                api().getWorkflowRunUsage(OWNER, REPO, 1L).orElseThrow();

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.workflowRunUsage());
    }

    @Test
    void getWorkflowRunUsage_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubWorkflowRunUsage> result = api().getWorkflowRunUsage(OWNER, REPO, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void listWorkflowRunsForRepo_shouldReturnRuns_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-runs.json")));

        final GitHubPage<GitHubWorkflowRun> page = api().listWorkflowRunsForRepo(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.workflowRuns().workflowRuns());
    }

    @Test
    void listWorkflowRunsForRepo_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-runs.json")));

        api().listWorkflowRunsForRepo(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listWorkflowRunsForRepo_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-runs.json")));

        api().listWorkflowRunsForRepo(
                        OWNER,
                        REPO,
                        GitHubListWorkflowRunsForRepoFilterBuilder.builder()
                                .branch("main")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("branch", "main");
    }

    @Test
    void reRunJobForWorkflowRun_shouldSendPost_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().reRunJobForWorkflowRun(OWNER, REPO, 3L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/jobs/3/rerun");
    }

    @Test
    void reRunJobForWorkflowRun_shouldSendPayload_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().reRunJobForWorkflowRun(OWNER, REPO, 3L, ActionsCatalogFixtures.reRunJobForWorkflowRunPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/repos/octocat/Hello-World/actions/jobs/3/rerun");
    }

    @Test
    void listWorkflowRuns_shouldReturnRuns_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-runs.json")));

        final GitHubPage<GitHubWorkflowRun> page = api().listWorkflowRuns(OWNER, REPO, WORKFLOW);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.workflowRuns().workflowRuns());
    }

    @Test
    void listWorkflowRuns_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-runs.json")));

        api().listWorkflowRuns(OWNER, REPO, WORKFLOW, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listWorkflowRuns_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-runs.json")));

        api().listWorkflowRuns(
                        OWNER,
                        REPO,
                        WORKFLOW,
                        GitHubListWorkflowRunsFilterBuilder.builder()
                                .branch("main")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("branch", "main");
    }

    private GitHubActionsWorkflowRunsApi api() {
        return new GitHubActionsWorkflowRunsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsWorkflowRunsApiTest.class, fileName);
    }

    private static HttpResponse redirectResponse(final int statusCode, final String location) {
        return new HttpResponse(statusCode, HttpHeaders.of(Map.of(LOCATION, location)), HttpBody.absent());
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
