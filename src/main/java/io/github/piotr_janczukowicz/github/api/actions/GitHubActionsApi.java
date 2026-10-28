package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Actions REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubActionsApi {

    private final LazyValue<GitHubActionsArtifactsApi> artifacts;
    private final LazyValue<GitHubActionsCacheApi> cache;
    private final LazyValue<GitHubActionsConcurrencyGroupsApi> concurrencyGroups;
    private final LazyValue<GitHubActionsHostedRunnersApi> hostedRunners;
    private final LazyValue<GitHubActionsOidcApi> oidc;
    private final LazyValue<GitHubActionsPermissionsApi> permissions;
    private final LazyValue<GitHubActionsPoliciesApi> policies;
    private final LazyValue<GitHubActionsSecretsApi> secrets;
    private final LazyValue<GitHubActionsSelfHostedRunnerGroupsApi> selfHostedRunnerGroups;
    private final LazyValue<GitHubActionsSelfHostedRunnersApi> selfHostedRunners;
    private final LazyValue<GitHubActionsVariablesApi> variables;
    private final LazyValue<GitHubActionsWorkflowJobsApi> workflowJobs;
    private final LazyValue<GitHubActionsWorkflowRunsApi> workflowRuns;
    private final LazyValue<GitHubActionsWorkflowsApi> workflows;

    /**
     * Actions resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.artifacts = new LazyValue<>(() -> new GitHubActionsArtifactsApi(caller));
        this.cache = new LazyValue<>(() -> new GitHubActionsCacheApi(caller));
        this.concurrencyGroups = new LazyValue<>(() -> new GitHubActionsConcurrencyGroupsApi(caller));
        this.hostedRunners = new LazyValue<>(() -> new GitHubActionsHostedRunnersApi(caller));
        this.oidc = new LazyValue<>(() -> new GitHubActionsOidcApi(caller));
        this.permissions = new LazyValue<>(() -> new GitHubActionsPermissionsApi(caller));
        this.policies = new LazyValue<>(() -> new GitHubActionsPoliciesApi(caller));
        this.secrets = new LazyValue<>(() -> new GitHubActionsSecretsApi(caller));
        this.selfHostedRunnerGroups = new LazyValue<>(() -> new GitHubActionsSelfHostedRunnerGroupsApi(caller));
        this.selfHostedRunners = new LazyValue<>(() -> new GitHubActionsSelfHostedRunnersApi(caller));
        this.variables = new LazyValue<>(() -> new GitHubActionsVariablesApi(caller));
        this.workflowJobs = new LazyValue<>(() -> new GitHubActionsWorkflowJobsApi(caller));
        this.workflowRuns = new LazyValue<>(() -> new GitHubActionsWorkflowRunsApi(caller));
        this.workflows = new LazyValue<>(() -> new GitHubActionsWorkflowsApi(caller));
    }

    /**
     * Artifact operations ({@code subcategory=artifacts}).
     *
     * @return artifacts API
     */
    public GitHubActionsArtifactsApi artifacts() {
        return artifacts.get();
    }

    /**
     * Cache operations ({@code subcategory=cache}).
     *
     * @return cache API
     */
    public GitHubActionsCacheApi cache() {
        return cache.get();
    }

    /**
     * Concurrency group operations ({@code subcategory=concurrency-groups}).
     *
     * @return concurrency groups API
     */
    public GitHubActionsConcurrencyGroupsApi concurrencyGroups() {
        return concurrencyGroups.get();
    }

    /**
     * GitHub-hosted runner operations ({@code subcategory=hosted-runners}).
     *
     * @return hosted runners API
     */
    public GitHubActionsHostedRunnersApi hostedRunners() {
        return hostedRunners.get();
    }

    /**
     * OIDC operations ({@code subcategory=oidc}).
     *
     * @return OIDC API
     */
    public GitHubActionsOidcApi oidc() {
        return oidc.get();
    }

    /**
     * Permission operations ({@code subcategory=permissions}).
     *
     * @return permissions API
     */
    public GitHubActionsPermissionsApi permissions() {
        return permissions.get();
    }

    /**
     * Policy operations ({@code subcategory=policies}).
     *
     * @return policies API
     */
    public GitHubActionsPoliciesApi policies() {
        return policies.get();
    }

    /**
     * Secret operations ({@code subcategory=secrets}).
     *
     * @return secrets API
     */
    public GitHubActionsSecretsApi secrets() {
        return secrets.get();
    }

    /**
     * Self-hosted runner group operations ({@code subcategory=self-hosted-runner-groups}).
     *
     * @return self-hosted runner groups API
     */
    public GitHubActionsSelfHostedRunnerGroupsApi selfHostedRunnerGroups() {
        return selfHostedRunnerGroups.get();
    }

    /**
     * Self-hosted runner operations ({@code subcategory=self-hosted-runners}).
     *
     * @return self-hosted runners API
     */
    public GitHubActionsSelfHostedRunnersApi selfHostedRunners() {
        return selfHostedRunners.get();
    }

    /**
     * Variable operations ({@code subcategory=variables}).
     *
     * @return variables API
     */
    public GitHubActionsVariablesApi variables() {
        return variables.get();
    }

    /**
     * Workflow job operations ({@code subcategory=workflow-jobs}).
     *
     * @return workflow jobs API
     */
    public GitHubActionsWorkflowJobsApi workflowJobs() {
        return workflowJobs.get();
    }

    /**
     * Workflow run operations ({@code subcategory=workflow-runs}).
     *
     * @return workflow runs API
     */
    public GitHubActionsWorkflowRunsApi workflowRuns() {
        return workflowRuns.get();
    }

    /**
     * Workflow operations ({@code subcategory=workflows}).
     *
     * @return workflows API
     */
    public GitHubActionsWorkflowsApi workflows() {
        return workflows.get();
    }
}
