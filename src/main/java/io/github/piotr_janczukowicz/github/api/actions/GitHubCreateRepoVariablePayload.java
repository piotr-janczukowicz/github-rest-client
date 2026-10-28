package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Create a repository variable.
 *
 * @param name The name of the variable.
 * @param value The value of the variable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/create-repo-variable/requestBody")
public record GitHubCreateRepoVariablePayload(String name, String value) {

    public GitHubCreateRepoVariablePayload {
        requireNonNull(name, "name");
        requireNonNull(value, "value");
    }
}
