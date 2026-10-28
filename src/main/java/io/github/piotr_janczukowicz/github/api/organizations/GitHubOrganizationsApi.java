package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Organizations REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsApi {

    private final LazyValue<GitHubOrganizationApi> organization;
    private final LazyValue<GitHubOrganizationsApiInsightsApi> apiInsights;
    private final LazyValue<GitHubOrganizationsArtifactMetadataApi> artifactMetadata;
    private final LazyValue<GitHubOrganizationsAttestationsApi> attestations;
    private final LazyValue<GitHubOrganizationsBlockingApi> blocking;
    private final LazyValue<GitHubOrganizationsBypassRequestsApi> bypassRequests;
    private final LazyValue<GitHubOrganizationsCustomPropertiesApi> customProperties;
    private final LazyValue<GitHubOrganizationsCustomPropertiesForOrgsApi> customPropertiesForOrgs;
    private final LazyValue<GitHubOrganizationsCustomRolesApi> customRoles;
    private final LazyValue<GitHubOrganizationsIssueFieldsApi> issueFields;
    private final LazyValue<GitHubOrganizationsIssueTypesApi> issueTypes;
    private final LazyValue<GitHubOrganizationsMembersApi> members;
    private final LazyValue<GitHubOrganizationsNetworkConfigurationsApi> networkConfigurations;
    private final LazyValue<GitHubOrganizationsOrganizationRolesApi> organizationRoles;
    private final LazyValue<GitHubOrganizationsOutsideCollaboratorsApi> outsideCollaborators;
    private final LazyValue<GitHubOrganizationsPersonalAccessTokensApi> personalAccessTokens;
    private final LazyValue<GitHubOrganizationsRuleSuitesApi> ruleSuites;
    private final LazyValue<GitHubOrganizationsRulesApi> rules;
    private final LazyValue<GitHubOrganizationsSecurityManagersApi> securityManagers;
    private final LazyValue<GitHubOrganizationsWebhooksApi> webhooks;

    /**
     * Organizations resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.organization = new LazyValue<>(() -> new GitHubOrganizationApi(caller));
        this.apiInsights = new LazyValue<>(() -> new GitHubOrganizationsApiInsightsApi(caller));
        this.artifactMetadata = new LazyValue<>(() -> new GitHubOrganizationsArtifactMetadataApi(caller));
        this.attestations = new LazyValue<>(() -> new GitHubOrganizationsAttestationsApi(caller));
        this.blocking = new LazyValue<>(() -> new GitHubOrganizationsBlockingApi(caller));
        this.bypassRequests = new LazyValue<>(() -> new GitHubOrganizationsBypassRequestsApi(caller));
        this.customProperties = new LazyValue<>(() -> new GitHubOrganizationsCustomPropertiesApi(caller));
        this.customPropertiesForOrgs = new LazyValue<>(() -> new GitHubOrganizationsCustomPropertiesForOrgsApi(caller));
        this.customRoles = new LazyValue<>(() -> new GitHubOrganizationsCustomRolesApi(caller));
        this.issueFields = new LazyValue<>(() -> new GitHubOrganizationsIssueFieldsApi(caller));
        this.issueTypes = new LazyValue<>(() -> new GitHubOrganizationsIssueTypesApi(caller));
        this.members = new LazyValue<>(() -> new GitHubOrganizationsMembersApi(caller));
        this.networkConfigurations = new LazyValue<>(() -> new GitHubOrganizationsNetworkConfigurationsApi(caller));
        this.organizationRoles = new LazyValue<>(() -> new GitHubOrganizationsOrganizationRolesApi(caller));
        this.outsideCollaborators = new LazyValue<>(() -> new GitHubOrganizationsOutsideCollaboratorsApi(caller));
        this.personalAccessTokens = new LazyValue<>(() -> new GitHubOrganizationsPersonalAccessTokensApi(caller));
        this.ruleSuites = new LazyValue<>(() -> new GitHubOrganizationsRuleSuitesApi(caller));
        this.rules = new LazyValue<>(() -> new GitHubOrganizationsRulesApi(caller));
        this.securityManagers = new LazyValue<>(() -> new GitHubOrganizationsSecurityManagersApi(caller));
        this.webhooks = new LazyValue<>(() -> new GitHubOrganizationsWebhooksApi(caller));
    }

    /**
     * Organization operations ({@code subcategory=orgs}).
     *
     * @return organization API
     */
    public GitHubOrganizationApi organization() {
        return organization.get();
    }

    /**
     * API Insights operations ({@code subcategory=api-insights}).
     *
     * @return API Insights API
     */
    public GitHubOrganizationsApiInsightsApi apiInsights() {
        return apiInsights.get();
    }

    /**
     * Artifact metadata operations ({@code subcategory=artifact-metadata}).
     *
     * @return artifact metadata API
     */
    public GitHubOrganizationsArtifactMetadataApi artifactMetadata() {
        return artifactMetadata.get();
    }

    /**
     * Attestations operations ({@code subcategory=attestations}).
     *
     * @return attestations API
     */
    public GitHubOrganizationsAttestationsApi attestations() {
        return attestations.get();
    }

    /**
     * Blocking operations ({@code subcategory=blocking}).
     *
     * @return blocking API
     */
    public GitHubOrganizationsBlockingApi blocking() {
        return blocking.get();
    }

    /**
     * Custom properties operations ({@code subcategory=custom-properties}).
     *
     * @return custom properties API
     */
    public GitHubOrganizationsCustomPropertiesApi customProperties() {
        return customProperties.get();
    }

    /**
     * Custom property values of an organization ({@code subcategory=custom-properties-for-orgs}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return custom properties for organizations API
     */
    public GitHubOrganizationsCustomPropertiesForOrgsApi customPropertiesForOrgs() {
        return customPropertiesForOrgs.get();
    }

    /**
     * Custom roles operations ({@code subcategory=custom-roles}).
     *
     * <p>Some methods need {@code GHEC}; the rest also run on {@code GHES}. See each method's {@code @throws}.
     *
     * @return custom roles API
     */
    public GitHubOrganizationsCustomRolesApi customRoles() {
        return customRoles.get();
    }

    /**
     * Bypass request operations ({@code subcategory=bypass-requests}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return bypass requests API
     */
    public GitHubOrganizationsBypassRequestsApi bypassRequests() {
        return bypassRequests.get();
    }

    /**
     * Issue fields operations ({@code subcategory=issue-fields}).
     *
     * @return issue fields API
     */
    public GitHubOrganizationsIssueFieldsApi issueFields() {
        return issueFields.get();
    }

    /**
     * Issue types operations ({@code subcategory=issue-types}).
     *
     * @return issue types API
     */
    public GitHubOrganizationsIssueTypesApi issueTypes() {
        return issueTypes.get();
    }

    /**
     * Members operations ({@code subcategory=members}).
     *
     * @return members API
     */
    public GitHubOrganizationsMembersApi members() {
        return members.get();
    }

    /**
     * Network configurations operations ({@code subcategory=network-configurations}).
     *
     * @return network configurations API
     */
    public GitHubOrganizationsNetworkConfigurationsApi networkConfigurations() {
        return networkConfigurations.get();
    }

    /**
     * Organization roles operations ({@code subcategory=organization-roles}).
     *
     * @return organization roles API
     */
    public GitHubOrganizationsOrganizationRolesApi organizationRoles() {
        return organizationRoles.get();
    }

    /**
     * Outside collaborators operations ({@code subcategory=outside-collaborators}).
     *
     * @return outside collaborators API
     */
    public GitHubOrganizationsOutsideCollaboratorsApi outsideCollaborators() {
        return outsideCollaborators.get();
    }

    /**
     * Personal access tokens operations ({@code subcategory=personal-access-tokens}).
     *
     * @return personal access tokens API
     */
    public GitHubOrganizationsPersonalAccessTokensApi personalAccessTokens() {
        return personalAccessTokens.get();
    }

    /**
     * Rule suites operations ({@code subcategory=rule-suites}).
     *
     * @return rule suites API
     */
    public GitHubOrganizationsRuleSuitesApi ruleSuites() {
        return ruleSuites.get();
    }

    /**
     * Rules operations ({@code subcategory=rules}).
     *
     * @return rules API
     */
    public GitHubOrganizationsRulesApi rules() {
        return rules.get();
    }

    /**
     * Security managers operations ({@code subcategory=security-managers}).
     *
     * @return security managers API
     * @deprecated Closing down notice: this operation is closing down and will be removed starting January 1, 2026.
     *     Prefer organization roles. Deprecation date {@code 2024-12-01}; removal date {@code 2026-01-01}.
     */
    @Deprecated(since = "2024-12-01")
    public GitHubOrganizationsSecurityManagersApi securityManagers() {
        return securityManagers.get();
    }

    /**
     * Webhooks operations ({@code subcategory=webhooks}).
     *
     * @return webhooks API
     */
    public GitHubOrganizationsWebhooksApi webhooks() {
        return webhooks.get();
    }
}
