package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
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
import org.jspecify.annotations.Nullable;

/** GitHub milestones REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesMilestonesApi extends GitHubResourceApi {

    private static final PathTemplate MILESTONE =
            PathTemplate.of("/repos/{owner}/{repo}/milestones/{milestone_number}");
    private static final PathTemplate MILESTONES = PathTemplate.of("/repos/{owner}/{repo}/milestones");

    private static final JsonType<List<GitHubMilestone>> MILESTONES_TYPE = JsonTypes.listOf(GitHubMilestone.class);

    /**
     * Milestones resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesMilestonesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List milestones ({@code GET /repos/{owner}/{repo}/milestones}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of milestones
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMilestone> listMilestones(final String owner, final String repo) throws IOException {
        return listMilestones(owner, repo, GitHubListMilestonesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List milestones ({@code GET /repos/{owner}/{repo}/milestones}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of milestones
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMilestone> listMilestones(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listMilestones(owner, repo, GitHubListMilestonesFilter.empty(), request);
    }

    /**
     * List milestones ({@code GET /repos/{owner}/{repo}/milestones}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of milestones
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMilestone> listMilestones(
            final String owner,
            final String repo,
            final GitHubListMilestonesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(MILESTONES, filter.toQueryParameters(), request, MILESTONES_TYPE, owner, repo);
    }

    /**
     * Create a milestone ({@code POST /repos/{owner}/{repo}/milestones}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload milestone to create
     * @return created milestone
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubMilestone createMilestone(
            final String owner, final String repo, final GitHubCreateMilestonePayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(MILESTONES, owner, repo), payload), GitHubMilestone.class);
    }

    /**
     * Get a milestone ({@code GET /repos/{owner}/{repo}/milestones/{milestone_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param milestoneNumber milestone number
     * @return milestone, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubMilestone> getMilestone(final String owner, final String repo, final int milestoneNumber)
            throws IOException {

        return caller.fetchOrEmpty(get(MILESTONE, owner, repo, milestoneNumber), GitHubMilestone.class);
    }

    /**
     * Update a milestone ({@code PATCH /repos/{owner}/{repo}/milestones/{milestone_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param milestoneNumber milestone number
     * @return updated milestone
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubMilestone updateMilestone(final String owner, final String repo, final int milestoneNumber)
            throws IOException {
        return updateMilestone(owner, repo, milestoneNumber, null);
    }

    /**
     * Update a milestone ({@code PATCH /repos/{owner}/{repo}/milestones/{milestone_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param milestoneNumber milestone number
     * @param payload optional milestone updates
     * @return updated milestone
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubMilestone updateMilestone(
            final String owner,
            final String repo,
            final int milestoneNumber,
            final @Nullable GitHubUpdateMilestonePayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(patch(MILESTONE, owner, repo, milestoneNumber), payload), GitHubMilestone.class);
    }

    /**
     * Delete a milestone ({@code DELETE /repos/{owner}/{repo}/milestones/{milestone_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param milestoneNumber milestone number
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteMilestone(final String owner, final String repo, final int milestoneNumber) throws IOException {
        caller.send(delete(MILESTONE, owner, repo, milestoneNumber));
    }
}
