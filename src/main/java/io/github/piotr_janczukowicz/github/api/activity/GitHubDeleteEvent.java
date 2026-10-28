package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Delete event payload.
 *
 * @param ref The git ref that was deleted
 * @param refType The type of the deleted ref
 * @param fullRef The full git ref that was deleted
 * @param pusherType The type of the user who deleted the ref
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/delete-event")
@RecordBuilder
public record GitHubDeleteEvent(
        String ref, String refType, @Nullable String fullRef, String pusherType) implements GitHubEventPayload {

    public GitHubDeleteEvent {
        requireNonNull(ref, "ref");
        requireNonNull(refType, "refType");
        requireNonNull(pusherType, "pusherType");
    }
}
