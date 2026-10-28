package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Iteration entry in add-field iteration configuration.
 *
 * @param title Title
 * @param startDate Start date
 * @param duration Duration in days
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-field-iteration-configuration/properties/iterations/items")
public record GitHubProjectFieldIterationConfigurationIteration(String title, String startDate, int duration) {

    public GitHubProjectFieldIterationConfigurationIteration {
        requireNonNull(title, "title");
        requireNonNull(startDate, "startDate");
    }
}
