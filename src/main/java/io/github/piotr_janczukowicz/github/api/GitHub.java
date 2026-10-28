package io.github.piotr_janczukowicz.github.api;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.actions.GitHubActionsApi;
import io.github.piotr_janczukowicz.github.api.activity.GitHubActivityApi;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubAgentAutomationApi;
import io.github.piotr_janczukowicz.github.api.agents.GitHubAgentsApi;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubAgentTaskApi;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncementBannersApi;
import io.github.piotr_janczukowicz.github.api.apps.GitHubAppsApi;
import io.github.piotr_janczukowicz.github.api.billing.GitHubBillingApi;
import io.github.piotr_janczukowicz.github.api.branches.GitHubBranchesApi;
import io.github.piotr_janczukowicz.github.api.campaigns.GitHubCampaignsApi;
import io.github.piotr_janczukowicz.github.api.checks.GitHubChecksApi;
import io.github.piotr_janczukowicz.github.api.classroom.GitHubClassroomApi;
import io.github.piotr_janczukowicz.github.api.codequality.GitHubCodeQualityApi;
import io.github.piotr_janczukowicz.github.api.codescanning.GitHubCodeScanningApi;
import io.github.piotr_janczukowicz.github.api.codesecurity.GitHubCodeSecurityApi;
import io.github.piotr_janczukowicz.github.api.codesofconduct.GitHubCodeOfConductApi;
import io.github.piotr_janczukowicz.github.api.codespaces.GitHubCodespacesApi;
import io.github.piotr_janczukowicz.github.api.collaborators.GitHubCollaboratorsApi;
import io.github.piotr_janczukowicz.github.api.commits.GitHubCommitsApi;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotApi;
import io.github.piotr_janczukowicz.github.api.copilotspaces.GitHubCopilotSpacesApi;
import io.github.piotr_janczukowicz.github.api.credentials.GitHubCredentialsApi;
import io.github.piotr_janczukowicz.github.api.dependabot.GitHubDependabotApi;
import io.github.piotr_janczukowicz.github.api.dependencygraph.GitHubDependencyGraphApi;
import io.github.piotr_janczukowicz.github.api.deploykeys.GitHubDeployKeysApi;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubDeploymentsApi;
import io.github.piotr_janczukowicz.github.api.emojis.GitHubEmojisApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminApi;
import io.github.piotr_janczukowicz.github.api.enterpriseteams.GitHubEnterpriseTeamsApi;
import io.github.piotr_janczukowicz.github.api.gists.GitHubGistsApi;
import io.github.piotr_janczukowicz.github.api.gitdatabase.GitHubGitDatabaseApi;
import io.github.piotr_janczukowicz.github.api.gitignore.GitHubGitignoreApi;
import io.github.piotr_janczukowicz.github.api.interactions.GitHubInteractionsApi;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssuesApi;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicensesApi;
import io.github.piotr_janczukowicz.github.api.markdown.GitHubMarkdownApi;
import io.github.piotr_janczukowicz.github.api.meta.GitHubMetaApi;
import io.github.piotr_janczukowicz.github.api.metrics.GitHubMetricsApi;
import io.github.piotr_janczukowicz.github.api.migrations.GitHubMigrationsApi;
import io.github.piotr_janczukowicz.github.api.oauthauthorizations.GitHubOauthAuthorizationsApi;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationsApi;
import io.github.piotr_janczukowicz.github.api.packages.GitHubPackagesApi;
import io.github.piotr_janczukowicz.github.api.pages.GitHubPagesApi;
import io.github.piotr_janczukowicz.github.api.privateregistries.GitHubPrivateRegistriesApi;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectsApi;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestsApi;
import io.github.piotr_janczukowicz.github.api.ratelimit.GitHubRateLimitApi;
import io.github.piotr_janczukowicz.github.api.reactions.GitHubReactionsApi;
import io.github.piotr_janczukowicz.github.api.releases.GitHubReleasesApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesApi;
import io.github.piotr_janczukowicz.github.api.scim.GitHubScimApi;
import io.github.piotr_janczukowicz.github.api.search.GitHubSearchApi;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningApi;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubSecurityAdvisoriesApi;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamsApi;
import io.github.piotr_janczukowicz.github.api.users.GitHubUsersApi;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub REST resource aggregator pinned to
 * {@link io.github.piotr_janczukowicz.github.client.GitHubClient#API_VERSION}.
 */
public final class GitHub {

    private final LazyValue<GitHubUsersApi> users;
    private final LazyValue<GitHubTeamsApi> teams;
    private final LazyValue<GitHubSecurityAdvisoriesApi> securityAdvisories;
    private final LazyValue<GitHubSecretScanningApi> secretScanning;
    private final LazyValue<GitHubRateLimitApi> rateLimit;
    private final LazyValue<GitHubReactionsApi> reactions;
    private final LazyValue<GitHubSearchApi> search;
    private final LazyValue<GitHubRepositoriesApi> repositories;
    private final LazyValue<GitHubActionsApi> actions;
    private final LazyValue<GitHubActivityApi> activity;
    private final LazyValue<GitHubAgentsApi> agents;
    private final LazyValue<GitHubAgentTaskApi> agentTasks;
    private final LazyValue<GitHubAgentAutomationApi> agentAutomations;
    private final LazyValue<GitHubAppsApi> apps;
    private final LazyValue<GitHubAnnouncementBannersApi> announcementBanners;
    private final LazyValue<GitHubBranchesApi> branches;
    private final LazyValue<GitHubBillingApi> billing;
    private final LazyValue<GitHubCampaignsApi> campaigns;
    private final LazyValue<GitHubChecksApi> checks;
    private final LazyValue<GitHubCommitsApi> commits;
    private final LazyValue<GitHubClassroomApi> classroom;
    private final LazyValue<GitHubOauthAuthorizationsApi> oauthAuthorizations;
    private final LazyValue<GitHubCodeQualityApi> codeQuality;
    private final LazyValue<GitHubCodeScanningApi> codeScanning;
    private final LazyValue<GitHubCodeSecurityApi> codeSecurity;
    private final LazyValue<GitHubCodeOfConductApi> codeOfConduct;
    private final LazyValue<GitHubCodespacesApi> codespaces;
    private final LazyValue<GitHubCollaboratorsApi> collaborators;
    private final LazyValue<GitHubCopilotApi> copilot;
    private final LazyValue<GitHubCopilotSpacesApi> copilotSpaces;
    private final LazyValue<GitHubCredentialsApi> credentials;
    private final LazyValue<GitHubDependabotApi> dependabot;
    private final LazyValue<GitHubDependencyGraphApi> dependencyGraph;
    private final LazyValue<GitHubDeployKeysApi> deployKeys;
    private final LazyValue<GitHubDeploymentsApi> deployments;
    private final LazyValue<GitHubEmojisApi> emojis;
    private final LazyValue<GitHubEnterpriseAdminApi> enterpriseAdmin;
    private final LazyValue<GitHubEnterpriseTeamsApi> enterpriseTeams;
    private final LazyValue<GitHubGistsApi> gists;
    private final LazyValue<GitHubGitDatabaseApi> gitDatabase;
    private final LazyValue<GitHubGitignoreApi> gitignore;
    private final LazyValue<GitHubIssuesApi> issues;
    private final LazyValue<GitHubInteractionsApi> interactions;
    private final LazyValue<GitHubLicensesApi> licenses;
    private final LazyValue<GitHubMarkdownApi> markdown;
    private final LazyValue<GitHubMetaApi> meta;
    private final LazyValue<GitHubMetricsApi> metrics;
    private final LazyValue<GitHubMigrationsApi> migrations;
    private final LazyValue<GitHubOrganizationsApi> organizations;
    private final LazyValue<GitHubPackagesApi> packages;
    private final LazyValue<GitHubPagesApi> pages;
    private final LazyValue<GitHubPrivateRegistriesApi> privateRegistries;
    private final LazyValue<GitHubProjectsApi> projects;
    private final LazyValue<GitHubPullRequestsApi> pullRequests;
    private final LazyValue<GitHubReleasesApi> releases;
    private final LazyValue<GitHubScimApi> scim;

    /**
     * Aggregator over {@code caller}. Create one {@link GitHubCaller} for a client and share it with this aggregator
     * and with any {@code GitHub*Api} you build yourself.
     *
     * @param caller executes and decodes the calls of every resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHub(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.users = new LazyValue<>(() -> new GitHubUsersApi(caller));
        this.teams = new LazyValue<>(() -> new GitHubTeamsApi(caller));
        this.securityAdvisories = new LazyValue<>(() -> new GitHubSecurityAdvisoriesApi(caller));
        this.secretScanning = new LazyValue<>(() -> new GitHubSecretScanningApi(caller));
        this.rateLimit = new LazyValue<>(() -> new GitHubRateLimitApi(caller));
        this.reactions = new LazyValue<>(() -> new GitHubReactionsApi(caller));
        this.search = new LazyValue<>(() -> new GitHubSearchApi(caller));
        this.repositories = new LazyValue<>(() -> new GitHubRepositoriesApi(caller));
        this.actions = new LazyValue<>(() -> new GitHubActionsApi(caller));
        this.activity = new LazyValue<>(() -> new GitHubActivityApi(caller));
        this.agents = new LazyValue<>(() -> new GitHubAgentsApi(caller));
        this.agentTasks = new LazyValue<>(() -> new GitHubAgentTaskApi(caller));
        this.agentAutomations = new LazyValue<>(() -> new GitHubAgentAutomationApi(caller));
        this.apps = new LazyValue<>(() -> new GitHubAppsApi(caller));
        this.announcementBanners = new LazyValue<>(() -> new GitHubAnnouncementBannersApi(caller));
        this.branches = new LazyValue<>(() -> new GitHubBranchesApi(caller));
        this.billing = new LazyValue<>(() -> new GitHubBillingApi(caller));
        this.campaigns = new LazyValue<>(() -> new GitHubCampaignsApi(caller));
        this.checks = new LazyValue<>(() -> new GitHubChecksApi(caller));
        this.commits = new LazyValue<>(() -> new GitHubCommitsApi(caller));
        this.classroom = new LazyValue<>(() -> new GitHubClassroomApi(caller));
        this.oauthAuthorizations = new LazyValue<>(() -> new GitHubOauthAuthorizationsApi(caller));
        this.codeQuality = new LazyValue<>(() -> new GitHubCodeQualityApi(caller));
        this.codeScanning = new LazyValue<>(() -> new GitHubCodeScanningApi(caller));
        this.codeSecurity = new LazyValue<>(() -> new GitHubCodeSecurityApi(caller));
        this.codeOfConduct = new LazyValue<>(() -> new GitHubCodeOfConductApi(caller));
        this.codespaces = new LazyValue<>(() -> new GitHubCodespacesApi(caller));
        this.collaborators = new LazyValue<>(() -> new GitHubCollaboratorsApi(caller));
        this.copilot = new LazyValue<>(() -> new GitHubCopilotApi(caller));
        this.copilotSpaces = new LazyValue<>(() -> new GitHubCopilotSpacesApi(caller));
        this.credentials = new LazyValue<>(() -> new GitHubCredentialsApi(caller));
        this.dependabot = new LazyValue<>(() -> new GitHubDependabotApi(caller));
        this.dependencyGraph = new LazyValue<>(() -> new GitHubDependencyGraphApi(caller));
        this.deployKeys = new LazyValue<>(() -> new GitHubDeployKeysApi(caller));
        this.deployments = new LazyValue<>(() -> new GitHubDeploymentsApi(caller));
        this.emojis = new LazyValue<>(() -> new GitHubEmojisApi(caller));
        this.enterpriseAdmin = new LazyValue<>(() -> new GitHubEnterpriseAdminApi(caller));
        this.enterpriseTeams = new LazyValue<>(() -> new GitHubEnterpriseTeamsApi(caller));
        this.gists = new LazyValue<>(() -> new GitHubGistsApi(caller));
        this.gitDatabase = new LazyValue<>(() -> new GitHubGitDatabaseApi(caller));
        this.gitignore = new LazyValue<>(() -> new GitHubGitignoreApi(caller));
        this.issues = new LazyValue<>(() -> new GitHubIssuesApi(caller));
        this.interactions = new LazyValue<>(() -> new GitHubInteractionsApi(caller));
        this.licenses = new LazyValue<>(() -> new GitHubLicensesApi(caller));
        this.markdown = new LazyValue<>(() -> new GitHubMarkdownApi(caller));
        this.meta = new LazyValue<>(() -> new GitHubMetaApi(caller));
        this.metrics = new LazyValue<>(() -> new GitHubMetricsApi(caller));
        this.migrations = new LazyValue<>(() -> new GitHubMigrationsApi(caller));
        this.organizations = new LazyValue<>(() -> new GitHubOrganizationsApi(caller));
        this.packages = new LazyValue<>(() -> new GitHubPackagesApi(caller));
        this.pages = new LazyValue<>(() -> new GitHubPagesApi(caller));
        this.privateRegistries = new LazyValue<>(() -> new GitHubPrivateRegistriesApi(caller));
        this.projects = new LazyValue<>(() -> new GitHubProjectsApi(caller));
        this.pullRequests = new LazyValue<>(() -> new GitHubPullRequestsApi(caller));
        this.releases = new LazyValue<>(() -> new GitHubReleasesApi(caller));
        this.scim = new LazyValue<>(() -> new GitHubScimApi(caller));
    }

    /**
     * Users REST resource.
     *
     * @return users API
     */
    public GitHubUsersApi users() {
        return users.get();
    }

    /**
     * Teams REST resource.
     *
     * @return teams API
     */
    public GitHubTeamsApi teams() {
        return teams.get();
    }

    /**
     * Security advisories REST resource.
     *
     * @return security advisories API
     */
    public GitHubSecurityAdvisoriesApi securityAdvisories() {
        return securityAdvisories.get();
    }

    /**
     * Secret scanning REST resource.
     *
     * @return secret scanning API
     */
    public GitHubSecretScanningApi secretScanning() {
        return secretScanning.get();
    }

    /**
     * Rate limit REST resource.
     *
     * @return rate limit API
     */
    public GitHubRateLimitApi rateLimit() {
        return rateLimit.get();
    }

    /**
     * Reactions REST resource.
     *
     * @return reactions API
     */
    public GitHubReactionsApi reactions() {
        return reactions.get();
    }

    /**
     * Search REST resource.
     *
     * @return search API
     */
    public GitHubSearchApi search() {
        return search.get();
    }

    /**
     * Repositories REST resource.
     *
     * @return repositories API
     */
    public GitHubRepositoriesApi repositories() {
        return repositories.get();
    }

    /**
     * Activity REST resources.
     *
     * @return activity API
     */
    public GitHubActivityApi activity() {
        return activity.get();
    }

    /**
     * Agents REST resource.
     *
     * @return agents API
     */
    public GitHubAgentsApi agents() {
        return agents.get();
    }

    /**
     * Agent tasks REST resource.
     *
     * @return agent tasks API
     */
    public GitHubAgentTaskApi agentTasks() {
        return agentTasks.get();
    }

    /**
     * Agent automations REST resource.
     *
     * @return agent automations API
     */
    public GitHubAgentAutomationApi agentAutomations() {
        return agentAutomations.get();
    }

    /**
     * Apps REST resource.
     *
     * @return apps API
     */
    public GitHubAppsApi apps() {
        return apps.get();
    }

    /**
     * Announcement banners REST resource.
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return announcement banners API
     */
    public GitHubAnnouncementBannersApi announcementBanners() {
        return announcementBanners.get();
    }

    /**
     * Actions REST resource.
     *
     * @return actions API
     */
    public GitHubActionsApi actions() {
        return actions.get();
    }

    /**
     * Branches REST resource.
     *
     * @return branches API
     */
    public GitHubBranchesApi branches() {
        return branches.get();
    }

    /**
     * Billing REST resource.
     *
     * @return billing API
     */
    public GitHubBillingApi billing() {
        return billing.get();
    }

    /**
     * Security campaigns REST resource.
     *
     * @return campaigns API
     */
    public GitHubCampaignsApi campaigns() {
        return campaigns.get();
    }

    /**
     * Checks REST resource.
     *
     * @return checks API
     */
    public GitHubChecksApi checks() {
        return checks.get();
    }

    /**
     * Commits REST resource.
     *
     * @return commits API
     */
    public GitHubCommitsApi commits() {
        return commits.get();
    }

    /**
     * GitHub Classroom REST resource.
     *
     * @return classroom API
     * @deprecated Endpoint closed: these operations were removed on August 28, 2026 and answer HTTP 410. Deprecation
     *     date {@code 2026-05-22}; removal date {@code 2026-08-28}.
     */
    @Deprecated(since = "2026-05-22")
    public GitHubClassroomApi classroom() {
        return classroom.get();
    }

    /**
     * OAuth Authorizations REST resource.
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return OAuth authorizations API
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     */
    @Deprecated(since = "2020-02-14")
    public GitHubOauthAuthorizationsApi oauthAuthorizations() {
        return oauthAuthorizations.get();
    }

    /**
     * Code quality REST resource.
     *
     * @return code quality API
     */
    public GitHubCodeQualityApi codeQuality() {
        return codeQuality.get();
    }

    /**
     * Code scanning REST resource.
     *
     * @return code scanning API
     */
    public GitHubCodeScanningApi codeScanning() {
        return codeScanning.get();
    }

    /**
     * Code security configurations REST resource.
     *
     * @return code security configurations API
     */
    public GitHubCodeSecurityApi codeSecurity() {
        return codeSecurity.get();
    }

    /**
     * Codes of conduct REST resource.
     *
     * @return codes of conduct API
     */
    public GitHubCodeOfConductApi codeOfConduct() {
        return codeOfConduct.get();
    }

    /**
     * Codespaces REST resource.
     *
     * @return codespaces API
     */
    public GitHubCodespacesApi codespaces() {
        return codespaces.get();
    }

    /**
     * Collaborators REST resource.
     *
     * @return collaborators API
     */
    public GitHubCollaboratorsApi collaborators() {
        return collaborators.get();
    }

    /**
     * Copilot REST resource.
     *
     * @return Copilot API
     */
    public GitHubCopilotApi copilot() {
        return copilot.get();
    }

    /**
     * Copilot Spaces REST resource.
     *
     * @return Copilot Spaces API
     */
    public GitHubCopilotSpacesApi copilotSpaces() {
        return copilotSpaces.get();
    }

    /**
     * Credentials REST resource.
     *
     * @return credentials API
     */
    public GitHubCredentialsApi credentials() {
        return credentials.get();
    }

    /**
     * Dependabot REST resource.
     *
     * @return Dependabot API
     */
    public GitHubDependabotApi dependabot() {
        return dependabot.get();
    }

    /**
     * Dependency graph REST resource.
     *
     * @return dependency graph API
     */
    public GitHubDependencyGraphApi dependencyGraph() {
        return dependencyGraph.get();
    }

    /**
     * Deploy keys REST resource.
     *
     * @return deploy keys API
     */
    public GitHubDeployKeysApi deployKeys() {
        return deployKeys.get();
    }

    /**
     * Deployments REST resource.
     *
     * @return deployments API
     */
    public GitHubDeploymentsApi deployments() {
        return deployments.get();
    }

    /**
     * Gists REST resource.
     *
     * @return gists API
     */
    public GitHubGistsApi gists() {
        return gists.get();
    }

    /**
     * Git database REST resource.
     *
     * @return Git database API
     */
    public GitHubGitDatabaseApi gitDatabase() {
        return gitDatabase.get();
    }

    /**
     * Emojis REST resource.
     *
     * @return emojis API
     */
    public GitHubEmojisApi emojis() {
        return emojis.get();
    }

    /**
     * Enterprise administration REST resource.
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return enterprise administration API
     */
    public GitHubEnterpriseAdminApi enterpriseAdmin() {
        return enterpriseAdmin.get();
    }

    /**
     * Enterprise teams REST resource.
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return enterprise teams API
     */
    public GitHubEnterpriseTeamsApi enterpriseTeams() {
        return enterpriseTeams.get();
    }

    /**
     * Gitignore REST resource.
     *
     * @return gitignore API
     */
    public GitHubGitignoreApi gitignore() {
        return gitignore.get();
    }

    /**
     * Issues REST resource.
     *
     * @return issues API
     */
    public GitHubIssuesApi issues() {
        return issues.get();
    }

    /**
     * Interactions REST resource.
     *
     * @return interactions API
     */
    public GitHubInteractionsApi interactions() {
        return interactions.get();
    }

    /**
     * Licenses REST resource.
     *
     * @return licenses API
     */
    public GitHubLicensesApi licenses() {
        return licenses.get();
    }

    /**
     * Markdown REST resource.
     *
     * @return markdown API
     */
    public GitHubMarkdownApi markdown() {
        return markdown.get();
    }

    /**
     * Meta REST resource.
     *
     * @return meta API
     */
    public GitHubMetaApi meta() {
        return meta.get();
    }

    /**
     * Metrics REST resource.
     *
     * @return metrics API
     */
    public GitHubMetricsApi metrics() {
        return metrics.get();
    }

    /**
     * Migrations REST resource.
     *
     * @return migrations API
     */
    public GitHubMigrationsApi migrations() {
        return migrations.get();
    }

    /**
     * Organizations REST resource.
     *
     * @return organizations API
     */
    public GitHubOrganizationsApi organizations() {
        return organizations.get();
    }

    /**
     * Packages REST resource.
     *
     * @return packages API
     */
    public GitHubPackagesApi packages() {
        return packages.get();
    }

    /**
     * Pages REST resource.
     *
     * @return pages API
     */
    public GitHubPagesApi pages() {
        return pages.get();
    }

    /**
     * Private registries REST resource.
     *
     * @return private registries API
     */
    public GitHubPrivateRegistriesApi privateRegistries() {
        return privateRegistries.get();
    }

    /**
     * Projects REST resource.
     *
     * @return projects API
     */
    public GitHubProjectsApi projects() {
        return projects.get();
    }

    /**
     * Pull requests REST resource.
     *
     * @return pull requests API
     */
    public GitHubPullRequestsApi pullRequests() {
        return pullRequests.get();
    }

    /**
     * Releases REST resource.
     *
     * @return releases API
     */
    public GitHubReleasesApi releases() {
        return releases.get();
    }

    /**
     * SCIM provisioning REST resource.
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return SCIM API
     */
    public GitHubScimApi scim() {
        return scim.get();
    }
}
