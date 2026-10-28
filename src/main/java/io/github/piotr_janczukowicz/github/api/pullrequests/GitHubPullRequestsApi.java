package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Pulls REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubPullRequestsApi {

    private final LazyValue<GitHubPullRequestApi> pullRequest;
    private final LazyValue<GitHubPullsCommentsApi> comments;
    private final LazyValue<GitHubPullsReviewRequestsApi> reviewRequests;
    private final LazyValue<GitHubPullsReviewsApi> reviews;
    private final LazyValue<GitHubPullsStacksApi> stacks;

    /**
     * Pulls resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullRequestsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.pullRequest = new LazyValue<>(() -> new GitHubPullRequestApi(caller));
        this.comments = new LazyValue<>(() -> new GitHubPullsCommentsApi(caller));
        this.reviewRequests = new LazyValue<>(() -> new GitHubPullsReviewRequestsApi(caller));
        this.reviews = new LazyValue<>(() -> new GitHubPullsReviewsApi(caller));
        this.stacks = new LazyValue<>(() -> new GitHubPullsStacksApi(caller));
    }

    /**
     * Pull request operations ({@code subcategory=pulls}).
     *
     * @return pull request API
     */
    public GitHubPullRequestApi pullRequest() {
        return pullRequest.get();
    }

    /**
     * Pull request review comment operations ({@code subcategory=comments}).
     *
     * @return comments API
     */
    public GitHubPullsCommentsApi comments() {
        return comments.get();
    }

    /**
     * Pull request review request operations ({@code subcategory=review-requests}).
     *
     * @return review requests API
     */
    public GitHubPullsReviewRequestsApi reviewRequests() {
        return reviewRequests.get();
    }

    /**
     * Pull request review operations ({@code subcategory=reviews}).
     *
     * @return reviews API
     */
    public GitHubPullsReviewsApi reviews() {
        return reviews.get();
    }

    /**
     * Pull request stack operations ({@code subcategory=stacks}).
     *
     * <p>The {@code CLOUD} and {@code GHEC} flavors carry this resource.
     *
     * @return stacks API
     */
    public GitHubPullsStacksApi stacks() {
        return stacks.get();
    }
}
