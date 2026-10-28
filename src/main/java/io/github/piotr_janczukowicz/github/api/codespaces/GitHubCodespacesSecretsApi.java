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

/** GitHub Codespaces user secrets REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodespacesSecretsApi extends GitHubResourceApi {

    private static final PathTemplate USER_CODESPACES_SECRETS = PathTemplate.of("/user/codespaces/secrets");
    private static final PathTemplate USER_CODESPACES_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/user/codespaces/secrets/public-key");
    private static final PathTemplate USER_CODESPACES_SECRET =
            PathTemplate.of("/user/codespaces/secrets/{secret_name}");
    private static final PathTemplate USER_CODESPACES_SECRET_REPOSITORIES =
            PathTemplate.of("/user/codespaces/secrets/{secret_name}/repositories");
    private static final PathTemplate USER_CODESPACES_SECRET_REPOSITORY =
            PathTemplate.of("/user/codespaces/secrets/{secret_name}/repositories/{repository_id}");

    /**
     * Codespaces user secrets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodespacesSecretsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List secrets for the authenticated user ({@code GET /user/codespaces/secrets}).
     *
     * @return first HTTP page of user secrets
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespacesSecret> listForAuthenticatedUser() throws IOException {
        return listForAuthenticatedUser(GitHubPageRequest.byPage());
    }

    /**
     * List secrets for the authenticated user ({@code GET /user/codespaces/secrets}).
     *
     * @param request pagination token
     * @return first HTTP page of user secrets
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespacesSecret> listForAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                USER_CODESPACES_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubCodespacesSecretsPage.class,
                        GitHubCodespacesSecretsPage::secrets,
                        GitHubCodespacesSecretsPage::totalCount));
    }

    /**
     * Get public key for the authenticated user ({@code GET /user/codespaces/secrets/public-key}).
     *
     * @return public key used to encrypt secrets
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespacesUserPublicKey getPublicKeyForAuthenticatedUser() throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(USER_CODESPACES_SECRETS_PUBLIC_KEY), GitHubCodespacesUserPublicKey.class);
    }

    /**
     * Get a secret for the authenticated user ({@code GET /user/codespaces/secrets/{secret_name}}).
     *
     * @param secretName secret name
     * @return user secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodespacesSecret> getSecretForAuthenticatedUser(final String secretName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(USER_CODESPACES_SECRET, secretName), GitHubCodespacesSecret.class);
    }

    /**
     * Create or update a secret for the authenticated user ({@code PUT /user/codespaces/secrets/{secret_name}}).
     *
     * <p>GitHub answers HTTP 201 when the secret is created and HTTP 204 when it is updated.
     *
     * @param secretName secret name
     * @param payload encrypted value, key ID and selected repositories
     * @return {@link GitHubSecretChange#CREATED} when GitHub created the secret (HTTP 201),
     *     {@link GitHubSecretChange#UPDATED} when it replaced an existing one (HTTP 204)
     * @throws IllegalArgumentException if {@code secretName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubSecretChange createOrUpdateSecretForAuthenticatedUser(
            final String secretName, final GitHubCreateOrUpdateSecretForAuthenticatedUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return GitHubSecretChange.fromStatus(caller.send(caller.json(put(USER_CODESPACES_SECRET, secretName), payload))
                .statusCode());
    }

    /**
     * Delete a secret for the authenticated user ({@code DELETE /user/codespaces/secrets/{secret_name}}).
     *
     * @param secretName secret name
     * @throws IllegalArgumentException if {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteSecretForAuthenticatedUser(final String secretName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(USER_CODESPACES_SECRET, secretName));
    }

    /**
     * List selected repositories for a user secret ({@code GET /user/codespaces/secrets/{secret_name}/repositories}).
     *
     * @param secretName secret name
     * @return selected repositories with their total count
     * @throws IllegalArgumentException if {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespacesSelectedRepositoriesPage listRepositoriesForSecretForAuthenticatedUser(
            final String secretName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(USER_CODESPACES_SECRET_REPOSITORIES, secretName), GitHubCodespacesSelectedRepositoriesPage.class);
    }

    /**
     * Set selected repositories for a user secret ({@code PUT /user/codespaces/secrets/{secret_name}/repositories}).
     *
     * @param secretName secret name
     * @param payload repository IDs that can access the secret
     * @throws IllegalArgumentException if {@code secretName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setRepositoriesForSecretForAuthenticatedUser(
            final String secretName, final GitHubSetRepositoriesForSecretForAuthenticatedUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(USER_CODESPACES_SECRET_REPOSITORIES, secretName), payload));
    }

    /**
     * Add a selected repository to a user secret ({@code PUT
     * /user/codespaces/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void addRepositoryForSecretForAuthenticatedUser(final String secretName, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(USER_CODESPACES_SECRET_REPOSITORY, secretName, repositoryId));
    }

    /**
     * Remove a selected repository from a user secret ({@code DELETE
     * /user/codespaces/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeRepositoryForSecretForAuthenticatedUser(final String secretName, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(USER_CODESPACES_SECRET_REPOSITORY, secretName, repositoryId));
    }
}
