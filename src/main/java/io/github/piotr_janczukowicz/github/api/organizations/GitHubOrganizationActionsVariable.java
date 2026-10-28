package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Organization variable for GitHub Actions.
 *
 * @param name The name of the variable.
 * @param value The value of the variable.
 * @param createdAt The date and time at which the variable was created.
 * @param updatedAt The date and time at which the variable was last updated.
 * @param visibility Visibility of a variable
 * @param selectedRepositoriesUrl Selected repositories url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-actions-variable")
public record GitHubOrganizationActionsVariable(
        String name,
        String value,
        Instant createdAt,
        Instant updatedAt,
        GitHubOrganizationActionsVariableVisibility visibility,
        @Nullable String selectedRepositoriesUrl) {

    public GitHubOrganizationActionsVariable {
        requireNonNull(name, "name");
        requireNonNull(value, "value");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(visibility, "visibility");
    }
}
