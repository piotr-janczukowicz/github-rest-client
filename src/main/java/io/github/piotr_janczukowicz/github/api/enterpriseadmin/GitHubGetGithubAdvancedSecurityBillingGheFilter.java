package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/advanced-security}, excluding pagination.
 *
 * @param advancedSecurityProduct The security product to get GitHub Advanced Security active committers for
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-github-advanced-security-billing-ghe/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGetGithubAdvancedSecurityBillingGheFilter(
        @Nullable GitHubAdvancedSecurityProduct advancedSecurityProduct) {

    /**
     * Filter with no query parameter set.
     *
     * @return empty filter
     */
    public static GitHubGetGithubAdvancedSecurityBillingGheFilter empty() {
        return GitHubGetGithubAdvancedSecurityBillingGheFilterBuilder.builder().build();
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
