package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDelivery;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDeliveryItem;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubListWebhookDeliveriesFilter;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubUpdateWebhookConfigForRepoPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfig;
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

/** GitHub organization webhooks REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsWebhooksApi extends GitHubResourceApi {

    private static final PathTemplate HOOKS = PathTemplate.of("/orgs/{org}/hooks");
    private static final PathTemplate HOOK = PathTemplate.of("/orgs/{org}/hooks/{hook_id}");
    private static final PathTemplate HOOK_CONFIG = PathTemplate.of("/orgs/{org}/hooks/{hook_id}/config");
    private static final PathTemplate HOOK_DELIVERIES = PathTemplate.of("/orgs/{org}/hooks/{hook_id}/deliveries");
    private static final PathTemplate HOOK_DELIVERY =
            PathTemplate.of("/orgs/{org}/hooks/{hook_id}/deliveries/{delivery_id}");
    private static final PathTemplate HOOK_DELIVERY_ATTEMPTS =
            PathTemplate.of("/orgs/{org}/hooks/{hook_id}/deliveries/{delivery_id}/attempts");
    private static final PathTemplate HOOK_PINGS = PathTemplate.of("/orgs/{org}/hooks/{hook_id}/pings");

    private static final JsonType<List<GitHubOrgHook>> HOOKS_TYPE = JsonTypes.listOf(GitHubOrgHook.class);
    private static final JsonType<List<GitHubHookDeliveryItem>> HOOK_DELIVERIES_TYPE =
            JsonTypes.listOf(GitHubHookDeliveryItem.class);

    /**
     * Webhooks resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsWebhooksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization webhooks ({@code GET /orgs/{org}/hooks}).
     *
     * @param org organization name
     * @return first HTTP page of webhooks
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgHook> listWebhooks(final String org) throws IOException {
        return listWebhooks(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization webhooks ({@code GET /orgs/{org}/hooks}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of webhooks
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgHook> listWebhooks(final String org, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(HOOKS, Map.of(), request, HOOKS_TYPE, org);
    }

    /**
     * Get an organization webhook ({@code GET /orgs/{org}/hooks/{hook_id}}).
     *
     * @param org organization name
     * @param hookId hook id
     * @return webhook, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubOrgHook> getWebhook(final String org, final long hookId) throws IOException {
        return caller.fetchOrEmpty(get(HOOK, org, hookId), GitHubOrgHook.class);
    }

    /**
     * Create an organization webhook ({@code POST /orgs/{org}/hooks}).
     *
     * @param org organization name
     * @param payload webhook to create
     * @return created webhook
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOrgHook createWebhook(final String org, final GitHubCreateOrgWebhookPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(HOOKS, org), payload), GitHubOrgHook.class);
    }

    /**
     * Update an organization webhook ({@code PATCH /orgs/{org}/hooks/{hook_id}}).
     *
     * @param org organization name
     * @param hookId hook id
     * @param payload webhook update
     * @return updated webhook
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOrgHook updateWebhook(final String org, final long hookId, final GitHubUpdateOrgWebhookPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(HOOK, org, hookId), payload), GitHubOrgHook.class);
    }

    /**
     * Delete an organization webhook ({@code DELETE /orgs/{org}/hooks/{hook_id}}).
     *
     * @param org organization name
     * @param hookId hook id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteWebhook(final String org, final long hookId) throws IOException {
        caller.send(delete(HOOK, org, hookId));
    }

    /**
     * Get webhook configuration for an organization ({@code GET /orgs/{org}/hooks/{hook_id}/config}).
     *
     * @param org organization name
     * @param hookId hook id
     * @return webhook configuration, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubWebhookConfig> getWebhookConfigForOrg(final String org, final long hookId)
            throws IOException {

        return caller.fetchOrEmpty(get(HOOK_CONFIG, org, hookId), GitHubWebhookConfig.class);
    }

    /**
     * Update webhook configuration for an organization ({@code PATCH /orgs/{org}/hooks/{hook_id}/config}).
     *
     * @param org organization name
     * @param hookId hook id
     * @param payload optional configuration update
     * @return updated webhook configuration
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubWebhookConfig updateWebhookConfigForOrg(
            final String org, final long hookId, final @Nullable GitHubUpdateWebhookConfigForRepoPayload payload)
            throws IOException {

        return caller.fetch(caller.jsonIfPresent(patch(HOOK_CONFIG, org, hookId), payload), GitHubWebhookConfig.class);
    }

    /**
     * List deliveries for an organization webhook ({@code GET /orgs/{org}/hooks/{hook_id}/deliveries}).
     *
     * @param org organization name
     * @param hookId hook id
     * @return first HTTP page of webhook deliveries
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHookDeliveryItem> listWebhookDeliveries(final String org, final long hookId)
            throws IOException {
        return listWebhookDeliveries(
                org, hookId, GitHubListWebhookDeliveriesFilter.empty(), GitHubPageRequest.byDeliveryCursor());
    }

    /**
     * List deliveries for an organization webhook ({@code GET /orgs/{org}/hooks/{hook_id}/deliveries}).
     *
     * @param org organization name
     * @param hookId hook id
     * @param filter query filters
     * @param request delivery cursor pagination token
     * @return first HTTP page of webhook deliveries
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHookDeliveryItem> listWebhookDeliveries(
            final String org,
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
                org,
                hookId);
    }

    /**
     * Get a webhook delivery for an organization webhook ({@code GET
     * /orgs/{org}/hooks/{hook_id}/deliveries/{delivery_id}}).
     *
     * @param org organization name
     * @param hookId hook id
     * @param deliveryId delivery id
     * @return webhook delivery, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubHookDelivery> getWebhookDelivery(final String org, final long hookId, final long deliveryId)
            throws IOException {

        return caller.fetchOrEmpty(get(HOOK_DELIVERY, org, hookId, deliveryId), GitHubHookDelivery.class);
    }

    /**
     * Ping an organization webhook ({@code POST /orgs/{org}/hooks/{hook_id}/pings}).
     *
     * @param org organization name
     * @param hookId hook id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void pingWebhook(final String org, final long hookId) throws IOException {
        caller.send(post(HOOK_PINGS, org, hookId));
    }

    /**
     * Redeliver a delivery for an organization webhook ({@code POST
     * /orgs/{org}/hooks/{hook_id}/deliveries/{delivery_id}/attempts}).
     *
     * @param org organization name
     * @param hookId hook id
     * @param deliveryId delivery id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void redeliverWebhookDelivery(final String org, final long hookId, final long deliveryId)
            throws IOException {

        caller.send(post(HOOK_DELIVERY_ATTEMPTS, org, hookId, deliveryId));
    }
}
