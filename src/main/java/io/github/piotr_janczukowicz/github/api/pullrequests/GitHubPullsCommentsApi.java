package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub pull request review comments REST resource for API version {@code 2026-03-10}. */
public final class GitHubPullsCommentsApi extends GitHubResourceApi {

    private static final PathTemplate PULL_COMMENT_REPLIES =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/comments/{comment_id}/replies");
    private static final PathTemplate PULL_COMMENTS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/comments");
    private static final PathTemplate COMMENT = PathTemplate.of("/repos/{owner}/{repo}/pulls/comments/{comment_id}");
    private static final PathTemplate COMMENTS = PathTemplate.of("/repos/{owner}/{repo}/pulls/comments");

    private static final JsonType<List<GitHubPullRequestReviewComment>> REVIEW_COMMENTS_TYPE =
            JsonTypes.listOf(GitHubPullRequestReviewComment.class);

    /**
     * Review comments resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullsCommentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List review comments in a repository ({@code GET /repos/{owner}/{repo}/pulls/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReviewComment> listForRepo(final String owner, final String repo)
            throws IOException {
        return listForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List review comments in a repository ({@code GET /repos/{owner}/{repo}/pulls/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReviewComment> listForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listForRepo(owner, repo, GitHubListRepoReviewCommentsFilter.empty(), request);
    }

    /**
     * List review comments in a repository ({@code GET /repos/{owner}/{repo}/pulls/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReviewComment> listForRepo(
            final String owner,
            final String repo,
            final GitHubListRepoReviewCommentsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMENTS, filter.toQueryParameters(), request, REVIEW_COMMENTS_TYPE, owner, repo);
    }

    /**
     * Get a review comment ({@code GET /repos/{owner}/{repo}/pulls/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId review comment id
     * @return review comment, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPullRequestReviewComment> get(final String owner, final String repo, final long commentId)
            throws IOException {

        return caller.fetchOrEmpty(get(COMMENT, owner, repo, commentId), GitHubPullRequestReviewComment.class);
    }

    /**
     * Update a review comment ({@code PATCH /repos/{owner}/{repo}/pulls/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId review comment id
     * @param payload updated comment body
     * @return updated review comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReviewComment update(
            final String owner, final String repo, final long commentId, final GitHubUpdateReviewCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(COMMENT, owner, repo, commentId), payload), GitHubPullRequestReviewComment.class);
    }

    /**
     * Delete a review comment ({@code DELETE /repos/{owner}/{repo}/pulls/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId review comment id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void delete(final String owner, final String repo, final long commentId) throws IOException {
        caller.send(delete(COMMENT, owner, repo, commentId));
    }

    /**
     * List review comments on a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return first HTTP page of review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReviewComment> list(final String owner, final String repo, final int pullNumber)
            throws IOException {
        return list(owner, repo, pullNumber, GitHubPageRequest.byPage());
    }

    /**
     * List review comments on a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param request pagination token
     * @return first HTTP page of review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReviewComment> list(
            final String owner, final String repo, final int pullNumber, final GitHubPageRequest request)
            throws IOException {
        return list(owner, repo, pullNumber, GitHubListPullReviewCommentsFilter.empty(), request);
    }

    /**
     * List review comments on a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReviewComment> list(
            final String owner,
            final String repo,
            final int pullNumber,
            final GitHubListPullReviewCommentsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                PULL_COMMENTS, filter.toQueryParameters(), request, REVIEW_COMMENTS_TYPE, owner, repo, pullNumber);
    }

    /**
     * Create a review comment ({@code POST /repos/{owner}/{repo}/pulls/{pull_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload review comment to create
     * @return created review comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReviewComment create(
            final String owner, final String repo, final int pullNumber, final GitHubCreateReviewCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(PULL_COMMENTS, owner, repo, pullNumber), payload),
                GitHubPullRequestReviewComment.class);
    }

    /**
     * Create a reply to a review comment ({@code POST
     * /repos/{owner}/{repo}/pulls/{pull_number}/comments/{comment_id}/replies}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param commentId review comment id to reply to
     * @param payload reply body
     * @return created reply
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReviewComment createReply(
            final String owner,
            final String repo,
            final int pullNumber,
            final long commentId,
            final GitHubCreateReviewCommentReplyPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(PULL_COMMENT_REPLIES, owner, repo, pullNumber, commentId), payload),
                GitHubPullRequestReviewComment.class);
    }
}
