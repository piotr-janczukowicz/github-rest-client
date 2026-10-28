package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A label for a self-hosted runner.
 *
 * @param id Unique identifier of the label
 * @param name Name of the label
 * @param type The type of label
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/runner-label")
public record GitHubRunnerLabel(
        @Nullable Long id, String name, @Nullable GitHubRunnerLabelType type) {

    public GitHubRunnerLabel {
        requireNonNull(name, "name");
    }
}
