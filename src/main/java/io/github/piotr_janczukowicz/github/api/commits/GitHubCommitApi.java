package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub commits REST resource for API version {@code 2026-03-10}. */
public final class GitHubCommitApi extends GitHubResourceApi {

    private static final PathTemplate COMMIT_PULLS =
            PathTemplate.of("/repos/{owner}/{repo}/commits/{+commit_sha}/pulls");
    private static final PathTemplate COMMIT_BRANCHES_WHERE_HEAD =
            PathTemplate.of("/repos/{owner}/{repo}/commits/{+commit_sha}/branches-where-head");
    private static final PathTemplate COMPARE = PathTemplate.of("/repos/{owner}/{repo}/compare/{+basehead}");
    private static final PathTemplate COMMIT = PathTemplate.of("/repos/{owner}/{repo}/commits/{+ref}");
    private static final PathTemplate COMMITS = PathTemplate.of("/repos/{owner}/{repo}/commits");

    private static final String DIFF_MEDIA_TYPE = "application/vnd.github.diff";
    private static final String PATCH_MEDIA_TYPE = "application/vnd.github.patch";
    private static final String SHA_MEDIA_TYPE = "application/vnd.github.sha";

    private static final JsonType<List<GitHubCommit>> COMMITS_TYPE = JsonTypes.listOf(GitHubCommit.class);
    private static final JsonType<List<GitHubBranchShort>> BRANCH_SHORT_TYPE =
            JsonTypes.listOf(GitHubBranchShort.class);
    private static final JsonType<List<GitHubPullRequestSimple>> PULL_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubPullRequestSimple.class);

    /**
     * Commits resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCommitApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List commits ({@code GET /repos/{owner}/{repo}/commits}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of commits
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommit> listCommits(final String owner, final String repo) throws IOException {
        return listCommits(owner, repo, GitHubListCommitsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List commits ({@code GET /repos/{owner}/{repo}/commits}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of commits
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommit> listCommits(
            final String owner,
            final String repo,
            final GitHubListCommitsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMITS, filter.toQueryParameters(), request, COMMITS_TYPE, owner, repo);
    }

    /**
     * Get a commit ({@code GET /repos/{owner}/{repo}/commits/{ref}}).
     *
     * <p>Returns the first page of the file listing, which holds at most 300 files. For a commit with more files, use
     * {@link #getCommit(String, String, String, GitHubPageRequest)} to request the later pages.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @return commit, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCommit> getCommit(final String owner, final String repo, final String ref)
            throws IOException {
        return getCommit(owner, repo, ref, GitHubPageRequest.none());
    }

    /**
     * Get a commit ({@code GET /repos/{owner}/{repo}/commits/{ref}}).
     *
     * <p>GitHub lists at most 300 changed files in the first response. A commit with more files returns the rest on
     * further pages, up to 3,000 files; each page repeats the commit data and only {@code files} changes. This method
     * returns one page and does not follow the {@code Link} header, so request later pages with {@code request}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @param request pagination token for the commit file listing
     * @return commit, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank, or {@code request} is
     *     invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCommit> getCommit(
            final String owner, final String repo, final String ref, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetchOrEmpty(
                caller.pageParameters(get(COMMIT, owner, repo, ref), request, GitHubPageRequestKindValidators.PAGE),
                GitHubCommit.class);
    }

    /**
     * Get a commit as a unified diff ({@code GET /repos/{owner}/{repo}/commits/{ref}} with {@code Accept:
     * application/vnd.github.diff}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #getCommit(String, String, String)}; the diff representation is selected by the {@code Accept} media type.
     * The body is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @return diff text, {@code ""} for an empty diff, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> getCommitDiff(final String owner, final String repo, final String ref) throws IOException {
        return fetchTextOrEmpty(get(COMMIT, owner, repo, ref), DIFF_MEDIA_TYPE);
    }

    /**
     * Get a commit as a patch ({@code GET /repos/{owner}/{repo}/commits/{ref}} with {@code Accept:
     * application/vnd.github.patch}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #getCommit(String, String, String)}; the patch representation is selected by the {@code Accept} media
     * type. The body is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @return patch text, {@code ""} for an empty patch, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> getCommitPatch(final String owner, final String repo, final String ref) throws IOException {
        return fetchTextOrEmpty(get(COMMIT, owner, repo, ref), PATCH_MEDIA_TYPE);
    }

    /**
     * Get the SHA-1 of a commit reference ({@code GET /repos/{owner}/{repo}/commits/{ref}} with {@code Accept:
     * application/vnd.github.sha}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #getCommit(String, String, String)}; the representation is selected by the {@code Accept} media type. The
     * body is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @return the commit SHA as text, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> getCommitSha(final String owner, final String repo, final String ref) throws IOException {
        return fetchTextOrEmpty(get(COMMIT, owner, repo, ref), SHA_MEDIA_TYPE);
    }

    /**
     * Compare two commits as a unified diff ({@code GET /repos/{owner}/{repo}/compare/{basehead}} with {@code Accept:
     * application/vnd.github.diff}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #compareCommits(String, String, String)}; the representation is selected by the {@code Accept} media type.
     * The body is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param basehead base and head refs in {@code BASE...HEAD} format
     * @return diff text, {@code ""} for an empty diff, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code basehead} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> compareCommitsDiff(final String owner, final String repo, final String basehead)
            throws IOException {
        return fetchTextOrEmpty(get(COMPARE, owner, repo, basehead), DIFF_MEDIA_TYPE);
    }

    /**
     * Compare two commits as a patch ({@code GET /repos/{owner}/{repo}/compare/{basehead}} with {@code Accept:
     * application/vnd.github.patch}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #compareCommits(String, String, String)}; the representation is selected by the {@code Accept} media type.
     * The body is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param basehead base and head refs in {@code BASE...HEAD} format
     * @return patch text, {@code ""} for an empty patch, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code basehead} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> compareCommitsPatch(final String owner, final String repo, final String basehead)
            throws IOException {
        return fetchTextOrEmpty(get(COMPARE, owner, repo, basehead), PATCH_MEDIA_TYPE);
    }

    /**
     * Compare two commits ({@code GET /repos/{owner}/{repo}/compare/{basehead}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param basehead base and head refs in {@code BASE...HEAD} format
     * @return comparison, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code basehead} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCommitComparison> compareCommits(final String owner, final String repo, final String basehead)
            throws IOException {
        return compareCommits(owner, repo, basehead, GitHubPageRequest.none());
    }

    /**
     * Compare two commits ({@code GET /repos/{owner}/{repo}/compare/{basehead}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param basehead base and head refs in {@code BASE...HEAD} format
     * @param request pagination token
     * @return comparison, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code basehead} is blank, or {@code request}
     *     is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCommitComparison> compareCommits(
            final String owner, final String repo, final String basehead, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetchOrEmpty(
                caller.pageParameters(
                        get(COMPARE, owner, repo, basehead), request, GitHubPageRequestKindValidators.PAGE),
                GitHubCommitComparison.class);
    }

    /**
     * List branches for HEAD commit ({@code GET /repos/{owner}/{repo}/commits/{commit_sha}/branches-where-head}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commitSha commit SHA
     * @return branches where the commit is HEAD
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code commitSha} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubBranchShort> listBranchesForHeadCommit(
            final String owner, final String repo, final String commitSha) throws IOException {

        return caller.fetch(get(COMMIT_BRANCHES_WHERE_HEAD, owner, repo, commitSha), BRANCH_SHORT_TYPE);
    }

    /**
     * List pull requests associated with a commit ({@code GET /repos/{owner}/{repo}/commits/{commit_sha}/pulls}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commitSha commit SHA
     * @return first HTTP page of pull requests
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code commitSha} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestSimple> listPullRequestsAssociatedWithCommit(
            final String owner, final String repo, final String commitSha) throws IOException {
        return listPullRequestsAssociatedWithCommit(owner, repo, commitSha, GitHubPageRequest.byPage());
    }

    /**
     * List pull requests associated with a commit ({@code GET /repos/{owner}/{repo}/commits/{commit_sha}/pulls}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commitSha commit SHA
     * @param request pagination token
     * @return first HTTP page of pull requests
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code commitSha} is blank, or
     *     {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestSimple> listPullRequestsAssociatedWithCommit(
            final String owner, final String repo, final String commitSha, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMIT_PULLS, Map.of(), request, PULL_REQUESTS_TYPE, owner, repo, commitSha);
    }
}
