package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Fork event payload. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/fork-event")
@RecordBuilder
public record GitHubForkEvent(@Nullable String action, GitHubForkEventForkee forkee) implements GitHubEventPayload {
    public GitHubForkEvent {
        requireNonNull(forkee, "forkee");
    }
}
