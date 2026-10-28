package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.github.pagination.GitHubTaskCounts;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub agent tasks REST resource for API version {@code 2026-03-10}. */
public final class GitHubAgentTaskApi extends GitHubResourceApi {

    private static final String PARAM_TASK_ID = "taskId";

    private static final PathTemplate AGENT_REPO_TASKS = PathTemplate.of("/agents/repos/{owner}/{repo}/tasks");
    private static final PathTemplate AGENT_REPO_TASK = PathTemplate.of("/agents/repos/{owner}/{repo}/tasks/{task_id}");
    private static final PathTemplate AGENT_TASKS = PathTemplate.of("/agents/tasks");
    private static final PathTemplate AGENT_TASK = PathTemplate.of("/agents/tasks/{task_id}");

    private static final GitHubListSpec<GitHubListTasksForRepoTask> TASKS_FOR_REPO_SPEC = GitHubListSpec.taskWrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubListTasksForRepoResponse.class,
            GitHubListTasksForRepoResponse::tasks,
            response -> new GitHubTaskCounts(response.totalActiveCount(), response.totalArchivedCount()));

    private static final GitHubListSpec<GitHubListTasksTask> TASKS_SPEC = GitHubListSpec.taskWrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubListTasksResponse.class,
            GitHubListTasksResponse::tasks,
            response -> new GitHubTaskCounts(response.totalActiveCount(), response.totalArchivedCount()));

    /**
     * Agent tasks resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAgentTaskApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List tasks for a repository ({@code GET /agents/repos/{owner}/{repo}/tasks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of tasks; {@link GitHubPage#totalActiveCount()} and
     *     {@link GitHubPage#totalArchivedCount()} give the counts of all active and archived tasks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListTasksForRepoTask> listTasksForRepo(final String owner, final String repo)
            throws IOException {
        return listTasksForRepo(owner, repo, GitHubListTasksForRepoFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List tasks for a repository ({@code GET /agents/repos/{owner}/{repo}/tasks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of tasks; {@link GitHubPage#totalActiveCount()} and
     *     {@link GitHubPage#totalArchivedCount()} give the counts of all active and archived tasks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListTasksForRepoTask> listTasksForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listTasksForRepo(owner, repo, GitHubListTasksForRepoFilter.empty(), request);
    }

    /**
     * List tasks for a repository ({@code GET /agents/repos/{owner}/{repo}/tasks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of tasks; {@link GitHubPage#totalActiveCount()} and
     *     {@link GitHubPage#totalArchivedCount()} give the counts of all active and archived tasks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListTasksForRepoTask> listTasksForRepo(
            final String owner,
            final String repo,
            final GitHubListTasksForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return listValues(AGENT_REPO_TASKS, filter.toQueryValues(), request, TASKS_FOR_REPO_SPEC, owner, repo);
    }

    /**
     * Start a task in a repository ({@code POST /agents/repos/{owner}/{repo}/tasks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload task to start
     * @return created task
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCreateTaskInRepoResponse createTaskInRepo(
            final String owner, final String repo, final GitHubCreateTaskInRepoPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(AGENT_REPO_TASKS, owner, repo), payload), GitHubCreateTaskInRepoResponse.class);
    }

    /**
     * Get a task in a repository ({@code GET /agents/repos/{owner}/{repo}/tasks/{task_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param taskId task identifier
     * @return task, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code taskId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubGetTaskByRepoAndIdResponse> getTaskByRepoAndId(
            final String owner, final String repo, final String taskId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(AGENT_REPO_TASK, owner, repo, requireNotBlank(taskId, PARAM_TASK_ID)),
                GitHubGetTaskByRepoAndIdResponse.class);
    }

    /**
     * List tasks for the authenticated user ({@code GET /agents/tasks}).
     *
     * @return first HTTP page of tasks; {@link GitHubPage#totalActiveCount()} and
     *     {@link GitHubPage#totalArchivedCount()} give the counts of all active and archived tasks
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListTasksTask> listTasks() throws IOException {
        return listTasks(GitHubListTasksFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List tasks for the authenticated user ({@code GET /agents/tasks}).
     *
     * @param request pagination token
     * @return first HTTP page of tasks; {@link GitHubPage#totalActiveCount()} and
     *     {@link GitHubPage#totalArchivedCount()} give the counts of all active and archived tasks
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListTasksTask> listTasks(final GitHubPageRequest request) throws IOException {
        return listTasks(GitHubListTasksFilter.empty(), request);
    }

    /**
     * List tasks for the authenticated user ({@code GET /agents/tasks}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of tasks; {@link GitHubPage#totalActiveCount()} and
     *     {@link GitHubPage#totalArchivedCount()} give the counts of all active and archived tasks
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListTasksTask> listTasks(
            final GitHubListTasksFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(AGENT_TASKS, filter.toQueryParameters(), request, TASKS_SPEC);
    }

    /**
     * Get a task by ID ({@code GET /agents/tasks/{task_id}}).
     *
     * @param taskId task identifier
     * @return task, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code taskId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubGetTaskByIdResponse> getTaskById(final String taskId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(AGENT_TASK, requireNotBlank(taskId, PARAM_TASK_ID)), GitHubGetTaskByIdResponse.class);
    }
}
