package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Deployments REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubDeploymentsApi {

    private final LazyValue<GitHubDeploymentApi> deployment;
    private final LazyValue<GitHubDeploymentsStatusesApi> statuses;
    private final LazyValue<GitHubDeploymentsEnvironmentsApi> environments;
    private final LazyValue<GitHubDeploymentsBranchPoliciesApi> branchPolicies;
    private final LazyValue<GitHubDeploymentsProtectionRulesApi> protectionRules;

    /**
     * Deployments resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDeploymentsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.deployment = new LazyValue<>(() -> new GitHubDeploymentApi(caller));
        this.statuses = new LazyValue<>(() -> new GitHubDeploymentsStatusesApi(caller));
        this.environments = new LazyValue<>(() -> new GitHubDeploymentsEnvironmentsApi(caller));
        this.branchPolicies = new LazyValue<>(() -> new GitHubDeploymentsBranchPoliciesApi(caller));
        this.protectionRules = new LazyValue<>(() -> new GitHubDeploymentsProtectionRulesApi(caller));
    }

    /**
     * Deployment operations ({@code subcategory=deployments}).
     *
     * @return deployment API
     */
    public GitHubDeploymentApi deployment() {
        return deployment.get();
    }

    /**
     * Deployment status operations ({@code subcategory=statuses}).
     *
     * @return statuses API
     */
    public GitHubDeploymentsStatusesApi statuses() {
        return statuses.get();
    }

    /**
     * Environment operations ({@code subcategory=environments}).
     *
     * @return environments API
     */
    public GitHubDeploymentsEnvironmentsApi environments() {
        return environments.get();
    }

    /**
     * Deployment branch policy operations ({@code subcategory=branch-policies}).
     *
     * @return branch policies API
     */
    public GitHubDeploymentsBranchPoliciesApi branchPolicies() {
        return branchPolicies.get();
    }

    /**
     * Deployment protection rule operations ({@code subcategory=protection-rules}).
     *
     * @return protection rules API
     */
    public GitHubDeploymentsProtectionRulesApi protectionRules() {
        return protectionRules.get();
    }
}
