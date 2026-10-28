package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

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

/** GitHub deployment branch policies REST resource for API version {@code 2026-03-10}. */
public final class GitHubDeploymentsBranchPoliciesApi extends GitHubResourceApi {

    private static final PathTemplate ENVIRONMENT_BRANCH_POLICY = PathTemplate.of(
            "/repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies/{branch_policy_id}");
    private static final PathTemplate ENVIRONMENT_BRANCH_POLICIES =
            PathTemplate.of("/repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies");

    /**
     * Deployment branch policies resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDeploymentsBranchPoliciesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List deployment branch policies ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @return first HTTP page of deployment branch policies
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeploymentBranchPolicy> listDeploymentBranchPolicies(
            final String owner, final String repo, final String environmentName) throws IOException {
        return listDeploymentBranchPolicies(owner, repo, environmentName, GitHubPageRequest.byPage());
    }

    /**
     * List deployment branch policies ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param request pagination token
     * @return first HTTP page of deployment branch policies
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDeploymentBranchPolicy> listDeploymentBranchPolicies(
            final String owner, final String repo, final String environmentName, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENVIRONMENT_BRANCH_POLICIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubDeploymentBranchPoliciesPage.class,
                        GitHubDeploymentBranchPoliciesPage::branchPolicies,
                        GitHubDeploymentBranchPoliciesPage::totalCount),
                owner,
                repo,
                environmentName);
    }

    /**
     * Get a deployment branch policy ({@code GET
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies/{branch_policy_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param branchPolicyId branch policy id
     * @return deployment branch policy, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubDeploymentBranchPolicy> getDeploymentBranchPolicy(
            final String owner, final String repo, final String environmentName, final long branchPolicyId)
            throws IOException {

        return caller.fetchOrEmpty(
                get(ENVIRONMENT_BRANCH_POLICY, owner, repo, environmentName, branchPolicyId),
                GitHubDeploymentBranchPolicy.class);
    }

    /**
     * Create a deployment branch policy ({@code POST
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param payload branch policy to create
     * @return created deployment branch policy
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful; HTTP 303, which GitHub sends when the same branch
     *     name pattern already exists, is not followed (a POST would be repeated as a GET) and surfaces as this
     *     exception
     */
    public GitHubDeploymentBranchPolicy createDeploymentBranchPolicy(
            final String owner,
            final String repo,
            final String environmentName,
            final GitHubCreateDeploymentBranchPolicyPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENVIRONMENT_BRANCH_POLICIES, owner, repo, environmentName), payload),
                GitHubDeploymentBranchPolicy.class);
    }

    /**
     * Update a deployment branch policy ({@code PUT
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies/{branch_policy_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param branchPolicyId branch policy id
     * @param payload branch policy update
     * @return updated deployment branch policy
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDeploymentBranchPolicy updateDeploymentBranchPolicy(
            final String owner,
            final String repo,
            final String environmentName,
            final long branchPolicyId,
            final GitHubUpdateDeploymentBranchPolicyPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ENVIRONMENT_BRANCH_POLICY, owner, repo, environmentName, branchPolicyId), payload),
                GitHubDeploymentBranchPolicy.class);
    }

    /**
     * Delete a deployment branch policy ({@code DELETE
     * /repos/{owner}/{repo}/environments/{environment_name}/deployment-branch-policies/{branch_policy_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param environmentName environment name
     * @param branchPolicyId branch policy id
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteDeploymentBranchPolicy(
            final String owner, final String repo, final String environmentName, final long branchPolicyId)
            throws IOException {

        caller.send(delete(ENVIRONMENT_BRANCH_POLICY, owner, repo, environmentName, branchPolicyId));
    }
}
