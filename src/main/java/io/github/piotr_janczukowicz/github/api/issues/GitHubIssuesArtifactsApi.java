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
import java.util.Optional;

/** GitHub issue artifacts REST resource for API version {@code 2026-03-10}. */
public final class GitHubIssuesArtifactsApi extends GitHubResourceApi {

    private static final PathTemplate ISSUE_ARTIFACTS =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/artifacts");
    private static final PathTemplate ISSUE_ARTIFACT =
            PathTemplate.of("/repos/{owner}/{repo}/issues/{issue_number}/artifacts/{artifact_number}");

    private static final JsonType<List<GitHubIssueArtifact>> ARTIFACTS_TYPE =
            JsonTypes.listOf(GitHubIssueArtifact.class);

    /**
     * Issue artifacts resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesArtifactsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List issue artifacts ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/artifacts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssueArtifact> listIssueArtifacts(
            final String owner, final String repo, final int issueNumber) throws IOException {
        return listIssueArtifacts(owner, repo, issueNumber, GitHubPageRequest.byPage());
    }

    /**
     * List issue artifacts ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/artifacts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param request pagination token
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssueArtifact> listIssueArtifacts(
            final String owner, final String repo, final int issueNumber, final GitHubPageRequest request)
            throws IOException {
        return listIssueArtifacts(owner, repo, issueNumber, GitHubListIssueArtifactsFilter.empty(), request);
    }

    /**
     * List issue artifacts ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/artifacts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubIssueArtifact> listIssueArtifacts(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubListIssueArtifactsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ISSUE_ARTIFACTS, filter.toQueryParameters(), request, ARTIFACTS_TYPE, owner, repo, issueNumber);
    }

    /**
     * Create an issue artifact ({@code POST /repos/{owner}/{repo}/issues/{issue_number}/artifacts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param payload artifact to create
     * @return created artifact
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueArtifact createIssueArtifact(
            final String owner,
            final String repo,
            final int issueNumber,
            final GitHubCreateIssueArtifactPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ISSUE_ARTIFACTS, owner, repo, issueNumber), payload), GitHubIssueArtifact.class);
    }

    /**
     * Get an issue artifact ({@code GET /repos/{owner}/{repo}/issues/{issue_number}/artifacts/{artifact_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param artifactNumber artifact number within the issue
     * @return the artifact, alone or with its version history, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubGetIssueArtifactResponse> getIssueArtifact(
            final String owner, final String repo, final int issueNumber, final int artifactNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(ISSUE_ARTIFACT, owner, repo, issueNumber, artifactNumber), GitHubGetIssueArtifactResponse.class);
    }

    /**
     * Update an issue artifact ({@code PATCH /repos/{owner}/{repo}/issues/{issue_number}/artifacts/{artifact_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param artifactNumber artifact number within the issue
     * @param payload fields to update
     * @return updated artifact
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubIssueArtifact updateIssueArtifact(
            final String owner,
            final String repo,
            final int issueNumber,
            final int artifactNumber,
            final GitHubUpdateIssueArtifactPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ISSUE_ARTIFACT, owner, repo, issueNumber, artifactNumber), payload),
                GitHubIssueArtifact.class);
    }

    /**
     * Delete an issue artifact ({@code DELETE
     * /repos/{owner}/{repo}/issues/{issue_number}/artifacts/{artifact_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param issueNumber issue number
     * @param artifactNumber artifact number within the issue
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteIssueArtifact(
            final String owner, final String repo, final int issueNumber, final int artifactNumber) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ISSUE_ARTIFACT, owner, repo, issueNumber, artifactNumber));
    }
}
