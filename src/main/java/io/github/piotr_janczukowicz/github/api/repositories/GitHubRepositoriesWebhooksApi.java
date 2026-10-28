package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
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
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub repository webhooks REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesWebhooksApi extends GitHubResourceApi {

    private static final PathTemplate HOOK_DELIVERY_ATTEMPTS =
            PathTemplate.of("/repos/{owner}/{repo}/hooks/{hook_id}/deliveries/{delivery_id}/attempts");
    private static final PathTemplate HOOK_TESTS = PathTemplate.of("/repos/{owner}/{repo}/hooks/{hook_id}/tests");
    private static final PathTemplate HOOK_PINGS = PathTemplate.of("/repos/{owner}/{repo}/hooks/{hook_id}/pings");
    private static final PathTemplate HOOK_DELIVERY =
            PathTemplate.of("/repos/{owner}/{repo}/hooks/{hook_id}/deliveries/{delivery_id}");
    private static final PathTemplate HOOK_DELIVERIES =
            PathTemplate.of("/repos/{owner}/{repo}/hooks/{hook_id}/deliveries");
    private static final PathTemplate HOOK_CONFIG = PathTemplate.of("/repos/{owner}/{repo}/hooks/{hook_id}/config");
    private static final PathTemplate HOOK = PathTemplate.of("/repos/{owner}/{repo}/hooks/{hook_id}");
    private static final PathTemplate HOOKS = PathTemplate.of("/repos/{owner}/{repo}/hooks");

    private static final JsonType<List<GitHubHook>> HOOKS_TYPE = JsonTypes.listOf(GitHubHook.class);
    private static final JsonType<List<GitHubHookDeliveryItem>> HOOK_DELIVERIES_TYPE =
            JsonTypes.listOf(GitHubHookDeliveryItem.class);

    /**
     * Webhooks resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesWebhooksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List webhooks ({@code GET /repos/{owner}/{repo}/hooks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of webhooks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHook> listWebhooks(final String owner, final String repo) throws IOException {
        return listWebhooks(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List webhooks ({@code GET /repos/{owner}/{repo}/hooks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of webhooks
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHook> listWebhooks(final String owner, final String repo, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(HOOKS, Map.of(), request, HOOKS_TYPE, owner, repo);
    }

    /**
     * Get a webhook ({@code GET /repos/{owner}/{repo}/hooks/{hook_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @return webhook, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubHook> getWebhook(final String owner, final String repo, final long hookId)
            throws IOException {

        return caller.fetchOrEmpty(get(HOOK, owner, repo, hookId), GitHubHook.class);
    }

    /**
     * Create a webhook ({@code POST /repos/{owner}/{repo}/hooks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload optional webhook to create
     * @return created webhook
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubHook createWebhook(
            final String owner, final String repo, final @Nullable GitHubCreateWebhookPayload payload)
            throws IOException {

        return caller.fetch(caller.jsonIfPresent(post(HOOKS, owner, repo), payload), GitHubHook.class);
    }

    /**
     * Update a webhook ({@code PATCH /repos/{owner}/{repo}/hooks/{hook_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @param payload webhook update
     * @return updated webhook
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubHook updateWebhook(
            final String owner, final String repo, final long hookId, final GitHubUpdateWebhookPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(HOOK, owner, repo, hookId), payload), GitHubHook.class);
    }

    /**
     * Delete a webhook ({@code DELETE /repos/{owner}/{repo}/hooks/{hook_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteWebhook(final String owner, final String repo, final long hookId) throws IOException {
        caller.send(delete(HOOK, owner, repo, hookId));
    }

    /**
     * Get webhook configuration ({@code GET /repos/{owner}/{repo}/hooks/{hook_id}/config}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @return webhook configuration, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubWebhookConfig> getWebhookConfigForRepo(
            final String owner, final String repo, final long hookId) throws IOException {

        return caller.fetchOrEmpty(get(HOOK_CONFIG, owner, repo, hookId), GitHubWebhookConfig.class);
    }

    /**
     * Update webhook configuration ({@code PATCH /repos/{owner}/{repo}/hooks/{hook_id}/config}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @param payload optional configuration update
     * @return updated webhook configuration
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubWebhookConfig updateWebhookConfigForRepo(
            final String owner,
            final String repo,
            final long hookId,
            final @Nullable GitHubUpdateWebhookConfigForRepoPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(patch(HOOK_CONFIG, owner, repo, hookId), payload), GitHubWebhookConfig.class);
    }

    /**
     * List webhook deliveries ({@code GET /repos/{owner}/{repo}/hooks/{hook_id}/deliveries}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @return first HTTP page of webhook deliveries
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHookDeliveryItem> listWebhookDeliveries(
            final String owner, final String repo, final long hookId) throws IOException {
        return listWebhookDeliveries(
                owner, repo, hookId, GitHubListWebhookDeliveriesFilter.empty(), GitHubPageRequest.byDeliveryCursor());
    }

    /**
     * List webhook deliveries ({@code GET /repos/{owner}/{repo}/hooks/{hook_id}/deliveries}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @param filter query filters
     * @param request delivery cursor pagination token
     * @return first HTTP page of webhook deliveries
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHookDeliveryItem> listWebhookDeliveries(
            final String owner,
            final String repo,
            final long hookId,
            final GitHubListWebhookDeliveriesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                HOOK_DELIVERIES,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.DELIVERY_CURSOR, HOOK_DELIVERIES_TYPE),
                owner,
                repo,
                hookId);
    }

    /**
     * Get a webhook delivery ({@code GET /repos/{owner}/{repo}/hooks/{hook_id}/deliveries/{delivery_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @param deliveryId identifier of the webhook delivery
     * @return webhook delivery, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubHookDelivery> getWebhookDelivery(
            final String owner, final String repo, final long hookId, final long deliveryId) throws IOException {

        return caller.fetchOrEmpty(get(HOOK_DELIVERY, owner, repo, hookId, deliveryId), GitHubHookDelivery.class);
    }

    /**
     * Ping a webhook ({@code POST /repos/{owner}/{repo}/hooks/{hook_id}/pings}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void pingWebhook(final String owner, final String repo, final long hookId) throws IOException {
        caller.send(post(HOOK_PINGS, owner, repo, hookId));
    }

    /**
     * Test push webhook ({@code POST /repos/{owner}/{repo}/hooks/{hook_id}/tests}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void testPushWebhook(final String owner, final String repo, final long hookId) throws IOException {
        caller.send(post(HOOK_TESTS, owner, repo, hookId));
    }

    /**
     * Redeliver a webhook delivery ({@code POST
     * /repos/{owner}/{repo}/hooks/{hook_id}/deliveries/{delivery_id}/attempts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param hookId identifier of the repository webhook
     * @param deliveryId identifier of the webhook delivery
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void redeliverWebhookDelivery(
            final String owner, final String repo, final long hookId, final long deliveryId) throws IOException {

        caller.send(post(HOOK_DELIVERY_ATTEMPTS, owner, repo, hookId, deliveryId));
    }
}
