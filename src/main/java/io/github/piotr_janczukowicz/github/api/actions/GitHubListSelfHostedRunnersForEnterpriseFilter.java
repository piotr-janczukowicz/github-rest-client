package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/actions/runners}, excluding pagination.
 *
 * @param name The name of a self-hosted runner
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/list-self-hosted-runners-for-enterprise/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListSelfHostedRunnersForEnterpriseFilter(
        @Nullable String name) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListSelfHostedRunnersForEnterpriseFilter empty() {
        return GitHubListSelfHostedRunnersForEnterpriseFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (name == null) {
            return Map.of();
        }

        return Map.of("name", name);
    }
}
