package io.github.piotr_janczukowicz.github.api.reactions;

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

/** GitHub reactions REST resource for API version {@code 2026-03-10}. */
public final class GitHubReactionsApi extends GitHubResourceApi {

    private static final PathTemplate RELEASE_REACTION =
            PathTemplate.of("/repos/{owner}/{repo}/releases/{release_id}/reactions/{reaction_id}");
    private static final PathTemplate PULL_COMMENT_REACTION =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions/{reaction_id}");
    private static final PathTemplate ISSUE_REACTION =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/reactions/{reaction_id}");
    private static final PathTemplate ISSUE_COMMENT_REACTION =
            PathTemplate.of("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions/{reaction_id}");
    private static final PathTemplate COMMIT_COMMENT_REACTION =
            PathTemplate.of("/repos/{owner}/{repo}/comments/{comment_id}/reactions/{reaction_id}");
    private static final PathTemplate RELEASE_REACTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/releases/{release_id}/reactions");
    private static final PathTemplate PULL_COMMENT_REACTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions");
    private static final PathTemplate ISSUE_REACTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/reactions");
    private static final PathTemplate ISSUE_COMMENT_REACTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions");
    private static final PathTemplate COMMIT_COMMENT_REACTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/comments/{comment_id}/reactions");

    private static final JsonType<List<GitHubReaction>> REACTIONS_TYPE = JsonTypes.listOf(GitHubReaction.class);

    /**
     * Reactions resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubReactionsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List reactions for a commit comment ({@code GET /repos/{owner}/{repo}/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId commit comment id
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForCommitComment(final String owner, final String repo, final long commentId)
            throws IOException {
        return listForCommitComment(
                owner, repo, commentId, GitHubListForCommitCommentFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List reactions for a commit comment ({@code GET /repos/{owner}/{repo}/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId commit comment id
     * @param filter query filters
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForCommitComment(
            final String owner, final String repo, final long commentId, final GitHubListForCommitCommentFilter filter)
            throws IOException {
        return listForCommitComment(owner, repo, commentId, filter, GitHubPageRequest.byPage());
    }

    /**
     * List reactions for a commit comment ({@code GET /repos/{owner}/{repo}/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId commit comment id
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForCommitComment(
            final String owner,
            final String repo,
            final long commentId,
            final GitHubListForCommitCommentFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(
                COMMIT_COMMENT_REACTIONS, filter.toQueryParameters(), request, REACTIONS_TYPE, owner, repo, commentId);
    }

    /**
     * List reactions for an issue comment ({@code GET /repos/{owner}/{repo}/issues/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId issue comment id
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForIssueComment(final String owner, final String repo, final long commentId)
            throws IOException {
        return listForIssueComment(
                owner, repo, commentId, GitHubListForCommitCommentFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List reactions for an issue comment ({@code GET /repos/{owner}/{repo}/issues/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId issue comment id
     * @param filter query filters
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForIssueComment(
            final String owner, final String repo, final long commentId, final GitHubListForCommitCommentFilter filter)
            throws IOException {
        return listForIssueComment(owner, repo, commentId, filter, GitHubPageRequest.byPage());
    }

    /**
     * List reactions for an issue comment ({@code GET /repos/{owner}/{repo}/issues/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId issue comment id
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForIssueComment(
            final String owner,
            final String repo,
            final long commentId,
            final GitHubListForCommitCommentFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(
                ISSUE_COMMENT_REACTIONS, filter.toQueryParameters(), request, REACTIONS_TYPE, owner, repo, commentId);
    }

    /**
     * List reactions for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForIssue(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return listForIssue(
                owner, repo, issueNumber, GitHubListForCommitCommentFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List reactions for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param filter query filters
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForIssue(
            final String owner, final String repo, final int issueNumber, final GitHubListForCommitCommentFilter filter)
            throws IOException {
        return listForIssue(owner, repo, issueNumber, filter, GitHubPageRequest.byPage());
    }

    /**
     * List reactions for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForIssue(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubListForCommitCommentFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(ISSUE_REACTIONS, filter.toQueryParameters(), request, REACTIONS_TYPE, owner, repo, issueNumber);
    }

    /**
     * List reactions for a pull request review comment ({@code GET
     * /repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId pull request review comment id
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForPullRequestReviewComment(
            final String owner, final String repo, final long commentId) throws IOException {
        return listForPullRequestReviewComment(
                owner, repo, commentId, GitHubListForCommitCommentFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List reactions for a pull request review comment ({@code GET
     * /repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId pull request review comment id
     * @param filter query filters
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForPullRequestReviewComment(
            final String owner, final String repo, final long commentId, final GitHubListForCommitCommentFilter filter)
            throws IOException {
        return listForPullRequestReviewComment(owner, repo, commentId, filter, GitHubPageRequest.byPage());
    }

    /**
     * List reactions for a pull request review comment ({@code GET
     * /repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId pull request review comment id
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForPullRequestReviewComment(
            final String owner,
            final String repo,
            final long commentId,
            final GitHubListForCommitCommentFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(
                PULL_COMMENT_REACTIONS, filter.toQueryParameters(), request, REACTIONS_TYPE, owner, repo, commentId);
    }

    /**
     * List reactions for a release ({@code GET /repos/{owner}/{repo}/releases/{release_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForRelease(final String owner, final String repo, final long releaseId)
            throws IOException {
        return listForRelease(owner, repo, releaseId, GitHubListForReleaseFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List reactions for a release ({@code GET /repos/{owner}/{repo}/releases/{release_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @param filter query filters
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForRelease(
            final String owner, final String repo, final long releaseId, final GitHubListForReleaseFilter filter)
            throws IOException {
        return listForRelease(owner, repo, releaseId, filter, GitHubPageRequest.byPage());
    }

    /**
     * List reactions for a release ({@code GET /repos/{owner}/{repo}/releases/{release_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of reactions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, including HTTP 404
     */
    public GitHubPage<GitHubReaction> listForRelease(
            final String owner,
            final String repo,
            final long releaseId,
            final GitHubListForReleaseFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(RELEASE_REACTIONS, filter.toQueryParameters(), request, REACTIONS_TYPE, owner, repo, releaseId);
    }

    /**
     * Create a reaction for a commit comment ({@code POST /repos/{owner}/{repo}/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId commit comment id
     * @param payload reaction to create
     * @return created reaction
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReaction createForCommitComment(
            final String owner,
            final String repo,
            final long commentId,
            final GitHubCreateForCommitCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(COMMIT_COMMENT_REACTIONS, owner, repo, commentId), payload), GitHubReaction.class);
    }

    /**
     * Create a reaction for an issue comment ({@code POST
     * /repos/{owner}/{repo}/issues/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId issue comment id
     * @param payload reaction to create
     * @return created reaction
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReaction createForIssueComment(
            final String owner,
            final String repo,
            final long commentId,
            final GitHubCreateForCommitCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ISSUE_COMMENT_REACTIONS, owner, repo, commentId), payload), GitHubReaction.class);
    }

    /**
     * Create a reaction for an issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload reaction to create
     * @return created reaction
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReaction createForIssue(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubCreateForCommitCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ISSUE_REACTIONS, owner, repo, issueNumber), payload), GitHubReaction.class);
    }

    /**
     * Create a reaction for a pull request review comment ({@code POST
     * /repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId pull request review comment id
     * @param payload reaction to create
     * @return created reaction
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReaction createForPullRequestReviewComment(
            final String owner,
            final String repo,
            final long commentId,
            final GitHubCreateForCommitCommentPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(PULL_COMMENT_REACTIONS, owner, repo, commentId), payload), GitHubReaction.class);
    }

    /**
     * Create a reaction for a release ({@code POST /repos/{owner}/{repo}/releases/{release_id}/reactions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @param payload reaction to create
     * @return created reaction
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReaction createForRelease(
            final String owner, final String repo, final long releaseId, final GitHubCreateForReleasePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(RELEASE_REACTIONS, owner, repo, releaseId), payload), GitHubReaction.class);
    }

    /**
     * Delete a reaction from a commit comment ({@code DELETE
     * /repos/{owner}/{repo}/comments/{comment_id}/reactions/{reaction_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId commit comment id
     * @param reactionId reaction id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteForCommitComment(
            final String owner, final String repo, final long commentId, final long reactionId) throws IOException {

        caller.send(delete(COMMIT_COMMENT_REACTION, owner, repo, commentId, reactionId));
    }

    /**
     * Delete a reaction from an issue comment ({@code DELETE
     * /repos/{owner}/{repo}/issues/comments/{comment_id}/reactions/{reaction_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId issue comment id
     * @param reactionId reaction id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteForIssueComment(
            final String owner, final String repo, final long commentId, final long reactionId) throws IOException {

        caller.send(delete(ISSUE_COMMENT_REACTION, owner, repo, commentId, reactionId));
    }

    /**
     * Delete a reaction from an issue ({@code DELETE
     * /repos/{owner}/{repo}/issues/{issue_number}/reactions/{reaction_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param reactionId reaction id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteForIssue(final String owner, final String repo, final int issueNumber, final long reactionId)
            throws IOException {

        caller.send(delete(ISSUE_REACTION, owner, repo, issueNumber, reactionId));
    }

    /**
     * Delete a reaction from a pull request review comment ({@code DELETE
     * /repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions/{reaction_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param commentId pull request review comment id
     * @param reactionId reaction id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteForPullRequestComment(
            final String owner, final String repo, final long commentId, final long reactionId) throws IOException {

        caller.send(delete(PULL_COMMENT_REACTION, owner, repo, commentId, reactionId));
    }

    /**
     * Delete a reaction from a release ({@code DELETE
     * /repos/{owner}/{repo}/releases/{release_id}/reactions/{reaction_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @param reactionId reaction id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteForRelease(final String owner, final String repo, final long releaseId, final long reactionId)
            throws IOException {

        caller.send(delete(RELEASE_REACTION, owner, repo, releaseId, reactionId));
    }
}
