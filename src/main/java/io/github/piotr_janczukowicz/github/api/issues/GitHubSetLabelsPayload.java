package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Set labels for an issue.
 *
 * @param labels The names of the labels to set for the issue. The labels you set replace any existing labels. You can
 *     pass an empty array to remove all labels. Alternatively, you can pass a single label as a `string` or an `array`
 *     of labels directly, but GitHub recommends passing an object with the `labels` key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/set-labels/requestBody")
public record GitHubSetLabelsPayload(List<GitHubSetLabelsLabel> labels) {

    public GitHubSetLabelsPayload {
        labels = List.copyOf(requireNonEmpty(labels, "labels"));
    }
}
