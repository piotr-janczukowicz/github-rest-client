package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request to revoke a credential type of an enterprise.
 *
 * @param credentialType Credential type to revoke
 * @param revokeCredentials Whether to revoke the credentials as well
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/revoke-credential-type-for-enterprise/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRevokeCredentialTypeForEnterprisePayload(
        GitHubRevokeCredentialTypeForEnterpriseCredentialType credentialType,
        @Nullable Boolean revokeCredentials) {

    public GitHubRevokeCredentialTypeForEnterprisePayload {
        requireNonNull(credentialType, "credentialType");
    }
}
