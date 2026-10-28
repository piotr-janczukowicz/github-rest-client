package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The public key used for setting Actions Secrets.
 *
 * @param keyId The identifier for the key.
 * @param key The Base64 encoded public key.
 * @param id ID
 * @param url URL
 * @param title Title
 * @param createdAt Created at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-public-key")
public record GitHubActionsPublicKey(
        String keyId,
        String key,
        @Nullable Long id,
        @Nullable String url,
        @Nullable String title,
        @Nullable String createdAt) {

    public GitHubActionsPublicKey {
        requireNonNull(keyId, "keyId");
        requireNonNull(key, "key");
    }
}
