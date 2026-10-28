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
 * Query filters for {@code get-manage-config-apply-status}.
 *
 * @param runId The run to report
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/get-manage-config-apply-status/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
public record GitHubGetManageConfigApplyStatusFilter(
        @JsonProperty("run_id") @Nullable String runId) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetManageConfigApplyStatusFilter empty() {
        return GitHubGetManageConfigApplyStatusFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (runId != null) {
            query.put("run_id", runId);
        }
        return Collections.unmodifiableMap(query);
    }
}
