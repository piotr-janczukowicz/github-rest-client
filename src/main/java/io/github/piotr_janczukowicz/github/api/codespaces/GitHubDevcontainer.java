package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A devcontainer configuration available in a repository.
 *
 * @param path Path to the devcontainer.json configuration.
 * @param name Name of the devcontainer configuration.
 * @param displayName Display name of the devcontainer configuration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/codespaces/list-devcontainers-in-repository-for-authenticated-user/200/properties/devcontainers/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDevcontainer(
        String path, @Nullable String name, @Nullable String displayName) {

    public GitHubDevcontainer {
        requireNonNull(path, "path");
    }
}
