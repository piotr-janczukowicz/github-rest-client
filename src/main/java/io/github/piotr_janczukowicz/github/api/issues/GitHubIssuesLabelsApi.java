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
import org.jspecify.annotations.Nullable;

/** GitHub issue labels REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesLabelsApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_LABELS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/labels");
    private static final PathTemplate MILESTONE_LABELS =
            PathTemplate.of("/repos/{owner}/{repo}/milestones/{milestone_number}/labels");
    private static final PathTemplate LABEL = PathTemplate.of("/repos/{owner}/{repo}/labels/{name}");
    private static final PathTemplate LABELS = PathTemplate.of("/repos/{owner}/{repo}/labels");
    private static final PathTemplate ISSUE_LABEL =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/labels/{name}");

    private static final JsonType<List<GitHubLabel>> LABELS_TYPE = JsonTypes.listOf(GitHubLabel.class);

    /**
     * Labels resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesLabelsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List labels for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of labels
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabel> listLabelsOnIssue(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return listLabelsOnIssue(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List labels for an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of labels
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabel> listLabelsOnIssue(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_LABELS, Map.of(), request, LABELS_TYPE, owner, repo, issueNumber);
    }

    /**
     * Add labels to an issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return labels on the issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubLabel> addLabels(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return addLabels(owner, repo, issueNumber, null);
    }

    /**
     * Add labels to an issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload optional labels to add
     * @return labels on the issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubLabel> addLabels(
            final String owner,
            final String repo,
            final int issueNumber,
            final @Nullable GitHubAddLabelsPayload payload)
            throws IOException {

        return caller.fetch(caller.jsonIfPresent(post(ISSUE_LABELS, owner, repo, issueNumber), payload), LABELS_TYPE);
    }

    /**
     * Set labels for an issue ({@code PUT /repos/{owner}/{repo}/issues/{issue_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return labels on the issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public List<GitHubLabel> setLabels(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return setLabels(owner, repo, issueNumber, null);
    }

    /**
     * Set labels for an issue ({@code PUT /repos/{owner}/{repo}/issues/{issue_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload optional labels to set
     * @return labels on the issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public List<GitHubLabel> setLabels(
            final String owner,
            final String repo,
            final int issueNumber,
            final @Nullable GitHubSetLabelsPayload payload)
            throws IOException {

        return caller.fetch(caller.jsonIfPresent(put(ISSUE_LABELS, owner, repo, issueNumber), payload), LABELS_TYPE);
    }

    /**
     * Remove all labels from an issue ({@code DELETE /repos/{owner}/{repo}/issues/{issue_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeAllLabels(final String owner, final String repo, final int issueNumber) throws IOException {
        caller.send(delete(ISSUE_LABELS, owner, repo, issueNumber));
    }

    /**
     * Remove a label from an issue ({@code DELETE /repos/{owner}/{repo}/issues/{issue_number}/labels/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param name label name
     * @return remaining labels on the issue
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubLabel> removeLabel(
            final String owner, final String repo, final int issueNumber, final String name) throws IOException {

        return caller.fetch(delete(ISSUE_LABEL, owner, repo, issueNumber, name), LABELS_TYPE);
    }

    /**
     * List labels for a repository ({@code GET /repos/{owner}/{repo}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of labels
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabel> listLabelsForRepo(final String owner, final String repo) throws IOException {
        return listLabelsForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List labels for a repository ({@code GET /repos/{owner}/{repo}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of labels
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabel> listLabelsForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(LABELS, Map.of(), request, LABELS_TYPE, owner, repo);
    }

    /**
     * Create a label ({@code POST /repos/{owner}/{repo}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload label to create
     * @return created label
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubLabel createLabel(final String owner, final String repo, final GitHubCreateLabelPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(LABELS, owner, repo), payload), GitHubLabel.class);
    }

    /**
     * Get a label ({@code GET /repos/{owner}/{repo}/labels/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name label name
     * @return label, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubLabel> getLabel(final String owner, final String repo, final String name) throws IOException {
        return caller.fetchOrEmpty(get(LABEL, owner, repo, name), GitHubLabel.class);
    }

    /**
     * Update a label ({@code PATCH /repos/{owner}/{repo}/labels/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name label name
     * @return updated label
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubLabel updateLabel(final String owner, final String repo, final String name) throws IOException {
        return updateLabel(owner, repo, name, null);
    }

    /**
     * Update a label ({@code PATCH /repos/{owner}/{repo}/labels/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name label name
     * @param payload optional label updates
     * @return updated label
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubLabel updateLabel(
            final String owner, final String repo, final String name, final @Nullable GitHubUpdateLabelPayload payload)
            throws IOException {

        return caller.fetch(caller.jsonIfPresent(patch(LABEL, owner, repo, name), payload), GitHubLabel.class);
    }

    /**
     * Delete a label ({@code DELETE /repos/{owner}/{repo}/labels/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name label name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteLabel(final String owner, final String repo, final String name) throws IOException {
        caller.send(delete(LABEL, owner, repo, name));
    }

    /**
     * List labels for issues in a milestone ({@code GET /repos/{owner}/{repo}/milestones/{milestone_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param milestoneNumber milestone number
     * @return first HTTP page of labels
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabel> listLabelsForMilestone(
            final String owner, final String repo, final int milestoneNumber) throws IOException {
        return listLabelsForMilestone(owner, repo, milestoneNumber, GitHubPageRequest.byPage());
    }

    /**
     * List labels for issues in a milestone ({@code GET /repos/{owner}/{repo}/milestones/{milestone_number}/labels}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param milestoneNumber milestone number
     * @param request pagination token
     * @return first HTTP page of labels
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabel> listLabelsForMilestone(
            final String owner, final String repo, final int milestoneNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(MILESTONE_LABELS, Map.of(), request, LABELS_TYPE, owner, repo, milestoneNumber);
    }
}
