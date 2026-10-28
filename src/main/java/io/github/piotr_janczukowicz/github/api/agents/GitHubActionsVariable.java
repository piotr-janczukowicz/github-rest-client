package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Actions variable.
 *
 * @param name The name of the variable.
 * @param value The value of the variable.
 * @param createdAt The date and time at which the variable was created.
 * @param updatedAt The date and time at which the variable was last updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-variable")
public record GitHubActionsVariable(String name, String value, Instant createdAt, Instant updatedAt) {

    public GitHubActionsVariable {
        requireNonNull(name, "name");
        requireNonNull(value, "value");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
