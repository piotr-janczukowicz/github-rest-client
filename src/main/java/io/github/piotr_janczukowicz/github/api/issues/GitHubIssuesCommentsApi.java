package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment;
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

/** GitHub issue comments REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesCommentsApi extends GitHubResourceApi {

    private static final PathTemplate COMMENT = PathTemplate.of("/repos/{owner}/{repo}/issues/comments/{comment_id}");
    private static final PathTemplate COMMENT_PIN =
            PathTemplate.of("/repos/{owner}/{repo}/issues/comments/{comment_id}/pin");
    private static final PathTemplate ISSUE_COMMENTS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/comments");
    private static final PathTemplate COMMENTS = PathTemplate.of("/repos/{owner}/{repo}/issues/comments");

    private static final JsonType<List<GitHubIssueComment>> COMMENTS_TYPE = JsonTypes.listOf(GitHubIssueComment.class);

    /**
     * Comments resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesCommentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue comments for a repository ({@code GET /repos/{owner}/{repo}/issues/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueComment> listCommentsForRepo(final String owner, final String repo)
            throws IOException {
        return listCommentsForRepo(owner, repo, GitHubListCommentsForRepoFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List issue comments for a repository ({@code GET /repos/{owner}/{repo}/issues/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueComment> listCommentsForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listCommentsForRepo(owner, repo, GitHubListCommentsForRepoFilter.empty(), request);
    }

    /**
     * List issue comments for a repository ({@code GET /repos/{owner}/{repo}/issues/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueComment> listCommentsForRepo(
            final String owner,
            final String repo,
            final GitHubListCommentsForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMENTS, filter.toQueryParameters(), request, COMMENTS_TYPE, owner, repo);
    }

    /**
     * Get an issue comment ({@code GET /repos/{owner}/{repo}/issues/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @return comment, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubIssueComment> getComment(final String owner, final String repo, final long commentId)
            throws IOException {

        return caller.fetchOrEmpty(get(COMMENT, owner, repo, commentId), GitHubIssueComment.class);
    }

    /**
     * Update an issue comment ({@code PATCH /repos/{owner}/{repo}/issues/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @param payload updated comment
     * @return updated comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssueComment updateComment(
            final String owner, final String repo, final long commentId, final GitHubUpdateCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(COMMENT, owner, repo, commentId), payload), GitHubIssueComment.class);
    }

    /**
     * Delete an issue comment ({@code DELETE /repos/{owner}/{repo}/issues/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteComment(final String owner, final String repo, final long commentId) throws IOException {
        caller.send(delete(COMMENT, owner, repo, commentId));
    }

    /**
     * Pin an issue comment ({@code PUT /repos/{owner}/{repo}/issues/comments/{comment_id}/pin}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @return pinned comment
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssueComment pinComment(final String owner, final String repo, final long commentId)
            throws IOException {

        return caller.fetch(put(COMMENT_PIN, owner, repo, commentId), GitHubIssueComment.class);
    }

    /**
     * Unpin an issue comment ({@code DELETE /repos/{owner}/{repo}/issues/comments/{comment_id}/pin}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void unpinComment(final String owner, final String repo, final long commentId) throws IOException {
        caller.send(delete(COMMENT_PIN, owner, repo, commentId));
    }

    /**
     * List issue comments ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueComment> listComments(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return listComments(owner, repo, issueNumber, GitHubListCommentsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List issue comments ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueComment> listComments(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        return listComments(owner, repo, issueNumber, GitHubListCommentsFilter.empty(), request);
    }

    /**
     * List issue comments ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueComment> listComments(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubListCommentsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_COMMENTS, filter.toQueryParameters(), request, COMMENTS_TYPE, owner, repo, issueNumber);
    }

    /**
     * Create an issue comment ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload comment to create
     * @return created comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssueComment createComment(
            final String owner, final String repo, final int issueNumber, final GitHubCreateCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ISSUE_COMMENTS, owner, repo, issueNumber), payload), GitHubIssueComment.class);
    }
}
