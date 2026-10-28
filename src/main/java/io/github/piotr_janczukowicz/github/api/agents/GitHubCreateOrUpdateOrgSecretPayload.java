package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ENCRYPTED_VALUE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create or update an organization Actions secret.
 *
 * @param encryptedValue Value for your secret, encrypted with LibSodium using the public key retrieved from the Get an
 *     organization public key endpoint.
 * @param keyId ID of the key you used to encrypt the secret.
 * @param visibility Which type of organization repositories have access to the organization secret. {@code selected}
 *     means only the repositories specified by {@code selected_repository_ids} can access the secret.
 * @param selectedRepositoryIds An array of repository ids that can access the organization secret. You can only provide
 *     a list of repository ids when the {@code visibility} is set to {@code selected}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agents/create-or-update-org-secret/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateOrUpdateOrgSecretPayload(
        String encryptedValue,
        String keyId,
        GitHubOrgSecretVisibility visibility,
        @Nullable List<Long> selectedRepositoryIds) {

    public GitHubCreateOrUpdateOrgSecretPayload {
        encryptedValue = requireMatches(encryptedValue, ENCRYPTED_VALUE, "encryptedValue");
        requireNonNull(keyId, "keyId");
        requireNonNull(visibility, "visibility");
        if (selectedRepositoryIds != null) {
            selectedRepositoryIds = List.copyOf(selectedRepositoryIds);
        }
    }

    /**
     * Describes this GitHubCreateOrUpdateOrgSecretPayload without credential material.
     *
     * @return text with {@code encryptedValue} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreateOrUpdateOrgSecretPayload[keyId=" + keyId + ", visibility=" + visibility
                + ", selectedRepositoryIds=" + selectedRepositoryIds + ", encryptedValue=<redacted>]";
    }
}
