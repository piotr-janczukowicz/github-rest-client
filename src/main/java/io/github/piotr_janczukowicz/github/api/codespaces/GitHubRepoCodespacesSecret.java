package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Set repository secrets for GitHub Codespaces.
 *
 * @param name The name of the secret.
 * @param createdAt The date and time at which the secret was created.
 * @param updatedAt The date and time at which the secret was last updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/repo-codespaces-secret")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepoCodespacesSecret(String name, Instant createdAt, Instant updatedAt) {

    public GitHubRepoCodespacesSecret {
        requireNonNull(name, "name");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
