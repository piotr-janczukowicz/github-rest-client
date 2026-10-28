package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

/** GitHub repository contents REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesContentsApi extends GitHubResourceApi {

    private static final PathTemplate ZIPBALL = PathTemplate.of("/repos/{owner}/{repo}/zipball/{+ref}");
    private static final PathTemplate TARBALL = PathTemplate.of("/repos/{owner}/{repo}/tarball/{+ref}");
    private static final PathTemplate README_DIR = PathTemplate.of("/repos/{owner}/{repo}/readme/{+dir}");
    private static final PathTemplate README = PathTemplate.of("/repos/{owner}/{repo}/readme");
    private static final PathTemplate CONTENTS = PathTemplate.of("/repos/{owner}/{repo}/contents/{+path}");
    private static final PathTemplate CONTENTS_ROOT = PathTemplate.of("/repos/{owner}/{repo}/contents");

    /**
     * Contents resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesContentsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get repository content ({@code GET /repos/{owner}/{repo}/contents/{path}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param path file or directory path
     * @return content result, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code path} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentResult> getContent(final String owner, final String repo, final String path)
            throws IOException {
        return getContent(owner, repo, path, GitHubGetContentFilter.empty());
    }

    /**
     * Get repository content ({@code GET /repos/{owner}/{repo}/contents/{path}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param path file or directory path
     * @param filter query filters
     * @return content result, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code path} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentResult> getContent(
            final String owner, final String repo, final String path, final GitHubGetContentFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(CONTENTS, owner, repo, path).withQuery(filter.toQueryParameters()), GitHubContentResult.class);
    }

    /**
     * Get the content of the repository root ({@code GET /repos/{owner}/{repo}/contents/{path}} with an empty path), a
     * directory listing.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return content result, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentResult> getRootContent(final String owner, final String repo) throws IOException {
        return getRootContent(owner, repo, GitHubGetContentFilter.empty());
    }

    /**
     * Get the content of the repository root ({@code GET /repos/{owner}/{repo}/contents/{path}} with an empty path), a
     * directory listing.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return content result, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentResult> getRootContent(
            final String owner, final String repo, final GitHubGetContentFilter filter) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(CONTENTS_ROOT, owner, repo).withQuery(filter.toQueryParameters()), GitHubContentResult.class);
    }

    /**
     * Get redirect location for repository content ({@code GET /repos/{owner}/{repo}/contents/{path}}).
     *
     * <p>GitHub returns HTTP 302 with a {@code Location} header when file content is too large for the JSON response;
     * for content it sends directly (HTTP 200) there is no redirect and the result is empty.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param path file path
     * @return redirect target from the {@code Location} header, or empty when the content is not behind a redirect
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code path} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 included), or is a redirect without a usable
     *     {@code Location}
     */
    public Optional<URI> getContentRedirectLocation(final String owner, final String repo, final String path)
            throws IOException {
        return getContentRedirectLocation(owner, repo, path, GitHubGetContentFilter.empty());
    }

    /**
     * Get redirect location for repository content ({@code GET /repos/{owner}/{repo}/contents/{path}}).
     *
     * <p>GitHub returns HTTP 302 with a {@code Location} header when file content is too large for the JSON response;
     * for content it sends directly (HTTP 200) there is no redirect and the result is empty.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param path file path
     * @param filter query filters
     * @return redirect target from the {@code Location} header, or empty when the content is not behind a redirect
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code path} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 included), or is a redirect without a usable
     *     {@code Location}
     */
    public Optional<URI> getContentRedirectLocation(
            final String owner, final String repo, final String path, final GitHubGetContentFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.redirectLocationIfAny(get(CONTENTS, owner, repo, path).withQuery(filter.toQueryParameters()));
    }

    /**
     * Create or update file contents ({@code PUT /repos/{owner}/{repo}/contents/{path}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param path file path
     * @param payload file contents to write
     * @return file commit response
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code path} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFileCommit createOrUpdateFileContents(
            final String owner,
            final String repo,
            final String path,
            final GitHubCreateOrUpdateFileContentsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(CONTENTS, owner, repo, path), payload), GitHubFileCommit.class);
    }

    /**
     * Delete a file ({@code DELETE /repos/{owner}/{repo}/contents/{path}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param path file path
     * @param payload delete request
     * @return file commit response
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code path} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFileCommit deleteFile(
            final String owner, final String repo, final String path, final GitHubDeleteFilePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(delete(CONTENTS, owner, repo, path), payload), GitHubFileCommit.class);
    }

    /**
     * Get a repository README ({@code GET /repos/{owner}/{repo}/readme}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return readme file content, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentFile> getReadme(final String owner, final String repo) throws IOException {
        return getReadme(owner, repo, GitHubGetContentFilter.empty());
    }

    /**
     * Get a repository README ({@code GET /repos/{owner}/{repo}/readme}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return readme file content, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentFile> getReadme(
            final String owner, final String repo, final GitHubGetContentFilter filter) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(README, owner, repo).withQuery(filter.toQueryParameters()), GitHubContentFile.class);
    }

    /**
     * Get a repository README for a directory ({@code GET /repos/{owner}/{repo}/readme/{dir}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param dir directory path
     * @return readme file content, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code dir} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentFile> getReadmeInDirectory(final String owner, final String repo, final String dir)
            throws IOException {
        return getReadmeInDirectory(owner, repo, dir, GitHubGetContentFilter.empty());
    }

    /**
     * Get a repository README for a directory ({@code GET /repos/{owner}/{repo}/readme/{dir}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param dir directory path
     * @param filter query filters
     * @return readme file content, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code dir} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubContentFile> getReadmeInDirectory(
            final String owner, final String repo, final String dir, final GitHubGetContentFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(README_DIR, owner, repo, dir).withQuery(filter.toQueryParameters()), GitHubContentFile.class);
    }

    /**
     * Download a repository archive (tar) ({@code GET /repos/{owner}/{repo}/tarball/{ref}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit, branch, or tag name
     * @return redirect location URI
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadTarballArchive(final String owner, final String repo, final String ref) throws IOException {
        return caller.redirectLocation(get(TARBALL, owner, repo, ref));
    }

    /**
     * Download a repository archive (tar) into {@code target} instead of returning the redirect ({@code GET
     * /repos/{owner}/{repo}/tarball/{ref}}). The archive is streamed to a temporary file next to {@code target} and
     * moved onto it only when complete; the redirect to the download host is followed without the {@code Authorization}
     * header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadTarballArchive(String, String, String)}, which returns the redirect location. This
     * method is a helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of
     * their own.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit, branch, or tag name
     * @param target destination file; its directory must exist, an existing file is replaced
     * @return {@code target}
     * @throws NullPointerException if {@code target} is {@code null}
     * @throws IllegalArgumentException if a text argument is blank
     * @throws IOException if the transport fails or the file cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Path downloadTarballArchive(final String owner, final String repo, final String ref, final Path target)
            throws IOException {
        requireNonNull(target, "target");

        return caller.download(get(TARBALL, owner, repo, ref), target);
    }

    /**
     * Download a repository archive (tar) into {@code out} instead of returning the redirect ({@code GET
     * /repos/{owner}/{repo}/tarball/{ref}}); {@code out} is not closed. The redirect to the download host is followed
     * without the {@code Authorization} header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadTarballArchive(String, String, String)}, which returns the redirect location. This
     * method is a helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of
     * their own.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit, branch, or tag name
     * @param out destination stream
     * @return number of bytes written
     * @throws NullPointerException if {@code out} is {@code null}
     * @throws IllegalArgumentException if a text argument is blank
     * @throws IOException if the transport fails or {@code out} cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public long downloadTarballArchive(final String owner, final String repo, final String ref, final OutputStream out)
            throws IOException {
        requireNonNull(out, "out");

        return caller.download(get(TARBALL, owner, repo, ref), out);
    }

    /**
     * Download a repository archive (zip) ({@code GET /repos/{owner}/{repo}/zipball/{ref}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit, branch, or tag name
     * @return redirect location URI
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadZipballArchive(final String owner, final String repo, final String ref) throws IOException {
        return caller.redirectLocation(get(ZIPBALL, owner, repo, ref));
    }

    /**
     * Download a repository archive (zip) into {@code target} instead of returning the redirect ({@code GET
     * /repos/{owner}/{repo}/zipball/{ref}}). The archive is streamed to a temporary file next to {@code target} and
     * moved onto it only when complete; the redirect to the download host is followed without the {@code Authorization}
     * header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadZipballArchive(String, String, String)}, which returns the redirect location. This
     * method is a helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of
     * their own.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit, branch, or tag name
     * @param target destination file; its directory must exist, an existing file is replaced
     * @return {@code target}
     * @throws NullPointerException if {@code target} is {@code null}
     * @throws IllegalArgumentException if a text argument is blank
     * @throws IOException if the transport fails or the file cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Path downloadZipballArchive(final String owner, final String repo, final String ref, final Path target)
            throws IOException {
        requireNonNull(target, "target");

        return caller.download(get(ZIPBALL, owner, repo, ref), target);
    }

    /**
     * Download a repository archive (zip) into {@code out} instead of returning the redirect ({@code GET
     * /repos/{owner}/{repo}/zipball/{ref}}); {@code out} is not closed. The redirect to the download host is followed
     * without the {@code Authorization} header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadZipballArchive(String, String, String)}, which returns the redirect location. This
     * method is a helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of
     * their own.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit, branch, or tag name
     * @param out destination stream
     * @return number of bytes written
     * @throws NullPointerException if {@code out} is {@code null}
     * @throws IllegalArgumentException if a text argument is blank
     * @throws IOException if the transport fails or {@code out} cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public long downloadZipballArchive(final String owner, final String repo, final String ref, final OutputStream out)
            throws IOException {
        requireNonNull(out, "out");

        return caller.download(get(ZIPBALL, owner, repo, ref), out);
    }
}
