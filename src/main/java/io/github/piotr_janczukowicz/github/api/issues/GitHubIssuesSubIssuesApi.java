package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
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

/** GitHub sub-issues REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesSubIssuesApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_SUB_ISSUES =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/sub_issues");
    private static final PathTemplate ISSUE_SUB_ISSUES_PRIORITY =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/sub_issues/priority");
    private static final PathTemplate ISSUE_SUB_ISSUE =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/sub_issue");
    private static final PathTemplate ISSUE_PARENT =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/parent");

    private static final JsonType<List<GitHubIssue>> ISSUES_TYPE = JsonTypes.listOf(GitHubIssue.class);

    /**
     * Sub-issues resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesSubIssuesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get parent issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/parent}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return parent issue, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubIssue> getParent(final String owner, final String repo, final int issueNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchOrEmpty(get(ISSUE_PARENT, owner, repo, issueNumber), GitHubIssue.class);
    }

    /**
     * Remove sub-issue ({@code DELETE /repos/{owner}/{repo}/issues/{issue_number}/sub_issue}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload sub-issue to remove
     * @return updated issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssue removeSubIssue(
            final String owner, final String repo, final int issueNumber, final GitHubRemoveSubIssuePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(delete(ISSUE_SUB_ISSUE, owner, repo, issueNumber), payload), GitHubIssue.class);
    }

    /**
     * List sub-issues ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/sub_issues}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of sub-issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssue> listSubIssues(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return listSubIssues(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List sub-issues ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/sub_issues}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of sub-issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssue> listSubIssues(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_SUB_ISSUES, Map.of(), request, ISSUES_TYPE, owner, repo, issueNumber);
    }

    /**
     * Add sub-issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/sub_issues}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload sub-issue to add
     * @return updated issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssue addSubIssue(
            final String owner, final String repo, final int issueNumber, final GitHubAddSubIssuePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ISSUE_SUB_ISSUES, owner, repo, issueNumber), payload), GitHubIssue.class);
    }

    /**
     * Reprioritize sub-issue ({@code PATCH /repos/{owner}/{repo}/issues/{issue_number}/sub_issues/priority}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload priority change
     * @return updated issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssue reprioritizeSubIssue(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubReprioritizeSubIssuePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ISSUE_SUB_ISSUES_PRIORITY, owner, repo, issueNumber), payload), GitHubIssue.class);
    }
}
