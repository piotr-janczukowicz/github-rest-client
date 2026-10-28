package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * GitHubCreateImpersonationOAuthTokenPayload
 *
 * @param scopes A list of
 *     [scopes](https://docs.github.com/enterprise-server@3.22/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/create-impersonation-o-auth-token/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubCreateImpersonationOAuthTokenPayload(List<String> scopes) {
    public GitHubCreateImpersonationOAuthTokenPayload {
        requireNonNull(scopes, "scopes");
        scopes = List.copyOf(scopes);
    }
}
