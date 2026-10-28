package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Add labels to an issue.
 *
 * @param labels The labels to add to the issue's existing labels. You can also pass an `array` of labels directly, but
 *     GitHub recommends passing an object with the `labels` key. To replace all of the labels for an issue, use "Set
 *     labels for an issue".
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/add-labels/requestBody")
public record GitHubAddLabelsPayload(List<GitHubAddLabelsLabel> labels) {

    public GitHubAddLabelsPayload {
        labels = List.copyOf(requireNonEmpty(labels, "labels"));
    }
}
