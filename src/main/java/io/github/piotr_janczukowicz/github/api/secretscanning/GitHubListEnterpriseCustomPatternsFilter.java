package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/secret-scanning/custom-patterns}, excluding pagination.
 *
 * @param state state filter
 * @param pushProtection push protection filter
 * @param sort sort field
 * @param direction sort direction
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/list-enterprise-custom-patterns/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubListEnterpriseCustomPatternsFilter(
        @Nullable GitHubSecretScanningCustomPatternState state,
        @Nullable GitHubSecretScanningCustomPatternPushProtection pushProtection,
        @Nullable GitHubSecretScanningCustomPatternSort sort,
        @Nullable GitHubDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListEnterpriseCustomPatternsFilter empty() {
        return GitHubListEnterpriseCustomPatternsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (pushProtection != null) {
            query.put("push_protection", pushProtection.wireValue());
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
