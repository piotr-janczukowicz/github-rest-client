package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Default values for a new codespace in a repository.
 *
 * @param location The default location for a new codespace.
 * @param devcontainerPath Path to the default devcontainer.json configuration.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/pre-flight-with-repo-for-authenticated-user/200/properties/defaults")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespaceDefaults(
        String location, @Nullable String devcontainerPath) {

    public GitHubCodespaceDefaults {
        requireNonNull(location, "location");
    }
}
