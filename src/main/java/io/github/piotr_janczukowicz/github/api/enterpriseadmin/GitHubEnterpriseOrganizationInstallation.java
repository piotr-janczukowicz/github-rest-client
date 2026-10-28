package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubAppPermissions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Installation of a GitHub App on an enterprise-owned organization.
 *
 * @param id The unique identifier of the installation
 * @param appSlug The URL-friendly name of the GitHub App
 * @param clientId The client identifier of the GitHub App
 * @param repositorySelection Whether all or only selected repositories are accessible
 * @param repositoriesUrl The URL that lists the repositories of the installation
 * @param permissions The permissions granted to the GitHub App
 * @param events The events the GitHub App subscribes to
 * @param createdAt The time the installation was created
 * @param updatedAt The time the installation was last updated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-organization-installation")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubEnterpriseOrganizationInstallation(
        long id,
        @Nullable String appSlug,
        String clientId,
        GitHubEnterpriseOrganizationInstallationRepositorySelection repositorySelection,
        String repositoriesUrl,
        GitHubAppPermissions permissions,
        @Nullable List<String> events,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubEnterpriseOrganizationInstallation {
        requireNonNull(clientId, "clientId");
        requireNonNull(repositorySelection, "repositorySelection");
        requireNonNull(repositoriesUrl, "repositoriesUrl");
        requireNonNull(permissions, "permissions");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
