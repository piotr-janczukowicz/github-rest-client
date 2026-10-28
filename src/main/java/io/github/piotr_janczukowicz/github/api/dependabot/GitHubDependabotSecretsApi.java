package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
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

/** GitHub Dependabot secrets REST resource for API version {@code 2026-03-10}. */
public final class GitHubDependabotSecretsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_DEPENDABOT_SECRETS = PathTemplate.of("/orgs/{org}/dependabot/secrets");
    private static final PathTemplate ORG_DEPENDABOT_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/orgs/{org}/dependabot/secrets/public-key");
    private static final PathTemplate ORG_DEPENDABOT_SECRET =
            PathTemplate.of("/orgs/{org}/dependabot/secrets/{secret_name}");
    private static final PathTemplate ORG_DEPENDABOT_SECRET_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/dependabot/secrets/{secret_name}/repositories");
    private static final PathTemplate ORG_DEPENDABOT_SECRET_REPOSITORY =
            PathTemplate.of("/orgs/{org}/dependabot/secrets/{secret_name}/repositories/{repository_id}");
    private static final PathTemplate REPO_DEPENDABOT_SECRETS =
            PathTemplate.of("/repos/{owner}/{repo}/dependabot/secrets");
    private static final PathTemplate REPO_DEPENDABOT_SECRETS_PUBLIC_KEY =
            PathTemplate.of("/repos/{owner}/{repo}/dependabot/secrets/public-key");
    private static final PathTemplate REPO_DEPENDABOT_SECRET =
            PathTemplate.of("/repos/{owner}/{repo}/dependabot/secrets/{secret_name}");

    /**
     * Dependabot secrets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependabotSecretsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/dependabot/secrets}).
     *
     * @param org organization name
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgDependabotSecret> listOrgSecrets(final String org) throws IOException {
        return listOrgSecrets(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization secrets ({@code GET /orgs/{org}/dependabot/secrets}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of organization secrets
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgDependabotSecret> listOrgSecrets(final String org, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_DEPENDABOT_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrgDependabotSecretsPage.class,
                        GitHubOrgDependabotSecretsPage::secrets,
                        GitHubOrgDependabotSecretsPage::totalCount),
                org);
    }

    /**
     * Get an organization public key ({@code GET /orgs/{org}/dependabot/secrets/public-key}).
     *
     * @param org organization name
     * @return public key used to encrypt secrets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDependabotPublicKey getOrgPublicKey(final String org) throws IOException {
        return caller.fetch(get(ORG_DEPENDABOT_SECRETS_PUBLIC_KEY, org), GitHubDependabotPublicKey.class);
    }

    /**
     * Get an organization secret ({@code GET /orgs/{org}/dependabot/secrets/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @return organization secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubOrgDependabotSecret> getOrgSecret(final String org, final String secretName)
            throws IOException {
        return caller.fetchOrEmpty(get(ORG_DEPENDABOT_SECRET, org, secretName), GitHubOrgDependabotSecret.class);
    }

    /**
     * Create or update an organization secret ({@code PUT /orgs/{org}/dependabot/secrets/{secret_name}}).
     *
     * <p>GitHub answers HTTP 201 when the secret is created and HTTP 204 when it is updated.
     *
     * @param org organization name
     * @param secretName secret name
     * @param payload encrypted value and visibility
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

        return GitHubSecretChange.fromStatus(
                caller.send(caller.json(put(ORG_DEPENDABOT_SECRET, org, secretName), payload))
                        .statusCode());
    }

    /**
     * Delete an organization secret ({@code DELETE /orgs/{org}/dependabot/secrets/{secret_name}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteOrgSecret(final String org, final String secretName) throws IOException {
        caller.send(delete(ORG_DEPENDABOT_SECRET, org, secretName));
    }

    /**
     * List selected repositories for an organization secret ({@code GET
     * /orgs/{org}/dependabot/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @return first HTTP page of repositories that can access the secret
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgSecret(final String org, final String secretName)
            throws IOException {
        return listSelectedReposForOrgSecret(org, secretName, GitHubPageRequest.byPage());
    }

    /**
     * List selected repositories for an organization secret ({@code GET
     * /orgs/{org}/dependabot/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param request pagination token
     * @return first HTTP page of repositories that can access the secret
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgSecret(
            final String org, final String secretName, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_DEPENDABOT_SECRET_REPOSITORIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubDependabotSelectedRepositoriesPage.class,
                        GitHubDependabotSelectedRepositoriesPage::repositories,
                        GitHubDependabotSelectedRepositoriesPage::totalCount),
                org,
                secretName);
    }

    /**
     * Set selected repositories for an organization secret ({@code PUT
     * /orgs/{org}/dependabot/secrets/{secret_name}/repositories}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param payload repository IDs that can access the secret
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setSelectedReposForOrgSecret(
            final String org, final String secretName, final GitHubSetSelectedReposForOrgSecretPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_DEPENDABOT_SECRET_REPOSITORIES, org, secretName), payload));
    }

    /**
     * Add selected repository to an organization secret ({@code PUT
     * /orgs/{org}/dependabot/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     */
    public void addSelectedRepoToOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        caller.send(put(ORG_DEPENDABOT_SECRET_REPOSITORY, org, secretName, repositoryId));
    }

    /**
     * Remove selected repository from an organization secret ({@code DELETE
     * /orgs/{org}/dependabot/secrets/{secret_name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param secretName secret name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     */
    public void removeSelectedRepoFromOrgSecret(final String org, final String secretName, final long repositoryId)
            throws IOException {
        caller.send(delete(ORG_DEPENDABOT_SECRET_REPOSITORY, org, secretName, repositoryId));
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/dependabot/secrets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of repository secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotSecret> listRepoSecrets(final String owner, final String repo)
            throws IOException {
        return listRepoSecrets(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository secrets ({@code GET /repos/{owner}/{repo}/dependabot/secrets}).
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
    public GitHubPage<GitHubDependabotSecret> listRepoSecrets(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_DEPENDABOT_SECRETS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubDependabotSecretsPage.class,
                        GitHubDependabotSecretsPage::secrets,
                        GitHubDependabotSecretsPage::totalCount),
                owner,
                repo);
    }

    /**
     * Get a repository public key ({@code GET /repos/{owner}/{repo}/dependabot/secrets/public-key}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return public key used to encrypt secrets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDependabotPublicKey getRepoPublicKey(final String owner, final String repo) throws IOException {
        return caller.fetch(get(REPO_DEPENDABOT_SECRETS_PUBLIC_KEY, owner, repo), GitHubDependabotPublicKey.class);
    }

    /**
     * Get a repository secret ({@code GET /repos/{owner}/{repo}/dependabot/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @return repository secret metadata, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubDependabotSecret> getRepoSecret(
            final String owner, final String repo, final String secretName) throws IOException {
        return caller.fetchOrEmpty(get(REPO_DEPENDABOT_SECRET, owner, repo, secretName), GitHubDependabotSecret.class);
    }

    /**
     * Create or update a repository secret ({@code PUT /repos/{owner}/{repo}/dependabot/secrets/{secret_name}}).
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
     */
    public GitHubSecretChange createOrUpdateRepoSecret(
            final String owner,
            final String repo,
            final String secretName,
            final GitHubCreateOrUpdateRepoSecretPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return GitHubSecretChange.fromStatus(
                caller.send(caller.json(put(REPO_DEPENDABOT_SECRET, owner, repo, secretName), payload))
                        .statusCode());
    }

    /**
     * Delete a repository secret ({@code DELETE /repos/{owner}/{repo}/dependabot/secrets/{secret_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param secretName secret name
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code secretName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRepoSecret(final String owner, final String repo, final String secretName) throws IOException {
        caller.send(delete(REPO_DEPENDABOT_SECRET, owner, repo, secretName));
    }
}
