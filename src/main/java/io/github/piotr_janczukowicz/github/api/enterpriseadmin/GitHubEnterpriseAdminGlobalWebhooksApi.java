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
import java.util.Map;
import java.util.Optional;

/** GitHub Enterprise Server global webhook REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminGlobalWebhooksApi extends GitHubResourceApi {

    private static final PathTemplate HOOKS = PathTemplate.of("/admin/hooks");
    private static final PathTemplate HOOK = PathTemplate.of("/admin/hooks/{hook_id}");
    private static final PathTemplate PINGS = PathTemplate.of("/admin/hooks/{hook_id}/pings");

    private static final JsonType<List<GitHubGlobalHook>> HOOKS_TYPE = JsonTypes.listOf(GitHubGlobalHook.class);

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminGlobalWebhooksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List global webhooks ({@code GET /admin/hooks}).
     *
     * @return first HTTP page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubGlobalHook> listGlobalWebhooks() throws IOException {
        return listGlobalWebhooks(GitHubPageRequest.byPage());
    }

    /**
     * List global webhooks ({@code GET /admin/hooks}).
     *
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubGlobalHook> listGlobalWebhooks(final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(HOOKS, Map.of(), request, HOOKS_TYPE);
    }

    /**
     * Create a global webhook ({@code POST /admin/hooks}).
     *
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubGlobalHook createGlobalWebhook(final GitHubCreateGlobalWebhookPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(HOOKS), payload), GitHubGlobalHook.class);
    }

    /**
     * Get a global webhook ({@code GET /admin/hooks/{hook_id}}).
     *
     * @param hookId webhook id
     * @return the resource, or empty if it does not exist
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubGlobalHook> getGlobalWebhook(final long hookId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(HOOK, hookId), GitHubGlobalHook.class);
    }

    /**
     * Update a global webhook ({@code PATCH /admin/hooks/{hook_id}}).
     *
     * @param hookId webhook id
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubGlobalHookWithoutSecret updateGlobalWebhook(
            final long hookId, final GitHubUpdateGlobalWebhookPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(HOOK, hookId), payload), GitHubGlobalHookWithoutSecret.class);
    }

    /**
     * Delete a global webhook ({@code DELETE /admin/hooks/{hook_id}}).
     *
     * @param hookId webhook id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void deleteGlobalWebhook(final long hookId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(HOOK, hookId));
    }

    /**
     * Ping a global webhook ({@code POST /admin/hooks/{hook_id}/pings}).
     *
     * @param hookId webhook id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void pingGlobalWebhook(final long hookId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(post(PINGS, hookId));
    }
}
