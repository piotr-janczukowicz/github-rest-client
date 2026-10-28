package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The public key used for setting user Codespaces' secrets.
 *
 * @param keyId The identifier for the key.
 * @param key The Base64 encoded public key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/codespaces-user-public-key")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespacesUserPublicKey(String keyId, String key) {

    public GitHubCodespacesUserPublicKey {
        requireNonNull(keyId, "keyId");
        requireNonNull(key, "key");
    }
}
