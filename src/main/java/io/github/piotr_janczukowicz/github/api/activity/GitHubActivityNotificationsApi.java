package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub activity notifications REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubActivityNotificationsApi extends GitHubResourceApi {

    private static final PathTemplate NOTIFICATIONS = PathTemplate.of("/notifications");
    private static final PathTemplate THREAD = PathTemplate.of("/notifications/threads/{thread_id}");
    private static final PathTemplate THREAD_SUBSCRIPTION =
            PathTemplate.of("/notifications/threads/{thread_id}/subscription");
    private static final PathTemplate REPO_NOTIFICATIONS = PathTemplate.of("/repos/{owner}/{repo}/notifications");

    private static final JsonType<List<GitHubThread>> THREADS_TYPE = JsonTypes.listOf(GitHubThread.class);

    private static final int NOTIFICATIONS_MAX_PER_PAGE = 50;

    private static final GitHubListSpec<GitHubThread> NOTIFICATIONS_SPEC =
            GitHubListSpec.pages(THREADS_TYPE).withMaxPerPage(NOTIFICATIONS_MAX_PER_PAGE);

    /**
     * Notifications resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActivityNotificationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List notifications for the authenticated user ({@code GET /notifications}).
     *
     * @return first HTTP page of threads
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listNotificationsForAuthenticatedUser() throws IOException {
        return listNotificationsForAuthenticatedUser(
                GitHubListNotificationsForAuthenticatedUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List notifications for the authenticated user ({@code GET /notifications}).
     *
     * @param filter query filters
     * @return first HTTP page of threads
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listNotificationsForAuthenticatedUser(
            final GitHubListNotificationsForAuthenticatedUserFilter filter) throws IOException {
        return listNotificationsForAuthenticatedUser(filter, GitHubPageRequest.byPage());
    }

    /**
     * List notifications for the authenticated user ({@code GET /notifications}).
     *
     * @param request pagination token
     * @return HTTP page of threads
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listNotificationsForAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        return listNotificationsForAuthenticatedUser(
                GitHubListNotificationsForAuthenticatedUserFilter.empty(), request);
    }

    /**
     * List notifications for the authenticated user ({@code GET /notifications}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of threads
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listNotificationsForAuthenticatedUser(
            final GitHubListNotificationsForAuthenticatedUserFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(NOTIFICATIONS, filter.toQueryParameters(), request, NOTIFICATIONS_SPEC);
    }

    /**
     * Mark notifications as read ({@code PUT /notifications}).
     *
     * @param payload optional read state; {@code null} sends no body
     * @return acceptance response (HTTP 202), or empty when GitHub marked everything as read and answered HTTP 205 with
     *     no content
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubMarkNotificationsAsReadResponse> markNotificationsAsRead(
            final @Nullable GitHubMarkNotificationsAsReadPayload payload) throws IOException {
        return caller.fetchUnless(
                caller.jsonIfPresent(put(NOTIFICATIONS), payload), GitHubMarkNotificationsAsReadResponse.class);
    }

    /**
     * Mark notifications as read without a request body ({@code PUT /notifications}).
     *
     * @return acceptance response (HTTP 202), or empty when GitHub marked everything as read and answered HTTP 205 with
     *     no content
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubMarkNotificationsAsReadResponse> markNotificationsAsRead() throws IOException {
        return markNotificationsAsRead(null);
    }

    /**
     * Get a thread ({@code GET /notifications/threads/{thread_id}}).
     *
     * @param threadId thread id
     * @return thread, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubThread> getThread(final long threadId) throws IOException {
        return caller.fetchOrEmpty(get(THREAD, threadId), GitHubThread.class);
    }

    /**
     * Mark a thread as read ({@code PATCH /notifications/threads/{thread_id}}).
     *
     * @param threadId thread id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void markThreadAsRead(final long threadId) throws IOException {
        caller.send(patch(THREAD, threadId));
    }

    /**
     * Mark a thread as done ({@code DELETE /notifications/threads/{thread_id}}).
     *
     * @param threadId thread id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void markThreadAsDone(final long threadId) throws IOException {
        caller.send(delete(THREAD, threadId));
    }

    /**
     * Get a thread subscription for the authenticated user ({@code GET
     * /notifications/threads/{thread_id}/subscription}).
     *
     * @param threadId thread id
     * @return thread subscription, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubThreadSubscription> getThreadSubscriptionForAuthenticatedUser(final long threadId)
            throws IOException {
        return caller.fetchOrEmpty(get(THREAD_SUBSCRIPTION, threadId), GitHubThreadSubscription.class);
    }

    /**
     * Set a thread subscription ({@code PUT /notifications/threads/{thread_id}/subscription}).
     *
     * @param threadId thread id
     * @param payload optional subscription settings; {@code null} sends no body
     * @return thread subscription
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubThreadSubscription setThreadSubscription(
            final long threadId, final @Nullable GitHubSetThreadSubscriptionPayload payload) throws IOException {
        return caller.fetch(
                caller.jsonIfPresent(put(THREAD_SUBSCRIPTION, threadId), payload), GitHubThreadSubscription.class);
    }

    /**
     * Set a thread subscription without a request body ({@code PUT /notifications/threads/{thread_id}/subscription}).
     *
     * @param threadId thread id
     * @return thread subscription
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubThreadSubscription setThreadSubscription(final long threadId) throws IOException {
        return setThreadSubscription(threadId, null);
    }

    /**
     * Delete a thread subscription ({@code DELETE /notifications/threads/{thread_id}/subscription}).
     *
     * @param threadId thread id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteThreadSubscription(final long threadId) throws IOException {
        caller.send(delete(THREAD_SUBSCRIPTION, threadId));
    }

    /**
     * List repository notifications for the authenticated user ({@code GET /repos/{owner}/{repo}/notifications}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of threads
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listRepoNotificationsForAuthenticatedUser(final String owner, final String repo)
            throws IOException {
        return listRepoNotificationsForAuthenticatedUser(
                owner, repo, GitHubListRepoNotificationsForAuthenticatedUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository notifications for the authenticated user ({@code GET /repos/{owner}/{repo}/notifications}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of threads
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listRepoNotificationsForAuthenticatedUser(
            final String owner, final String repo, final GitHubListRepoNotificationsForAuthenticatedUserFilter filter)
            throws IOException {
        return listRepoNotificationsForAuthenticatedUser(owner, repo, filter, GitHubPageRequest.byPage());
    }

    /**
     * List repository notifications for the authenticated user ({@code GET /repos/{owner}/{repo}/notifications}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return HTTP page of threads
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listRepoNotificationsForAuthenticatedUser(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listRepoNotificationsForAuthenticatedUser(
                owner, repo, GitHubListRepoNotificationsForAuthenticatedUserFilter.empty(), request);
    }

    /**
     * List repository notifications for the authenticated user ({@code GET /repos/{owner}/{repo}/notifications}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of threads
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubThread> listRepoNotificationsForAuthenticatedUser(
            final String owner,
            final String repo,
            final GitHubListRepoNotificationsForAuthenticatedUserFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(REPO_NOTIFICATIONS, filter.toQueryParameters(), request, THREADS_TYPE, owner, repo);
    }

    /**
     * Mark repository notifications as read ({@code PUT /repos/{owner}/{repo}/notifications}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload optional read state; {@code null} sends no body
     * @return acceptance response (HTTP 202), or empty when GitHub marked everything as read and answered HTTP 205 with
     *     no content
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubMarkRepoNotificationsAsReadResponse> markRepoNotificationsAsRead(
            final String owner, final String repo, final @Nullable GitHubMarkRepoNotificationsAsReadPayload payload)
            throws IOException {
        return caller.fetchUnless(
                caller.jsonIfPresent(put(REPO_NOTIFICATIONS, owner, repo), payload),
                GitHubMarkRepoNotificationsAsReadResponse.class);
    }

    /**
     * Mark repository notifications as read without a request body ({@code PUT /repos/{owner}/{repo}/notifications}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return acceptance response (HTTP 202), or empty when GitHub marked everything as read and answered HTTP 205 with
     *     no content
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubMarkRepoNotificationsAsReadResponse> markRepoNotificationsAsRead(
            final String owner, final String repo) throws IOException {
        return markRepoNotificationsAsRead(owner, repo, null);
    }
}
