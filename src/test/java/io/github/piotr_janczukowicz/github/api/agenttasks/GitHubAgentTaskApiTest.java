package io.github.piotr_janczukowicz.github.api.agenttasks;

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
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubAgentTaskApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String TASK_ID = "task-1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAgentTaskApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listTasksForRepo_shouldReturnTasks_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-tasks-for-repo-response.json")));

        final GitHubPage<GitHubListTasksForRepoTask> page = api().listTasksForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            AgentTasksCatalogFixtures.listTasksForRepoResponse().tasks());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(page.totalActiveCount()).isEqualTo(1);
            softly.assertThat(page.totalArchivedCount()).isEqualTo(1);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/tasks?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listTasksForRepo_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-tasks-for-repo-response.json")));

        api().listTasksForRepo(
                        OWNER, REPO, AgentTasksCatalogFixtures.listTasksForRepoFilter(), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery())
                .contains("sort=updated_at")
                .contains("direction=asc")
                .contains("state=completed")
                .contains("is_archived=false")
                .contains("creator_id=1&creator_id=2");
    }

    @Test
    void listTasksForRepo_shouldThrow_whenOwnerBlank() {
        final GitHubAgentTaskApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listTasksForRepo(" ", REPO));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getTaskById_shouldThrow_whenTaskIdBlank() {
        final GitHubAgentTaskApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getTaskById(" "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("taskId");
    }

    @Test
    void getTaskByRepoAndId_shouldThrow_whenTaskIdBlank() {
        final GitHubAgentTaskApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getTaskByRepoAndId(OWNER, REPO, " "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("taskId");
    }

    @Test
    void createTaskInRepo_shouldThrow_whenPayloadNull() {
        final GitHubAgentTaskApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createTaskInRepo(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void listTasks_shouldThrow_whenPerPageAboveMaximum() {
        final GitHubAgentTaskApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listTasks(GitHubPageNumberRequest.of(1, 101)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("100");
    }

    @Test
    void createTaskInRepo_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("create-task-in-repo-response.json")));

        final GitHubCreateTaskInRepoResponse task =
                api().createTaskInRepo(OWNER, REPO, AgentTasksCatalogFixtures.createTaskInRepoPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(task).isEqualTo(AgentTasksCatalogFixtures.createTaskInRepoResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/tasks");
        });
    }

    @Test
    void getTaskByRepoAndId_shouldReturnTask_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("get-task-by-repo-and-id-response.json")));

        final Optional<GitHubGetTaskByRepoAndIdResponse> task = api().getTaskByRepoAndId(OWNER, REPO, TASK_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(task).contains(AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/agents/repos/octocat/Hello-World/tasks/task-1");
        });
    }

    @Test
    void getTaskByRepoAndId_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGetTaskByRepoAndIdResponse> task = api().getTaskByRepoAndId(OWNER, REPO, TASK_ID);

        assertThat(task).isEmpty();
    }

    @Test
    void listTasks_shouldReturnTasks_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-tasks-response.json")));

        final GitHubPage<GitHubListTasksTask> page = api().listTasks();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            AgentTasksCatalogFixtures.listTasksResponse().tasks());
            softly.assertThat(page.totalActiveCount()).isEqualTo(1);
            softly.assertThat(page.totalArchivedCount()).isEqualTo(1);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/agents/tasks?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listTasks_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-tasks-response.json")));

        api().listTasks(AgentTasksCatalogFixtures.listTasksFilter(), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery())
                .contains("sort=updated_at")
                .contains("direction=asc")
                .contains("state=completed")
                .contains("is_archived=false");
    }

    @Test
    void getTaskById_shouldReturnTask_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("get-task-by-id-response.json")));

        final Optional<GitHubGetTaskByIdResponse> task = api().getTaskById(TASK_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(task).contains(AgentTasksCatalogFixtures.getTaskByIdResponse());
            softly.assertThat(requestUri).hasToString("https://api.github.com/agents/tasks/task-1");
        });
    }

    @Test
    void getTaskById_shouldDecodeUnknownState_whenServerSendsNewState() throws Exception {
        final String body =
                json("get-task-by-id-response.json").replace("\"state\": \"queued\"", "\"state\": \"hibernating\"");
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), body));

        final Optional<GitHubGetTaskByIdResponse> task = api().getTaskById(TASK_ID);

        assertThat(task)
                .hasValueSatisfying(
                        found -> assertThat(found.state()).isEqualTo(GitHubGetTaskByIdResponseState.UNKNOWN));
    }

    @Test
    void getTaskById_shouldKeepTheArtifactDataRaw_whenArtifactDataHasAThirdShape() throws Exception {
        final String body = json("get-task-by-id-response.json")
                .replaceFirst("(?s)\"data\": \\{.*?\\}", "\"data\": {\"unexpected\": true}");
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), body));

        final Optional<GitHubGetTaskByIdResponse> task = api().getTaskById(TASK_ID);

        assertThat(task)
                .hasValueSatisfying(found -> assertThat(
                                found.artifacts().getFirst().data())
                        .isEqualTo(new GitHubGetTaskByIdResponseArtifactDataUnknown(Map.of("unexpected", true))));
    }

    @Test
    void getTaskById_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGetTaskByIdResponse> task = api().getTaskById(TASK_ID);

        assertThat(task).isEmpty();
    }

    @Test
    void listTasks_shouldSendPageAndPerPage_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-tasks-response.json")));

        api().listTasks(GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/agents/tasks?page=2&per_page=10");
    }

    private GitHubAgentTaskApi api() {
        return new GitHubAgentTaskApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubAgentTaskApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
