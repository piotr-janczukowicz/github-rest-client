package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDelivery;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDeliveryItem;
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
import java.util.Optional;

/**
 * GitHub App webhooks REST resource for API version {@code 2026-03-10}.
 *
 * <p>The spec says that these calls ({@code /app/hook/config} and {@code /app/hook/deliveries}) must be made with a
 * JSON web token (JWT) of the app. A client that authenticates with another credential gets a plain HTTP 401, 403 or
 * 404.
 */
public final class GitHubAppsWebhooksApi extends GitHubResourceApi {

    private static final PathTemplate HOOK_CONFIG = PathTemplate.of("/app/hook/config");
    private static final PathTemplate HOOK_DELIVERIES = PathTemplate.of("/app/hook/deliveries");
    private static final PathTemplate HOOK_DELIVERY = PathTemplate.of("/app/hook/deliveries/{delivery_id}");
    private static final PathTemplate HOOK_DELIVERY_ATTEMPTS =
            PathTemplate.of("/app/hook/deliveries/{delivery_id}/attempts");

    private static final JsonType<List<GitHubHookDeliveryItem>> HOOK_DELIVERIES_TYPE =
            JsonTypes.listOf(GitHubHookDeliveryItem.class);

    private static final GitHubListSpec<GitHubHookDeliveryItem> HOOK_DELIVERIES_SPEC =
            GitHubListSpec.array(GitHubPageRequestKindValidators.DELIVERY_CURSOR, HOOK_DELIVERIES_TYPE);

    /**
     * GitHub App webhooks resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAppsWebhooksApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get a webhook configuration for an app ({@code GET /app/hook/config}).
     *
     * @return webhook configuration
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubWebhookConfig getWebhookConfigForApp() throws IOException {
        return caller.fetch(get(HOOK_CONFIG), GitHubWebhookConfig.class);
    }

    /**
     * Update a webhook configuration for an app ({@code PATCH /app/hook/config}).
     *
     * @param payload configuration changes; the spec requires a body
     * @return updated webhook configuration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubWebhookConfig updateWebhookConfigForApp(final GitHubUpdateWebhookConfigForAppPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(HOOK_CONFIG), payload), GitHubWebhookConfig.class);
    }

    /**
     * List deliveries for an app webhook ({@code GET /app/hook/deliveries}).
     *
     * @return first HTTP page of deliveries
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHookDeliveryItem> listWebhookDeliveries() throws IOException {
        return listWebhookDeliveries(GitHubListWebhookDeliveriesFilter.empty(), GitHubPageRequest.byDeliveryCursor());
    }

    /**
     * List deliveries for an app webhook ({@code GET /app/hook/deliveries}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of deliveries
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubHookDeliveryItem> listWebhookDeliveries(
            final GitHubListWebhookDeliveriesFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(HOOK_DELIVERIES, filter.toQueryParameters(), request, HOOK_DELIVERIES_SPEC);
    }

    /**
     * Get a delivery for an app webhook ({@code GET /app/hook/deliveries/{delivery_id}}).
     *
     * @param deliveryId delivery id
     * @return delivery, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubHookDelivery> getWebhookDelivery(final long deliveryId) throws IOException {
        return caller.fetchOrEmpty(get(HOOK_DELIVERY, deliveryId), GitHubHookDelivery.class);
    }

    /**
     * Redeliver a delivery for an app webhook ({@code POST /app/hook/deliveries/{delivery_id}/attempts}).
     *
     * @param deliveryId delivery id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void redeliverWebhookDelivery(final long deliveryId) throws IOException {
        caller.send(post(HOOK_DELIVERY_ATTEMPTS, deliveryId));
    }
}
