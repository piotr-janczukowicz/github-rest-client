package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ENCRYPTED_VALUE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatchesIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create or update a repository Dependabot secret.
 *
 * @param encryptedValue Value for your secret, encrypted with LibSodium using the public key retrieved from the Get a
 *     repository public key endpoint.
 * @param keyId ID of the key you used to encrypt the secret.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/create-or-update-repo-secret/requestBody")
public record GitHubCreateOrUpdateRepoSecretPayload(
        @Nullable String encryptedValue, @Nullable String keyId) {

    public GitHubCreateOrUpdateRepoSecretPayload {
        encryptedValue = requireMatchesIfPresent(encryptedValue, ENCRYPTED_VALUE, "encryptedValue");
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
