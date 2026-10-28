package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/** GitHub Actions artifacts REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsArtifactsApi extends GitHubResourceApi {

    private static final String ZIP_FORMAT = "zip";
    private static final PathTemplate REPO_ARTIFACTS = PathTemplate.of("/repos/{owner}/{repo}/actions/artifacts");
    private static final PathTemplate REPO_ARTIFACT =
            PathTemplate.of("/repos/{owner}/{repo}/actions/artifacts/{artifact_id}");
    private static final PathTemplate REPO_RUN_ARTIFACTS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/runs/{run_id}/artifacts");
    private static final PathTemplate REPO_ARTIFACT_ARCHIVE =
            PathTemplate.of("/repos/{owner}/{repo}/actions/artifacts/{artifact_id}/{archive_format}");

    private static final GitHubListSpec<GitHubArtifact> ARTIFACTS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubArtifacts.class,
            GitHubArtifacts::artifacts,
            GitHubArtifacts::totalCount);

    /**
     * Artifacts resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsArtifactsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List artifacts for a repository ({@code GET /repos/{owner}/{repo}/actions/artifacts}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and no filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubArtifact> listArtifactsForRepo(final String owner, final String repo) throws IOException {
        return listArtifactsForRepo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List artifacts for a repository ({@code GET /repos/{owner}/{repo}/actions/artifacts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubArtifact> listArtifactsForRepo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listArtifactsForRepo(owner, repo, GitHubListArtifactsForRepoFilter.empty(), request);
    }

    /**
     * List artifacts for a repository ({@code GET /repos/{owner}/{repo}/actions/artifacts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubArtifact> listArtifactsForRepo(
            final String owner,
            final String repo,
            final GitHubListArtifactsForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_ARTIFACTS, filter.toQueryParameters(), request, ARTIFACTS_SPEC, owner, repo);
    }

    /**
     * Get an artifact ({@code GET /repos/{owner}/{repo}/actions/artifacts/{artifact_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param artifactId artifact ID
     * @return artifact, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubArtifact> getArtifact(final String owner, final String repo, final long artifactId)
            throws IOException {
        return caller.fetchOrEmpty(get(REPO_ARTIFACT, owner, repo, artifactId), GitHubArtifact.class);
    }

    /**
     * Delete an artifact ({@code DELETE /repos/{owner}/{repo}/actions/artifacts/{artifact_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param artifactId artifact ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteArtifact(final String owner, final String repo, final long artifactId) throws IOException {
        caller.send(delete(REPO_ARTIFACT, owner, repo, artifactId));
    }

    /**
     * Download an artifact as a zip archive ({@code GET
     * /repos/{owner}/{repo}/actions/artifacts/{artifact_id}/{archive_format}} with format {@code zip}). Same as
     * {@link #downloadArtifact(String, String, long, String)}, the only format GitHub documents.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param artifactId artifact ID
     * @return redirect location of the archive
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 410 when the artifact is no longer available),
     *     or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadArtifact(final String owner, final String repo, final long artifactId) throws IOException {
        return downloadArtifact(owner, repo, artifactId, ZIP_FORMAT);
    }

    /**
     * Download an artifact ({@code GET /repos/{owner}/{repo}/actions/artifacts/{artifact_id}/{archive_format}}).
     *
     * <p>GitHub answers with a redirect to the archive; this method returns that location.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param artifactId artifact ID
     * @param archiveFormat archive format, {@code zip}
     * @return redirect location of the archive
     * @throws IllegalArgumentException if {@code owner}, {@code repo} or {@code archiveFormat} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (the spec lists HTTP 410 for this call, which GitHub
     *     sends when the artifact is no longer available), or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadArtifact(
            final String owner, final String repo, final long artifactId, final String archiveFormat)
            throws IOException {
        return caller.redirectLocation(get(REPO_ARTIFACT_ARCHIVE, owner, repo, artifactId, archiveFormat));
    }

    /**
     * List artifacts of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/artifacts}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubArtifact> listWorkflowRunArtifacts(final String owner, final String repo, final long runId)
            throws IOException {
        return listWorkflowRunArtifacts(owner, repo, runId, GitHubPageRequest.byPage());
    }

    /**
     * List artifacts of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/artifacts}).
     *
     * <p>Uses an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param request pagination token
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubArtifact> listWorkflowRunArtifacts(
            final String owner, final String repo, final long runId, final GitHubPageRequest request)
            throws IOException {
        return listWorkflowRunArtifacts(owner, repo, runId, GitHubListWorkflowRunArtifactsFilter.empty(), request);
    }

    /**
     * List artifacts of a workflow run ({@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/artifacts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param runId workflow run ID
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of artifacts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubArtifact> listWorkflowRunArtifacts(
            final String owner,
            final String repo,
            final long runId,
            final GitHubListWorkflowRunArtifactsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_RUN_ARTIFACTS, filter.toQueryParameters(), request, ARTIFACTS_SPEC, owner, repo, runId);
    }
}
