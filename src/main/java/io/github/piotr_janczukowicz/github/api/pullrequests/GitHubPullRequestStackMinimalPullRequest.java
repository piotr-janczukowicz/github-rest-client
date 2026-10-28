package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Pull request in a stack list.
 *
 * @param number Pull request number
 * @param state Pull request state
 * @param draft Whether the pull request is a draft
 * @param mergedAt When the pull request was merged
 * @param head Head ref and SHA
 */
@CatalogSchema("components/schemas/pull-request-stack-minimal/properties/pull_requests/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPullRequestStackMinimalPullRequest(
        int number,
        GitHubPullRequestStackState state,
        boolean draft,
        @Nullable Instant mergedAt,
        GitHubPullRequestStackMinimalPullRequestHead head) {

    public GitHubPullRequestStackMinimalPullRequest {
        requireNonNull(state, "state");
        requireNonNull(head, "head");
    }
}
