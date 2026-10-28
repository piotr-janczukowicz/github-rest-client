package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsPublicKey;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsSecret;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsSecret;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
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

/** GitHub Actions secrets REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsSecretsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_SECRETS = PathTemplate.of("/orgs/{org}/actions/secrets");
    private static final PathTemplate ORG_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/orgs/{org}/actions/secrets/public-key");
    private static final PathTemplate ORG_SECRET = PathTemplate.of("/orgs/{org}/actions/secrets/{secret_name}");
    private static final PathTemplate ORG_SECRET_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/actions/secrets/{secret_name}/repositories");
    private static final PathTemplate ORG_SECRET_REPOSITORY =
            PathTemplate.of("/orgs/{org}/actions/secrets/{secret_name}/repositories/{repository_id}");
    private static final PathTemplate REPO_ORGANIZATION_SECRETS =
            PathTemplate.of("/repos/{owner}/{repo}/actions/organization-secrets");
    private static final PathTemplate REPO_SECRETS = PathTemplate.of("/repos/{owner}/{repo}/actions/secrets");
    private static final PathTemplate REPO_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/repos/{owner}/{repo}/actions/secrets/public-key");
    private static final PathTemplate REPO_SECRET =
            PathTemplate.of("/repos/{owner}/{repo}/actions/secrets/{secret_name}");
    private static final PathTemplate ENVIRONMENT_SECRETS =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/secrets");
    private static final PathTemplate ENVIRONMENT_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/secrets/public-key");
    private static final PathTemplate ENVIRONMENT_SECRET =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/secrets/{secret_name}");

    private static final GitHubListSpec<GitHubOrganizationActionsSecret> ORG_SECRETS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubOrganizationActionsSecrets.class,
            GitHubOrganizationActionsSecrets::secrets,
            GitHubOrganizationActionsSecrets::totalCount);
    private static final GitHubListSpec<GitHubMinimalRepository> SELECTED_REPOSITORIES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubSelectedRepositories.class,
            GitHubSelectedRepositories::repositories,
            GitHubSelectedRepositories::totalCount);
    private static final GitHubListSpec<GitHubActionsSecret> REPO_SECRETS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubActionsSecrets.class,
            GitHubActionsSecrets::secrets,
            GitHubActionsSecrets::totalCount);

    /**
     * Secrets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsSecretsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/actions/secrets}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationActionsSecret> listOrgSecrets(final String org) throws IOException {
        return listOrgSecrets(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/actions/secrets}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationActionsSecret> listOrgSecrets(final String org, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_SECRETS, Map.of(), request, ORG_SECRETS_SPEC, org);
    }

    /**
     * Get an organization public key ({@code GET /orgs/{org}/actions/secrets/public-key}).
     *
     * @param org organization name
     * @return public key used to encrypt secrets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsPublicKey getOrgPublicKey(final String org) throws IOException {
        return caller.fetch(get(ORG_SECRETS_PUBLIC_KEY, org), GitHubActionsPublicKey.class);
    }

    /**
     * Get an organization secret ({@code GET /orgs/{org}/actions/secrets/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @return organization secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubOrganizationActionsSecret> getOrgSecret(final String org, final String secretName)
            throws IOException {
        return caller.fetchOrEmpty(get(ORG_SECRET, org, secretName), GitHubOrganizationActionsSecret.class);
    }

    /**
     * Create or update an organization secret ({@code PUT /orgs/{org}/actions/secrets/{secret_name}}).
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
     */
    public GitHubSecretChange createOrUpdateOrgSecret(
            final String org, final String secretName, final GitHubCreateOrUpdateOrgSecretPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return GitHubSecretChange.fromStatus(caller.send(caller.json(put(ORG_SECRET, org, secretName), payload))
                .statusCode());
    }

    /**
     * Delete an organization secret ({@code DELETE /orgs/{org}/actions/secrets/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteOrgSecret(final String org, final String secretName) throws IOException {
        caller.send(delete(ORG_SECRET, org, secretName));
    }

    /**
     * List repositories with access to an organization secret ({@code GET
     * /orgs/{org}/actions/secrets/{secret_name}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @param secretName secret name
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgSecret(final String org, final String secretName)
            throws IOException {
        return listSelectedReposForOrgSecret(org, secretName, GitHubPageRequest.byPage());
    }

    /**
     * List repositories with access to an organization secret ({@code GET
     * /orgs/{org}/actions/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgSecret(
            final String org, final String secretName, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_SECRET_REPOSITORIES, Map.of(), request, SELECTED_REPOSITORIES_SPEC, org, secretName);
    }

    /**
     * Set repositories with access to an organization secret ({@code PUT
     * /orgs/{org}/actions/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param payload repository IDs
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setSelectedReposForOrgSecret(
            final String org, final String secretName, final GitHubSetSelectedReposForOrgSecretPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_SECRET_REPOSITORIES, org, secretName), payload));
    }

    /**
     * Add a repository to an organization secret ({@code PUT
     * /orgs/{org}/actions/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void addSelectedRepoToOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        caller.send(put(ORG_SECRET_REPOSITORY, org, secretName, repositoryId));
    }

    /**
     * Remove a repository from an organization secret ({@code DELETE
     * /orgs/{org}/actions/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeSelectedRepoFromOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        caller.send(delete(ORG_SECRET_REPOSITORY, org, secretName, repositoryId));
    }

    /**
     * List organization secrets visible to a repository ({@code GET
     * /repos/{owner}/{repo}/actions/organization-secrets}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsSecret> listRepoOrganizationSecrets(final String owner, final String repo)
            throws IOException {
        return listRepoOrganizationSecrets(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List organization secrets visible to a repository ({@code GET
     * /repos/{owner}/{repo}/actions/organization-secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsSecret> listRepoOrganizationSecrets(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_ORGANIZATION_SECRETS, Map.of(), request, REPO_SECRETS_SPEC, owner, repo);
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/actions/secrets}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of repository secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsSecret> listRepoSecrets(final String owner, final String repo) throws IOException {
        return listRepoSecrets(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/actions/secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of repository secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsSecret> listRepoSecrets(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_SECRETS, Map.of(), request, REPO_SECRETS_SPEC, owner, repo);
    }

    /**
     * Get a repository public key ({@code GET /repos/{owner}/{repo}/actions/secrets/public-key}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return public key used to encrypt secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsPublicKey getRepoPublicKey(final String owner, final String repo) throws IOException {
        return caller.fetch(get(REPO_SECRETS_PUBLIC_KEY, owner, repo), GitHubActionsPublicKey.class);
    }

    /**
     * Get a repository secret ({@code GET /repos/{owner}/{repo}/actions/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @return repository secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubActionsSecret> getRepoSecret(final String owner, final String repo, final String secretName)
            throws IOException {
        return caller.fetchOrEmpty(get(REPO_SECRET, owner, repo, secretName), GitHubActionsSecret.class);
    }

    /**
     * Create or update a repository secret ({@code PUT /repos/{owner}/{repo}/actions/secrets/{secret_name}}).
     *
     * <p>GitHub answers HTTP 201 when the secret is created and HTTP 204 when it is updated.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @param payload encrypted value and key ID
     * @return {@link GitHubSecretChange#CREATED} when GitHub created the secret (HTTP 201),
     *     {@link GitHubSecretChange#UPDATED} when it replaced an existing one (HTTP 204)
     * @throws IllegalArgumentException if any argument is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSecretChange createOrUpdateRepoSecret(
            final String owner,
            final String repo,
            final String secretName,
            final GitHubCreateOrUpdateRepoSecretPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return GitHubSecretChange.fromStatus(
                caller.send(caller.json(put(REPO_SECRET, owner, repo, secretName), payload))
                        .statusCode());
    }

    /**
     * Delete a repository secret ({@code DELETE /repos/{owner}/{repo}/actions/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRepoSecret(final String owner, final String repo, final String secretName) throws IOException {
        caller.send(delete(REPO_SECRET, owner, repo, secretName));
    }

    /**
     * List environment secrets ({@code GET /repos/{owner}/{repo}/environments/{environment_name}/secrets}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @return first HTTP page of environment secrets
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsSecret> listEnvironmentSecrets(
            final String owner, final String repo, final String environmentName) throws IOException {
        return listEnvironmentSecrets(owner, repo, environmentName, GitHubPageRequest.byPage());
    }

    /**
     * List environment secrets ({@code GET /repos/{owner}/{repo}/environments/{environment_name}/secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param request pagination token
     * @return first HTTP page of environment secrets
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsSecret> listEnvironmentSecrets(
            final String owner, final String repo, final String environmentName, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ENVIRONMENT_SECRETS, Map.of(), request, REPO_SECRETS_SPEC, owner, repo, environmentName);
    }

    /**
     * Get an environment public key ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/secrets/public-key}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @return public key used to encrypt secrets
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsPublicKey getEnvironmentPublicKey(
            final String owner, final String repo, final String environmentName) throws IOException {
        return caller.fetch(
                get(ENVIRONMENT_SECRETS_PUBLIC_KEY, owner, repo, environmentName), GitHubActionsPublicKey.class);
    }

    /**
     * Get an environment secret ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param secretName secret name
     * @return environment secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubActionsSecret> getEnvironmentSecret(
            final String owner, final String repo, final String environmentName, final String secretName)
            throws IOException {
        return caller.fetchOrEmpty(
                get(ENVIRONMENT_SECRET, owner, repo, environmentName, secretName), GitHubActionsSecret.class);
    }

    /**
     * Create or update an environment secret ({@code PUT
     * /repos/{owner}/{repo}/environments/{environment_name}/secrets/{secret_name}}).
     *
     * <p>GitHub answers HTTP 201 when the secret is created and HTTP 204 when it is updated.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param secretName secret name
     * @param payload encrypted value and key ID
     * @return {@link GitHubSecretChange#CREATED} when GitHub created the secret (HTTP 201),
     *     {@link GitHubSecretChange#UPDATED} when it replaced an existing one (HTTP 204)
     * @throws IllegalArgumentException if any argument is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSecretChange createOrUpdateEnvironmentSecret(
            final String owner,
            final String repo,
            final String environmentName,
            final String secretName,
            final GitHubCreateOrUpdateEnvironmentSecretPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return GitHubSecretChange.fromStatus(
                caller.send(caller.json(put(ENVIRONMENT_SECRET, owner, repo, environmentName, secretName), payload))
                        .statusCode());
    }

    /**
     * Delete an environment secret ({@code DELETE
     * /repos/{owner}/{repo}/environments/{environment_name}/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param secretName secret name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteEnvironmentSecret(
            final String owner, final String repo, final String environmentName, final String secretName)
            throws IOException {
        caller.send(delete(ENVIRONMENT_SECRET, owner, repo, environmentName, secretName));
    }
}
