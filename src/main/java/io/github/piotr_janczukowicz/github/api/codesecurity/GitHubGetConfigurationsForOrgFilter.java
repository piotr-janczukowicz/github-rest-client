package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/code-security/configurations}, excluding pagination.
 *
 * @param targetType The target type of the code security configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/get-configurations-for-org/parameters")
@RecordBuilder
public record GitHubGetConfigurationsForOrgFilter(@Nullable GitHubGetConfigurationsForOrgTargetType targetType) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetConfigurationsForOrgFilter empty() {
        return GitHubGetConfigurationsForOrgFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (targetType == null) {
            return Map.of();
        }

        return Map.of("target_type", targetType.wireValue());
    }
}
