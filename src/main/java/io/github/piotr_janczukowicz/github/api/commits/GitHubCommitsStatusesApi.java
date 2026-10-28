package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub commit statuses REST resource for API version {@code 2026-03-10}. */
public final class GitHubCommitsStatusesApi extends GitHubResourceApi {

    private static final PathTemplate COMMIT_STATUS = PathTemplate.of("/repos/{owner}/{repo}/commits/{+ref}/status");
    private static final PathTemplate COMMIT_STATUSES =
            PathTemplate.of("/repos/{owner}/{repo}/commits/{+ref}/statuses");
    private static final PathTemplate STATUSES = PathTemplate.of("/repos/{owner}/{repo}/statuses/{+sha}");

    private static final JsonType<List<GitHubCommitStatus>> STATUSES_TYPE = JsonTypes.listOf(GitHubCommitStatus.class);

    /**
     * Commit statuses resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCommitsStatusesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a commit status ({@code POST /repos/{owner}/{repo}/statuses/{sha}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param sha commit SHA
     * @param payload status to create
     * @return created commit status
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code sha} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCommitStatus createCommitStatus(
            final String owner, final String repo, final String sha, final GitHubCreateCommitStatusPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(STATUSES, owner, repo, sha), payload), GitHubCommitStatus.class);
    }

    /**
     * List commit statuses for a reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/statuses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @return first HTTP page of commit statuses
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitStatus> listCommitStatusesForRef(
            final String owner, final String repo, final String ref) throws IOException {
        return listCommitStatusesForRef(owner, repo, ref, GitHubPageRequest.byPage());
    }

    /**
     * List commit statuses for a reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/statuses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @param request pagination token
     * @return first HTTP page of commit statuses
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank, or {@code request} is
     *     invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitStatus> listCommitStatusesForRef(
            final String owner, final String repo, final String ref, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(COMMIT_STATUSES, Map.of(), request, STATUSES_TYPE, owner, repo, ref);
    }

    /**
     * Get the combined status for a reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/status}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @return combined status, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCombinedCommitStatus> getCombinedStatusForRef(
            final String owner, final String repo, final String ref) throws IOException {
        return getCombinedStatusForRef(owner, repo, ref, GitHubPageRequest.byPage());
    }

    /**
     * Get the combined status for a reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/status}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit reference
     * @param request pagination token
     * @return combined status, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank, or {@code request} is
     *     invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCombinedCommitStatus> getCombinedStatusForRef(
            final String owner, final String repo, final String ref, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetchOrEmpty(
                caller.pageParameters(
                        get(COMMIT_STATUS, owner, repo, ref), request, GitHubPageRequestKindValidators.PAGE),
                GitHubCombinedCommitStatus.class);
    }
}
