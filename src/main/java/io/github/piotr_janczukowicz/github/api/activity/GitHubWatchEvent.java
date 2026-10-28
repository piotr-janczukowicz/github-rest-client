package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Watch event payload.
 *
 * @param action The action that was performed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/watch-event")
@RecordBuilder
public record GitHubWatchEvent(String action) implements GitHubEventPayload {

    public GitHubWatchEvent {
        requireNonNull(action, "action");
    }
}
