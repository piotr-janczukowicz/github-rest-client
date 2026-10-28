package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Iteration setting for an iteration field.
 *
 * @param id Id
 * @param startDate Start date
 * @param duration Duration in days
 * @param title Title
 * @param completed Whether the iteration has been completed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-iteration-settings")
public record GitHubProjectIterationSetting(
        String id, String startDate, int duration, GitHubProjectFormattedText title, boolean completed) {

    public GitHubProjectIterationSetting {
        requireNonNull(id, "id");
        requireNonNull(startDate, "startDate");
        requireNonNull(title, "title");
    }
}
