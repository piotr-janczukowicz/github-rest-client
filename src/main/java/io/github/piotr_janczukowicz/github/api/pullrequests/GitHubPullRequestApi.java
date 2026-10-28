package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubDiffEntry;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub pull requests REST resource for API version {@code 2026-03-10}. */
public final class GitHubPullRequestApi extends GitHubResourceApi {

    private static final PathTemplate PULL_UPDATE_BRANCH =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/update-branch");
    private static final PathTemplate PULL_MERGE_ASYNC_UUID =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/merge-async/{uuid}");
    private static final PathTemplate PULL_MERGE_ASYNC =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/merge-async");
    private static final PathTemplate PULL_MERGE = PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/merge");
    private static final PathTemplate PULL_FILES = PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/files");
    private static final PathTemplate PULL_COMMITS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/commits");
    private static final PathTemplate PULL = PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}");
    private static final PathTemplate PULLS = PathTemplate.of("/repos/{owner}/{repo}/pulls");

    private static final String DIFF_MEDIA_TYPE = "application/vnd.github.diff";
    private static final String PATCH_MEDIA_TYPE = "application/vnd.github.patch";

    private static final JsonType<List<GitHubPullRequestSimple>> PULL_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubPullRequestSimple.class);
    private static final JsonType<List<GitHubCommit>> COMMITS_TYPE = JsonTypes.listOf(GitHubCommit.class);
    private static final JsonType<List<GitHubDiffEntry>> DIFF_ENTRIES_TYPE = JsonTypes.listOf(GitHubDiffEntry.class);

    /**
     * Pull requests resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullRequestApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List pull requests ({@code GET /repos/{owner}/{repo}/pulls}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of pull requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestSimple> list(final String owner, final String repo) throws IOException {
        return list(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List pull requests ({@code GET /repos/{owner}/{repo}/pulls}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of pull requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestSimple> list(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return list(owner, repo, GitHubListPullRequestsFilter.empty(), request);
    }

    /**
     * List pull requests ({@code GET /repos/{owner}/{repo}/pulls}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of pull requests
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestSimple> list(
            final String owner,
            final String repo,
            final GitHubListPullRequestsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PULLS, filter.toQueryParameters(), request, PULL_REQUESTS_TYPE, owner, repo);
    }

    /**
     * Create a pull request ({@code POST /repos/{owner}/{repo}/pulls}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload pull request to create
     * @return created pull request
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequest create(final String owner, final String repo, final GitHubCreatePullRequestPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(PULLS, owner, repo), payload), GitHubPullRequest.class);
    }

    /**
     * Get a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return pull request, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPullRequest> get(final String owner, final String repo, final int pullNumber)
            throws IOException {

        return caller.fetchOrEmpty(get(PULL, owner, repo, pullNumber), GitHubPullRequest.class);
    }

    /**
     * Get a pull request as a unified diff ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}} with {@code Accept:
     * application/vnd.github.diff}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #get(String, String, int)}; the diff representation is selected by the {@code Accept} media type. The body
     * is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return diff text, {@code ""} for an empty diff, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> getDiff(final String owner, final String repo, final int pullNumber) throws IOException {
        return fetchTextOrEmpty(get(PULL, owner, repo, pullNumber), DIFF_MEDIA_TYPE);
    }

    /**
     * Get a pull request as a patch ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}} with {@code Accept:
     * application/vnd.github.patch}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #get(String, String, int)}; the patch representation is selected by the {@code Accept} media type. The
     * body is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return patch text, {@code ""} for an empty patch, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> getPatch(final String owner, final String repo, final int pullNumber) throws IOException {
        return fetchTextOrEmpty(get(PULL, owner, repo, pullNumber), PATCH_MEDIA_TYPE);
    }

    /**
     * Update a pull request ({@code PATCH /repos/{owner}/{repo}/pulls/{pull_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional pull request updates
     * @return updated pull request
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequest update(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubUpdatePullRequestPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(patch(PULL, owner, repo, pullNumber), payload), GitHubPullRequest.class);
    }

    /**
     * List commits on a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/commits}).
     *
     * <p>GitHub lists at most 250 commits per pull request, with at most 100 per page; for a complete list use the
     * repository commits listing.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return first HTTP page of commits
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommit> listCommits(final String owner, final String repo, final int pullNumber)
            throws IOException {
        return listCommits(owner, repo, pullNumber, GitHubPageRequest.byPage());
    }

    /**
     * List commits on a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/commits}).
     *
     * <p>GitHub lists at most 250 commits per pull request, with at most 100 per page; for a complete list use the
     * repository commits listing.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param request pagination token
     * @return first HTTP page of commits
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommit> listCommits(
            final String owner, final String repo, final int pullNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PULL_COMMITS, Map.of(), request, COMMITS_TYPE, owner, repo, pullNumber);
    }

    /**
     * List pull request files ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/files}).
     *
     * <p>GitHub includes at most 3000 files per pull request, with at most 100 per page.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return first HTTP page of diff entries
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDiffEntry> listFiles(final String owner, final String repo, final int pullNumber)
            throws IOException {
        return listFiles(owner, repo, pullNumber, GitHubPageRequest.byPage());
    }

    /**
     * List pull request files ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/files}).
     *
     * <p>GitHub includes at most 3000 files per pull request, with at most 100 per page.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param request pagination token
     * @return first HTTP page of diff entries
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDiffEntry> listFiles(
            final String owner, final String repo, final int pullNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PULL_FILES, Map.of(), request, DIFF_ENTRIES_TYPE, owner, repo, pullNumber);
    }

    /**
     * Check if a pull request has been merged ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/merge}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return {@code true} if merged (HTTP 204), {@code false} on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields {@code false})
     */
    public boolean isMerged(final String owner, final String repo, final int pullNumber) throws IOException {
        return caller.isExisting(get(PULL_MERGE, owner, repo, pullNumber));
    }

    /**
     * Merge a pull request ({@code PUT /repos/{owner}/{repo}/pulls/{pull_number}/merge}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional merge options
     * @return merge result
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestMergeResult merge(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubMergePullRequestPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(put(PULL_MERGE, owner, repo, pullNumber), payload),
                GitHubPullRequestMergeResult.class);
    }

    /**
     * Merge a pull request asynchronously ({@code PUT /repos/{owner}/{repo}/pulls/{pull_number}/merge-async}).
     *
     * <p>Merges the pull request into the base branch in the background or adds it to a merge queue.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional merge options
     * @return asynchronous merge result
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPullRequestMergeAsyncResult mergeAsync(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubMergePullRequestAsyncPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                caller.jsonIfPresent(put(PULL_MERGE_ASYNC, owner, repo, pullNumber), payload),
                GitHubPullRequestMergeAsyncResult.class);
    }

    /**
     * Get the result of an asynchronous merge ({@code GET
     * /repos/{owner}/{repo}/pulls/{pull_number}/merge-async/{uuid}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param uuid asynchronous merge request UUID
     * @return merge result, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code uuid} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubPullRequestMergeAsyncResult> getMergeAsyncResult(
            final String owner, final String repo, final int pullNumber, final String uuid) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchOrEmpty(
                get(PULL_MERGE_ASYNC_UUID, owner, repo, pullNumber, uuid), GitHubPullRequestMergeAsyncResult.class);
    }

    /**
     * Update a pull request branch ({@code PUT /repos/{owner}/{repo}/pulls/{pull_number}/update-branch}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional update options
     * @return branch update result
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestBranchUpdateResult updateBranch(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubUpdatePullRequestBranchPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(put(PULL_UPDATE_BRANCH, owner, repo, pullNumber), payload),
                GitHubPullRequestBranchUpdateResult.class);
    }
}
