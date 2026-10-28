package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organization private registry configuration with selected repositories.
 *
 * @param name Name
 * @param registryType Registry type
 * @param authType Authentication type
 * @param url Url
 * @param username Username
 * @param replacesBase Replaces base
 * @param visibility Visibility
 * @param selectedRepositoryIds Selected repository ids
 * @param tenantId Tenant id
 * @param clientId Client id
 * @param awsRegion Aws region
 * @param accountId Account id
 * @param roleName Role name
 * @param domain Domain
 * @param domainOwner Domain owner
 * @param jfrogOidcProviderName Jfrog oidc provider name
 * @param audience Audience
 * @param identityMappingName Identity mapping name
 * @param namespace Namespace
 * @param serviceSlug Service slug
 * @param apiHost Api host
 * @param workloadIdentityProvider Workload identity provider
 * @param serviceAccount Service account
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/org-private-registry-configuration-with-selected-repositories")
public record GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories(
        String name,
        GitHubPrivateRegistryType registryType,
        @Nullable GitHubPrivateRegistryAuthType authType,
        @Nullable String url,
        @Nullable String username,
        @Nullable Boolean replacesBase,
        GitHubPrivateRegistryVisibility visibility,
        @Nullable List<Long> selectedRepositoryIds,
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
        @Nullable String serviceAccount,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories {
        requireNonNull(name, "name");
        requireNonNull(registryType, "registryType");
        requireNonNull(visibility, "visibility");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
