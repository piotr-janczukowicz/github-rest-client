package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List selected repositories for an organization Dependabot secret response.
 *
 * @param totalCount Total count
 * @param repositories Repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/list-selected-repos-for-org-secret/200")
public record GitHubDependabotSelectedRepositoriesPage(int totalCount, List<GitHubMinimalRepository> repositories) {

    public GitHubDependabotSelectedRepositoriesPage {
        requireNonNull(repositories, "repositories");
    }
}
