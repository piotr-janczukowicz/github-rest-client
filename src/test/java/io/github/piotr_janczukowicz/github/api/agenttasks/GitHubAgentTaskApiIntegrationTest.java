package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.agentTasksApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAgentTaskApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String TASK_ID = "task-1";
    private static final String REPO_TASKS_PATH = "/agents/repos/octocat/Hello-World/tasks";
    private static final String REPO_TASK_PATH = "/agents/repos/octocat/Hello-World/tasks/task-1";
    private static final String TASKS_PATH = "/agents/tasks";
    private static final String TASK_PATH = "/agents/tasks/task-1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listTasksForRepo_shouldReturnTasks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_TASKS_PATH,
                okJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "list-tasks-for-repo-response.json")));

        final GitHubPage<GitHubListTasksForRepoTask> page;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            page = agentTasks.api().listTasksForRepo(OWNER, REPO);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        AgentTasksCatalogFixtures.listTasksForRepoResponse().tasks());
        HTTP.verifyRequested(GET, REPO_TASKS_PATH);
        HTTP.verifyQueryParam(GET, REPO_TASKS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_TASKS_PATH, "per_page", "100");
    }

    @Test
    void listTasksForRepo_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                REPO_TASKS_PATH,
                okJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "list-tasks-for-repo-response.json"))
                        .header(LINK, linkNext(HTTP.uri(REPO_TASKS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "list-tasks-for-repo-response.json")));

        final GitHubPage<GitHubListTasksForRepoTask> first;
        final Optional<GitHubPage<GitHubListTasksForRepoTask>> second;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            first = agentTasks.api().listTasksForRepo(OWNER, REPO);
            second = first.next();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items())
                    .containsExactlyElementsOf(
                            AgentTasksCatalogFixtures.listTasksForRepoResponse().tasks());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items())
                    .containsExactlyElementsOf(
                            AgentTasksCatalogFixtures.listTasksForRepoResponse().tasks());
        });
        HTTP.verifyRequested(GET, REPO_TASKS_PATH, 2);
    }

    @Test
    void listTasksForRepo_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                REPO_TASKS_PATH,
                okJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "list-tasks-for-repo-response.json")));

        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            agentTasks.api().listTasksForRepo(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, REPO_TASKS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, REPO_TASKS_PATH, "per_page", "10");
    }

    @Test
    void createTaskInRepo_shouldReturnTask_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_TASKS_PATH,
                createdJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "create-task-in-repo-response.json")));

        final GitHubCreateTaskInRepoResponse task;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            task = agentTasks.api().createTaskInRepo(OWNER, REPO, AgentTasksCatalogFixtures.createTaskInRepoPayload());
        }

        assertThat(task).isEqualTo(AgentTasksCatalogFixtures.createTaskInRepoResponse());
        HTTP.verifyRequested(POST, REPO_TASKS_PATH);
    }

    @Test
    void getTaskByRepoAndId_shouldReturnTask_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_TASK_PATH,
                okJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "get-task-by-repo-and-id-response.json")));

        final Optional<GitHubGetTaskByRepoAndIdResponse> task;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            task = agentTasks.api().getTaskByRepoAndId(OWNER, REPO, TASK_ID);
        }

        assertThat(task).contains(AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse());
        HTTP.verifyRequested(GET, REPO_TASK_PATH);
    }

    @Test
    void getTaskByRepoAndId_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_TASK_PATH, notFound());

        final Optional<GitHubGetTaskByRepoAndIdResponse> task;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            task = agentTasks.api().getTaskByRepoAndId(OWNER, REPO, TASK_ID);
        }

        assertThat(task).isEmpty();
    }

    @Test
    void listTasks_shouldReturnTasks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                TASKS_PATH,
                okJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "list-tasks-response.json")));

        final GitHubPage<GitHubListTasksTask> page;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            page = agentTasks.api().listTasks();
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        AgentTasksCatalogFixtures.listTasksResponse().tasks());
        HTTP.verifyRequested(GET, TASKS_PATH);
        HTTP.verifyQueryParam(GET, TASKS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, TASKS_PATH, "per_page", "100");
    }

    @Test
    void getTaskById_shouldReturnTask_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                TASK_PATH,
                okJson(jsonBytes(GitHubAgentTaskApiIntegrationTest.class, "get-task-by-id-response.json")));

        final Optional<GitHubGetTaskByIdResponse> task;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            task = agentTasks.api().getTaskById(TASK_ID);
        }

        assertThat(task).contains(AgentTasksCatalogFixtures.getTaskByIdResponse());
        HTTP.verifyRequested(GET, TASK_PATH);
    }

    @Test
    void getTaskById_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, TASK_PATH, notFound());

        final Optional<GitHubGetTaskByIdResponse> task;
        try (final GitHubApiWireMockSupport.AgentTasksApiClient agentTasks = agentTasksApi(HTTP)) {
            task = agentTasks.api().getTaskById(TASK_ID);
        }

        assertThat(task).isEmpty();
    }
}
