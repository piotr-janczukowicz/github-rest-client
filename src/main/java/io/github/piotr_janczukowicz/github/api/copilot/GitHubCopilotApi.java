package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Copilot REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubCopilotApi {

    private final LazyValue<GitHubCopilotCopilotCloudAgentManagementApi> copilotCloudAgentManagement;
    private final LazyValue<GitHubCopilotCopilotCodingAgentManagementApi> copilotCodingAgentManagement;
    private final LazyValue<GitHubCopilotCopilotContentExclusionManagementApi> copilotContentExclusionManagement;
    private final LazyValue<GitHubCopilotCopilotCustomAgentsApi> copilotCustomAgents;
    private final LazyValue<GitHubCopilotCopilotModelPolicyManagementApi> copilotModelPolicyManagement;
    private final LazyValue<GitHubCopilotCopilotUsageMetricsApi> copilotUsageMetrics;
    private final LazyValue<GitHubCopilotCopilotUserManagementApi> copilotUserManagement;

    /**
     * Copilot resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.copilotCloudAgentManagement =
                new LazyValue<>(() -> new GitHubCopilotCopilotCloudAgentManagementApi(caller));
        this.copilotCodingAgentManagement =
                new LazyValue<>(() -> new GitHubCopilotCopilotCodingAgentManagementApi(caller));
        this.copilotContentExclusionManagement =
                new LazyValue<>(() -> new GitHubCopilotCopilotContentExclusionManagementApi(caller));
        this.copilotCustomAgents = new LazyValue<>(() -> new GitHubCopilotCopilotCustomAgentsApi(caller));
        this.copilotModelPolicyManagement =
                new LazyValue<>(() -> new GitHubCopilotCopilotModelPolicyManagementApi(caller));
        this.copilotUsageMetrics = new LazyValue<>(() -> new GitHubCopilotCopilotUsageMetricsApi(caller));
        this.copilotUserManagement = new LazyValue<>(() -> new GitHubCopilotCopilotUserManagementApi(caller));
    }

    /**
     * Copilot cloud agent management operations ({@code subcategory=copilot-cloud-agent-management}).
     *
     * @return cloud agent management API
     */
    public GitHubCopilotCopilotCloudAgentManagementApi copilotCloudAgentManagement() {
        return copilotCloudAgentManagement.get();
    }

    /**
     * Copilot coding agent management operations ({@code subcategory=copilot-coding-agent-management}).
     *
     * @return coding agent management API
     */
    public GitHubCopilotCopilotCodingAgentManagementApi copilotCodingAgentManagement() {
        return copilotCodingAgentManagement.get();
    }

    /**
     * Copilot content exclusion management operations ({@code subcategory=copilot-content-exclusion-management}).
     *
     * @return content exclusion management API
     */
    public GitHubCopilotCopilotContentExclusionManagementApi copilotContentExclusionManagement() {
        return copilotContentExclusionManagement.get();
    }

    /**
     * Copilot custom agents operations ({@code subcategory=copilot-custom-agents}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return custom agents API
     */
    public GitHubCopilotCopilotCustomAgentsApi copilotCustomAgents() {
        return copilotCustomAgents.get();
    }

    /**
     * Copilot model policy management operations ({@code subcategory=copilot-model-policy-management}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return model policy management API
     */
    public GitHubCopilotCopilotModelPolicyManagementApi copilotModelPolicyManagement() {
        return copilotModelPolicyManagement.get();
    }

    /**
     * Copilot usage metrics operations ({@code subcategory=copilot-usage-metrics}).
     *
     * @return usage metrics API
     */
    public GitHubCopilotCopilotUsageMetricsApi copilotUsageMetrics() {
        return copilotUsageMetrics.get();
    }

    /**
     * Copilot user management operations ({@code subcategory=copilot-user-management}).
     *
     * @return user management API
     */
    public GitHubCopilotCopilotUserManagementApi copilotUserManagement() {
        return copilotUserManagement.get();
    }
}
