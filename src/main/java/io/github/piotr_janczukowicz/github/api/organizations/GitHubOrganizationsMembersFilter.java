package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/members}, excluding pagination.
 *
 * <p>{@code filter} is {@link GitHubOrganizationMembersKind}; {@code role} is
 * {@link GitHubOrganizationMembersListRole}.
 *
 * @param filter Members filter
 * @param role Role filter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/orgs/list-members/parameters")
@RecordBuilder
public record GitHubOrganizationsMembersFilter(
        @Nullable GitHubOrganizationMembersKind filter,
        @Nullable GitHubOrganizationMembersListRole role) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubOrganizationsMembersFilter empty() {
        return GitHubOrganizationsMembersFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (filter != null) {
            query.put("filter", filter.wireValue());
        }
        if (role != null) {
            query.put("role", role.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
