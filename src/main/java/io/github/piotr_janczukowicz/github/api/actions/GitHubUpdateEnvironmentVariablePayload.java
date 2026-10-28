package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update an environment variable.
 *
 * @param name The name of the variable.
 * @param value The value of the variable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/update-environment-variable/requestBody")
public record GitHubUpdateEnvironmentVariablePayload(
        @Nullable String name, @Nullable String value) {}
