package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
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

/** GitHub Actions policies REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsPoliciesApi extends GitHubResourceApi {

    private static final PathTemplate ORG_POLICIES = PathTemplate.of("/orgs/{org}/actions/policies");
    private static final PathTemplate ORG_POLICY = PathTemplate.of("/orgs/{org}/actions/policies/{policy_id}");
    private static final PathTemplate REPO_POLICIES = PathTemplate.of("/repos/{owner}/{repo}/actions/policies");
    private static final PathTemplate REPO_POLICY =
            PathTemplate.of("/repos/{owner}/{repo}/actions/policies/{policy_id}");

    private static final PathTemplate ENTERPRISE_POLICIES =
            PathTemplate.of("/enterprises/{enterprise}/actions/policies");
    private static final PathTemplate ENTERPRISE_POLICY =
            PathTemplate.of("/enterprises/{enterprise}/actions/policies/{policy_id}");
    private static final GitHubListSpec<GitHubActionsPolicy> ENTERPRISE_POLICIES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubActionsPoliciesForEnterprise.class,
            GitHubActionsPoliciesForEnterprise::policies,
            GitHubActionsPoliciesForEnterprise::totalCount);
    private static final GitHubListSpec<GitHubActionsPolicy> POLICIES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubActionsPolicies.class,
            GitHubActionsPolicies::policies,
            GitHubActionsPolicies::totalCount);

    /**
     * Policies resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsPoliciesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization Actions policies ({@code GET /orgs/{org}/actions/policies}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param org organization name
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsPolicy> getOrgActionsPolicies(final String org) throws IOException {
        return getOrgActionsPolicies(org, GitHubPageRequest.byPage());
    }

    /**
     * List organization Actions policies ({@code GET /orgs/{org}/actions/policies}).
     *
     * <p>Uses an empty filter.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsPolicy> getOrgActionsPolicies(final String org, final GitHubPageRequest request)
            throws IOException {
        return getOrgActionsPolicies(org, GitHubGetOrgActionsPoliciesFilter.empty(), request);
    }

    /**
     * List organization Actions policies ({@code GET /orgs/{org}/actions/policies}).
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsPolicy> getOrgActionsPolicies(
            final String org, final GitHubGetOrgActionsPoliciesFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_POLICIES, filter.toQueryParameters(), request, POLICIES_SPEC, org);
    }

    /**
     * Create an organization Actions policy ({@code POST /orgs/{org}/actions/policies}).
     *
     * @param org organization name
     * @param payload policy name, enforcement, conditions and rules
     * @return created policy
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsPolicy createOrgActionsPolicy(
            final String org, final GitHubCreateOrgActionsPolicyPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_POLICIES, org), payload), GitHubActionsPolicy.class);
    }

    /**
     * Get an organization Actions policy ({@code GET /orgs/{org}/actions/policies/{policy_id}}).
     *
     * @param org organization name
     * @param policyId policy ID
     * @return policy, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubActionsPolicy> getOrgActionsPolicy(final String org, final long policyId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_POLICY, org, policyId), GitHubActionsPolicy.class);
    }

    /**
     * Update an organization Actions policy ({@code PUT /orgs/{org}/actions/policies/{policy_id}}).
     *
     * @param org organization name
     * @param policyId policy ID
     * @param payload changed policy name, enforcement, conditions or rules
     * @return updated policy
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsPolicy updateOrgActionsPolicy(
            final String org, final long policyId, final GitHubUpdateOrgActionsPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(ORG_POLICY, org, policyId), payload), GitHubActionsPolicy.class);
    }

    /**
     * Delete an organization Actions policy ({@code DELETE /orgs/{org}/actions/policies/{policy_id}}).
     *
     * @param org organization name
     * @param policyId policy ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteOrgActionsPolicy(final String org, final long policyId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_POLICY, org, policyId));
    }

    /**
     * List repository Actions policies ({@code GET /repos/{owner}/{repo}/actions/policies}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsPolicy> getRepoActionsPolicies(final String owner, final String repo)
            throws IOException {
        return getRepoActionsPolicies(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository Actions policies ({@code GET /repos/{owner}/{repo}/actions/policies}).
     *
     * <p>Uses an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsPolicy> getRepoActionsPolicies(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return getRepoActionsPolicies(owner, repo, GitHubGetRepoActionsPoliciesFilter.empty(), request);
    }

    /**
     * List repository Actions policies ({@code GET /repos/{owner}/{repo}/actions/policies}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubActionsPolicy> getRepoActionsPolicies(
            final String owner,
            final String repo,
            final GitHubGetRepoActionsPoliciesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_POLICIES, filter.toQueryParameters(), request, POLICIES_SPEC, owner, repo);
    }

    /**
     * Create a repository Actions policy ({@code POST /repos/{owner}/{repo}/actions/policies}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload policy name, enforcement, conditions and rules
     * @return created policy
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsPolicy createRepoActionsPolicy(
            final String owner, final String repo, final GitHubCreateRepoActionsPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(REPO_POLICIES, owner, repo), payload), GitHubActionsPolicy.class);
    }

    /**
     * Get a repository Actions policy ({@code GET /repos/{owner}/{repo}/actions/policies/{policy_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param policyId policy ID
     * @return policy, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubActionsPolicy> getRepoActionsPolicy(
            final String owner, final String repo, final long policyId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(REPO_POLICY, owner, repo, policyId), GitHubActionsPolicy.class);
    }

    /**
     * Update a repository Actions policy ({@code PUT /repos/{owner}/{repo}/actions/policies/{policy_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param policyId policy ID
     * @param payload changed policy name, enforcement, conditions or rules
     * @return updated policy
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsPolicy updateRepoActionsPolicy(
            final String owner,
            final String repo,
            final long policyId,
            final GitHubUpdateRepoActionsPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(REPO_POLICY, owner, repo, policyId), payload), GitHubActionsPolicy.class);
    }

    /**
     * Delete a repository Actions policy ({@code DELETE /repos/{owner}/{repo}/actions/policies/{policy_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param policyId policy ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteRepoActionsPolicy(final String owner, final String repo, final long policyId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(REPO_POLICY, owner, repo, policyId));
    }

    /**
     * List enterprise Actions policies ({@code GET /enterprises/{enterprise}/actions/policies}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubActionsPolicy> getEnterpriseActionsPolicies(final String enterprise) throws IOException {
        return getEnterpriseActionsPolicies(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List enterprise Actions policies ({@code GET /enterprises/{enterprise}/actions/policies}).
     *
     * @param enterprise enterprise slug
     * @param request pagination token
     * @return first HTTP page of policies
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubActionsPolicy> getEnterpriseActionsPolicies(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_POLICIES, Map.of(), request, ENTERPRISE_POLICIES_SPEC, enterprise);
    }

    /**
     * Create an enterprise Actions policy ({@code POST /enterprises/{enterprise}/actions/policies}).
     *
     * @param enterprise enterprise slug
     * @param payload policy name, enforcement, conditions and rules
     * @return created policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsPolicy createEnterpriseActionsPolicy(
            final String enterprise, final GitHubCreateEnterpriseActionsPolicyPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ENTERPRISE_POLICIES, enterprise), payload), GitHubActionsPolicy.class);
    }

    /**
     * Get an enterprise Actions policy ({@code GET /enterprises/{enterprise}/actions/policies/{policy_id}}).
     *
     * @param enterprise enterprise slug
     * @param policyId policy ID
     * @return policy, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubActionsPolicy> getEnterpriseActionsPolicy(final String enterprise, final long policyId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ENTERPRISE_POLICY, enterprise, policyId), GitHubActionsPolicy.class);
    }

    /**
     * Update an enterprise Actions policy ({@code PUT /enterprises/{enterprise}/actions/policies/{policy_id}}).
     *
     * @param enterprise enterprise slug
     * @param policyId policy ID
     * @param payload policy fields to update
     * @return updated policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubActionsPolicy updateEnterpriseActionsPolicy(
            final String enterprise, final long policyId, final GitHubUpdateEnterpriseActionsPolicyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ENTERPRISE_POLICY, enterprise, policyId), payload), GitHubActionsPolicy.class);
    }

    /**
     * Delete an enterprise Actions policy ({@code DELETE /enterprises/{enterprise}/actions/policies/{policy_id}}).
     *
     * @param enterprise enterprise slug
     * @param policyId policy ID
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void deleteEnterpriseActionsPolicy(final String enterprise, final long policyId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(ENTERPRISE_POLICY, enterprise, policyId));
    }
}
