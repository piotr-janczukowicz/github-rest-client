package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ENCRYPTED_VALUE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update organization private registry.
 *
 * @param registryType The registry type.
 * @param url The URL of the private registry.
 * @param username The username to use when authenticating with the private registry; required for username and password
 *     authentication.
 * @param replacesBase Whether this private registry replaces the base registry (for example, Maven Central) rather than
 *     supplementing it.
 * @param encryptedValue The value for the secret, encrypted with LibSodium using the public key from the get-public-key
 *     endpoint, then Base64-encoded.
 * @param keyId The ID of the public key used to encrypt the secret.
 * @param visibility Which type of organization repositories have access to the private registry.
 * @param selectedRepositoryIds The repository IDs that can access the private registry; only used when
 *     {@code visibility} is {@code selected}.
 * @param authType The authentication type for the private registry.
 * @param tenantId The tenant ID of the Azure AD application, used with OIDC authentication.
 * @param clientId The client ID of the Azure AD application, used with OIDC authentication.
 * @param awsRegion The AWS region, used with OIDC authentication to AWS CodeArtifact.
 * @param accountId The AWS account ID, used with OIDC authentication to AWS CodeArtifact.
 * @param roleName The AWS IAM role name to assume, used with OIDC authentication to AWS CodeArtifact.
 * @param domain The CodeArtifact domain, used with OIDC authentication to AWS CodeArtifact.
 * @param domainOwner The AWS account ID that owns the CodeArtifact domain.
 * @param jfrogOidcProviderName The JFrog OIDC provider name, used with OIDC authentication to JFrog.
 * @param audience The OIDC audience, used with OIDC authentication.
 * @param identityMappingName The JFrog identity mapping name, used with OIDC authentication to JFrog.
 * @param namespace The Cloudsmith namespace (organization), used with OIDC authentication to Cloudsmith.
 * @param serviceSlug The Cloudsmith service slug, used with OIDC authentication to Cloudsmith.
 * @param apiHost The Cloudsmith API host, used with OIDC authentication to Cloudsmith.
 * @param workloadIdentityProvider The GCP workload identity provider, used with OIDC authentication to Google Cloud.
 * @param serviceAccount The GCP service account to impersonate, used with OIDC authentication to Google Cloud.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/private-registries/update-org-private-registry/requestBody")
public record GitHubUpdateOrgPrivateRegistryPayload(
        @Nullable GitHubPrivateRegistryType registryType,
        @Nullable URI url,
        @Nullable OptionalNull<String> username,
        @Nullable Boolean replacesBase,
        @Nullable String encryptedValue,
        @Nullable String keyId,
        @Nullable GitHubPrivateRegistryVisibility visibility,
        @Nullable List<Long> selectedRepositoryIds,
        @Nullable GitHubPrivateRegistryAuthType authType,
        @Nullable String tenantId,
        @Nullable String clientId,
        @Nullable String awsRegion,
        @Nullable String accountId,
        @Nullable String roleName,
        @Nullable String domain,
        @Nullable String domainOwner,
        @Nullable String jfrogOidcProviderName,
        @Nullable String audience,
        @Nullable String identityMappingName,
        @Nullable String namespace,
        @Nullable String serviceSlug,
        @Nullable String apiHost,
        @Nullable String workloadIdentityProvider,
        @Nullable String serviceAccount) {

    public GitHubUpdateOrgPrivateRegistryPayload {
        if (encryptedValue != null) {
            encryptedValue = requireMatches(encryptedValue, ENCRYPTED_VALUE, "encryptedValue");
        }
        if (selectedRepositoryIds != null) {
            selectedRepositoryIds = List.copyOf(selectedRepositoryIds);
        }
    }

    /**
     * Describes this GitHubUpdateOrgPrivateRegistryPayload without credential material.
     *
     * @return text with {@code encryptedValue} redacted
     */
    @Override
    public String toString() {
        return "GitHubUpdateOrgPrivateRegistryPayload[registryType=" + registryType + ", url=" + url + ", username="
                + username + ", visibility=" + visibility + ", keyId=" + keyId + ", encryptedValue=<redacted>]";
    }
}
