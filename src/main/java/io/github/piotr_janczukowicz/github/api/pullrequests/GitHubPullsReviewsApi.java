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
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub pull request reviews REST resource for API version {@code 2026-03-10}. */
public final class GitHubPullsReviewsApi extends GitHubResourceApi {

    private static final PathTemplate PULL_REVIEW =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}");
    private static final PathTemplate PULL_REVIEW_EVENTS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}/events");
    private static final PathTemplate PULL_REVIEW_DISMISSALS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}/dismissals");
    private static final PathTemplate PULL_REVIEW_COMMENTS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}/comments");
    private static final PathTemplate PULL_REVIEWS =
            PathTemplate.of("/repos/{owner}/{repo}/pulls/{pull_number}/reviews");

    private static final JsonType<List<GitHubPullRequestReview>> REVIEWS_TYPE =
            JsonTypes.listOf(GitHubPullRequestReview.class);
    private static final JsonType<List<GitHubReviewComment>> REVIEW_COMMENTS_TYPE =
            JsonTypes.listOf(GitHubReviewComment.class);

    /**
     * Reviews resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPullsReviewsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List reviews for a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/reviews}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @return first HTTP page of reviews
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReview> list(final String owner, final String repo, final int pullNumber)
            throws IOException {
        return list(owner, repo, pullNumber, GitHubPageRequest.byPage());
    }

    /**
     * List reviews for a pull request ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/reviews}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param request pagination token
     * @return first HTTP page of reviews
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPullRequestReview> list(
            final String owner, final String repo, final int pullNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PULL_REVIEWS, Map.of(), request, REVIEWS_TYPE, owner, repo, pullNumber);
    }

    /**
     * Create a review ({@code POST /repos/{owner}/{repo}/pulls/{pull_number}/reviews}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param payload optional review to create
     * @return created review
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReview create(
            final String owner,
            final String repo,
            final int pullNumber,
            final @Nullable GitHubCreateReviewPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(post(PULL_REVIEWS, owner, repo, pullNumber), payload),
                GitHubPullRequestReview.class);
    }

    /**
     * Get a review ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param reviewId review id
     * @return review, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPullRequestReview> get(
            final String owner, final String repo, final int pullNumber, final long reviewId) throws IOException {

        return caller.fetchOrEmpty(get(PULL_REVIEW, owner, repo, pullNumber, reviewId), GitHubPullRequestReview.class);
    }

    /**
     * Update a review ({@code PUT /repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param reviewId review id
     * @param payload updated review body
     * @return updated review
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReview update(
            final String owner,
            final String repo,
            final int pullNumber,
            final long reviewId,
            final GitHubUpdateReviewPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(PULL_REVIEW, owner, repo, pullNumber, reviewId), payload),
                GitHubPullRequestReview.class);
    }

    /**
     * Delete a pending review ({@code DELETE /repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param reviewId review id
     * @return deleted review
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReview deletePending(
            final String owner, final String repo, final int pullNumber, final long reviewId) throws IOException {

        return caller.fetch(delete(PULL_REVIEW, owner, repo, pullNumber, reviewId), GitHubPullRequestReview.class);
    }

    /**
     * List comments for a review ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param reviewId review id
     * @return first HTTP page of legacy review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubReviewComment> listComments(
            final String owner, final String repo, final int pullNumber, final long reviewId) throws IOException {
        return listComments(owner, repo, pullNumber, reviewId, GitHubPageRequest.byPage());
    }

    /**
     * List comments for a review ({@code GET /repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}/comments}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param reviewId review id
     * @param request pagination token
     * @return first HTTP page of legacy review comments
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubReviewComment> listComments(
            final String owner,
            final String repo,
            final int pullNumber,
            final long reviewId,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                PULL_REVIEW_COMMENTS, Map.of(), request, REVIEW_COMMENTS_TYPE, owner, repo, pullNumber, reviewId);
    }

    /**
     * Dismiss a review ({@code PUT /repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}/dismissals}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param reviewId review id
     * @param payload dismissal message
     * @return dismissed review
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReview dismiss(
            final String owner,
            final String repo,
            final int pullNumber,
            final long reviewId,
            final GitHubDismissReviewPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(PULL_REVIEW_DISMISSALS, owner, repo, pullNumber, reviewId), payload),
                GitHubPullRequestReview.class);
    }

    /**
     * Submit a pending review ({@code POST /repos/{owner}/{repo}/pulls/{pull_number}/reviews/{review_id}/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param pullNumber pull request number
     * @param reviewId review id
     * @param payload submit action
     * @return submitted review
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPullRequestReview submit(
            final String owner,
            final String repo,
            final int pullNumber,
            final long reviewId,
            final GitHubSubmitReviewPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(PULL_REVIEW_EVENTS, owner, repo, pullNumber, reviewId), payload),
                GitHubPullRequestReview.class);
    }
}
