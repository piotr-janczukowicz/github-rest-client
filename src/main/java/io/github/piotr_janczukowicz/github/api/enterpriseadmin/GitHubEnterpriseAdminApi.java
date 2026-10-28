package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub enterprise administration ({@code GHEC} and {@code GHES}) REST resource aggregator for API version
 * {@code 2026-03-10}.
 */
public final class GitHubEnterpriseAdminApi {

    private final LazyValue<GitHubEnterpriseAdminScimApi> scim;
    private final LazyValue<GitHubEnterpriseAdminEnterpriseRolesApi> enterpriseRoles;
    private final LazyValue<GitHubEnterpriseAdminOrganizationInstallationsApi> organizationInstallations;
    private final LazyValue<GitHubEnterpriseAdminAuditLogApi> auditLog;
    private final LazyValue<GitHubEnterpriseAdminCustomPropertiesForOrgsApi> customPropertiesForOrgs;
    private final LazyValue<GitHubEnterpriseAdminLicensingApi> licensing;
    private final LazyValue<GitHubEnterpriseAdminLdapApi> ldap;
    private final LazyValue<GitHubEnterpriseAdminManageGhesApi> manageGhes;
    private final LazyValue<GitHubEnterpriseAdminLiveMigrationsApi> liveMigrations;
    private final LazyValue<GitHubEnterpriseAdminGlobalWebhooksApi> globalWebhooks;
    private final LazyValue<GitHubEnterpriseAdminUsersApi> users;
    private final LazyValue<GitHubEnterpriseAdminPreReceiveEnvironmentsApi> preReceiveEnvironments;
    private final LazyValue<GitHubEnterpriseAdminPreReceiveHooksApi> preReceiveHooks;
    private final LazyValue<GitHubEnterpriseAdminOrgPreReceiveHooksApi> orgPreReceiveHooks;
    private final LazyValue<GitHubEnterpriseAdminRepoPreReceiveHooksApi> repoPreReceiveHooks;
    private final LazyValue<GitHubEnterpriseAdminOrgsApi> orgs;
    private final LazyValue<GitHubEnterpriseAdminAnnouncementApi> announcement;
    private final LazyValue<GitHubEnterpriseAdminAiControlsApi> aiControls;
    private final LazyValue<GitHubEnterpriseAdminCredentialAuthorizationsApi> credentialAuthorizations;
    private final LazyValue<GitHubEnterpriseAdminNetworkConfigurationsApi> networkConfigurations;
    private final LazyValue<GitHubEnterpriseAdminAdminStatsApi> adminStats;
    private final LazyValue<GitHubEnterpriseAdminEnterprisesApi> enterprises;
    private final LazyValue<GitHubEnterpriseAdminBypassRequestsApi> bypassRequests;
    private final LazyValue<GitHubEnterpriseAdminCodeSecurityAndAnalysisApi> codeSecurityAndAnalysis;
    private final LazyValue<GitHubEnterpriseAdminCustomPropertiesApi> customProperties;
    private final LazyValue<GitHubEnterpriseAdminRulesApi> rules;
    private final LazyValue<GitHubEnterpriseAdminSecurityAdvisoriesApi> securityAdvisories;
    private final LazyValue<GitHubEnterpriseAdminTokenInventoryApi> tokenInventory;

    /**
     * Enterprise administration resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.scim = new LazyValue<>(() -> new GitHubEnterpriseAdminScimApi(caller));
        this.enterpriseRoles = new LazyValue<>(() -> new GitHubEnterpriseAdminEnterpriseRolesApi(caller));
        this.organizationInstallations =
                new LazyValue<>(() -> new GitHubEnterpriseAdminOrganizationInstallationsApi(caller));
        this.auditLog = new LazyValue<>(() -> new GitHubEnterpriseAdminAuditLogApi(caller));
        this.customPropertiesForOrgs =
                new LazyValue<>(() -> new GitHubEnterpriseAdminCustomPropertiesForOrgsApi(caller));
        this.licensing = new LazyValue<>(() -> new GitHubEnterpriseAdminLicensingApi(caller));
        this.ldap = new LazyValue<>(() -> new GitHubEnterpriseAdminLdapApi(caller));
        this.manageGhes = new LazyValue<>(() -> new GitHubEnterpriseAdminManageGhesApi(caller));
        this.liveMigrations = new LazyValue<>(() -> new GitHubEnterpriseAdminLiveMigrationsApi(caller));
        this.globalWebhooks = new LazyValue<>(() -> new GitHubEnterpriseAdminGlobalWebhooksApi(caller));
        this.users = new LazyValue<>(() -> new GitHubEnterpriseAdminUsersApi(caller));
        this.preReceiveEnvironments = new LazyValue<>(() -> new GitHubEnterpriseAdminPreReceiveEnvironmentsApi(caller));
        this.preReceiveHooks = new LazyValue<>(() -> new GitHubEnterpriseAdminPreReceiveHooksApi(caller));
        this.orgPreReceiveHooks = new LazyValue<>(() -> new GitHubEnterpriseAdminOrgPreReceiveHooksApi(caller));
        this.repoPreReceiveHooks = new LazyValue<>(() -> new GitHubEnterpriseAdminRepoPreReceiveHooksApi(caller));
        this.orgs = new LazyValue<>(() -> new GitHubEnterpriseAdminOrgsApi(caller));
        this.announcement = new LazyValue<>(() -> new GitHubEnterpriseAdminAnnouncementApi(caller));
        this.aiControls = new LazyValue<>(() -> new GitHubEnterpriseAdminAiControlsApi(caller));
        this.credentialAuthorizations =
                new LazyValue<>(() -> new GitHubEnterpriseAdminCredentialAuthorizationsApi(caller));
        this.networkConfigurations = new LazyValue<>(() -> new GitHubEnterpriseAdminNetworkConfigurationsApi(caller));
        this.adminStats = new LazyValue<>(() -> new GitHubEnterpriseAdminAdminStatsApi(caller));
        this.enterprises = new LazyValue<>(() -> new GitHubEnterpriseAdminEnterprisesApi(caller));
        this.bypassRequests = new LazyValue<>(() -> new GitHubEnterpriseAdminBypassRequestsApi(caller));
        this.codeSecurityAndAnalysis =
                new LazyValue<>(() -> new GitHubEnterpriseAdminCodeSecurityAndAnalysisApi(caller));
        this.customProperties = new LazyValue<>(() -> new GitHubEnterpriseAdminCustomPropertiesApi(caller));
        this.rules = new LazyValue<>(() -> new GitHubEnterpriseAdminRulesApi(caller));
        this.securityAdvisories = new LazyValue<>(() -> new GitHubEnterpriseAdminSecurityAdvisoriesApi(caller));
        this.tokenInventory = new LazyValue<>(() -> new GitHubEnterpriseAdminTokenInventoryApi(caller));
    }

    /**
     * Enterprise SCIM provisioning operations ({@code subcategory=scim}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return enterprise SCIM API
     */
    public GitHubEnterpriseAdminScimApi scim() {
        return scim.get();
    }

    /**
     * Enterprise role operations ({@code subcategory=enterprise-roles}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return enterprise roles API
     */
    public GitHubEnterpriseAdminEnterpriseRolesApi enterpriseRoles() {
        return enterpriseRoles.get();
    }

    /**
     * Organization installation operations ({@code subcategory=organization-installations}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return organization installations API
     */
    public GitHubEnterpriseAdminOrganizationInstallationsApi organizationInstallations() {
        return organizationInstallations.get();
    }

    /**
     * Audit log operations ({@code subcategory=audit-log}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return audit log API
     */
    public GitHubEnterpriseAdminAuditLogApi auditLog() {
        return auditLog.get();
    }

    /**
     * Custom property operations for organizations ({@code subcategory=custom-properties-for-orgs}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return custom properties for organizations API
     */
    public GitHubEnterpriseAdminCustomPropertiesForOrgsApi customPropertiesForOrgs() {
        return customPropertiesForOrgs.get();
    }

    /**
     * Licensing operations ({@code subcategory=licensing}).
     *
     * <p>The {@code GHEC} and {@code GHES} flavors carry parts of this resource; see each method.
     *
     * @return licensing API
     */
    public GitHubEnterpriseAdminLicensingApi licensing() {
        return licensing.get();
    }

    /**
     * LDAP operations ({@code subcategory=ldap}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return LDAP API
     */
    public GitHubEnterpriseAdminLdapApi ldap() {
        return ldap.get();
    }

    /**
     * Management console operations ({@code subcategory=manage-ghes}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return management console API
     */
    public GitHubEnterpriseAdminManageGhesApi manageGhes() {
        return manageGhes.get();
    }

    /**
     * Live migration operations ({@code subcategory=live-migrations}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return live migration API
     */
    public GitHubEnterpriseAdminLiveMigrationsApi liveMigrations() {
        return liveMigrations.get();
    }

    /**
     * Global webhook operations ({@code subcategory=global-webhooks}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return global webhook API
     */
    public GitHubEnterpriseAdminGlobalWebhooksApi globalWebhooks() {
        return globalWebhooks.get();
    }

    /**
     * User administration operations ({@code subcategory=users}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return user administration API
     */
    public GitHubEnterpriseAdminUsersApi users() {
        return users.get();
    }

    /**
     * Pre-receive environment operations ({@code subcategory=pre-receive-environments}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return pre-receive environment API
     */
    public GitHubEnterpriseAdminPreReceiveEnvironmentsApi preReceiveEnvironments() {
        return preReceiveEnvironments.get();
    }

    /**
     * Pre-receive hook operations ({@code subcategory=pre-receive-hooks}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return pre-receive hook API
     */
    public GitHubEnterpriseAdminPreReceiveHooksApi preReceiveHooks() {
        return preReceiveHooks.get();
    }

    /**
     * Organization pre-receive hook operations ({@code subcategory=org-pre-receive-hooks}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return organization pre-receive hook API
     */
    public GitHubEnterpriseAdminOrgPreReceiveHooksApi orgPreReceiveHooks() {
        return orgPreReceiveHooks.get();
    }

    /**
     * Repository pre-receive hook operations ({@code subcategory=repo-pre-receive-hooks}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return repository pre-receive hook API
     */
    public GitHubEnterpriseAdminRepoPreReceiveHooksApi repoPreReceiveHooks() {
        return repoPreReceiveHooks.get();
    }

    /**
     * Organization administration operations ({@code subcategory=orgs}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return organization administration API
     */
    public GitHubEnterpriseAdminOrgsApi orgs() {
        return orgs.get();
    }

    /**
     * Announcement banner operations ({@code subcategory=announcement}).
     *
     * <p>Only the {@code GHES} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return announcement banner API
     */
    public GitHubEnterpriseAdminAnnouncementApi announcement() {
        return announcement.get();
    }

    /**
     * Copilot AI controls operations ({@code subcategory=ai-controls}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return AI controls API
     */
    public GitHubEnterpriseAdminAiControlsApi aiControls() {
        return aiControls.get();
    }

    /**
     * Credential authorization operations ({@code subcategory=credential-authorizations}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return credential authorizations API
     */
    public GitHubEnterpriseAdminCredentialAuthorizationsApi credentialAuthorizations() {
        return credentialAuthorizations.get();
    }

    /**
     * Hosted compute network configuration operations ({@code subcategory=network-configurations}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return network configurations API
     */
    public GitHubEnterpriseAdminNetworkConfigurationsApi networkConfigurations() {
        return networkConfigurations.get();
    }

    /**
     * Server statistics operations ({@code subcategory=admin-stats}).
     *
     * <p>The {@code GHEC} and {@code GHES} flavors carry parts of this resource; see each method.
     *
     * @return admin statistics API
     */
    public GitHubEnterpriseAdminAdminStatsApi adminStats() {
        return adminStats.get();
    }

    /**
     * Enterprise access restriction operations ({@code subcategory=enterprises}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return enterprises API
     */
    public GitHubEnterpriseAdminEnterprisesApi enterprises() {
        return enterprises.get();
    }

    /**
     * Push rule bypass request operations ({@code subcategory=bypass-requests}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return bypass requests API
     */
    public GitHubEnterpriseAdminBypassRequestsApi bypassRequests() {
        return bypassRequests.get();
    }

    /**
     * Code security and analysis operations ({@code subcategory=code-security-and-analysis}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return code security and analysis API
     */
    public GitHubEnterpriseAdminCodeSecurityAndAnalysisApi codeSecurityAndAnalysis() {
        return codeSecurityAndAnalysis.get();
    }

    /**
     * Custom property operations for repositories ({@code subcategory=custom-properties}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return custom properties API
     */
    public GitHubEnterpriseAdminCustomPropertiesApi customProperties() {
        return customProperties.get();
    }

    /**
     * Enterprise ruleset operations ({@code subcategory=rules}).
     *
     * <p>The {@code GHEC} and {@code GHES} flavors carry parts of this resource; see each method.
     *
     * @return rules API
     */
    public GitHubEnterpriseAdminRulesApi rules() {
        return rules.get();
    }

    /**
     * Innersource vulnerability sync operations ({@code subcategory=security-advisories}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return security advisories API
     */
    public GitHubEnterpriseAdminSecurityAdvisoriesApi securityAdvisories() {
        return securityAdvisories.get();
    }

    /**
     * Token inventory operations ({@code subcategory=token-inventory}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return token inventory API
     */
    public GitHubEnterpriseAdminTokenInventoryApi tokenInventory() {
        return tokenInventory.get();
    }
}
