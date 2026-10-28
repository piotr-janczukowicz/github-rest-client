package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;

/** Pull request stack detail. */
@CatalogSchema({
    "operations/pull-request-stacks/get/200",
    "operations/pull-request-stacks/create/201",
    "operations/pull-request-stacks/add/200",
    "operations/pull-request-stacks/unstack/200"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPullRequestStackDetail(
        long id,
        int number,
        String nodeId,
        String url,
        GitHubPullRequestStackRefBase base,
        boolean open,
        Instant createdAt,
        List<GitHubPullRequestStackPullRequest> pullRequests) {

    public GitHubPullRequestStackDetail {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(base, "base");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(pullRequests, "pullRequests");
    }
}
