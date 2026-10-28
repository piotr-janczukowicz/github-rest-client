package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.uri.HttpUris;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.jspecify.annotations.Nullable;

/** GitHub repository release assets REST resource for API version {@code 2026-03-10}. */
public final class GitHubReleasesAssetsApi extends GitHubResourceApi {

    private static final PathTemplate RELEASE_ASSETS =
            PathTemplate.of("/repos/{owner}/{repo}/releases/{release_id}/assets");
    private static final PathTemplate ASSET = PathTemplate.of("/repos/{owner}/{repo}/releases/assets/{asset_id}");

    private static final String OCTET_STREAM_MEDIA_TYPE = "application/octet-stream";
    private static final String PARAM_BODY = "body";
    private static final String PARAM_CONTENT_TYPE = "contentType";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_UPLOAD_URL = "uploadUrl";

    private static final JsonType<List<GitHubReleaseAsset>> RELEASE_ASSETS_TYPE =
            JsonTypes.listOf(GitHubReleaseAsset.class);

    /**
     * Release assets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubReleasesAssetsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List release assets ({@code GET /repos/{owner}/{repo}/releases/{release_id}/assets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @return first HTTP page of release assets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubReleaseAsset> listReleaseAssets(final String owner, final String repo, final long releaseId)
            throws IOException {
        return listReleaseAssets(owner, repo, releaseId, GitHubPageRequest.byPage());
    }

    /**
     * List release assets ({@code GET /repos/{owner}/{repo}/releases/{release_id}/assets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param releaseId release id
     * @param request pagination token
     * @return first HTTP page of release assets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubReleaseAsset> listReleaseAssets(
            final String owner, final String repo, final long releaseId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RELEASE_ASSETS, Map.of(), request, RELEASE_ASSETS_TYPE, owner, repo, releaseId);
    }

    /**
     * Get a release asset ({@code GET /repos/{owner}/{repo}/releases/assets/{asset_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param assetId asset id
     * @return release asset, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubReleaseAsset> getReleaseAsset(final String owner, final String repo, final long assetId)
            throws IOException {

        return caller.fetchOrEmpty(get(ASSET, owner, repo, assetId), GitHubReleaseAsset.class);
    }

    /**
     * Update a release asset ({@code PATCH /repos/{owner}/{repo}/releases/assets/{asset_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param assetId asset id
     * @param payload optional asset updates
     * @return updated release asset
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReleaseAsset updateReleaseAsset(
            final String owner,
            final String repo,
            final long assetId,
            final @Nullable GitHubUpdateReleaseAssetPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(patch(ASSET, owner, repo, assetId), payload), GitHubReleaseAsset.class);
    }

    /**
     * Delete a release asset ({@code DELETE /repos/{owner}/{repo}/releases/assets/{asset_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param assetId asset id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteReleaseAsset(final String owner, final String repo, final long assetId) throws IOException {
        caller.send(delete(ASSET, owner, repo, assetId));
    }

    /**
     * Download the binary of a release asset into {@code target} ({@code GET
     * /repos/{owner}/{repo}/releases/assets/{asset_id}} with {@code Accept: application/octet-stream}). The response
     * redirects to the storage host; the redirect is followed without the {@code Authorization} header. The body is
     * streamed to a temporary file next to {@code target} and moved onto it only when complete.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #getReleaseAsset(String, String, long)}, which returns the asset metadata; the binary is selected by the
     * {@code Accept} media type.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param assetId asset id
     * @param target destination file; its directory must exist, an existing file is replaced
     * @return {@code target}
     * @throws NullPointerException if {@code target} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails or the file cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Path downloadReleaseAsset(final String owner, final String repo, final long assetId, final Path target)
            throws IOException {
        requireNonNull(target, "target");

        return caller.download(get(ASSET, owner, repo, assetId).withHeader(ACCEPT, OCTET_STREAM_MEDIA_TYPE), target);
    }

    /**
     * Download the binary of a release asset into {@code out} ({@code GET
     * /repos/{owner}/{repo}/releases/assets/{asset_id}} with {@code Accept: application/octet-stream}); {@code out} is
     * not closed. The redirect to the storage host is followed without the {@code Authorization} header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #getReleaseAsset(String, String, long)}, which returns the asset metadata; the binary is selected by the
     * {@code Accept} media type.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param assetId asset id
     * @param out destination stream
     * @return number of bytes written
     * @throws NullPointerException if {@code out} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails or {@code out} cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public long downloadReleaseAsset(final String owner, final String repo, final long assetId, final OutputStream out)
            throws IOException {
        requireNonNull(out, "out");

        return caller.download(get(ASSET, owner, repo, assetId).withHeader(ACCEPT, OCTET_STREAM_MEDIA_TYPE), out);
    }

    /**
     * Upload a release asset ({@code POST /repos/{owner}/{repo}/releases/{release_id}/assets}, sent to the release
     * {@code upload_url}) from a file.
     *
     * <p>The spec path is {@code POST /repos/{owner}/{repo}/releases/{release_id}/assets} on a separate upload host.
     * The host differs between deployments, so the call takes the {@code upload_url} that GitHub returns with the
     * release instead of building the address from owner, repo and release id.
     *
     * @param uploadUrl upload URL from the release {@code upload_url} field
     * @param file asset file
     * @param contentType asset content type
     * @param name required asset file name query parameter
     * @param label optional asset label query parameter
     * @return uploaded release asset
     * @throws NullPointerException if {@code uploadUrl}, {@code file}, or {@code contentType} is {@code null}
     * @throws IllegalArgumentException if {@code contentType} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReleaseAsset uploadReleaseAsset(
            final String uploadUrl,
            final Path file,
            final String contentType,
            final String name,
            final @Nullable String label)
            throws IOException {
        requireNonNull(file, PARAM_BODY);
        return uploadReleaseAsset(uploadUrl, HttpBody.ofFile(file), contentType, name, label);
    }

    /**
     * Upload a release asset ({@code POST /repos/{owner}/{repo}/releases/{release_id}/assets}, sent to the release
     * {@code upload_url}) from a repeatable stream. See {@link #uploadReleaseAsset(String, Path, String, String,
     * String)} for why the call is keyed by the {@code upload_url}.
     *
     * @param uploadUrl upload URL from the release {@code upload_url} field
     * @param source factory that opens a new stream for each attempt
     * @param contentLength asset length
     * @param contentType asset content type
     * @param name required asset file name query parameter
     * @param label optional asset label query parameter
     * @return uploaded release asset
     * @throws NullPointerException if {@code uploadUrl}, {@code source}, or {@code contentType} is {@code null}
     * @throws IllegalArgumentException if {@code contentType} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubReleaseAsset uploadReleaseAsset(
            final String uploadUrl,
            final Supplier<? extends InputStream> source,
            final long contentLength,
            final String contentType,
            final String name,
            final @Nullable String label)
            throws IOException {
        requireNonNull(source, PARAM_BODY);
        return uploadReleaseAsset(uploadUrl, HttpBody.ofStream(source, contentLength), contentType, name, label);
    }

    private GitHubReleaseAsset uploadReleaseAsset(
            final String uploadUrl,
            final HttpBody body,
            final String contentType,
            final String name,
            final @Nullable String label)
            throws IOException {
        requireNonNull(uploadUrl, PARAM_UPLOAD_URL);
        requireNotBlank(contentType, PARAM_CONTENT_TYPE);
        requireNotBlank(name, PARAM_NAME);

        final URI uploadUri = HttpUris.stripUriTemplate(uploadUrl);

        return caller.fetch(
                post(uploadUri)
                        .withQuery(new GitHubUploadReleaseAssetFilter(name, label).toQueryParameters())
                        .withBody(body)
                        .withHeader(CONTENT_TYPE, contentType),
                GitHubReleaseAsset.class);
    }
}
