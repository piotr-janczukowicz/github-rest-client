package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergedUpstream;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub repository branches REST resource for API version {@code 2026-03-10}. */
public final class GitHubBranchApi extends GitHubResourceApi {

    private static final PathTemplate BRANCHES = PathTemplate.of("/repos/{owner}/{repo}/branches");
    private static final PathTemplate BRANCH = PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}");
    private static final PathTemplate BRANCH_RENAME =
            PathTemplate.of("/repos/{owner}/{repo}/branches/{+branch}/rename");
    private static final PathTemplate MERGES = PathTemplate.of("/repos/{owner}/{repo}/merges");
    private static final PathTemplate MERGE_UPSTREAM = PathTemplate.of("/repos/{owner}/{repo}/merge-upstream");

    private static final JsonType<List<GitHubShortBranch>> SHORT_BRANCHES_TYPE =
            JsonTypes.listOf(GitHubShortBranch.class);

    /**
     * Branches resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBranchApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List branches ({@code GET /repos/{owner}/{repo}/branches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of branches
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubShortBranch> listBranches(final String owner, final String repo) throws IOException {
        return listBranches(owner, repo, GitHubListBranchesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List branches ({@code GET /repos/{owner}/{repo}/branches}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listBranches(String, String, GitHubListBranchesFilter,
     * GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of branches
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubShortBranch> listBranches(
            final String owner, final String repo, final GitHubListBranchesFilter filter) throws IOException {
        return listBranches(owner, repo, filter, GitHubPageRequest.byPage());
    }

    /**
     * List branches ({@code GET /repos/{owner}/{repo}/branches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of branches
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubShortBranch> listBranches(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listBranches(owner, repo, GitHubListBranchesFilter.empty(), request);
    }

    /**
     * List branches ({@code GET /repos/{owner}/{repo}/branches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of branches
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubShortBranch> listBranches(
            final String owner,
            final String repo,
            final GitHubListBranchesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(BRANCHES, filter.toQueryParameters(), request, SHORT_BRANCHES_TYPE, owner, repo);
    }

    /**
     * Get a branch ({@code GET /repos/{owner}/{repo}/branches/{branch}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return branch, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubBranchWithProtection> getBranch(final String owner, final String repo, final String branch)
            throws IOException {

        return caller.fetchOrEmpty(get(BRANCH, owner, repo, branch), GitHubBranchWithProtection.class);
    }

    /**
     * Rename a branch ({@code POST /repos/{owner}/{repo}/branches/{branch}/rename}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch current branch name
     * @param payload new branch name
     * @return renamed branch
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubBranchWithProtection renameBranch(
            final String owner, final String repo, final String branch, final GitHubRenameBranchPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(BRANCH_RENAME, owner, repo, branch), payload), GitHubBranchWithProtection.class);
    }

    /**
     * Merge a branch ({@code POST /repos/{owner}/{repo}/merges}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload merge request
     * @return merge commit, or empty when already merged (HTTP 204) or no body or {@code {}}
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCommit> merge(final String owner, final String repo, final GitHubMergeBranchPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchUnless(
                caller.json(post(MERGES, owner, repo), payload), GitHubCommit.class, HttpStatus.NO_CONTENT);
    }

    /**
     * Merge an upstream branch ({@code POST /repos/{owner}/{repo}/merge-upstream}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload merge upstream request
     * @return merge result
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubMergedUpstream mergeUpstream(
            final String owner, final String repo, final GitHubMergeUpstreamPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(MERGE_UPSTREAM, owner, repo), payload), GitHubMergedUpstream.class);
    }
}
