package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/code-security/configurations/{configuration_id}/repositories},
 * excluding pagination.
 *
 * @param status A comma-separated list of statuses. If specified, only repositories with these attachment statuses will
 *     be returned. Can be: `all`, `attached`, `attaching`, `removed`, `enforced`, `failed`, `updating`,
 *     `removed_by_enterprise`, `enterprise_enforced`
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/get-repositories-for-enterprise-configuration/parameters")
@RecordBuilder
public record GitHubGetRepositoriesForEnterpriseConfigurationFilter(
        @Nullable String status) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetRepositoriesForEnterpriseConfigurationFilter empty() {
        return GitHubGetRepositoriesForEnterpriseConfigurationFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (status == null) {
            return Map.of();
        }

        return Map.of("status", status);
    }
}
