package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Organization programmatic access grant.
 *
 * @param id Unique identifier of the fine-grained personal access token grant
 * @param owner Token owner
 * @param repositorySelection Type of repository selection
 * @param repositoriesUrl URL to the list of repositories the token can access
 * @param permissions Permissions granted, categorized by type
 * @param accessGrantedAt Date and time when access was approved
 * @param tokenId Unique identifier of the user's token
 * @param tokenName Name given to the user's token
 * @param tokenExpired Whether the associated token has expired
 * @param tokenExpiresAt Date and time when the associated token expires
 * @param tokenLastUsedAt Date and time when the associated token was last used
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-programmatic-access-grant")
public record GitHubOrganizationPatGrant(
        long id,
        GitHubSimpleUser owner,
        GitHubOrganizationPatRepositorySelection repositorySelection,
        String repositoriesUrl,
        GitHubOrganizationPatPermissions permissions,

        @SuppressSchemaTypeMismatch("dump type is string or null and omits format date-time")
        Instant accessGrantedAt,

        long tokenId,
        String tokenName,
        boolean tokenExpired,

        @SuppressSchemaTypeMismatch("dump type is string or null and omits format date-time") @Nullable Instant tokenExpiresAt,

        @SuppressSchemaTypeMismatch("dump type is string or null and omits format date-time") @Nullable Instant tokenLastUsedAt) {

    public GitHubOrganizationPatGrant {
        requireNonNull(owner, "owner");
        requireNonNull(repositorySelection, "repositorySelection");
        requireNonNull(repositoriesUrl, "repositoriesUrl");
        requireNonNull(permissions, "permissions");
        requireNonNull(accessGrantedAt, "accessGrantedAt");
        requireNonNull(tokenName, "tokenName");
    }
}
