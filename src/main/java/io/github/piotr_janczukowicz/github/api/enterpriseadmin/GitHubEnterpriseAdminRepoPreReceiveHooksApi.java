package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub Enterprise Server repository pre-receive hook REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminRepoPreReceiveHooksApi extends GitHubResourceApi {

    private static final PathTemplate HOOKS = PathTemplate.of("/repos/{owner}/{repo}/pre-receive-hooks");
    private static final PathTemplate HOOK =
            PathTemplate.of("/repos/{owner}/{repo}/pre-receive-hooks/{pre_receive_hook_id}");

    private static final JsonType<List<GitHubRepositoryPreReceiveHook>> HOOKS_TYPE =
            JsonTypes.listOf(GitHubRepositoryPreReceiveHook.class);

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminRepoPreReceiveHooksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the pre-receive hooks of a repository ({@code GET /repos/{owner}/{repo}/pre-receive-hooks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubRepositoryPreReceiveHook> listPreReceiveHooksForRepo(final String owner, final String repo)
            throws IOException {
        return listPreReceiveHooksForRepo(owner, repo, GitHubListPreReceiveHooksForRepoFilter.empty());
    }

    /**
     * List the pre-receive hooks of a repository ({@code GET /repos/{owner}/{repo}/pre-receive-hooks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter sort and direction
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubRepositoryPreReceiveHook> listPreReceiveHooksForRepo(
            final String owner, final String repo, final GitHubListPreReceiveHooksForRepoFilter filter)
            throws IOException {
        return listPreReceiveHooksForRepo(owner, repo, filter, GitHubPageRequest.byPage());
    }

    /**
     * List the pre-receive hooks of a repository ({@code GET /repos/{owner}/{repo}/pre-receive-hooks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter sort and direction
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubRepositoryPreReceiveHook> listPreReceiveHooksForRepo(
            final String owner,
            final String repo,
            final GitHubListPreReceiveHooksForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(HOOKS, filter.toQueryParameters(), request, HOOKS_TYPE, owner, repo);
    }

    /**
     * Get a pre-receive hook of a repository ({@code GET
     * /repos/{owner}/{repo}/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param preReceiveHookId pre-receive hook id
     * @return the resource, or empty if it does not exist
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubRepositoryPreReceiveHook> getPreReceiveHookForRepo(
            final String owner, final String repo, final long preReceiveHookId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(HOOK, owner, repo, preReceiveHookId), GitHubRepositoryPreReceiveHook.class);
    }

    /**
     * Update the enforcement of a pre-receive hook for a repository ({@code PATCH
     * /repos/{owner}/{repo}/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param preReceiveHookId pre-receive hook id
     * @param payload request body
     * @return the resource
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubRepositoryPreReceiveHook updatePreReceiveHookEnforcementForRepo(
            final String owner,
            final String repo,
            final long preReceiveHookId,
            final GitHubUpdatePreReceiveHookEnforcementForRepoPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(HOOK, owner, repo, preReceiveHookId), payload), GitHubRepositoryPreReceiveHook.class);
    }

    /**
     * Remove the enforcement of a pre-receive hook for a repository ({@code DELETE
     * /repos/{owner}/{repo}/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param preReceiveHookId pre-receive hook id
     * @return the hook
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubRepositoryPreReceiveHook removePreReceiveHookEnforcementForRepo(
            final String owner, final String repo, final long preReceiveHookId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(delete(HOOK, owner, repo, preReceiveHookId), GitHubRepositoryPreReceiveHook.class);
    }
}
