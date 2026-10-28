package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsWorkflowsApiIntegrationTest {

    private static final String WORKFLOWS_PATH = "/repos/octocat/Hello-World/actions/workflows";
    private static final String WORKFLOW_PATH = "/repos/octocat/Hello-World/actions/workflows/ci.yml";
    private static final String DISPATCHES_PATH = "/repos/octocat/Hello-World/actions/workflows/ci.yml/dispatches";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listRepoWorkflows_shouldReturnWorkflows_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                WORKFLOWS_PATH,
                okJson(jsonBytes(GitHubActionsWorkflowsApiIntegrationTest.class, "workflows.json")));

        final GitHubPage<GitHubWorkflow> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().workflows().listRepoWorkflows("octocat", "Hello-World");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.workflows().workflows());
        HTTP.verifyQueryParam(GET, WORKFLOWS_PATH, "page", "1");
    }

    @Test
    void getWorkflow_shouldReturnWorkflow_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, WORKFLOW_PATH, okJson(jsonBytes(GitHubActionsWorkflowsApiIntegrationTest.class, "workflow.json")));

        final GitHubWorkflow workflow;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            workflow = actions.api()
                    .workflows()
                    .getWorkflow("octocat", "Hello-World", "ci.yml")
                    .orElseThrow();
        }

        assertThat(workflow).isEqualTo(ActionsCatalogFixtures.workflow());
    }

    @Test
    void createWorkflowDispatch_shouldReturnResponse_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                DISPATCHES_PATH,
                okJson(jsonBytes(GitHubActionsWorkflowsApiIntegrationTest.class, "workflow-dispatch-response.json")));

        final GitHubWorkflowDispatchResponse response;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            response = actions.api()
                    .workflows()
                    .createWorkflowDispatch(
                            "octocat", "Hello-World", "ci.yml", ActionsCatalogFixtures.createWorkflowDispatchPayload());
        }

        assertThat(response).isEqualTo(ActionsCatalogFixtures.workflowDispatchResponse());
        HTTP.verifyRequested(POST, DISPATCHES_PATH);
    }

    @Test
    void enableWorkflow_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, WORKFLOW_PATH + "/enable", noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().workflows().enableWorkflow("octocat", "Hello-World", "ci.yml");
        }

        HTTP.verifyRequested(PUT, WORKFLOW_PATH + "/enable");
    }

    @Test
    void disableWorkflow_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, WORKFLOW_PATH + "/disable", noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().workflows().disableWorkflow("octocat", "Hello-World", "ci.yml");
        }

        HTTP.verifyRequested(PUT, WORKFLOW_PATH + "/disable");
    }

    @Test
    void getWorkflowUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                WORKFLOW_PATH + "/timing",
                okJson(jsonBytes(GitHubActionsWorkflowsApiIntegrationTest.class, "workflow-usage.json")));

        final GitHubWorkflowUsage usage;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            usage = actions.api()
                    .workflows()
                    .getWorkflowUsage("octocat", "Hello-World", "ci.yml")
                    .orElseThrow();
        }

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.workflowUsage());
    }
}
