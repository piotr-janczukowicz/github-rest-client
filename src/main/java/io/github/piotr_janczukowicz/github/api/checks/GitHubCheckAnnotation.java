package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Check Annotation.
 *
 * @param path Path
 * @param startLine Start line
 * @param endLine End line
 * @param startColumn Start column
 * @param endColumn End column
 * @param annotationLevel Annotation level
 * @param title Title
 * @param message Message
 * @param rawDetails Raw details
 * @param blobHref Blob href
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-annotation")
public record GitHubCheckAnnotation(
        String path,
        int startLine,
        int endLine,
        @Nullable Integer startColumn,
        @Nullable Integer endColumn,
        @Nullable String annotationLevel,
        @Nullable String title,
        @Nullable String message,
        @Nullable String rawDetails,
        String blobHref) {

    public GitHubCheckAnnotation {
        requireNonNull(path, "path");
        requireNonNull(blobHref, "blobHref");
    }
}
