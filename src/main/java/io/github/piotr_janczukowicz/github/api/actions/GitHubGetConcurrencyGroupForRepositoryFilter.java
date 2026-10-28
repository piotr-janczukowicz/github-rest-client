package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastIfPresent;

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
 * Query filters for {@code GET /repos/{owner}/{repo}/actions/concurrency_groups/{concurrency_group_name}}, excluding
 * pagination.
 *
 * @param aheadOfRun Number of workflow runs ahead of this run in the concurrency group
 * @param aheadOfJob Number of jobs ahead of this job in the concurrency group
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/get-concurrency-group-for-repository/parameters")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetConcurrencyGroupForRepositoryFilter(
        @Nullable Integer aheadOfRun, @Nullable Integer aheadOfJob) {

    public GitHubGetConcurrencyGroupForRepositoryFilter {
        requireAtLeastIfPresent(aheadOfRun, 1, "aheadOfRun");
        requireAtLeastIfPresent(aheadOfJob, 1, "aheadOfJob");
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetConcurrencyGroupForRepositoryFilter empty() {
        return GitHubGetConcurrencyGroupForRepositoryFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (aheadOfRun != null) {
            query.put("ahead_of_run", aheadOfRun.toString());
        }
        if (aheadOfJob != null) {
            query.put("ahead_of_job", aheadOfJob.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
