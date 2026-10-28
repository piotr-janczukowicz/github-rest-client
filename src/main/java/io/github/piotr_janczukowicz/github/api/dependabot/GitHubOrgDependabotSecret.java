package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Secrets for GitHub Dependabot for an organization.
 *
 * @param name The name of the secret.
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param visibility Visibility of a secret
 * @param selectedRepositoriesUrl Selected repositories url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-dependabot-secret")
public record GitHubOrgDependabotSecret(
        String name,
        Instant createdAt,
        Instant updatedAt,
        GitHubDependabotSecretVisibility visibility,
        @Nullable String selectedRepositoriesUrl) {

    public GitHubOrgDependabotSecret {
        requireNonNull(name, "name");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(visibility, "visibility");
    }
}
