package io.github.piotr_janczukowicz.github.api.issues;

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

/** GitHub issue events REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesEventsApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_EVENTS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/events");
    private static final PathTemplate EVENT = PathTemplate.of("/repos/{owner}/{repo}/issues/events/{event_id}");
    private static final PathTemplate EVENTS = PathTemplate.of("/repos/{owner}/{repo}/issues/events");

    private static final JsonType<List<GitHubIssueEvent>> EVENTS_TYPE = JsonTypes.listOf(GitHubIssueEvent.class);

    /**
     * Events resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesEventsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue events for a repository ({@code GET /repos/{owner}/{repo}/issues/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueEvent> listEventsForRepo(final String owner, final String repo) throws IOException {
        return listEventsForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List issue events for a repository ({@code GET /repos/{owner}/{repo}/issues/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueEvent> listEventsForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(EVENTS, Map.of(), request, EVENTS_TYPE, owner, repo);
    }

    /**
     * Get an issue event ({@code GET /repos/{owner}/{repo}/issues/events/{event_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param eventId event id
     * @return event, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubIssueEvent> getEvent(final String owner, final String repo, final long eventId)
            throws IOException {

        return caller.fetchOrEmpty(get(EVENT, owner, repo, eventId), GitHubIssueEvent.class);
    }

    /**
     * List issue events ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueEvent> listEvents(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return listEvents(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List issue events ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/events}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueEvent> listEvents(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_EVENTS, Map.of(), request, EVENTS_TYPE, owner, repo, issueNumber);
    }
}
