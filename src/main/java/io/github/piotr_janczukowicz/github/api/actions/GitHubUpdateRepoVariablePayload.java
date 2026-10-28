package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update a repository variable.
 *
 * @param name The name of the variable.
 * @param value The value of the variable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/update-repo-variable/requestBody")
public record GitHubUpdateRepoVariablePayload(
        @Nullable String name, @Nullable String value) {}
