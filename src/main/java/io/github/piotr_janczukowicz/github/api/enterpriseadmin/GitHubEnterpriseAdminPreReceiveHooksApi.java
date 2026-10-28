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

/** GitHub Enterprise Server pre-receive hook REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminPreReceiveHooksApi extends GitHubResourceApi {

    private static final PathTemplate HOOKS = PathTemplate.of("/admin/pre-receive-hooks");
    private static final PathTemplate HOOK = PathTemplate.of("/admin/pre-receive-hooks/{pre_receive_hook_id}");

    private static final JsonType<List<GitHubPreReceiveHook>> HOOKS_TYPE = JsonTypes.listOf(GitHubPreReceiveHook.class);

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminPreReceiveHooksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List pre-receive hooks ({@code GET /admin/pre-receive-hooks}).
     *
     * @return first HTTP page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPreReceiveHook> listPreReceiveHooks() throws IOException {
        return listPreReceiveHooks(GitHubListPreReceiveHooksFilter.empty());
    }

    /**
     * List pre-receive hooks ({@code GET /admin/pre-receive-hooks}).
     *
     * @param filter sort and direction
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPreReceiveHook> listPreReceiveHooks(final GitHubListPreReceiveHooksFilter filter)
            throws IOException {
        return listPreReceiveHooks(filter, GitHubPageRequest.byPage());
    }

    /**
     * List pre-receive hooks ({@code GET /admin/pre-receive-hooks}).
     *
     * @param filter sort and direction
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubPreReceiveHook> listPreReceiveHooks(
            final GitHubListPreReceiveHooksFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(HOOKS, filter.toQueryParameters(), request, HOOKS_TYPE);
    }

    /**
     * Create a pre-receive hook ({@code POST /admin/pre-receive-hooks}).
     *
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPreReceiveHook createPreReceiveHook(final GitHubCreatePreReceiveHookPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(HOOKS), payload), GitHubPreReceiveHook.class);
    }

    /**
     * Get a pre-receive hook ({@code GET /admin/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param preReceiveHookId pre-receive hook id
     * @return the resource, or empty if it does not exist
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubPreReceiveHook> getPreReceiveHook(final long preReceiveHookId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(HOOK, preReceiveHookId), GitHubPreReceiveHook.class);
    }

    /**
     * Update a pre-receive hook ({@code PATCH /admin/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param preReceiveHookId pre-receive hook id
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPreReceiveHook updatePreReceiveHook(
            final long preReceiveHookId, final GitHubUpdatePreReceiveHookPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(HOOK, preReceiveHookId), payload), GitHubPreReceiveHook.class);
    }

    /**
     * Delete a pre-receive hook ({@code DELETE /admin/pre-receive-hooks/{pre_receive_hook_id}}).
     *
     * @param preReceiveHookId pre-receive hook id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void deletePreReceiveHook(final long preReceiveHookId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(HOOK, preReceiveHookId));
    }
}
