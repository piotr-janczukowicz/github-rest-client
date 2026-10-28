package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Installation of a GitHub App on an enterprise-owned organization.
 *
 * @param clientId The client identifier of the GitHub App
 * @param repositorySelection Whether all, selected or no repositories are accessible
 * @param repositories The names of the repositories to grant access to
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-apps/create-installation/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCreateInstallationPayload(
        String clientId,
        GitHubCreateInstallationRepositorySelection repositorySelection,
        @Nullable List<String> repositories) {

    public GitHubCreateInstallationPayload {
        requireNonNull(clientId, "clientId");
        requireNonNull(repositorySelection, "repositorySelection");
        if (repositories != null) {
            repositories = List.copyOf(repositories);
        }
    }
}
