package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Git database blobs REST resource for API version {@code 2026-03-10}. */
public final class GitHubGitBlobsApi extends GitHubResourceApi {

    private static final PathTemplate BLOBS = PathTemplate.of("/repos/{owner}/{repo}/git/blobs");
    private static final PathTemplate BLOB = PathTemplate.of("/repos/{owner}/{repo}/git/blobs/{file_sha}");

    /**
     * Blobs resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGitBlobsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a blob ({@code POST /repos/{owner}/{repo}/git/blobs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload blob to create
     * @return created blob
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubShortBlob createBlob(final String owner, final String repo, final GitHubCreateBlobPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(BLOBS, owner, repo), payload), GitHubShortBlob.class);
    }

    /**
     * Get a blob ({@code GET /repos/{owner}/{repo}/git/blobs/{file_sha}}).
     *
     * <p>GitHub sends the content as base64 with a line break every 60 characters, about 1.38 times the blob size, and
     * the whole response is buffered in memory. With the default {@code maxResponseBytes} of 64 MiB, a blob larger than
     * about 47 MiB fails with {@link io.github.piotr_janczukowicz.http.ResponseTooLargeException}, although the API
     * allows blobs up to 100 MB. Raise {@code maxResponseBytes} in the transport configuration for such blobs, and
     * expect the response text and the decoded bytes to be in memory at the same time.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param fileSha blob SHA
     * @return blob, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubBlob> getBlob(final String owner, final String repo, final String fileSha)
            throws IOException {

        return caller.fetchOrEmpty(get(BLOB, owner, repo, fileSha), GitHubBlob.class);
    }
}
