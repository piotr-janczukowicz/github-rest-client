package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/credentials}, excluding pagination.
 *
 * @param tokenTypes A comma-separated list of credential types to filter by
 * @param authorizationState Filter by enterprise-access status
 * @param owner Filter to credentials owned by this user, given as a login
 * @param organization Filter to credentials authorized to this organization in the enterprise, given as a login
 * @param application Filter to credentials for this application, given as a GitHub App slug or an OAuth App client id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-enterprise-token-inventory/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubListEnterpriseTokenInventoryFilter(
        @Nullable String tokenTypes,
        @Nullable GitHubListEnterpriseTokenInventoryAuthorizationState authorizationState,
        @Nullable String owner,
        @Nullable String organization,
        @Nullable String application) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListEnterpriseTokenInventoryFilter empty() {
        return GitHubListEnterpriseTokenInventoryFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (tokenTypes != null) {
            query.put("token_types", tokenTypes);
        }
        if (authorizationState != null) {
            query.put("authorization_state", authorizationState.wireValue());
        }
        if (owner != null) {
            query.put("owner", owner);
        }
        if (organization != null) {
            query.put("organization", organization);
        }
        if (application != null) {
            query.put("application", application);
        }

        return Collections.unmodifiableMap(query);
    }
}
