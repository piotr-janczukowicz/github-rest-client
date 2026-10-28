package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Organization private registries public key.
 *
 * @param keyId Key id
 * @param key Key
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/private-registries/get-org-public-key/200")
public record GitHubPrivateRegistriesPublicKey(String keyId, String key) {

    public GitHubPrivateRegistriesPublicKey {
        requireNonNull(keyId, "keyId");
        requireNonNull(key, "key");
    }
}
