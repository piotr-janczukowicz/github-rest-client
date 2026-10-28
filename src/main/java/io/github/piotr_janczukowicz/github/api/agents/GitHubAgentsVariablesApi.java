package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsVariable;
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

/** GitHub Agents variables REST resource for API version {@code 2026-03-10}. */
public final class GitHubAgentsVariablesApi extends GitHubResourceApi {

    /** Largest {@code per_page} the agent variable list endpoints accept. */
    private static final int VARIABLES_MAX_PER_PAGE = 30;

    private static final PathTemplate ORG_AGENTS_VARIABLES = PathTemplate.of("/orgs/{org}/agents/variables");
    private static final PathTemplate ORG_AGENTS_VARIABLE = PathTemplate.of("/orgs/{org}/agents/variables/{name}");
    private static final PathTemplate ORG_AGENTS_VARIABLE_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/agents/variables/{name}/repositories");
    private static final PathTemplate ORG_AGENTS_VARIABLE_REPOSITORY =
            PathTemplate.of("/orgs/{org}/agents/variables/{name}/repositories/{repository_id}");
    private static final PathTemplate REPO_AGENTS_ORGANIZATION_VARIABLES =
            PathTemplate.of("/repos/{owner}/{repo}/agents/organization-variables");
    private static final PathTemplate REPO_AGENTS_VARIABLES = PathTemplate.of("/repos/{owner}/{repo}/agents/variables");
    private static final PathTemplate REPO_AGENTS_VARIABLE =
            PathTemplate.of("/repos/{owner}/{repo}/agents/variables/{name}");

    /**
     * Agents variables resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAgentsVariablesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization variables ({@code GET /orgs/{org}/agents/variables}).
     *
     * @param org organization name
     * @return first HTTP page of organization variables
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationActionsVariable> listOrgVariables(final String org) throws IOException {
        return listOrgVariables(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization variables ({@code GET /orgs/{org}/agents/variables}). {@code per_page} is capped at 30.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of organization variables
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationActionsVariable> listOrgVariables(
            final String org, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_AGENTS_VARIABLES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrgActionsVariablesPage.class,
                        GitHubOrgActionsVariablesPage::variables,
                        GitHubOrgActionsVariablesPage::totalCount,
                        VARIABLES_MAX_PER_PAGE),
                org);
    }

    /**
     * Create an organization variable ({@code POST /orgs/{org}/agents/variables}).
     *
     * @param org organization name
     * @param payload variable name, value and visibility
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void createOrgVariable(final String org, final GitHubCreateOrgVariablePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ORG_AGENTS_VARIABLES, org), payload));
    }

    /**
     * Get an organization variable ({@code GET /orgs/{org}/agents/variables/{name}}).
     *
     * @param org organization name
     * @param name variable name
     * @return organization variable, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubOrganizationActionsVariable> getOrgVariable(final String org, final String name)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_AGENTS_VARIABLE, org, name), GitHubOrganizationActionsVariable.class);
    }

    /**
     * Update an organization variable ({@code PATCH /orgs/{org}/agents/variables/{name}}).
     *
     * @param org organization name
     * @param name variable name
     * @param payload new value, name and visibility
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void updateOrgVariable(final String org, final String name, final GitHubUpdateOrgVariablePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(ORG_AGENTS_VARIABLE, org, name), payload));
    }

    /**
     * Delete an organization variable ({@code DELETE /orgs/{org}/agents/variables/{name}}).
     *
     * @param org organization name
     * @param name variable name
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteOrgVariable(final String org, final String name) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_AGENTS_VARIABLE, org, name));
    }

    /**
     * List selected repositories for an organization variable ({@code GET
     * /orgs/{org}/agents/variables/{name}/repositories}).
     *
     * @param org organization name
     * @param name variable name
     * @return first HTTP page of repositories that can access the variable
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgVariable(final String org, final String name)
            throws IOException {
        return listSelectedReposForOrgVariable(org, name, GitHubPageRequest.byPage());
    }

    /**
     * List selected repositories for an organization variable ({@code GET
     * /orgs/{org}/agents/variables/{name}/repositories}).
     *
     * @param org organization name
     * @param name variable name
     * @param request pagination token
     * @return first HTTP page of repositories that can access the variable
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMinimalRepository> listSelectedReposForOrgVariable(
            final String org, final String name, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ORG_AGENTS_VARIABLE_REPOSITORIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrgVariableSelectedRepositoriesPage.class,
                        GitHubOrgVariableSelectedRepositoriesPage::repositories,
                        GitHubOrgVariableSelectedRepositoriesPage::totalCount),
                org,
                name);
    }

    /**
     * Set selected repositories for an organization variable ({@code PUT
     * /orgs/{org}/agents/variables/{name}/repositories}).
     *
     * @param org organization name
     * @param name variable name
     * @param payload repository IDs that can access the variable
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setSelectedReposForOrgVariable(
            final String org, final String name, final GitHubSetSelectedReposForOrgVariablePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_AGENTS_VARIABLE_REPOSITORIES, org, name), payload));
    }

    /**
     * Add selected repository to an organization variable ({@code PUT
     * /orgs/{org}/agents/variables/{name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param name variable name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void addSelectedRepoToOrgVariable(final String org, final String name, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(ORG_AGENTS_VARIABLE_REPOSITORY, org, name, repositoryId));
    }

    /**
     * Remove selected repository from an organization variable ({@code DELETE
     * /orgs/{org}/agents/variables/{name}/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param name variable name
     * @param repositoryId repository ID
     * @throws IllegalArgumentException if {@code org} or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 409: "Conflict when visibility type is not set
     *     to selected", no response body)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void removeSelectedRepoFromOrgVariable(final String org, final String name, final long repositoryId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_AGENTS_VARIABLE_REPOSITORY, org, name, repositoryId));
    }

    /**
     * List repository organization variables ({@code GET /repos/{owner}/{repo}/agents/organization-variables}).
     * {@code per_page} is capped at 30.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of organization variables visible to the repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsVariable> listRepoOrganizationVariables(final String owner, final String repo)
            throws IOException {
        return listRepoOrganizationVariables(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository organization variables ({@code GET /repos/{owner}/{repo}/agents/organization-variables}).
     * {@code per_page} is capped at 30.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of organization variables visible to the repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsVariable> listRepoOrganizationVariables(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_AGENTS_ORGANIZATION_VARIABLES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubRepoOrganizationVariablesPage.class,
                        GitHubRepoOrganizationVariablesPage::variables,
                        GitHubRepoOrganizationVariablesPage::totalCount,
                        VARIABLES_MAX_PER_PAGE),
                owner,
                repo);
    }

    /**
     * List repository variables ({@code GET /repos/{owner}/{repo}/agents/variables}). {@code per_page} is capped at 30.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of repository variables
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsVariable> listRepoVariables(final String owner, final String repo)
            throws IOException {
        return listRepoVariables(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository variables ({@code GET /repos/{owner}/{repo}/agents/variables}). {@code per_page} is capped at 30.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of repository variables
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsVariable> listRepoVariables(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPO_AGENTS_VARIABLES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubActionsVariablesPage.class,
                        GitHubActionsVariablesPage::variables,
                        GitHubActionsVariablesPage::totalCount,
                        VARIABLES_MAX_PER_PAGE),
                owner,
                repo);
    }

    /**
     * Create a repository variable ({@code POST /repos/{owner}/{repo}/agents/variables}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload variable name and value
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void createRepoVariable(final String owner, final String repo, final GitHubCreateRepoVariablePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(REPO_AGENTS_VARIABLES, owner, repo), payload));
    }

    /**
     * Get a repository variable ({@code GET /repos/{owner}/{repo}/agents/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name variable name
     * @return repository variable, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubActionsVariable> getRepoVariable(final String owner, final String repo, final String name)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(REPO_AGENTS_VARIABLE, owner, repo, name), GitHubActionsVariable.class);
    }

    /**
     * Update a repository variable ({@code PATCH /repos/{owner}/{repo}/agents/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name variable name
     * @param payload new value and name
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code name} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void updateRepoVariable(
            final String owner, final String repo, final String name, final GitHubUpdateRepoVariablePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(REPO_AGENTS_VARIABLE, owner, repo, name), payload));
    }

    /**
     * Delete a repository variable ({@code DELETE /repos/{owner}/{repo}/agents/variables/{name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param name variable name
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code name} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteRepoVariable(final String owner, final String repo, final String name) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(REPO_AGENTS_VARIABLE, owner, repo, name));
    }
}
