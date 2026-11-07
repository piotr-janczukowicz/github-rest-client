package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/cost-centers/memberships}.
 *
 * @param resourceType The type of the resource to look up.
 * @param name The name of the resource: a user login, an organization name, an {@code owner/repo} repository name or an
 *     enterprise team slug.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-cost-center-membership/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCostCenterMembershipFilter(GitHubGetCostCenterMembershipResourceType resourceType, String name) {
    public GitHubGetCostCenterMembershipFilter {
        requireNonNull(resourceType, "resourceType");
        requireNonNull(name, "name");
    }

    /**
     * Filter for one resource.
     *
     * @param resourceType the type of the resource
     * @param name the name of the resource
     * @return filter
     * @throws NullPointerException if {@code resourceType} or {@code name} is {@code null}
     */
    public static GitHubGetCostCenterMembershipFilter of(
            final GitHubGetCostCenterMembershipResourceType resourceType, final String name) {
        return GitHubGetCostCenterMembershipFilterBuilder.builder()
                .resourceType(resourceType)
                .name(name)
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        query.put("resource_type", resourceType.wireValue());
        query.put("name", name);
        return Map.copyOf(query);
    }
}
