package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Option for a single select field in project field responses.
 *
 * @param id Id
 * @param name Name
 * @param description Description
 * @param color Color
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-single-select-options")
public record GitHubProjectSingleSelectOption(
        String id, GitHubProjectFormattedText name, GitHubProjectFormattedText description, String color) {

    public GitHubProjectSingleSelectOption {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(description, "description");
        requireNonNull(color, "color");
    }
}
