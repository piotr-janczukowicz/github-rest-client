package io.github.piotr_janczukowicz.github.api.commits;

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
import java.util.Map;
import java.util.Optional;

/** GitHub commit comments REST resource for API version {@code 2026-03-10}. */
public final class GitHubCommitsCommentsApi extends GitHubResourceApi {

    private static final PathTemplate COMMENT = PathTemplate.of("/repos/{owner}/{repo}/comments/{comment_id}");
    private static final PathTemplate COMMIT_COMMENTS =
            PathTemplate.of("/repos/{owner}/{repo}/commits/{+commit_sha}/comments");
    private static final PathTemplate COMMENTS = PathTemplate.of("/repos/{owner}/{repo}/comments");

    private static final JsonType<List<GitHubCommitComment>> COMMIT_COMMENTS_TYPE =
            JsonTypes.listOf(GitHubCommitComment.class);

    /**
     * Commit comments resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCommitsCommentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List commit comments for a commit ({@code GET /repos/{owner}/{repo}/commits/{commit_sha}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commitSha commit SHA
     * @return first HTTP page of commit comments
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code commitSha} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitComment> listCommentsForCommit(
            final String owner, final String repo, final String commitSha) throws IOException {
        return listCommentsForCommit(owner, repo, commitSha, GitHubPageRequest.byPage());
    }

    /**
     * List commit comments for a commit ({@code GET /repos/{owner}/{repo}/commits/{commit_sha}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commitSha commit SHA
     * @param request pagination token
     * @return first HTTP page of commit comments
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code commitSha} is blank, or
     *     {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitComment> listCommentsForCommit(
            final String owner, final String repo, final String commitSha, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMIT_COMMENTS, Map.of(), request, COMMIT_COMMENTS_TYPE, owner, repo, commitSha);
    }

    /**
     * List commit comments for a repository ({@code GET /repos/{owner}/{repo}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of commit comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitComment> listCommitCommentsForRepo(final String owner, final String repo)
            throws IOException {
        return listCommitCommentsForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List commit comments for a repository ({@code GET /repos/{owner}/{repo}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of commit comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitComment> listCommitCommentsForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMENTS, Map.of(), request, COMMIT_COMMENTS_TYPE, owner, repo);
    }

    /**
     * Create a commit comment ({@code POST /repos/{owner}/{repo}/commits/{commit_sha}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commitSha commit SHA
     * @param payload comment to create
     * @return created commit comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code commitSha} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCommitComment createCommitComment(
            final String owner,
            final String repo,
            final String commitSha,
            final GitHubCreateCommitCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(COMMIT_COMMENTS, owner, repo, commitSha), payload), GitHubCommitComment.class);
    }

    /**
     * Get a commit comment ({@code GET /repos/{owner}/{repo}/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @return commit comment, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCommitComment> getCommitComment(final String owner, final String repo, final long commentId)
            throws IOException {

        return caller.fetchOrEmpty(get(COMMENT, owner, repo, commentId), GitHubCommitComment.class);
    }

    /**
     * Update a commit comment ({@code PATCH /repos/{owner}/{repo}/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @param payload updated comment
     * @return updated commit comment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCommitComment updateCommitComment(
            final String owner, final String repo, final long commentId, final GitHubUpdateCommitCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(COMMENT, owner, repo, commentId), payload), GitHubCommitComment.class);
    }

    /**
     * Delete a commit comment ({@code DELETE /repos/{owner}/{repo}/comments/{comment_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId comment id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteCommitComment(final String owner, final String repo, final long commentId) throws IOException {
        caller.send(delete(COMMENT, owner, repo, commentId));
    }
}
