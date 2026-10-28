package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsVariable;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsVariable;
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

/** GitHub Actions variables REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsVariablesApi extends GitHubResourceApi {

    private static final PathTemplate ORG_VARIABLES = PathTemplate.of("/orgs/{org}/actions/variables");
    private static final PathTemplate ORG_VARIABLE = PathTemplate.of("/orgs/{org}/actions/variables/{name}");
    private static final PathTemplate ORG_VARIABLE_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/actions/variables/{name}/repositories");
    private static final PathTemplate ORG_VARIABLE_REPOSITORY =
            PathTemplate.of("/orgs/{org}/actions/variables/{name}/repositories/{repository_id}");
    private static final PathTemplate REPO_ORGANIZATION_VARIABLES =
            PathTemplate.of("/repos/{owner}/{repo}/actions/organization-variables");
    private static final PathTemplate REPO_VARIABLES = PathTemplate.of("/repos/{owner}/{repo}/actions/variables");
    private static final PathTemplate REPO_VARIABLE = PathTemplate.of("/repos/{owner}/{repo}/actions/variables/{name}");
    private static final PathTemplate ENVIRONMENT_VARIABLES =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/variables");
    private static final PathTemplate ENVIRONMENT_VARIABLE =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/variables/{name}");

    /** Endpoint maximum for {@code per_page} on the variable lists. */
    private static final int VARIABLES_MAX_PER_PAGE = 30;

    private static final GitHubListSpec<GitHubOrganizationActionsVariable> ORG_VARIABLES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubOrganizationActionsVariables.class,
            GitHubOrganizationActionsVariables::variables,
            GitHubOrganizationActionsVariables::totalCount,
            VARIABLES_MAX_PER_PAGE);
    private static final GitHubListSpec<GitHubMinimalRepository> SELECTED_REPOSITORIES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubSelectedRepositories.class,
            GitHubSelectedRepositories::repositories,
            GitHubSelectedRepositories::totalCount);
    private static final GitHubListSpec<GitHubActionsVariable> REPO_VARIABLES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubActionsVariables.class,
            GitHubActionsVariables::variables,
            GitHubActionsVariables::totalCount,
            VARIABLES_MAX_PER_PAGE);

    /**
     * Variables resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsVariablesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization variables ({@code GET /orgs/{org}/actions/variables}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @return first HTTP page of organization variables
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationActionsVariable> listOrgVariables(final String org) throws IOException {
        return listOrgVariables(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization variables ({@code GET /orgs/{org}/actions/variables}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of organization variables
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationActionsVariable> listOrgVariables(
            final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_VARIABLES, Map.of(), request, ORG_VARIABLES_SPEC, org);
    }

    /**
     * Create an organization variable ({@code POST /orgs/{org}/actions/variables}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param org organization name
     * @param payload variable name, value and visibility
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void createOrgVariable(final String org, final GitHubCreateOrgVariablePayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ORG_VARIABLES, org), payload));
    }

    /**
     * Get an organization variable ({@code GET /orgs/{org}/actions/variables/{name}}).
     *
     * @param org organization name
     * @param name variable name
     * @return organization variable, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubOrganizationActionsVariable> getOrgVariable(final String org, final String name)
            throws IOException {
        return caller.fetchOrEmpty(get(ORG_VARIABLE, org, name), GitHubOrganizationActionsVariable.class);
    }

    /**
     * Update an organization variable ({@code PATCH /orgs/{org}/actions/variables/{name}}).
     *
     * @param org organization name
     * @param name variable name
     * @param payload changed name, value, or visibility
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updateOrgVariable(final String org, final String name, final GitHubUpdateOrgVariablePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(ORG_VARIABLE, org, name), payload));
    }

    /**
     * Delete an organization variable ({@code DELETE /orgs/{org}/actions/variables/{name}}).
     *
     * @param org organization name
     * @param name variable name
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteOrgVariable(final String org, final String name) throws IOException {
        caller.send(delete(ORG_VARIABLE, org, name));
    }

    /**
     * List repositories with access to an organization variable ({@code GET
     * /orgs/{org}/actions/variables/{name}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @param name variable name
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgVariable(final String org, final String name)
            throws IOException {
        return listSelectedReposForOrgVariable(org, name, GitHubPageRequest.byPage());
    }

    /**
     * List repositories with access to an organization variable ({@code GET
     * /orgs/{org}/actions/variables/{name}/repositories}).
     *
     * @param org organization name
     * @param name variable name
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgVariable(
            final String org, final String name, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_VARIABLE_REPOSITORIES, Map.of(), request, SELECTED_REPOSITORIES_SPEC, org, name);
    }

    /**
     * Set repositories with access to an organization variable ({@code PUT
     * /orgs/{org}/actions/variables/{name}/repositories}).
     *
     * @param org organization name
     * @param name variable name
     * @param payload repository IDs
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setSelectedReposForOrgVariable(
            final String org, final String name, final GitHubSetSelectedReposForOrgVariablePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_VARIABLE_REPOSITORIES, org, name), payload));
    }

    /**
     * Add a repository to an organization variable ({@code PUT
     * /orgs/{org}/actions/variables/{name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param name variable name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void addSelectedRepoToOrgVariable(final String org, final String name, final long repositoryId)
            throws IOException {
        caller.send(put(ORG_VARIABLE_REPOSITORY, org, name, repositoryId));
    }

    /**
     * Remove a repository from an organization variable ({@code DELETE
     * /orgs/{org}/actions/variables/{name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param name variable name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeSelectedRepoFromOrgVariable(final String org, final String name, final long repositoryId)
            throws IOException {
        caller.send(delete(ORG_VARIABLE_REPOSITORY, org, name, repositoryId));
    }

    /**
     * List organization variables visible to a repository ({@code GET
     * /repos/{owner}/{repo}/actions/organization-variables}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of organization variables
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsVariable> listRepoOrganizationVariables(final String owner, final String repo)
            throws IOException {
        return listRepoOrganizationVariables(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List organization variables visible to a repository ({@code GET
     * /repos/{owner}/{repo}/actions/organization-variables}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of organization variables
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsVariable> listRepoOrganizationVariables(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_ORGANIZATION_VARIABLES, Map.of(), request, REPO_VARIABLES_SPEC, owner, repo);
    }

    /**
     * List repository variables ({@code GET /repos/{owner}/{repo}/actions/variables}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of repository variables
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsVariable> listRepoVariables(final String owner, final String repo)
            throws IOException {
        return listRepoVariables(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository variables ({@code GET /repos/{owner}/{repo}/actions/variables}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of repository variables
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsVariable> listRepoVariables(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_VARIABLES, Map.of(), request, REPO_VARIABLES_SPEC, owner, repo);
    }

    /**
     * Create a repository variable ({@code POST /repos/{owner}/{repo}/actions/variables}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload variable name and value
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void createRepoVariable(final String owner, final String repo, final GitHubCreateRepoVariablePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(REPO_VARIABLES, owner, repo), payload));
    }

    /**
     * Get a repository variable ({@code GET /repos/{owner}/{repo}/actions/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name variable name
     * @return repository variable, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubActionsVariable> getRepoVariable(final String owner, final String repo, final String name)
            throws IOException {
        return caller.fetchOrEmpty(get(REPO_VARIABLE, owner, repo, name), GitHubActionsVariable.class);
    }

    /**
     * Update a repository variable ({@code PATCH /repos/{owner}/{repo}/actions/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name variable name
     * @param payload changed name or value
     * @throws IllegalArgumentException if any argument is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updateRepoVariable(
            final String owner, final String repo, final String name, final GitHubUpdateRepoVariablePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(REPO_VARIABLE, owner, repo, name), payload));
    }

    /**
     * Delete a repository variable ({@code DELETE /repos/{owner}/{repo}/actions/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name variable name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRepoVariable(final String owner, final String repo, final String name) throws IOException {
        caller.send(delete(REPO_VARIABLE, owner, repo, name));
    }

    /**
     * List environment variables ({@code GET /repos/{owner}/{repo}/environments/{environment_name}/variables}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @return first HTTP page of environment variables
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsVariable> listEnvironmentVariables(
            final String owner, final String repo, final String environmentName) throws IOException {
        return listEnvironmentVariables(owner, repo, environmentName, GitHubPageRequest.byPage());
    }

    /**
     * List environment variables ({@code GET /repos/{owner}/{repo}/environments/{environment_name}/variables}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param request pagination token
     * @return first HTTP page of environment variables
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsVariable> listEnvironmentVariables(
            final String owner, final String repo, final String environmentName, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ENVIRONMENT_VARIABLES, Map.of(), request, REPO_VARIABLES_SPEC, owner, repo, environmentName);
    }

    /**
     * Create an environment variable ({@code POST /repos/{owner}/{repo}/environments/{environment_name}/variables}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param payload variable name and value
     * @throws IllegalArgumentException if any argument is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void createEnvironmentVariable(
            final String owner,
            final String repo,
            final String environmentName,
            final GitHubCreateEnvironmentVariablePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ENVIRONMENT_VARIABLES, owner, repo, environmentName), payload));
    }

    /**
     * Get an environment variable ({@code GET /repos/{owner}/{repo}/environments/{environment_name}/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param name variable name
     * @return environment variable, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubActionsVariable> getEnvironmentVariable(
            final String owner, final String repo, final String environmentName, final String name) throws IOException {
        return caller.fetchOrEmpty(
                get(ENVIRONMENT_VARIABLE, owner, repo, environmentName, name), GitHubActionsVariable.class);
    }

    /**
     * Update an environment variable ({@code PATCH
     * /repos/{owner}/{repo}/environments/{environment_name}/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param name variable name
     * @param payload changed name or value
     * @throws IllegalArgumentException if any argument is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updateEnvironmentVariable(
            final String owner,
            final String repo,
            final String environmentName,
            final String name,
            final GitHubUpdateEnvironmentVariablePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(ENVIRONMENT_VARIABLE, owner, repo, environmentName, name), payload));
    }

    /**
     * Delete an environment variable ({@code DELETE
     * /repos/{owner}/{repo}/environments/{environment_name}/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param name variable name
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteEnvironmentVariable(
            final String owner, final String repo, final String environmentName, final String name) throws IOException {
        caller.send(delete(ENVIRONMENT_VARIABLE, owner, repo, environmentName, name));
    }
}
