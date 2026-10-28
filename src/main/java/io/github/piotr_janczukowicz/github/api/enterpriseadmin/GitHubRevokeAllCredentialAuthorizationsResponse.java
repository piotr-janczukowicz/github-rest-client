package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Revocation request was queued.
 *
 * @param message Message describing the queued revocation
 * @param warning Warning about the queued revocation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/revoke-all-credential-authorizations/202")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRevokeAllCredentialAuthorizationsResponse(
        @Nullable String message, @Nullable String warning) {}
