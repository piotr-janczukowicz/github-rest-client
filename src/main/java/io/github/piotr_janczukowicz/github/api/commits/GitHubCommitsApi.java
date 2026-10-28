package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Commits REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubCommitsApi {

    private final LazyValue<GitHubCommitApi> commit;
    private final LazyValue<GitHubCommitsCommentsApi> comments;
    private final LazyValue<GitHubCommitsStatusesApi> statuses;

    /**
     * Commits resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCommitsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.commit = new LazyValue<>(() -> new GitHubCommitApi(caller));
        this.comments = new LazyValue<>(() -> new GitHubCommitsCommentsApi(caller));
        this.statuses = new LazyValue<>(() -> new GitHubCommitsStatusesApi(caller));
    }

    /**
     * Commit operations ({@code subcategory=commits}).
     *
     * @return commit API
     */
    public GitHubCommitApi commit() {
        return commit.get();
    }

    /**
     * Commit comment operations ({@code subcategory=comments}).
     *
     * @return comments API
     */
    public GitHubCommitsCommentsApi comments() {
        return comments.get();
    }

    /**
     * Commit status operations ({@code subcategory=statuses}).
     *
     * @return statuses API
     */
    public GitHubCommitsStatusesApi statuses() {
        return statuses.get();
    }
}
