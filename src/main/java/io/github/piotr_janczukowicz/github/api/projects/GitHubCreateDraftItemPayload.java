package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create draft item request body.
 *
 * @param title Title
 * @param body Body
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/projects/create-draft-item-for-org/requestBody",
    "operations/projects/create-draft-item-for-authenticated-user/requestBody"
})
public record GitHubCreateDraftItemPayload(
        String title, @Nullable String body) {

    public GitHubCreateDraftItemPayload {
        requireNonNull(title, "title");
    }
}
