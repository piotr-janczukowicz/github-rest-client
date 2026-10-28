package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * A credential or GitHub App installation in an enterprise's token inventory.
 *
 * @param inventoryId Opaque identifier for retrieving this item within the enterprise.
 * @param credentialId The credential's ID for audit-log correlation
 * @param hashedToken Base64-encoded SHA-256 hash of the token
 * @param fingerprint The SSH key's SHA-256 fingerprint
 * @param itemType The type of the inventory item
 * @param credentialType The type of the credential
 * @param displayName The display name of the credential
 * @param owner The user who owns the credential
 * @param ownerType The type of credential owner
 * @param application The application of the credential
 * @param credentialState The state of the credential
 * @param authorizationState The enterprise-access status of the credential
 * @param effectiveAccessState Whether the credential has effective access
 * @param stateReason The reason for the credential state
 * @param createdAt When the credential was created
 * @param lastUsedAt When the credential was last used
 * @param expiresAt When the credential expires
 * @param nextExpiresAt When the next credential expires
 * @param credentialInstanceCount The number of credential instances
 * @param enterpriseAuthorized Whether the item is authorized directly at the enterprise level
 * @param authorizationCount The number of authorizing organizations, plus one when enterprise-authorized
 * @param authorizedOrganizations The organizations that authorized the credential
 * @param ageDays The age of the credential in whole days
 * @param neverExpires Whether this is an active credential with no expiration
 * @param pastExpirationPolicy Whether the credential exceeds a configured lifetime limit
 * @param pastExpirationPolicyBasis The basis for past expiration policy
 * @param expiryUnknown Whether the expiration of the credential could not be determined
 * @param scopes The OAuth scopes recorded for the token
 * @param permissions The permissions by resource
 * @param repositorySelection The repository selection for the credential
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-token-inventory-item")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseTokenInventoryItem(
        String inventoryId,
        @Nullable Long credentialId,
        @Nullable String hashedToken,
        @Nullable String fingerprint,
        GitHubEnterpriseTokenInventoryItemType itemType,
        GitHubEnterpriseTokenInventoryItemCredentialType credentialType,
        @Nullable String displayName,
        @Nullable GitHubEnterpriseTokenInventoryItemOwner owner,
        @Nullable GitHubEnterpriseTokenInventoryItemOwnerType ownerType,
        @Nullable GitHubEnterpriseTokenInventoryItemApplication application,
        GitHubEnterpriseTokenInventoryItemCredentialState credentialState,
        GitHubEnterpriseTokenInventoryItemAuthorizationState authorizationState,
        GitHubEnterpriseTokenInventoryItemEffectiveAccessState effectiveAccessState,
        @Nullable String stateReason,
        @Nullable Instant createdAt,
        @Nullable Instant lastUsedAt,
        @Nullable Instant expiresAt,
        @Nullable Instant nextExpiresAt,
        @Nullable Integer credentialInstanceCount,
        boolean enterpriseAuthorized,
        int authorizationCount,
        List<GitHubEnterpriseTokenInventoryItemAuthorizedOrganization> authorizedOrganizations,
        @Nullable Integer ageDays,
        @Nullable Boolean neverExpires,
        @Nullable Boolean pastExpirationPolicy,
        @Nullable GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis pastExpirationPolicyBasis,
        @Nullable Boolean expiryUnknown,
        @Nullable List<String> scopes,
        @Nullable Map<String, String> permissions,
        @Nullable GitHubEnterpriseTokenInventoryItemRepositorySelection repositorySelection) {

    public GitHubEnterpriseTokenInventoryItem {
        requireNonNull(inventoryId, "inventoryId");
        requireNonNull(itemType, "itemType");
        requireNonNull(credentialType, "credentialType");
        requireNonNull(credentialState, "credentialState");
        requireNonNull(authorizationState, "authorizationState");
        requireNonNull(effectiveAccessState, "effectiveAccessState");
        requireNonNull(authorizedOrganizations, "authorizedOrganizations");
    }
}
