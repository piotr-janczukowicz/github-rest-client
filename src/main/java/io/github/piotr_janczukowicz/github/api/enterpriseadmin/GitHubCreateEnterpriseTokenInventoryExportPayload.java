package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Optional filters that scope a token inventory export to a subset of the inventory.
 *
 * @param tokenTypes The credential types to include
 * @param authorizationState Filter by enterprise-access status
 * @param owner Filter to credentials owned by this user, given as a login
 * @param organization Filter to credentials authorized to this organization, given as a login
 * @param application Filter to credentials for this application
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/create-enterprise-token-inventory-export/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateEnterpriseTokenInventoryExportPayload(
        @Nullable List<String> tokenTypes,
        @Nullable GitHubCreateEnterpriseTokenInventoryExportAuthorizationState authorizationState,
        @Nullable String owner,
        @Nullable String organization,
        @Nullable String application) {

    public GitHubCreateEnterpriseTokenInventoryExportPayload {
        if (tokenTypes != null) {
            tokenTypes = List.copyOf(tokenTypes);
        }
    }
}
