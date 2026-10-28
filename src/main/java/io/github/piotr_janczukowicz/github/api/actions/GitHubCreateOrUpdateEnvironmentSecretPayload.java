package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ENCRYPTED_VALUE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Create or update an environment Actions secret.
 *
 * @param encryptedValue Value for your secret, encrypted with LibSodium using the public key retrieved from the Get a
 *     environment public key endpoint.
 * @param keyId ID of the key you used to encrypt the secret.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/create-or-update-environment-secret/requestBody")
public record GitHubCreateOrUpdateEnvironmentSecretPayload(String encryptedValue, String keyId) {

    public GitHubCreateOrUpdateEnvironmentSecretPayload {
        encryptedValue = requireMatches(encryptedValue, ENCRYPTED_VALUE, "encryptedValue");
        requireNonNull(keyId, "keyId");
    }

    /**
     * Describes this GitHubCreateOrUpdateEnvironmentSecretPayload without credential material.
     *
     * @return text with {@code encryptedValue} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreateOrUpdateEnvironmentSecretPayload[keyId=" + keyId + ", encryptedValue=<redacted>]";
    }
}
