package io.github.piotr_janczukowicz.github.api.repositories;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/**
 * GitHub repository Git LFS REST resource for API version {@code 2026-03-10}.
 *
 * <p>Enable and disable Git Large File Storage for a repository.
 */
public final class GitHubRepositoriesLfsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_LFS = PathTemplate.of("/repos/{owner}/{repo}/lfs");

    /**
     * Repository Git LFS resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesLfsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Enable Git LFS for a repository ({@code PUT /repos/{owner}/{repo}/lfs}).
     *
     * <p>The response is HTTP 202 Accepted without a body; the call returns when GitHub accepts the request.
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void enableLfsForRepo(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(put(REPO_LFS, owner, repo));
    }

    /**
     * Disable Git LFS for a repository ({@code DELETE /repos/{owner}/{repo}/lfs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void disableLfsForRepo(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(REPO_LFS, owner, repo));
    }
}
