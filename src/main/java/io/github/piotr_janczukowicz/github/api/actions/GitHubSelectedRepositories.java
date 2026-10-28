package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repositories selected to access an organization secret or variable.
 *
 * @param totalCount Total number of repositories
 * @param repositories The repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/actions/list-selected-repos-for-org-secret/200",
    "operations/actions/list-selected-repos-for-org-variable/200"
})
public record GitHubSelectedRepositories(int totalCount, List<GitHubMinimalRepository> repositories) {

    public GitHubSelectedRepositories {
        requireNonNull(repositories, "repositories");
    }
}
