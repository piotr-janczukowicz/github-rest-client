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
 * Query filters for {@code get-manage-maintenance}.
 *
 * @param uuid The UUID of the node
 * @param clusterRoles The cluster roles of the nodes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/get-manage-maintenance/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
public record GitHubGetManageMaintenanceFilter(
        @Nullable String uuid,
        @JsonProperty("cluster_roles") @Nullable String clusterRoles) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetManageMaintenanceFilter empty() {
        return GitHubGetManageMaintenanceFilterBuilder.builder().build();
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
        if (clusterRoles != null) {
            query.put("cluster_roles", clusterRoles);
        }
        return Collections.unmodifiableMap(query);
    }
}
