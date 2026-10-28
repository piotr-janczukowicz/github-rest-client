package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create repository webhook.
 *
 * @param name Name
 * @param config Config
 * @param events Events
 * @param active Active
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-webhook/requestBody")
public record GitHubCreateWebhookPayload(
        @Nullable String name,
        @Nullable GitHubWebhookConfig config,
        @Nullable List<String> events,
        @Nullable Boolean active) {
    public GitHubCreateWebhookPayload {
        if (events != null) {
            events = List.copyOf(events);
        }
    }
}
