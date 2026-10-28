package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/settings/billing/advanced-security}, excluding pagination.
 *
 * @param advancedSecurityProduct The security product to get GitHub Advanced Security active committers for.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-github-advanced-security-billing-org/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGetGithubAdvancedSecurityBillingOrgFilter(
        @Nullable GitHubGetAdvancedSecurityBillingOrgProduct advancedSecurityProduct) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetGithubAdvancedSecurityBillingOrgFilter empty() {
        return GitHubGetGithubAdvancedSecurityBillingOrgFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (advancedSecurityProduct == null) {
            return Map.of();
        }
        return Map.of("advanced_security_product", advancedSecurityProduct.wireValue());
    }
}
