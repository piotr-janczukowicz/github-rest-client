package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Annotation added to a check run when updating it.
 *
 * @param path The path of the file to add an annotation to.
 * @param startLine The start line of the annotation.
 * @param endLine The end line of the annotation.
 * @param startColumn The start column of the annotation.
 * @param endColumn The end column of the annotation.
 * @param annotationLevel The level of the annotation.
 * @param message A short description of the feedback for these lines of code.
 * @param title The title that represents the annotation.
 * @param rawDetails Details about this annotation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/update/requestBody/properties/output/properties/annotations/items")
public record GitHubUpdateCheckRunAnnotation(
        String path,
        int startLine,
        int endLine,
        @Nullable Integer startColumn,
        @Nullable Integer endColumn,
        GitHubUpdateCheckRunAnnotationLevel annotationLevel,
        String message,
        @Nullable String title,
        @Nullable String rawDetails) {

    public GitHubUpdateCheckRunAnnotation {
        requireNonNull(path, "path");
        requireNonNull(annotationLevel, "annotationLevel");
        requireNonNull(message, "message");
    }
}
