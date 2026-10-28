package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsWorkflowsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String WORKFLOW = "ci.yml";
    private static final String BASE = "https://api.github.com/repos/octocat/Hello-World/actions/workflows/ci.yml";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsWorkflowsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listRepoWorkflows_shouldReturnWorkflows_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflows.json")));

        final GitHubPage<GitHubWorkflow> page = api().listRepoWorkflows(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.workflows().workflows());
    }

    @Test
    void listRepoWorkflows_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflows.json")));

        api().listRepoWorkflows(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void getWorkflow_shouldReturnWorkflow_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow.json")));

        final GitHubWorkflow workflow = api().getWorkflow(OWNER, REPO, WORKFLOW).orElseThrow();

        assertThat(workflow).isEqualTo(ActionsCatalogFixtures.workflow());
    }

    @Test
    void getWorkflow_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubWorkflow> result = api().getWorkflow(OWNER, REPO, WORKFLOW);

        assertThat(result).isEmpty();
    }

    @Test
    void disableWorkflow_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableWorkflow(OWNER, REPO, WORKFLOW);

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "/disable");
    }

    @Test
    void createWorkflowDispatch_shouldReturnResponse_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("workflow-dispatch-response.json")));

        final GitHubWorkflowDispatchResponse response = api().createWorkflowDispatch(
                        OWNER, REPO, WORKFLOW, ActionsCatalogFixtures.createWorkflowDispatchPayload());

        assertThat(response).isEqualTo(ActionsCatalogFixtures.workflowDispatchResponse());
    }

    @Test
    void enableWorkflow_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableWorkflow(OWNER, REPO, WORKFLOW);

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "/enable");
    }

    @Test
    void getWorkflowUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("workflow-usage.json")));

        final GitHubWorkflowUsage usage =
                api().getWorkflowUsage(OWNER, REPO, WORKFLOW).orElseThrow();

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.workflowUsage());
    }

    @Test
    void getWorkflowUsage_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubWorkflowUsage> result = api().getWorkflowUsage(OWNER, REPO, WORKFLOW);

        assertThat(result).isEmpty();
    }

    private GitHubActionsWorkflowsApi api() {
        return new GitHubActionsWorkflowsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsWorkflowsApiTest.class, fileName);
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
