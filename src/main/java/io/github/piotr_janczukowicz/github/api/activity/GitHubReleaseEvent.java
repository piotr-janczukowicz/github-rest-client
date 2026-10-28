package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Release event payload. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/release-event")
@RecordBuilder
public record GitHubReleaseEvent(String action, GitHubReleaseEventRelease release) implements GitHubEventPayload {
    public GitHubReleaseEvent {
        requireNonNull(action, "action");
        requireNonNull(release, "release");
    }
}
