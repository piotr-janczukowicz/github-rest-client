package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The public key used for setting Codespaces secrets.
 *
 * @param keyId The identifier for the key.
 * @param key The Base64 encoded public key.
 * @param id The numeric identifier of the key.
 * @param url The API URL of the key.
 * @param title The title of the key.
 * @param createdAt The date the key was created.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/codespaces-public-key")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespacesPublicKey(
        String keyId,
        String key,
        @Nullable Long id,
        @Nullable String url,
        @Nullable String title,
        @Nullable String createdAt) {

    public GitHubCodespacesPublicKey {
        requireNonNull(keyId, "keyId");
        requireNonNull(key, "key");
    }
}
