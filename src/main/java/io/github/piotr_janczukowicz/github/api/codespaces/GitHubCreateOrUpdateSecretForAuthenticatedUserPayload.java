package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ENCRYPTED_VALUE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatchesIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create or update a user Codespaces secret.
 *
 * @param encryptedValue Value for your secret, encrypted with LibSodium using the public key retrieved from the Get the
 *     public key for the authenticated user endpoint.
 * @param keyId ID of the key you used to encrypt the secret.
 * @param selectedRepositoryIds An array of repository ids that can access the user secret. You can manage the list of
 *     accessible repositories with the Set selected repositories for a user secret endpoint.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/create-or-update-secret-for-authenticated-user/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateOrUpdateSecretForAuthenticatedUserPayload(
        @Nullable String encryptedValue,
        String keyId,
        @Nullable @CatalogSimpleXor("integer|string") List<String> selectedRepositoryIds) {

    public GitHubCreateOrUpdateSecretForAuthenticatedUserPayload {
        encryptedValue = requireMatchesIfPresent(encryptedValue, ENCRYPTED_VALUE, "encryptedValue");
        requireNonNull(keyId, "keyId");
        if (selectedRepositoryIds != null) {
            selectedRepositoryIds = List.copyOf(selectedRepositoryIds);
        }
    }

    /**
     * Describes this GitHubCreateOrUpdateSecretForAuthenticatedUserPayload without credential material.
     *
     * @return text with {@code encryptedValue} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreateOrUpdateSecretForAuthenticatedUserPayload[keyId=" + keyId + ", selectedRepositoryIds="
                + selectedRepositoryIds + ", encryptedValue=<redacted>]";
    }
}
