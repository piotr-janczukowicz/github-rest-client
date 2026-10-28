package io.github.piotr_janczukowicz.github.api.projects;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Single-select option for add-field requests.
 *
 * @param name Display name
 * @param color Color
 * @param description Description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/projects-v2-field-single-select-option")
public record GitHubProjectFieldSingleSelectOption(
        @Nullable String name,
        @Nullable GitHubProjectFieldSingleSelectColor color,
        @Nullable String description) {}
