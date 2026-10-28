package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Delegated credential authorization revocation was queued.
 *
 * @param message Message describing the queued revocation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/revoke-delegated-credential-authorizations/202")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRevokeDelegatedCredentialAuthorizationsResponse(String message) {

    public GitHubRevokeDelegatedCredentialAuthorizationsResponse {
        requireNonNull(message, "message");
    }
}
