package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Iteration configuration for add-field requests.
 *
 * @param startDate Start date of the first iteration
 * @param duration Default iteration duration in days
 * @param iterations Optional explicit iterations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-field-iteration-configuration")
public record GitHubProjectFieldIterationConfiguration(
        String startDate, int duration, @Nullable List<GitHubProjectFieldIterationConfigurationIteration> iterations) {

    public GitHubProjectFieldIterationConfiguration {
        requireNonNull(startDate, "startDate");
        if (iterations != null) {
            iterations = List.copyOf(iterations);
        }
    }
}
