package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GhesVersions;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code get-manage-upgrade-status}.
 *
 * @param uuid The UUID of the node
 * @param isVerbose Whether to include the steps
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/get-manage-upgrade-status/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
@GhesVersions(since = "3.22")
public record GitHubGetManageUpgradeStatusFilter(
        @Nullable String uuid,
        @JsonProperty("is_verbose") @Nullable Boolean isVerbose) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetManageUpgradeStatusFilter empty() {
        return GitHubGetManageUpgradeStatusFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (uuid != null) {
            query.put("uuid", uuid);
        }
        if (isVerbose != null) {
            query.put("is_verbose", isVerbose.toString());
        }
        return Collections.unmodifiableMap(query);
    }
}
