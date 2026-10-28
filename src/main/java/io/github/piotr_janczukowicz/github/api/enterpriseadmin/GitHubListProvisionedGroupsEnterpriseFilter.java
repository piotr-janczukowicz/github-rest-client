package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /scim/v2/enterprises/{enterprise}/Groups}, excluding pagination.
 *
 * @param filter Filters results using the equals query parameter operator ({@code eq}).
 * @param excludedAttributes Excludes the specified attribute from being returned in the results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-provisioned-groups-enterprise/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListProvisionedGroupsEnterpriseFilter(
        @Nullable String filter,
        @JsonProperty("excludedAttributes") @Nullable String excludedAttributes) {

    /**
     * Filter with no query parameter set.
     *
     * @return empty filter
     */
    public static GitHubListProvisionedGroupsEnterpriseFilter empty() {
        return GitHubListProvisionedGroupsEnterpriseFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (filter != null) {
            query.put("filter", filter);
        }
        if (excludedAttributes != null) {
            query.put("excludedAttributes", excludedAttributes);
        }

        return Collections.unmodifiableMap(query);
    }
}
