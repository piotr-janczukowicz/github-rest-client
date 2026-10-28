package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A workflow referenced or reused by the initial caller workflow.
 *
 * @param path The path of the referenced workflow
 * @param sha The SHA of the referenced workflow
 * @param ref The ref of the referenced workflow
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/referenced-workflow")
public record GitHubReferencedWorkflow(
        String path, String sha, @Nullable String ref) {

    public GitHubReferencedWorkflow {
        requireNonNull(path, "path");
        requireNonNull(sha, "sha");
    }
}
