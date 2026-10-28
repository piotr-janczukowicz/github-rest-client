package io.github.piotr_janczukowicz.github.api.releases;

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

/** GitHub repository releases REST resource for API version {@code 2026-03-10}. */
public final class GitHubReleaseApi extends GitHubResourceApi {

    private static final PathTemplate RELEASES_GENERATE_NOTES =
            PathTemplate.of("/repos/{owner}/{repo}/releases/generate-notes");
    private static final PathTemplate RELEASE = PathTemplate.of("/repos/{owner}/{repo}/releases/{release_id}");
    private static final PathTemplate RELEASES = PathTemplate.of("/repos/{owner}/{repo}/releases");
    private static final PathTemplate RELEASE_BY_TAG = PathTemplate.of("/repos/{owner}/{repo}/releases/tags/{+tag}");
    private static final PathTemplate LATEST_RELEASE = PathTemplate.of("/repos/{owner}/{repo}/releases/latest");

    private static final JsonType<List<GitHubRelease>> RELEASES_TYPE = JsonTypes.listOf(GitHubRelease.class);

    /**
     * Releases resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubReleaseApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List releases ({@code GET /repos/{owner}/{repo}/releases}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of releases
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRelease> listReleases(final String owner, final String repo) throws IOException {
        return listReleases(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List releases ({@code GET /repos/{owner}/{repo}/releases}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of releases
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRelease> listReleases(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RELEASES, Map.of(), request, RELEASES_TYPE, owner, repo);
    }

    /**
     * Get a release ({@code GET /repos/{owner}/{repo}/releases/{release_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @return release, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRelease> getRelease(final String owner, final String repo, final long releaseId)
            throws IOException {

        return caller.fetchOrEmpty(get(RELEASE, owner, repo, releaseId), GitHubRelease.class);
    }

    /**
     * Get the latest release ({@code GET /repos/{owner}/{repo}/releases/latest}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return latest release, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRelease> getLatestRelease(final String owner, final String repo) throws IOException {
        return caller.fetchOrEmpty(get(LATEST_RELEASE, owner, repo), GitHubRelease.class);
    }

    /**
     * Get a release by tag ({@code GET /repos/{owner}/{repo}/releases/tags/{tag}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param tag tag name
     * @return release, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code tag} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRelease> getReleaseByTag(final String owner, final String repo, final String tag)
            throws IOException {

        return caller.fetchOrEmpty(get(RELEASE_BY_TAG, owner, repo, tag), GitHubRelease.class);
    }

    /**
     * Create a release ({@code POST /repos/{owner}/{repo}/releases}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload release to create
     * @return created release
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRelease createRelease(final String owner, final String repo, final GitHubCreateReleasePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(RELEASES, owner, repo), payload), GitHubRelease.class);
    }

    /**
     * Update a release ({@code PATCH /repos/{owner}/{repo}/releases/{release_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @param payload optional release updates
     * @return updated release
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRelease updateRelease(
            final String owner,
            final String repo,
            final long releaseId,
            final @Nullable GitHubUpdateReleasePayload payload)
            throws IOException {

        return caller.fetch(caller.jsonIfPresent(patch(RELEASE, owner, repo, releaseId), payload), GitHubRelease.class);
    }

    /**
     * Delete a release ({@code DELETE /repos/{owner}/{repo}/releases/{release_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRelease(final String owner, final String repo, final long releaseId) throws IOException {
        caller.send(delete(RELEASE, owner, repo, releaseId));
    }

    /**
     * Generate release notes ({@code POST /repos/{owner}/{repo}/releases/generate-notes}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload generate notes request
     * @return generated release notes
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReleaseNotesContent generateReleaseNotes(
            final String owner, final String repo, final GitHubGenerateReleaseNotesPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(RELEASES_GENERATE_NOTES, owner, repo), payload), GitHubReleaseNotesContent.class);
    }
}
