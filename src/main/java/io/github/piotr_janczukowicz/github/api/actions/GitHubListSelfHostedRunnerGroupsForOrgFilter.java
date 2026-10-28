package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/actions/runner-groups}, excluding pagination.
 *
 * @param visibleToRepository Only return runner groups that are allowed to be used by this repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/list-self-hosted-runner-groups-for-org/parameters")
@RecordBuilder
public record GitHubListSelfHostedRunnerGroupsForOrgFilter(
        @Nullable String visibleToRepository) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListSelfHostedRunnerGroupsForOrgFilter empty() {
        return GitHubListSelfHostedRunnerGroupsForOrgFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (visibleToRepository == null) {
            return Map.of();
        }

        return Map.of("visible_to_repository", visibleToRepository);
    }
}
