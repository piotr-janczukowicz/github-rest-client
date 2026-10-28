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
import org.jspecify.annotations.Nullable;

/** GitHub issues REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssueApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_SUGGESTION_APPROVE =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/suggestions/{suggestion_id}/approve");
    private static final PathTemplate ISSUE_SUGGESTION_DISMISS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/suggestions/{suggestion_id}/dismiss");
    private static final PathTemplate ISSUE_SUGGESTIONS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/suggestions");
    private static final PathTemplate ISSUE_RELATES_TO =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/relates_to");
    private static final PathTemplate ISSUE_RELATES_TO_ISSUE =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/relates_to/{issue_id}");
    private static final PathTemplate ISSUE_LOCK = PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/lock");
    private static final PathTemplate ISSUE = PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}");
    private static final PathTemplate REPO_ISSUES = PathTemplate.of("/repos/{owner}/{repo}/issues");
    private static final PathTemplate USER_ISSUES = PathTemplate.of("/user/issues");
    private static final PathTemplate ORG_ISSUES = PathTemplate.of("/orgs/{org}/issues");
    private static final PathTemplate ISSUES = PathTemplate.of("/issues");

    private static final JsonType<List<GitHubIssue>> ISSUES_TYPE = JsonTypes.listOf(GitHubIssue.class);
    private static final JsonType<List<GitHubIssueSuggestion>> ISSUE_SUGGESTIONS_TYPE =
            JsonTypes.listOf(GitHubIssueSuggestion.class);

    /**
     * Issues resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssueApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issues assigned to the authenticated user ({@code GET /issues}).
     *
     * @return first HTTP page of issues
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> list() throws IOException {
        return list(GitHubListFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List issues assigned to the authenticated user ({@code GET /issues}).
     *
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> list(final GitHubPageRequest request) throws IOException {
        return list(GitHubListFilter.empty(), request);
    }

    /**
     * List issues assigned to the authenticated user ({@code GET /issues}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> list(final GitHubListFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUES, filter.toQueryParameters(), request, ISSUES_TYPE);
    }

    /**
     * List organization issues assigned to the authenticated user ({@code GET /orgs/{org}/issues}).
     *
     * @param org organization login
     * @return first HTTP page of issues
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForOrg(final String org) throws IOException {
        return listForOrg(org, GitHubListForOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization issues assigned to the authenticated user ({@code GET /orgs/{org}/issues}).
     *
     * @param org organization login
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForOrg(final String org, final GitHubPageRequest request) throws IOException {
        return listForOrg(org, GitHubListForOrgFilter.empty(), request);
    }

    /**
     * List organization issues assigned to the authenticated user ({@code GET /orgs/{org}/issues}).
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForOrg(
            final String org, final GitHubListForOrgFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_ISSUES, filter.toQueryParameters(), request, ISSUES_TYPE, org);
    }

    /**
     * List repository issues ({@code GET /repos/{owner}/{repo}/issues}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForRepo(final String owner, final String repo) throws IOException {
        return listForRepo(owner, repo, GitHubListForRepoFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository issues ({@code GET /repos/{owner}/{repo}/issues}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForRepo(final String owner, final String repo, final GitHubPageRequest request)
            throws IOException {
        return listForRepo(owner, repo, GitHubListForRepoFilter.empty(), request);
    }

    /**
     * List repository issues ({@code GET /repos/{owner}/{repo}/issues}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForRepo(
            final String owner,
            final String repo,
            final GitHubListForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(REPO_ISSUES, filter.toQueryParameters(), request, ISSUES_TYPE, owner, repo);
    }

    /**
     * List user account issues assigned to the authenticated user ({@code GET /user/issues}).
     *
     * @return first HTTP page of issues
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForAuthenticatedUser() throws IOException {
        return listForAuthenticatedUser(GitHubListForAuthenticatedUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List user account issues assigned to the authenticated user ({@code GET /user/issues}).
     *
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForAuthenticatedUser(final GitHubPageRequest request) throws IOException {
        return listForAuthenticatedUser(GitHubListForAuthenticatedUserFilter.empty(), request);
    }

    /**
     * List user account issues assigned to the authenticated user ({@code GET /user/issues}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of issues
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listForAuthenticatedUser(
            final GitHubListForAuthenticatedUserFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_ISSUES, filter.toQueryParameters(), request, ISSUES_TYPE);
    }

    /**
     * Create an issue ({@code POST /repos/{owner}/{repo}/issues}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload issue to create
     * @return created issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssue create(final String owner, final String repo, final GitHubCreatePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(REPO_ISSUES, owner, repo), payload), GitHubIssue.class);
    }

    /**
     * Get an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return issue, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubIssue> get(final String owner, final String repo, final int issueNumber) throws IOException {
        return caller.fetchOrEmpty(get(ISSUE, owner, repo, issueNumber), GitHubIssue.class);
    }

    /**
     * Update an issue ({@code PATCH /repos/{owner}/{repo}/issues/{issue_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload optional issue updates
     * @return updated issue, including pending suggestions when present
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssueUpdate update(
            final String owner, final String repo, final int issueNumber, final @Nullable GitHubUpdatePayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(patch(ISSUE, owner, repo, issueNumber), payload), GitHubIssueUpdate.class);
    }

    /**
     * Lock an issue ({@code PUT /repos/{owner}/{repo}/issues/{issue_number}/lock}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void lock(final String owner, final String repo, final int issueNumber) throws IOException {
        lock(owner, repo, issueNumber, null);
    }

    /**
     * Lock an issue ({@code PUT /repos/{owner}/{repo}/issues/{issue_number}/lock}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload optional lock reason
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void lock(
            final String owner, final String repo, final int issueNumber, final @Nullable GitHubLockPayload payload)
            throws IOException {

        caller.send(caller.jsonIfPresent(put(ISSUE_LOCK, owner, repo, issueNumber), payload));
    }

    /**
     * Unlock an issue ({@code DELETE /repos/{owner}/{repo}/issues/{issue_number}/lock}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void unlock(final String owner, final String repo, final int issueNumber) throws IOException {
        caller.send(delete(ISSUE_LOCK, owner, repo, issueNumber));
    }

    /**
     * List issue suggestions ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/suggestions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of issue suggestions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssueSuggestion> listSuggestions(
            final String owner, final String repo, final int issueNumber) throws IOException {
        return listSuggestions(
                owner, repo, issueNumber, GitHubListSuggestionsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List issue suggestions ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/suggestions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of issue suggestions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssueSuggestion> listSuggestions(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        return listSuggestions(owner, repo, issueNumber, GitHubListSuggestionsFilter.empty(), request);
    }

    /**
     * List issue suggestions ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/suggestions}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of issue suggestions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssueSuggestion> listSuggestions(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubListSuggestionsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                ISSUE_SUGGESTIONS,
                filter.toQueryParameters(),
                request,
                ISSUE_SUGGESTIONS_TYPE,
                owner,
                repo,
                issueNumber);
    }

    /**
     * Approve an issue suggestion ({@code POST
     * /repos/{owner}/{repo}/issues/{issue_number}/suggestions/{suggestion_id}/approve}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param suggestionId suggestion id
     * @return approved suggestion
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueSuggestion approveSuggestion(
            final String owner, final String repo, final int issueNumber, final long suggestionId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                post(ISSUE_SUGGESTION_APPROVE, owner, repo, issueNumber, suggestionId), GitHubIssueSuggestion.class);
    }

    /**
     * Dismiss an issue suggestion ({@code POST
     * /repos/{owner}/{repo}/issues/{issue_number}/suggestions/{suggestion_id}/dismiss}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param suggestionId suggestion id
     * @return dismissed suggestion
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueSuggestion dismissSuggestion(
            final String owner, final String repo, final int issueNumber, final long suggestionId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                post(ISSUE_SUGGESTION_DISMISS, owner, repo, issueNumber, suggestionId), GitHubIssueSuggestion.class);
    }

    /**
     * List issues related to an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/relates_to}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of related issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssue> listRelatesTo(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return listRelatesTo(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List issues related to an issue ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/relates_to}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return HTTP page of related issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssue> listRelatesTo(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_RELATES_TO, Map.of(), request, ISSUES_TYPE, owner, repo, issueNumber);
    }

    /**
     * Add a related issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/relates_to}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload issue to mark as related
     * @return updated issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssue addRelatesTo(
            final String owner, final String repo, final int issueNumber, final GitHubAddRelatesToPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ISSUE_RELATES_TO, owner, repo, issueNumber), payload), GitHubIssue.class);
    }

    /**
     * Remove a related issue ({@code DELETE /repos/{owner}/{repo}/issues/{issue_number}/relates_to/{issue_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param issueId id of the related issue to remove
     * @return updated issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssue removeRelatesTo(final String owner, final String repo, final int issueNumber, final long issueId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(delete(ISSUE_RELATES_TO_ISSUE, owner, repo, issueNumber, issueId), GitHubIssue.class);
    }
}
