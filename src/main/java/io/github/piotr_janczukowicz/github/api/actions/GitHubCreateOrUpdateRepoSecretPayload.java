package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ENCRYPTED_VALUE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Create or update a repository Actions secret.
 *
 * @param encryptedValue Value for your secret, encrypted with LibSodium using the public key retrieved from the Get a
 *     repository public key endpoint.
 * @param keyId ID of the key you used to encrypt the secret.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/create-or-update-repo-secret/requestBody")
public record GitHubCreateOrUpdateRepoSecretPayload(String encryptedValue, String keyId) {

    public GitHubCreateOrUpdateRepoSecretPayload {
        encryptedValue = requireMatches(encryptedValue, ENCRYPTED_VALUE, "encryptedValue");
        requireNonNull(keyId, "keyId");
    }

    /**
     * Describes this GitHubCreateOrUpdateRepoSecretPayload without credential material.
     *
     * @return text with {@code encryptedValue} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreateOrUpdateRepoSecretPayload[keyId=" + keyId + ", encryptedValue=<redacted>]";
    }
}
