package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubAgentAutomationApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String AUTOMATION_ID = "automation-1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAgentAutomationApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAutomations_shouldReturnAutomations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("list-automations-item.json") + "]"));

        final GitHubPage<GitHubListAutomationsItem> page = api().listAutomations();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(AgentAutomationsCatalogFixtures.listAutomationsItem());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/agents/automations?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listAutomations_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("list-automations-item.json") + "]"));

        api().listAutomations(AgentAutomationsCatalogFixtures.listAutomationsFilter(), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("owner_type=user");
    }

    @Test
    void listAutomations_shouldSendPageAndPerPage_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("list-automations-item.json") + "]"));

        api().listAutomations(GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/agents/automations?page=2&per_page=10");
    }

    @Test
    void listAvailableAutomationTools_shouldReturnTools_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("list-available-automation-tools-item.json") + "]"));

        final List<GitHubListAvailableAutomationToolsItem> tools = api().listAvailableAutomationTools();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(tools)
                    .containsExactly(AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItem());
            softly.assertThat(requestUri).hasToString("https://api.github.com/agents/automations/available-tools");
        });
    }

    @Test
    void listAvailableAutomationTriggers_shouldReturnTriggers_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json("list-available-automation-triggers-item.json") + "]"));

        final List<GitHubListAvailableAutomationTriggersItem> triggers = api().listAvailableAutomationTriggers();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(triggers)
                    .containsExactly(AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItem());
            softly.assertThat(requestUri).hasToString("https://api.github.com/agents/automations/available-triggers");
        });
    }

    @Test
    void getAutomation_shouldReturnAutomation_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("get-automation-response.json")));

        final Optional<GitHubGetAutomationResponse> automation = api().getAutomation(AUTOMATION_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(automation).contains(AgentAutomationsCatalogFixtures.getAutomationResponse());
            softly.assertThat(requestUri).hasToString("https://api.github.com/agents/automations/automation-1");
        });
    }

    @Test
    void getAutomation_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGetAutomationResponse> automation = api().getAutomation(AUTOMATION_ID);

        assertThat(automation).isEmpty();
    }

    @Test
    void getAutomation_shouldThrow_whenAutomationIdBlank() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getAutomation(" "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getRepoAutomation_shouldThrow_whenAutomationIdBlank() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getRepoAutomation(OWNER, REPO, " "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationId");
    }

    @Test
    void editRepoAutomation_shouldThrow_whenAutomationIdBlank() {
        final GitHubEditRepoAutomationPayload payload = AgentAutomationsCatalogFixtures.editRepoAutomationPayload();

        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.editRepoAutomation(OWNER, REPO, " ", payload));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationId");
    }

    @Test
    void listAutomations_shouldThrow_whenPerPageAboveMaximum() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listAutomations(GitHubPageNumberRequest.of(1, 101)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("100");
    }

    @Test
    void listAutomationTasks_shouldReturnTasks_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-automation-tasks-response.json")));

        final GitHubPage<GitHubListAutomationTasksTask> page = api().listAutomationTasks(AUTOMATION_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(AgentAutomationsCatalogFixtures.listAutomationTasksResponse()
                            .tasks());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/agents/automations/automation-1/tasks?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAutomationTasks_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-automation-tasks-response.json")));

        api().listAutomationTasks(
                        AUTOMATION_ID,
                        AgentAutomationsCatalogFixtures.listAutomationTasksFilter(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery())
                .contains("sort=updated_at")
                .contains("direction=asc")
                .contains("state=completed")
                .contains("is_archived=false");
    }

    @Test
    void listAutomationTasks_shouldRequestGivenPage_whenOnlyRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-automation-tasks-response.json")));

        api().listAutomationTasks(AUTOMATION_ID, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=2").contains("per_page=10");
    }

    @Test
    void listRepoAutomations_shouldRequestGivenPage_whenOnlyRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-repo-automations-response.json")));

        api().listRepoAutomations(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=2").contains("per_page=10");
    }

    @Test
    void listAutomationTasks_shouldThrow_whenAutomationIdBlank() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listAutomationTasks(" "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void listRepoAutomations_shouldReturnAutomations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-repo-automations-response.json")));

        final GitHubPage<GitHubListRepoAutomationsAutomation> page = api().listRepoAutomations(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(AgentAutomationsCatalogFixtures.listRepoAutomationsResponse()
                            .automations());
            softly.assertThat(page.totalCount()).isEqualTo(1);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/automations?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listRepoAutomations_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-repo-automations-response.json")));

        api().listRepoAutomations(
                        OWNER,
                        REPO,
                        AgentAutomationsCatalogFixtures.listRepoAutomationsFilter(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("owner_type=user");
    }

    @Test
    void listRepoAutomations_shouldThrow_whenOwnerBlank() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listRepoAutomations(" ", REPO));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createRepoAutomation_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("create-repo-automation-response.json")));

        final GitHubCreateRepoAutomationResponse automation =
                api().createRepoAutomation(OWNER, REPO, AgentAutomationsCatalogFixtures.createRepoAutomationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(automation).isEqualTo(AgentAutomationsCatalogFixtures.createRepoAutomationResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/automations");
        });
    }

    @Test
    void createRepoAutomation_shouldThrow_whenPayloadNull() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createRepoAutomation(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getRepoAutomation_shouldReturnAutomation_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("get-repo-automation-response.json")));

        final Optional<GitHubGetRepoAutomationResponse> automation =
                api().getRepoAutomation(OWNER, REPO, AUTOMATION_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(automation).contains(AgentAutomationsCatalogFixtures.getRepoAutomationResponse());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/automations/automation-1");
        });
    }

    @Test
    void getRepoAutomation_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGetRepoAutomationResponse> automation =
                api().getRepoAutomation(OWNER, REPO, AUTOMATION_ID);

        assertThat(automation).isEmpty();
    }

    @Test
    void editRepoAutomation_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("edit-repo-automation-response.json")));

        final GitHubEditRepoAutomationResponse automation = api().editRepoAutomation(
                        OWNER, REPO, AUTOMATION_ID, AgentAutomationsCatalogFixtures.editRepoAutomationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(automation).isEqualTo(AgentAutomationsCatalogFixtures.editRepoAutomationResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/automations/automation-1");
        });
    }

    @Test
    void editRepoAutomation_shouldThrow_whenPayloadNull() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.editRepoAutomation(OWNER, REPO, AUTOMATION_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteRepoAutomation_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoAutomation(OWNER, REPO, AUTOMATION_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/automations/automation-1");
        });
    }

    @Test
    void deleteRepoAutomation_shouldThrow_whenRepoBlank() {
        final GitHubAgentAutomationApi api = api();

        final Throwable thrown = catchThrowable(() -> api.deleteRepoAutomation(OWNER, " ", AUTOMATION_ID));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    private GitHubAgentAutomationApi api() {
        return new GitHubAgentAutomationApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubAgentAutomationApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
