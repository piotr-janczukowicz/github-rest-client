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
 * Query filters for {@code GET /orgs/{org}/secret-scanning/custom-patterns} and {@code GET
 * /repos/{owner}/{repo}/secret-scanning/custom-patterns}, excluding pagination.
 *
 * @param state state filter
 * @param pushProtection push protection filter
 * @param sort sort field
 * @param direction sort direction
 */
@CatalogSchema({
    "operations/secret-scanning/list-org-custom-patterns/parameters",
    "operations/secret-scanning/list-repo-custom-patterns/parameters"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListSecretScanningCustomPatternsFilter(
        @Nullable GitHubSecretScanningCustomPatternState state,
        @Nullable GitHubSecretScanningCustomPatternPushProtection pushProtection,
        @Nullable GitHubSecretScanningCustomPatternSort sort,
        @Nullable GitHubDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListSecretScanningCustomPatternsFilter empty() {
        return GitHubListSecretScanningCustomPatternsFilterBuilder.builder().build();
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
