package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/actions/runner-groups}, excluding pagination.
 *
 * @param visibleToOrganization Only return runner groups that are allowed to be used by this organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/list-self-hosted-runner-groups-for-enterprise/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListSelfHostedRunnerGroupsForEnterpriseFilter(
        @Nullable String visibleToOrganization) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListSelfHostedRunnerGroupsForEnterpriseFilter empty() {
        return GitHubListSelfHostedRunnerGroupsForEnterpriseFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (visibleToOrganization == null) {
            return Map.of();
        }

        return Map.of("visible_to_organization", visibleToOrganization);
    }
}
