package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update repository webhook.
 *
 * @param config Config
 * @param events Events
 * @param addEvents Add events
 * @param removeEvents Remove events
 * @param active Active
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-webhook/requestBody")
public record GitHubUpdateWebhookPayload(
        @Nullable GitHubWebhookConfig config,
        @Nullable List<String> events,
        @Nullable List<String> addEvents,
        @Nullable List<String> removeEvents,
        @Nullable Boolean active) {

    public GitHubUpdateWebhookPayload {
        if (events != null) {
            events = List.copyOf(events);
        }
        if (addEvents != null) {
            addEvents = List.copyOf(addEvents);
        }
        if (removeEvents != null) {
            removeEvents = List.copyOf(removeEvents);
        }
    }
}
