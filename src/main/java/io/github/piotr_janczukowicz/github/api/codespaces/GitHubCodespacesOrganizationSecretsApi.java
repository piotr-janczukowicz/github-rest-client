package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
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

/** GitHub Codespaces organization secrets REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodespacesOrganizationSecretsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_CODESPACES_SECRETS = PathTemplate.of("/orgs/{org}/codespaces/secrets");
    private static final PathTemplate ORG_CODESPACES_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/orgs/{org}/codespaces/secrets/public-key");
    private static final PathTemplate ORG_CODESPACES_SECRET =
            PathTemplate.of("/orgs/{org}/codespaces/secrets/{secret_name}");
    private static final PathTemplate ORG_CODESPACES_SECRET_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/codespaces/secrets/{secret_name}/repositories");
    private static final PathTemplate ORG_CODESPACES_SECRET_REPOSITORY =
            PathTemplate.of("/orgs/{org}/codespaces/secrets/{secret_name}/repositories/{repository_id}");

    /**
     * Codespaces organization secrets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodespacesOrganizationSecretsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/codespaces/secrets}).
     *
     * @param org organization name
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespacesOrgSecret> listOrgSecrets(final String org) throws IOException {
        return listOrgSecrets(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/codespaces/secrets}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodespacesOrgSecret> listOrgSecrets(final String org, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_CODESPACES_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrgCodespacesSecretsPage.class,
                        GitHubOrgCodespacesSecretsPage::secrets,
                        GitHubOrgCodespacesSecretsPage::totalCount),
                org);
    }

    /**
     * Get an organization public key ({@code GET /orgs/{org}/codespaces/secrets/public-key}).
     *
     * @param org organization name
     * @return public key used to encrypt secrets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodespacesPublicKey getOrgPublicKey(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_CODESPACES_SECRETS_PUBLIC_KEY, org), GitHubCodespacesPublicKey.class);
    }

    /**
     * Get an organization secret ({@code GET /orgs/{org}/codespaces/secrets/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @return organization secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodespacesOrgSecret> getOrgSecret(final String org, final String secretName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_CODESPACES_SECRET, org, secretName), GitHubCodespacesOrgSecret.class);
    }

    /**
     * Create or update an organization secret ({@code PUT /orgs/{org}/codespaces/secrets/{secret_name}}).
     *
     * <p>GitHub answers HTTP 201 when the secret is created and HTTP 204 when it is updated.
     *
     * @param org organization name
     * @param secretName secret name
     * @param payload encrypted value, key ID and visibility
     * @return {@link GitHubSecretChange#CREATED} when GitHub created the secret (HTTP 201),
     *     {@link GitHubSecretChange#UPDATED} when it replaced an existing one (HTTP 204)
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubSecretChange createOrUpdateOrgSecret(
            final String org, final String secretName, final GitHubCreateOrUpdateOrgSecretPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return GitHubSecretChange.fromStatus(
                caller.send(caller.json(put(ORG_CODESPACES_SECRET, org, secretName), payload))
                        .statusCode());
    }

    /**
     * Delete an organization secret ({@code DELETE /orgs/{org}/codespaces/secrets/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteOrgSecret(final String org, final String secretName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_CODESPACES_SECRET, org, secretName));
    }

    /**
     * List selected repositories for an organization secret ({@code GET
     * /orgs/{org}/codespaces/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @return first HTTP page of repositories that can access the secret
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgSecret(final String org, final String secretName)
            throws IOException {
        return listSelectedReposForOrgSecret(org, secretName, GitHubPageRequest.byPage());
    }

    /**
     * List selected repositories for an organization secret ({@code GET
     * /orgs/{org}/codespaces/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param request pagination token
     * @return first HTTP page of repositories that can access the secret
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgSecret(
            final String org, final String secretName, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_CODESPACES_SECRET_REPOSITORIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubCodespacesSelectedRepositoriesPage.class,
                        GitHubCodespacesSelectedRepositoriesPage::repositories,
                        GitHubCodespacesSelectedRepositoriesPage::totalCount),
                org,
                secretName);
    }

    /**
     * Set selected repositories for an organization secret ({@code PUT
     * /orgs/{org}/codespaces/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param payload repository IDs that can access the secret
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setSelectedReposForOrgSecret(
            final String org, final String secretName, final GitHubSetSelectedReposForOrgSecretPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_CODESPACES_SECRET_REPOSITORIES, org, secretName), payload));
    }

    /**
     * Add selected repository to an organization secret ({@code PUT
     * /orgs/{org}/codespaces/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void addSelectedRepoToOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(ORG_CODESPACES_SECRET_REPOSITORY, org, secretName, repositoryId));
    }

    /**
     * Remove selected repository from an organization secret ({@code DELETE
     * /orgs/{org}/codespaces/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeSelectedRepoFromOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_CODESPACES_SECRET_REPOSITORY, org, secretName, repositoryId));
    }
}
