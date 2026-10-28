package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Git database REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubGitDatabaseApi {

    private final LazyValue<GitHubGitBlobsApi> blobs;
    private final LazyValue<GitHubGitCommitsApi> commits;
    private final LazyValue<GitHubGitRefsApi> refs;
    private final LazyValue<GitHubGitTagsApi> tags;
    private final LazyValue<GitHubGitTreesApi> trees;

    /**
     * Git database resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGitDatabaseApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.blobs = new LazyValue<>(() -> new GitHubGitBlobsApi(caller));
        this.commits = new LazyValue<>(() -> new GitHubGitCommitsApi(caller));
        this.refs = new LazyValue<>(() -> new GitHubGitRefsApi(caller));
        this.tags = new LazyValue<>(() -> new GitHubGitTagsApi(caller));
        this.trees = new LazyValue<>(() -> new GitHubGitTreesApi(caller));
    }

    /**
     * Blob operations ({@code subcategory=blobs}).
     *
     * @return blobs API
     */
    public GitHubGitBlobsApi blobs() {
        return blobs.get();
    }

    /**
     * Commit operations ({@code subcategory=commits}).
     *
     * @return commits API
     */
    public GitHubGitCommitsApi commits() {
        return commits.get();
    }

    /**
     * Reference operations ({@code subcategory=refs}).
     *
     * @return refs API
     */
    public GitHubGitRefsApi refs() {
        return refs.get();
    }

    /**
     * Tag operations ({@code subcategory=tags}).
     *
     * @return tags API
     */
    public GitHubGitTagsApi tags() {
        return tags.get();
    }

    /**
     * Tree operations ({@code subcategory=trees}).
     *
     * @return trees API
     */
    public GitHubGitTreesApi trees() {
        return trees.get();
    }
}
