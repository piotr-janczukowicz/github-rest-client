package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List selected repositories for an organization Actions variable response.
 *
 * @param totalCount Total count
 * @param repositories Repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agents/list-selected-repos-for-org-variable/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOrgVariableSelectedRepositoriesPage(int totalCount, List<GitHubMinimalRepository> repositories) {

    public GitHubOrgVariableSelectedRepositoriesPage {
        requireNonNull(repositories, "repositories");
    }
}
