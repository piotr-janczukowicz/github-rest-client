package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The public key used for setting Dependabot Secrets.
 *
 * @param keyId The identifier for the key.
 * @param key The Base64 encoded public key.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/dependabot-public-key")
public record GitHubDependabotPublicKey(String keyId, String key) {

    public GitHubDependabotPublicKey {
        requireNonNull(keyId, "keyId");
        requireNonNull(key, "key");
    }
}
