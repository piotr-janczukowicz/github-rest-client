package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
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
import org.jspecify.annotations.Nullable;

/** GitHub issue assignees REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesAssigneesApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_ASSIGNEES =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/assignees");
    private static final PathTemplate ISSUE_ASSIGNEE =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/assignees/{assignee}");
    private static final PathTemplate ASSIGNEE = PathTemplate.of("/repos/{owner}/{repo}/assignees/{assignee}");
    private static final PathTemplate ASSIGNEES = PathTemplate.of("/repos/{owner}/{repo}/assignees");

    private static final JsonType<List<GitHubSimpleUser>> SIMPLE_USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Assignees resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesAssigneesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List assignees ({@code GET /repos/{owner}/{repo}/assignees}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of assignees
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listAssignees(final String owner, final String repo) throws IOException {
        return listAssignees(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List assignees ({@code GET /repos/{owner}/{repo}/assignees}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of assignees
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listAssignees(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ASSIGNEES, Map.of(), request, SIMPLE_USERS_TYPE, owner, repo);
    }

    /**
     * Check if a user can be assigned ({@code GET /repos/{owner}/{repo}/assignees/{assignee}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param assignee username
     * @return {@code true} when GitHub returns HTTP 204
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields {@code false})
     */
    public boolean checkUserCanBeAssigned(final String owner, final String repo, final String assignee)
            throws IOException {

        return caller.isExisting(get(ASSIGNEE, owner, repo, assignee));
    }

    /**
     * Add assignees to an issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/assignees}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return updated issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssue addAssignees(final String owner, final String repo, final int issueNumber) throws IOException {
        return addAssignees(owner, repo, issueNumber, null);
    }

    /**
     * Add assignees to an issue ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/assignees}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload optional assignees to add
     * @return updated issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssue addAssignees(
            final String owner,
            final String repo,
            final int issueNumber,
            final @Nullable GitHubAddAssigneesPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(post(ISSUE_ASSIGNEES, owner, repo, issueNumber), payload), GitHubIssue.class);
    }

    /**
     * Remove assignees from an issue ({@code DELETE /repos/{owner}/{repo}/issues/{issue_number}/assignees}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return updated issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssue removeAssignees(final String owner, final String repo, final int issueNumber)
            throws IOException {
        return removeAssignees(owner, repo, issueNumber, null);
    }

    /**
     * Remove assignees from an issue ({@code DELETE /repos/{owner}/{repo}/issues/{issue_number}/assignees}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload optional assignees to remove
     * @return updated issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIssue removeAssignees(
            final String owner,
            final String repo,
            final int issueNumber,
            final @Nullable GitHubRemoveAssigneesPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(delete(ISSUE_ASSIGNEES, owner, repo, issueNumber), payload), GitHubIssue.class);
    }

    /**
     * Check if a user can be assigned to an issue ({@code GET
     * /repos/{owner}/{repo}/issues/{issue_number}/assignees/{assignee}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param assignee username
     * @return {@code true} when GitHub returns HTTP 204
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code assignee} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields {@code false})
     */
    public boolean checkUserCanBeAssignedToIssue(
            final String owner, final String repo, final int issueNumber, final String assignee) throws IOException {

        return caller.isExisting(get(ISSUE_ASSIGNEE, owner, repo, issueNumber, assignee));
    }
}
