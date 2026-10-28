package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** A branch of the pull request of a pull request event. The label and the user are only in the full pull request. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/pull-request-event/properties/pull_request/properties/head",
    "components/schemas/pull-request-event/properties/pull_request/properties/base",
    "components/schemas/pull-request-review-event/properties/pull_request/properties/head",
    "components/schemas/pull-request-review-event/properties/pull_request/properties/base",
    "components/schemas/pull-request-review-comment-event/properties/pull_request/properties/head",
    "components/schemas/pull-request-review-comment-event/properties/pull_request/properties/base"
})
@RecordBuilder
public record GitHubPullRequestEventPullRequestRef(
        @Nullable String label,
        String ref,
        GitHubPullRequestEventPullRequestRepo repo,
        String sha,
        @Nullable OptionalNull<GitHubSimpleUser> user) {
    public GitHubPullRequestEventPullRequestRef {
        requireNonNull(ref, "ref");
        requireNonNull(repo, "repo");
        requireNonNull(sha, "sha");
    }
}
