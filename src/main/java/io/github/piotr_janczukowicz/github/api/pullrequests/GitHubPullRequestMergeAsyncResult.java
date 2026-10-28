package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pull request asynchronous merge result.
 *
 * @param status Merge status
 * @param details Result details
 */
@CatalogSchema("components/schemas/pull-request-merge-async-result")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPullRequestMergeAsyncResult(
        GitHubPullRequestMergeAsyncStatus status, GitHubPullRequestMergeAsyncResultDetails details) {

    public GitHubPullRequestMergeAsyncResult {
        requireNonNull(status, "status");
        requireNonNull(details, "details");
    }
}
