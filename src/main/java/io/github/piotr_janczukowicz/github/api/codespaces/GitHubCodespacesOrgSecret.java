package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Secrets for a GitHub Codespace of an organization.
 *
 * @param name The name of the secret.
 * @param createdAt The date and time at which the secret was created.
 * @param updatedAt The date and time at which the secret was last updated.
 * @param visibility The type of repositories in the organization that the secret is visible to.
 * @param selectedRepositoriesUrl The API URL at which the list of repositories this secret is visible to can be
 *     retrieved.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/codespaces-org-secret")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespacesOrgSecret(
        String name,
        Instant createdAt,
        Instant updatedAt,
        GitHubCodespacesSecretVisibility visibility,
        @Nullable String selectedRepositoriesUrl) {

    public GitHubCodespacesOrgSecret {
        requireNonNull(name, "name");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(visibility, "visibility");
    }
}
