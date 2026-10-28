package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create event payload.
 *
 * @param ref The git ref that was created
 * @param refType The type of the created ref
 * @param fullRef The full git ref that was created
 * @param masterBranch The default branch of the repository
 * @param description The repository description
 * @param pusherType The type of the user who created the ref
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/create-event")
@RecordBuilder
public record GitHubCreateEvent(
        String ref,
        String refType,
        @Nullable String fullRef,
        String masterBranch,
        @Nullable String description,
        String pusherType)
        implements GitHubEventPayload {

    public GitHubCreateEvent {
        requireNonNull(ref, "ref");
        requireNonNull(refType, "refType");
        requireNonNull(masterBranch, "masterBranch");
        requireNonNull(pusherType, "pusherType");
    }
}
