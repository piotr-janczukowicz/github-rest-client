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
 * Query filters for {@code get-manage-config-apply-events}.
 *
 * @param lastRequestId The last request id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/get-manage-config-apply-events/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
public record GitHubGetManageConfigApplyEventsFilter(
        @JsonProperty("last_request_id") @Nullable String lastRequestId) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetManageConfigApplyEventsFilter empty() {
        return GitHubGetManageConfigApplyEventsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (lastRequestId != null) {
            query.put("last_request_id", lastRequestId);
        }
        return Collections.unmodifiableMap(query);
    }
}
