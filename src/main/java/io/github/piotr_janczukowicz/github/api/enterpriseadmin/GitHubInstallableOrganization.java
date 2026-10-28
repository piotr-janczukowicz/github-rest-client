package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Enterprise-owned organization on which a GitHub App can be installed.
 *
 * @param id The unique identifier of the organization
 * @param login The organization name
 * @param accessibleRepositoriesUrl The URL that lists the repositories the GitHub App can access
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/installable-organization")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubInstallableOrganization(
        long id, String login, @Nullable String accessibleRepositoriesUrl) {

    public GitHubInstallableOrganization {
        requireNonNull(login, "login");
    }
}
