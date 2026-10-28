package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsSecret;
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

/** GitHub Agents secrets REST resource for API version {@code 2026-03-10}. */
public final class GitHubAgentsSecretsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_AGENTS_SECRETS = PathTemplate.of("/orgs/{org}/agents/secrets");
    private static final PathTemplate ORG_AGENTS_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/orgs/{org}/agents/secrets/public-key");
    private static final PathTemplate ORG_AGENTS_SECRET = PathTemplate.of("/orgs/{org}/agents/secrets/{secret_name}");
    private static final PathTemplate ORG_AGENTS_SECRET_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/agents/secrets/{secret_name}/repositories");
    private static final PathTemplate ORG_AGENTS_SECRET_REPOSITORY =
            PathTemplate.of("/orgs/{org}/agents/secrets/{secret_name}/repositories/{repository_id}");
    private static final PathTemplate REPO_AGENTS_ORGANIZATION_SECRETS =
            PathTemplate.of("/repos/{owner}/{repo}/agents/organization-secrets");
    private static final PathTemplate REPO_AGENTS_SECRETS = PathTemplate.of("/repos/{owner}/{repo}/agents/secrets");
    private static final PathTemplate REPO_AGENTS_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/repos/{owner}/{repo}/agents/secrets/public-key");
    private static final PathTemplate REPO_AGENTS_SECRET =
            PathTemplate.of("/repos/{owner}/{repo}/agents/secrets/{secret_name}");

    /**
     * Agents secrets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAgentsSecretsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/agents/secrets}).
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
    public GitHubPage<GitHubOrganizationActionsSecret> listOrgSecrets(final String org) throws IOException {
        return listOrgSecrets(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/agents/secrets}).
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
    public GitHubPage<GitHubOrganizationActionsSecret> listOrgSecrets(final String org, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_AGENTS_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrgActionsSecretsPage.class,
                        GitHubOrgActionsSecretsPage::secrets,
                        GitHubOrgActionsSecretsPage::totalCount),
                org);
    }

    /**
     * Get an organization public key ({@code GET /orgs/{org}/agents/secrets/public-key}).
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
    public GitHubActionsPublicKey getOrgPublicKey(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_AGENTS_SECRETS_PUBLIC_KEY, org), GitHubActionsPublicKey.class);
    }

    /**
     * Get an organization secret ({@code GET /orgs/{org}/agents/secrets/{secret_name}}).
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
    public Optional<GitHubOrganizationActionsSecret> getOrgSecret(final String org, final String secretName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_AGENTS_SECRET, org, secretName), GitHubOrganizationActionsSecret.class);
    }

    /**
     * Create or update an organization secret ({@code PUT /orgs/{org}/agents/secrets/{secret_name}}).
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

        return GitHubSecretChange.fromStatus(caller.send(caller.json(put(ORG_AGENTS_SECRET, org, secretName), payload))
                .statusCode());
    }

    /**
     * Delete an organization secret ({@code DELETE /orgs/{org}/agents/secrets/{secret_name}}).
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
        caller.send(delete(ORG_AGENTS_SECRET, org, secretName));
    }

    /**
     * List selected repositories for an organization secret ({@code GET
     * /orgs/{org}/agents/secrets/{secret_name}/repositories}).
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
     * /orgs/{org}/agents/secrets/{secret_name}/repositories}).
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
                ORG_AGENTS_SECRET_REPOSITORIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrgSecretSelectedRepositoriesPage.class,
                        GitHubOrgSecretSelectedRepositoriesPage::repositories,
                        GitHubOrgSecretSelectedRepositoriesPage::totalCount),
                org,
                secretName);
    }

    /**
     * Set selected repositories for an organization secret ({@code PUT
     * /orgs/{org}/agents/secrets/{secret_name}/repositories}).
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

        caller.send(caller.json(put(ORG_AGENTS_SECRET_REPOSITORIES, org, secretName), payload));
    }

    /**
     * Add selected repository to an organization secret ({@code PUT
     * /orgs/{org}/agents/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void addSelectedRepoToOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(ORG_AGENTS_SECRET_REPOSITORY, org, secretName, repositoryId));
    }

    /**
     * Remove selected repository from an organization secret ({@code DELETE
     * /orgs/{org}/agents/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeSelectedRepoFromOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_AGENTS_SECRET_REPOSITORY, org, secretName, repositoryId));
    }

    /**
     * List repository organization secrets ({@code GET /repos/{owner}/{repo}/agents/organization-secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of organization secrets visible to the repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsSecret> listRepoOrganizationSecrets(final String owner, final String repo)
            throws IOException {
        return listRepoOrganizationSecrets(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository organization secrets ({@code GET /repos/{owner}/{repo}/agents/organization-secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of organization secrets visible to the repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsSecret> listRepoOrganizationSecrets(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_AGENTS_ORGANIZATION_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubRepoOrganizationSecretsPage.class,
                        GitHubRepoOrganizationSecretsPage::secrets,
                        GitHubRepoOrganizationSecretsPage::totalCount),
                owner,
                repo);
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/agents/secrets}).
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
    public GitHubPage<GitHubActionsSecret> listRepoSecrets(final String owner, final String repo) throws IOException {
        return listRepoSecrets(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/agents/secrets}).
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
    public GitHubPage<GitHubActionsSecret> listRepoSecrets(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_AGENTS_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubActionsSecretsPage.class,
                        GitHubActionsSecretsPage::secrets,
                        GitHubActionsSecretsPage::totalCount),
                owner,
                repo);
    }

    /**
     * Get a repository public key ({@code GET /repos/{owner}/{repo}/agents/secrets/public-key}).
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
    public GitHubActionsPublicKey getRepoPublicKey(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(REPO_AGENTS_SECRETS_PUBLIC_KEY, owner, repo), GitHubActionsPublicKey.class);
    }

    /**
     * Get a repository secret ({@code GET /repos/{owner}/{repo}/agents/secrets/{secret_name}}).
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
    public Optional<GitHubActionsSecret> getRepoSecret(final String owner, final String repo, final String secretName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(REPO_AGENTS_SECRET, owner, repo, secretName), GitHubActionsSecret.class);
    }

    /**
     * Create or update a repository secret ({@code PUT /repos/{owner}/{repo}/agents/secrets/{secret_name}}).
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
                caller.send(caller.json(put(REPO_AGENTS_SECRET, owner, repo, secretName), payload))
                        .statusCode());
    }

    /**
     * Delete a repository secret ({@code DELETE /repos/{owner}/{repo}/agents/secrets/{secret_name}}).
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
        caller.send(delete(REPO_AGENTS_SECRET, owner, repo, secretName));
    }
}
