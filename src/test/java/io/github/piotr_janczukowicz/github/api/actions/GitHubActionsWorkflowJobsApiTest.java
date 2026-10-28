package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsWorkflowJobsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final URI LOGS_LOCATION = URI.create("https://example.com/logs.zip");

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsWorkflowJobsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getJobForWorkflowRun_shouldReturnJob_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("job.json")));

        final GitHubJob job = api().getJobForWorkflowRun(OWNER, REPO, 1L).orElseThrow();

        assertThat(job).isEqualTo(ActionsCatalogFixtures.job());
    }

    @Test
    void getJobForWorkflowRun_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubJob> result = api().getJobForWorkflowRun(OWNER, REPO, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void downloadJobLogsForWorkflowRun_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any())).thenReturn(redirectResponse(HttpStatus.FOUND.code(), LOGS_LOCATION.toString()));

        final URI location = api().downloadJobLogsForWorkflowRun(OWNER, REPO, 1L);

        assertThat(location).isEqualTo(LOGS_LOCATION);
    }

    @Test
    void downloadStepLogsForWorkflowRunJob_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any())).thenReturn(redirectResponse(HttpStatus.FOUND.code(), LOGS_LOCATION.toString()));

        final URI location = api().downloadStepLogsForWorkflowRunJob(OWNER, REPO, 1L, 2);

        assertThat(location).isEqualTo(LOGS_LOCATION);
    }

    @Test
    void downloadStepLogsForWorkflowRunJob_shouldRequestStepPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(redirectResponse(HttpStatus.FOUND.code(), LOGS_LOCATION.toString()));

        api().downloadStepLogsForWorkflowRunJob(OWNER, REPO, 1L, 2);

        assertThat(captureSingleRequest().uri().getPath())
                .isEqualTo("/repos/octocat/Hello-World/actions/jobs/1/steps/2/logs");
    }

    @Test
    void downloadStepLogsForWorkflowRunJob_shouldThrow_whenStepNumberNegative() {
        final GitHubActionsWorkflowJobsApi sut = api();

        final Throwable thrown = catchThrowable(() -> sut.downloadStepLogsForWorkflowRunJob(OWNER, REPO, 1L, -1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("stepNumber");
    }

    @Test
    void listJobsForWorkflowRunAttempt_shouldReturnJobs_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("jobs.json")));

        final GitHubPage<GitHubJob> page = api().listJobsForWorkflowRunAttempt(OWNER, REPO, 1L, 2L);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.jobs().jobs());
    }

    @Test
    void listJobsForWorkflowRunAttempt_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("jobs.json")));

        api().listJobsForWorkflowRunAttempt(OWNER, REPO, 1L, 2L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listJobsForWorkflowRun_shouldReturnJobs_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("jobs.json")));

        final GitHubPage<GitHubJob> page = api().listJobsForWorkflowRun(OWNER, REPO, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.jobs().jobs());
    }

    @Test
    void listJobsForWorkflowRun_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("jobs.json")));

        api().listJobsForWorkflowRun(OWNER, REPO, 1L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listJobsForWorkflowRun_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("jobs.json")));

        api().listJobsForWorkflowRun(
                        OWNER,
                        REPO,
                        1L,
                        GitHubListJobsForWorkflowRunFilterBuilder.builder()
                                .filter(GitHubJobFilterMode.LATEST)
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("filter", "latest");
    }

    private GitHubActionsWorkflowJobsApi api() {
        return new GitHubActionsWorkflowJobsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsWorkflowJobsApiTest.class, fileName);
    }

    private static HttpResponse redirectResponse(final int statusCode, final String location) {
        return new HttpResponse(statusCode, HttpHeaders.of(Map.of(LOCATION, location)), HttpBody.absent());
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
