package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubUpdateGlobalWebhookPayload
 *
 * @param config Key/value pairs to provide settings for this webhook.
 * @param events The [events](https://docs.github.com/enterprise-server@3.22/webhooks/event-payloads) that trigger this
 *     webhook. A global webhook can be triggered by `user` and `organization` events. Default: `user` and
 *     `organization`.
 * @param active Determines if notifications are sent when the webhook is triggered. Set to `true` to send
 *     notifications.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-global-webhook/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdateGlobalWebhookPayload(
        @Nullable GitHubUpdateGlobalWebhookPayloadConfig config,
        @Nullable List<String> events,
        @Nullable Boolean active) {
    public GitHubUpdateGlobalWebhookPayload {
        if (events != null) {
            events = List.copyOf(events);
        }
    }
}
