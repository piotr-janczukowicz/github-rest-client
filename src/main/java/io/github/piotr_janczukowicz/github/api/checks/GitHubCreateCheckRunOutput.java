package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Output of a check run when creating it.
 *
 * @param title The title of the check run.
 * @param summary The summary of the check run. This parameter supports Markdown.
 * @param text The details of the check run. This parameter supports Markdown.
 * @param annotations Adds information from your analysis to specific lines of code.
 * @param images Adds images to the output displayed in the GitHub pull request UI.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/create/requestBody/properties/output")
public record GitHubCreateCheckRunOutput(
        String title,
        String summary,
        @Nullable String text,
        @Nullable List<GitHubCreateCheckRunAnnotation> annotations,
        @Nullable List<GitHubCreateCheckRunImage> images) {

    public GitHubCreateCheckRunOutput {
        requireNonNull(title, "title");
        requireNonNull(summary, "summary");
        summary = requireMaxLength(summary, 65_535, "summary");
        text = requireMaxLengthIfPresent(text, 65_535, "text");
        if (annotations != null) {
            annotations = List.copyOf(requireSizeAtMost(annotations, 50, "annotations"));
        }
        if (images != null) {
            images = List.copyOf(images);
        }
    }
}
