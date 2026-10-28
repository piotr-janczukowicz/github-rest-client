package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/cost-centers}.
 *
 * @param state Set to active or deleted to only list cost centers in a specific state.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-all-cost-centers/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetAllCostCentersFilter(@Nullable GitHubGetAllCostCentersState state) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetAllCostCentersFilter empty() {
        return GitHubGetAllCostCentersFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (state == null) {
            return Map.of();
        }
        return Map.of("state", state.wireValue());
    }
}
