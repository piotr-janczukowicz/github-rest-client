package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Request to revoke a credential type of a user.
 *
 * @param credentialType Credential type to revoke
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/revoke-credential-type-for-user/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRevokeCredentialTypeForUserPayload(GitHubRevokeCredentialTypeForUserCredentialType credentialType) {

    public GitHubRevokeCredentialTypeForUserPayload {
        requireNonNull(credentialType, "credentialType");
    }
}
