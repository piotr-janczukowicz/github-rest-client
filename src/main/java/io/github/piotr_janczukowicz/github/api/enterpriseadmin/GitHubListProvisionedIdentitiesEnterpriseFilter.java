package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /scim/v2/enterprises/{enterprise}/Users}, excluding pagination.
 *
 * @param filter Filters results using the equals query parameter operator ({@code eq}).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-provisioned-identities-enterprise/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListProvisionedIdentitiesEnterpriseFilter(
        @Nullable String filter) {

    /**
     * Filter with no query parameter set.
     *
     * @return empty filter
     */
    public static GitHubListProvisionedIdentitiesEnterpriseFilter empty() {
        return GitHubListProvisionedIdentitiesEnterpriseFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (filter == null) {
            return Map.of();
        }

        return Map.of("filter", filter);
    }
}
