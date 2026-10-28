package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;

/**
 * Repositories allowed to run GitHub Actions in an organization.
 *
 * @param totalCount Total number of repositories
 * @param repositories The repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-selected-repositories-enabled-github-actions-organization/200")
public record GitHubSelectedRepositoriesEnabledActions(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count")
        int totalCount,

        List<GitHubRepository> repositories) {

    public GitHubSelectedRepositoriesEnabledActions {
        requireNonNull(repositories, "repositories");
    }
}
