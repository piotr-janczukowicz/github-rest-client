package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/stacks}, excluding pagination.
 *
 * @param pullRequest Filter to the stack containing this pull request number
 */
@CatalogSchema("operations/pull-request-stacks/list/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListPullRequestStacksFilter(@Nullable Integer pullRequest) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListPullRequestStacksFilter empty() {
        return GitHubListPullRequestStacksFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (pullRequest == null) {
            return Map.of();
        }

        return Map.of("pull_request", Integer.toString(pullRequest));
    }
}
