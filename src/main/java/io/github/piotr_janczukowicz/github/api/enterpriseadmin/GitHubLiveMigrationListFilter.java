package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code live-migration-list}, excluding the {@code after} cursor, which the listing takes as an
 * argument.
 *
 * @param pageSize Number of migrations per page
 * @param status Filter migrations by status
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/live-migration-list/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
public record GitHubLiveMigrationListFilter(
        @Nullable Integer pageSize, @Nullable GitHubLiveMigrationListStatus status) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubLiveMigrationListFilter empty() {
        return GitHubLiveMigrationListFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (pageSize != null) {
            query.put("page_size", pageSize.toString());
        }
        if (status != null) {
            query.put("status", status.wireValue());
        }
        return Collections.unmodifiableMap(query);
    }
}
