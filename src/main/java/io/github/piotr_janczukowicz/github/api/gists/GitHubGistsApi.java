package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Gists REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubGistsApi {

    private final LazyValue<GitHubGistApi> gist;
    private final LazyValue<GitHubGistsCommentsApi> comments;

    /**
     * Gists resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGistsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.gist = new LazyValue<>(() -> new GitHubGistApi(caller));
        this.comments = new LazyValue<>(() -> new GitHubGistsCommentsApi(caller));
    }

    /**
     * Gist operations ({@code subcategory=gists}).
     *
     * @return gist API
     */
    public GitHubGistApi gist() {
        return gist.get();
    }

    /**
     * Gist comment operations ({@code subcategory=comments}).
     *
     * @return gist comments API
     */
    public GitHubGistsCommentsApi comments() {
        return comments.get();
    }
}
