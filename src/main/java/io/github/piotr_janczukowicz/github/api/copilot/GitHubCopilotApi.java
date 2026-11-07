package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Copilot REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubCopilotApi {

    private final LazyValue<GitHubCopilotCloudAgentManagementApi> cloudAgentManagement;
    private final LazyValue<GitHubCopilotCodeReviewApi> codeReview;
    private final LazyValue<GitHubCopilotCodingAgentManagementApi> codingAgentManagement;
    private final LazyValue<GitHubCopilotContentExclusionManagementApi> contentExclusionManagement;
    private final LazyValue<GitHubCopilotCustomAgentsApi> customAgents;
    private final LazyValue<GitHubCopilotFeaturePolicyManagementApi> featurePolicyManagement;
    private final LazyValue<GitHubCopilotModelPolicyManagementApi> modelPolicyManagement;
    private final LazyValue<GitHubCopilotTeamCustomizationsApi> teamCustomizations;
    private final LazyValue<GitHubCopilotUsageMetricsApi> usageMetrics;
    private final LazyValue<GitHubCopilotUserManagementApi> userManagement;

    /**
     * Copilot resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.cloudAgentManagement = new LazyValue<>(() -> new GitHubCopilotCloudAgentManagementApi(caller));
        this.codeReview = new LazyValue<>(() -> new GitHubCopilotCodeReviewApi(caller));
        this.codingAgentManagement = new LazyValue<>(() -> new GitHubCopilotCodingAgentManagementApi(caller));
        this.contentExclusionManagement = new LazyValue<>(() -> new GitHubCopilotContentExclusionManagementApi(caller));
        this.customAgents = new LazyValue<>(() -> new GitHubCopilotCustomAgentsApi(caller));
        this.featurePolicyManagement = new LazyValue<>(() -> new GitHubCopilotFeaturePolicyManagementApi(caller));
        this.modelPolicyManagement = new LazyValue<>(() -> new GitHubCopilotModelPolicyManagementApi(caller));
        this.teamCustomizations = new LazyValue<>(() -> new GitHubCopilotTeamCustomizationsApi(caller));
        this.usageMetrics = new LazyValue<>(() -> new GitHubCopilotUsageMetricsApi(caller));
        this.userManagement = new LazyValue<>(() -> new GitHubCopilotUserManagementApi(caller));
    }

    /**
     * Copilot cloud agent management operations ({@code subcategory=copilot-cloud-agent-management}).
     *
     * @return cloud agent management API
     */
    public GitHubCopilotCloudAgentManagementApi cloudAgentManagement() {
        return cloudAgentManagement.get();
    }

    /**
     * Copilot code review operations ({@code subcategory=copilot-code-review}).
     *
     * <p>The {@code CLOUD} and {@code GHEC} flavors carry this resource.
     *
     * @return code review API
     */
    public GitHubCopilotCodeReviewApi codeReview() {
        return codeReview.get();
    }

    /**
     * Copilot coding agent management operations ({@code subcategory=copilot-coding-agent-management}).
     *
     * @return coding agent management API
     */
    public GitHubCopilotCodingAgentManagementApi codingAgentManagement() {
        return codingAgentManagement.get();
    }

    /**
     * Copilot content exclusion management operations ({@code subcategory=copilot-content-exclusion-management}).
     *
     * @return content exclusion management API
     */
    public GitHubCopilotContentExclusionManagementApi contentExclusionManagement() {
        return contentExclusionManagement.get();
    }

    /**
     * Copilot custom agents operations ({@code subcategory=copilot-custom-agents}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return custom agents API
     */
    public GitHubCopilotCustomAgentsApi customAgents() {
        return customAgents.get();
    }

    /**
     * Copilot feature policy management operations ({@code subcategory=copilot-feature-policy-management}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return feature policy management API
     */
    public GitHubCopilotFeaturePolicyManagementApi featurePolicyManagement() {
        return featurePolicyManagement.get();
    }

    /**
     * Copilot model policy management operations ({@code subcategory=copilot-model-policy-management}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return model policy management API
     */
    public GitHubCopilotModelPolicyManagementApi modelPolicyManagement() {
        return modelPolicyManagement.get();
    }

    /**
     * Copilot team customizations operations ({@code subcategory=copilot-team-customizations}).
     *
     * <p>The {@code CLOUD} and {@code GHEC} flavors carry this resource.
     *
     * @return team customizations API
     */
    public GitHubCopilotTeamCustomizationsApi teamCustomizations() {
        return teamCustomizations.get();
    }

    /**
     * Copilot usage metrics operations ({@code subcategory=copilot-usage-metrics}).
     *
     * @return usage metrics API
     */
    public GitHubCopilotUsageMetricsApi usageMetrics() {
        return usageMetrics.get();
    }

    /**
     * Copilot user management operations ({@code subcategory=copilot-user-management}).
     *
     * @return user management API
     */
    public GitHubCopilotUserManagementApi userManagement() {
        return userManagement.get();
    }
}
