package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Repository that a GitHub App installation can access.
 *
 * @param id The unique identifier of the repository
 * @param name The name of the repository
 * @param fullName The full name of the repository, including the owner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/accessible-repository")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAccessibleRepository(long id, String name, String fullName) {

    public GitHubAccessibleRepository {
        requireNonNull(name, "name");
        requireNonNull(fullName, "fullName");
    }
}
