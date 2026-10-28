package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request to revoke the credential authorizations of a user.
 *
 * @param revokeCredentials Whether to revoke the credentials as well
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/revoke-credential-authorizations-for-user/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRevokeCredentialAuthorizationsForUserPayload(
        @Nullable Boolean revokeCredentials) {}
