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

/** GitHub issue dependencies REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesIssueDependenciesApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_DEPENDENCIES_BLOCKED_BY =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocked_by");
    private static final PathTemplate ISSUE_DEPENDENCIES_BLOCKING =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocking");
    private static final PathTemplate ISSUE_DEPENDENCIES_BLOCKED_BY_ISSUE =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocked_by/{issue_id}");

    private static final JsonType<List<GitHubIssue>> ISSUES_TYPE = JsonTypes.listOf(GitHubIssue.class);

    /**
     * Issue dependencies resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesIssueDependenciesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List dependencies an issue is blocked by ({@code GET
     * /repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocked_by}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of blocking issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listDependenciesBlockedBy(
            final String owner, final String repo, final int issueNumber) throws IOException {
        return listDependenciesBlockedBy(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List dependencies an issue is blocked by ({@code GET
     * /repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocked_by}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of blocking issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listDependenciesBlockedBy(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_DEPENDENCIES_BLOCKED_BY, Map.of(), request, ISSUES_TYPE, owner, repo, issueNumber);
    }

    /**
     * Add a dependency an issue is blocked by ({@code POST
     * /repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocked_by}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload blocking issue
     * @return updated issue
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssue addBlockedByDependency(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubAddBlockedByDependencyPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ISSUE_DEPENDENCIES_BLOCKED_BY, owner, repo, issueNumber), payload), GitHubIssue.class);
    }

    /**
     * Remove dependency an issue is blocked by ({@code DELETE
     * /repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocked_by/{issue_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param issueId blocking issue id
     * @return updated issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssue removeDependencyBlockedBy(
            final String owner, final String repo, final int issueNumber, final long issueId) throws IOException {

        return caller.fetch(
                delete(ISSUE_DEPENDENCIES_BLOCKED_BY_ISSUE, owner, repo, issueNumber, issueId), GitHubIssue.class);
    }

    /**
     * List dependencies an issue is blocking ({@code GET
     * /repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocking}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of blocked issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listDependenciesBlocking(
            final String owner, final String repo, final int issueNumber) throws IOException {
        return listDependenciesBlocking(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List dependencies an issue is blocking ({@code GET
     * /repos/{owner}/{repo}/issues/{issue_number}/dependencies/blocking}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of blocked issues
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssue> listDependenciesBlocking(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_DEPENDENCIES_BLOCKING, Map.of(), request, ISSUES_TYPE, owner, repo, issueNumber);
    }
}
