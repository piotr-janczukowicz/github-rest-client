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

/** GitHub issue timeline REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesTimelineApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_TIMELINE =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/timeline");

    private static final JsonType<List<GitHubTimelineEvent>> TIMELINE_TYPE =
            JsonTypes.listOf(GitHubTimelineEvent.class);

    /**
     * Timeline resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesTimelineApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List timeline events for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/timeline}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of timeline events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTimelineEvent> listEventsForTimeline(
            final String owner, final String repo, final int issueNumber) throws IOException {
        return listEventsForTimeline(
                owner, repo, issueNumber, GitHubListEventsForTimelineFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List timeline events for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/timeline}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of timeline events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTimelineEvent> listEventsForTimeline(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        return listEventsForTimeline(owner, repo, issueNumber, GitHubListEventsForTimelineFilter.empty(), request);
    }

    /**
     * List timeline events for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/timeline}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of timeline events
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTimelineEvent> listEventsForTimeline(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubListEventsForTimelineFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_TIMELINE, filter.toQueryParameters(), request, TIMELINE_TYPE, owner, repo, issueNumber);
    }
}
