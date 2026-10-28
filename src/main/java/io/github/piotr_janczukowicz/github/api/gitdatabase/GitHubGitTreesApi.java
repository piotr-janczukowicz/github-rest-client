package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Git database trees REST resource for API version {@code 2026-03-10}. */
public final class GitHubGitTreesApi extends GitHubResourceApi {

    private static final PathTemplate TREES = PathTemplate.of("/repos/{owner}/{repo}/git/trees");
    private static final PathTemplate TREE = PathTemplate.of("/repos/{owner}/{repo}/git/trees/{+tree_sha}");

    /**
     * Trees resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGitTreesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a tree ({@code POST /repos/{owner}/{repo}/git/trees}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload tree to create
     * @return created tree
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGitTree createTree(final String owner, final String repo, final GitHubCreateTreePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(TREES, owner, repo), payload), GitHubGitTree.class);
    }

    /**
     * Get a tree ({@code GET /repos/{owner}/{repo}/git/trees/{tree_sha}}), not read recursively. See
     * {@link #getTree(String, String, String, GitHubGetTreeFilter)} for recursion and for a {@code truncated} tree.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param treeSha tree SHA, or a ref name that resolves to a tree
     * @return tree, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGitTree> getTree(final String owner, final String repo, final String treeSha)
            throws IOException {
        return getTree(owner, repo, treeSha, GitHubGetTreeFilter.empty());
    }

    /**
     * Get a tree ({@code GET /repos/{owner}/{repo}/git/trees/{tree_sha}}).
     *
     * <p>A recursive tree is limited by GitHub: when it is too large the response has {@code truncated} set, and the
     * entries are only part of the tree; check {@link GitHubGitTree#truncated()} and read the subtrees one by one then.
     * Use {@link GitHubGetTreeFilter#recursively()} to ask for recursion; any value of {@code recursive},
     * {@code "false"} included, enables it.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param treeSha tree SHA, or a ref name that resolves to a tree
     * @param filter query filters
     * @return tree, or empty on HTTP 404
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code treeSha} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGitTree> getTree(
            final String owner, final String repo, final String treeSha, final GitHubGetTreeFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(TREE, owner, repo, treeSha).withQuery(filter.toQueryParameters()), GitHubGitTree.class);
    }
}
