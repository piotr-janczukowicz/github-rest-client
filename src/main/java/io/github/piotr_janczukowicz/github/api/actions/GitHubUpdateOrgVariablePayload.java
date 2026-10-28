package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update an organization variable.
 *
 * @param name The name of the variable.
 * @param value The value of the variable.
 * @param visibility The type of repositories in the organization that can access the variable. {@code selected} means
 *     only the repositories specified by {@code selected_repository_ids} can access the variable.
 * @param selectedRepositoryIds An array of repository ids that can access the organization variable. You can only
 *     provide a list of repository ids when the {@code visibility} is set to {@code selected}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/update-org-variable/requestBody")
public record GitHubUpdateOrgVariablePayload(
        @Nullable String name,
        @Nullable String value,
        @Nullable GitHubOrgVariableVisibility visibility,
        @Nullable List<Long> selectedRepositoryIds) {

    public GitHubUpdateOrgVariablePayload {
        if (selectedRepositoryIds != null) {
            selectedRepositoryIds = List.copyOf(selectedRepositoryIds);
        }
    }
}
