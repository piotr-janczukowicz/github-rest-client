package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List selected repositories for a Codespaces secret response.
 *
 * @param totalCount Total count
 * @param repositories Repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/codespaces/list-selected-repos-for-org-secret/200",
    "operations/codespaces/list-repositories-for-secret-for-authenticated-user/200"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespacesSelectedRepositoriesPage(int totalCount, List<GitHubMinimalRepository> repositories) {

    public GitHubCodespacesSelectedRepositoriesPage {
        requireNonNull(repositories, "repositories");
    }
}
