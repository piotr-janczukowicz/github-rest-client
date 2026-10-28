package io.github.piotr_janczukowicz.github.api.projects;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration for iteration fields on a project field response.
 *
 * @param startDay Day of the week when the iteration starts
 * @param duration Duration of the iteration in days
 * @param iterations Iteration settings
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-field/properties/configuration")
public record GitHubProjectFieldConfiguration(
        @Nullable Integer startDay,
        @Nullable Integer duration,
        @Nullable List<GitHubProjectIterationSetting> iterations) {}
