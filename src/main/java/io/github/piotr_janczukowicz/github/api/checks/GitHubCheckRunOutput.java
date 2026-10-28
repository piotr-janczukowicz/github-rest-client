package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Output of a check run.
 *
 * @param title The title of the check run.
 * @param summary The summary of the check run.
 * @param text The details of the check run.
 * @param annotationsCount Annotations count
 * @param annotationsUrl Annotations URL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-run/properties/output")
public record GitHubCheckRunOutput(
        @Nullable String title,
        @Nullable String summary,
        @Nullable String text,
        int annotationsCount,
        String annotationsUrl) {

    public GitHubCheckRunOutput {
        requireNonNull(annotationsUrl, "annotationsUrl");
    }
}
