package io.github.piotr_janczukowicz.github.api.agentautomations;

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
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub agent automations REST resource for API version {@code 2026-03-10}. */
public final class GitHubAgentAutomationApi extends GitHubResourceApi {

    private static final String PARAM_AUTOMATION_ID = "automationId";

    private static final PathTemplate AGENT_AUTOMATIONS = PathTemplate.of("/agents/automations");
    private static final PathTemplate AGENT_AUTOMATION_AVAILABLE_TOOLS =
            PathTemplate.of("/agents/automations/available-tools");
    private static final PathTemplate AGENT_AUTOMATION_AVAILABLE_TRIGGERS =
            PathTemplate.of("/agents/automations/available-triggers");
    private static final PathTemplate AGENT_AUTOMATION = PathTemplate.of("/agents/automations/{automation_id}");
    private static final PathTemplate AGENT_AUTOMATION_TASKS =
            PathTemplate.of("/agents/automations/{automation_id}/tasks");
    private static final PathTemplate AGENT_REPO_AUTOMATIONS =
            PathTemplate.of("/agents/repos/{owner}/{repo}/automations");
    private static final PathTemplate AGENT_REPO_AUTOMATION =
            PathTemplate.of("/agents/repos/{owner}/{repo}/automations/{automation_id}");

    private static final JsonType<List<GitHubListAutomationsItem>> AUTOMATIONS_TYPE =
            JsonTypes.listOf(GitHubListAutomationsItem.class);
    private static final JsonType<List<GitHubListAvailableAutomationToolsItem>> AVAILABLE_TOOLS_TYPE =
            JsonTypes.listOf(GitHubListAvailableAutomationToolsItem.class);
    private static final JsonType<List<GitHubListAvailableAutomationTriggersItem>> AVAILABLE_TRIGGERS_TYPE =
            JsonTypes.listOf(GitHubListAvailableAutomationTriggersItem.class);

    private static final GitHubListSpec<GitHubListAutomationTasksTask> AUTOMATION_TASKS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubListAutomationTasksResponse.class,
            GitHubListAutomationTasksResponse::tasks,
            null);

    private static final GitHubListSpec<GitHubListRepoAutomationsAutomation> REPO_AUTOMATIONS_SPEC =
            GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubListRepoAutomationsResponse.class,
                    GitHubListRepoAutomationsResponse::automations,
                    GitHubListRepoAutomationsResponse::totalCount);

    /**
     * Agent automations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAgentAutomationApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List automations for the authenticated user ({@code GET /agents/automations}).
     *
     * @return first HTTP page of automations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListAutomationsItem> listAutomations() throws IOException {
        return listAutomations(GitHubListAutomationsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List automations for the authenticated user ({@code GET /agents/automations}).
     *
     * @param request pagination token
     * @return first HTTP page of automations
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListAutomationsItem> listAutomations(final GitHubPageRequest request) throws IOException {
        return listAutomations(GitHubListAutomationsFilter.empty(), request);
    }

    /**
     * List automations for the authenticated user ({@code GET /agents/automations}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of automations
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListAutomationsItem> listAutomations(
            final GitHubListAutomationsFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(AGENT_AUTOMATIONS, filter.toQueryParameters(), request, AUTOMATIONS_TYPE);
    }

    /**
     * List available automation tools ({@code GET /agents/automations/available-tools}).
     *
     * @return tool groups that can be selected in an automation
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubListAvailableAutomationToolsItem> listAvailableAutomationTools() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(AGENT_AUTOMATION_AVAILABLE_TOOLS), AVAILABLE_TOOLS_TYPE);
    }

    /**
     * List available automation triggers ({@code GET /agents/automations/available-triggers}).
     *
     * @return triggers and their configurable fields
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubListAvailableAutomationTriggersItem> listAvailableAutomationTriggers() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(AGENT_AUTOMATION_AVAILABLE_TRIGGERS), AVAILABLE_TRIGGERS_TYPE);
    }

    /**
     * Get an automation by ID ({@code GET /agents/automations/{automation_id}}).
     *
     * @param automationId automation identifier
     * @return automation, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code automationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubGetAutomationResponse> getAutomation(final String automationId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(AGENT_AUTOMATION, requireNotBlank(automationId, PARAM_AUTOMATION_ID)),
                GitHubGetAutomationResponse.class);
    }

    /**
     * List tasks for an automation ({@code GET /agents/automations/{automation_id}/tasks}).
     *
     * @param automationId automation identifier
     * @return first HTTP page of tasks
     * @throws IllegalArgumentException if {@code automationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListAutomationTasksTask> listAutomationTasks(final String automationId) throws IOException {
        return listAutomationTasks(automationId, GitHubListAutomationTasksFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List tasks for an automation ({@code GET /agents/automations/{automation_id}/tasks}).
     *
     * @param automationId automation identifier
     * @param request pagination token
     * @return first HTTP page of tasks
     * @throws IllegalArgumentException if {@code automationId} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListAutomationTasksTask> listAutomationTasks(
            final String automationId, final GitHubPageRequest request) throws IOException {
        return listAutomationTasks(automationId, GitHubListAutomationTasksFilter.empty(), request);
    }

    /**
     * List tasks for an automation ({@code GET /agents/automations/{automation_id}/tasks}).
     *
     * @param automationId automation identifier
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of tasks
     * @throws IllegalArgumentException if {@code automationId} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListAutomationTasksTask> listAutomationTasks(
            final String automationId, final GitHubListAutomationTasksFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                AGENT_AUTOMATION_TASKS,
                filter.toQueryParameters(),
                request,
                AUTOMATION_TASKS_SPEC,
                requireNotBlank(automationId, PARAM_AUTOMATION_ID));
    }

    /**
     * List automations for a repository ({@code GET /agents/repos/{owner}/{repo}/automations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of automations with the complete filtered count
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListRepoAutomationsAutomation> listRepoAutomations(final String owner, final String repo)
            throws IOException {
        return listRepoAutomations(owner, repo, GitHubListRepoAutomationsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List automations for a repository ({@code GET /agents/repos/{owner}/{repo}/automations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of automations with the complete filtered count
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListRepoAutomationsAutomation> listRepoAutomations(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listRepoAutomations(owner, repo, GitHubListRepoAutomationsFilter.empty(), request);
    }

    /**
     * List automations for a repository ({@code GET /agents/repos/{owner}/{repo}/automations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of automations with the complete filtered count
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubListRepoAutomationsAutomation> listRepoAutomations(
            final String owner,
            final String repo,
            final GitHubListRepoAutomationsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(AGENT_REPO_AUTOMATIONS, filter.toQueryParameters(), request, REPO_AUTOMATIONS_SPEC, owner, repo);
    }

    /**
     * Create an automation for a repository ({@code POST /agents/repos/{owner}/{repo}/automations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload automation to create
     * @return created automation
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCreateRepoAutomationResponse createRepoAutomation(
            final String owner, final String repo, final GitHubCreateRepoAutomationPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(AGENT_REPO_AUTOMATIONS, owner, repo), payload),
                GitHubCreateRepoAutomationResponse.class);
    }

    /**
     * Get an automation for a repository ({@code GET /agents/repos/{owner}/{repo}/automations/{automation_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param automationId automation identifier
     * @return automation, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code automationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubGetRepoAutomationResponse> getRepoAutomation(
            final String owner, final String repo, final String automationId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(AGENT_REPO_AUTOMATION, owner, repo, requireNotBlank(automationId, PARAM_AUTOMATION_ID)),
                GitHubGetRepoAutomationResponse.class);
    }

    /**
     * Edit an automation for a repository ({@code PATCH /agents/repos/{owner}/{repo}/automations/{automation_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param automationId automation identifier
     * @param payload fields to update; omitted fields are left unchanged
     * @return updated automation
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code automationId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubEditRepoAutomationResponse editRepoAutomation(
            final String owner,
            final String repo,
            final String automationId,
            final GitHubEditRepoAutomationPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(
                        patch(AGENT_REPO_AUTOMATION, owner, repo, requireNotBlank(automationId, PARAM_AUTOMATION_ID)),
                        payload),
                GitHubEditRepoAutomationResponse.class);
    }

    /**
     * Delete an automation for a repository ({@code DELETE /agents/repos/{owner}/{repo}/automations/{automation_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param automationId automation identifier
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code automationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteRepoAutomation(final String owner, final String repo, final String automationId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(AGENT_REPO_AUTOMATION, owner, repo, requireNotBlank(automationId, PARAM_AUTOMATION_ID)));
    }
}
