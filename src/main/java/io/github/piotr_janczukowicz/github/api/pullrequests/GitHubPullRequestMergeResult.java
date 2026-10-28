package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pull request merge result.
 *
 * @param sha Merge commit SHA
 * @param merged Whether the pull request was merged
 * @param message Result message
 */
@CatalogSchema("components/schemas/pull-request-merge-result")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestMergeResult(String sha, boolean merged, String message) {

    public GitHubPullRequestMergeResult {
        requireNonNull(sha, "sha");
        requireNonNull(message, "message");
    }
}
