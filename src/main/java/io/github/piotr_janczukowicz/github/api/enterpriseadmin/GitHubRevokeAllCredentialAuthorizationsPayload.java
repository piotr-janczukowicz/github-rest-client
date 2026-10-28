package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request to revoke all credential authorizations of an enterprise.
 *
 * @param revokeCredentials Whether to revoke the credentials as well
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/revoke-all-credential-authorizations/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRevokeAllCredentialAuthorizationsPayload(
        @Nullable Boolean revokeCredentials) {}
