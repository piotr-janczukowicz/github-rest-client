package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireBetween;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Parameters of the merge queue rule: timeouts, entry limits, grouping strategy and merge method of the queue. */
@CatalogSchema("components/schemas/repository-rule-merge-queue/properties/parameters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryRuleMergeQueueParameters(
        int checkResponseTimeoutMinutes,
        GitHubRepositoryRuleMergeQueueGroupingStrategy groupingStrategy,
        int maxEntriesToBuild,
        int maxEntriesToMerge,
        GitHubRepositoryRuleMergeQueueMergeMethod mergeMethod,
        int minEntriesToMerge,
        int minEntriesToMergeWaitMinutes) {
    public GitHubRepositoryRuleMergeQueueParameters {
        requireBetween(checkResponseTimeoutMinutes, 1, 360, "checkResponseTimeoutMinutes");
        requireNonNull(groupingStrategy, "groupingStrategy");
        requireBetween(maxEntriesToBuild, 0, 100, "maxEntriesToBuild");
        requireBetween(maxEntriesToMerge, 0, 100, "maxEntriesToMerge");
        requireNonNull(mergeMethod, "mergeMethod");
        requireBetween(minEntriesToMerge, 0, 100, "minEntriesToMerge");
        requireBetween(minEntriesToMergeWaitMinutes, 0, 360, "minEntriesToMergeWaitMinutes");
    }
}
