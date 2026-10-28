package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List selected repositories for immutable releases enforcement response.
 *
 * @param totalCount Total count
 * @param repositories Repositories
 */
@CatalogSchema("operations/orgs/get-immutable-releases-settings-repositories/200")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubImmutableReleasesSelectedRepositoriesPage(
        int totalCount, List<GitHubMinimalRepository> repositories) {

    public GitHubImmutableReleasesSelectedRepositoriesPage {
        requireNonNull(repositories, "repositories");
    }
}
