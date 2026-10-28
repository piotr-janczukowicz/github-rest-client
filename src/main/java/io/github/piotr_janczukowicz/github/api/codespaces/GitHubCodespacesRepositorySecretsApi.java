package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/** GitHub Codespaces repository secrets REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodespacesRepositorySecretsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_CODESPACES_SECRETS =
            PathTemplate.of("/repos/{owner}/{repo}/codespaces/secrets");
    private static final PathTemplate REPO_CODESPACES_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/repos/{owner}/{repo}/codespaces/secrets/public-key");
    private static final PathTemplate REPO_CODESPACES_SECRET =
            PathTemplate.of("/repos/{owner}/{repo}/codespaces/secrets/{secret_name}");

    /**
     * Codespaces repository secrets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodespacesRepositorySecretsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/codespaces/secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of repository secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepoCodespacesSecret> listRepoSecrets(final String owner, final String repo)
            throws IOException {
        return listRepoSecrets(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/codespaces/secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of repository secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepoCodespacesSecret> listRepoSecrets(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_CODESPACES_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubRepoCodespacesSecretsPage.class,
                        GitHubRepoCodespacesSecretsPage::secrets,
                        GitHubRepoCodespacesSecretsPage::totalCount),
                owner,
                repo);
    }

    /**
     * Get a repository public key ({@code GET /repos/{owner}/{repo}/codespaces/secrets/public-key}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return public key used to encrypt secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespacesPublicKey getRepoPublicKey(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(REPO_CODESPACES_SECRETS_PUBLIC_KEY, owner, repo), GitHubCodespacesPublicKey.class);
    }

    /**
     * Get a repository secret ({@code GET /repos/{owner}/{repo}/codespaces/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @return repository secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubRepoCodespacesSecret> getRepoSecret(
            final String owner, final String repo, final String secretName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(REPO_CODESPACES_SECRET, owner, repo, secretName), GitHubRepoCodespacesSecret.class);
    }

    /**
     * Create or update a repository secret ({@code PUT /repos/{owner}/{repo}/codespaces/secrets/{secret_name}}).
     *
     * <p>GitHub answers HTTP 201 when the secret is created and HTTP 204 when it is updated.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @param payload encrypted value and key ID
     * @return {@link GitHubSecretChange#CREATED} when GitHub created the secret (HTTP 201),
     *     {@link GitHubSecretChange#UPDATED} when it replaced an existing one (HTTP 204)
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code secretName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubSecretChange createOrUpdateRepoSecret(
            final String owner,
            final String repo,
            final String secretName,
            final GitHubCreateOrUpdateRepoSecretPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return GitHubSecretChange.fromStatus(
                caller.send(caller.json(put(REPO_CODESPACES_SECRET, owner, repo, secretName), payload))
                        .statusCode());
    }

    /**
     * Delete a repository secret ({@code DELETE /repos/{owner}/{repo}/codespaces/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteRepoSecret(final String owner, final String repo, final String secretName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(REPO_CODESPACES_SECRET, owner, repo, secretName));
    }
}
