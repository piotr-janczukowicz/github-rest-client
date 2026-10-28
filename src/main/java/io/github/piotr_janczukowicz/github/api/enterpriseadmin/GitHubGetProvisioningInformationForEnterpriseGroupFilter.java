package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /scim/v2/enterprises/{enterprise}/Groups/{scim_group_id}}, excluding pagination.
 *
 * @param excludedAttributes Excludes the specified attribute from being returned in the results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-provisioning-information-for-enterprise-group/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGetProvisioningInformationForEnterpriseGroupFilter(
        @JsonProperty("excludedAttributes") @Nullable String excludedAttributes) {

    /**
     * Filter with no query parameter set.
     *
     * @return empty filter
     */
    public static GitHubGetProvisioningInformationForEnterpriseGroupFilter empty() {
        return GitHubGetProvisioningInformationForEnterpriseGroupFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (excludedAttributes == null) {
            return Map.of();
        }

        return Map.of("excludedAttributes", excludedAttributes);
    }
}
