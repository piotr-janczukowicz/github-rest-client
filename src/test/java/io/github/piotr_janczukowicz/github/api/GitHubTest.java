package io.github.piotr_janczukowicz.github.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.actions.GitHubActionsApi;
import io.github.piotr_janczukowicz.github.api.activity.GitHubActivityApi;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubAgentAutomationApi;
import io.github.piotr_janczukowicz.github.api.agents.GitHubAgentsApi;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubAgentTaskApi;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncementBannersApi;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncementBannersEnterprisesApi;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncementBannersOrganizationsApi;
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
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminAdminStatsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminAiControlsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminAnnouncementApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminAuditLogApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminBypassRequestsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCodeSecurityAndAnalysisApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCredentialAuthorizationsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCustomPropertiesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCustomPropertiesForOrgsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminEnterpriseRolesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminEnterprisesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminGlobalWebhooksApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminLdapApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminLicensingApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminLiveMigrationsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminManageGhesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminNetworkConfigurationsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminOrgPreReceiveHooksApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminOrganizationInstallationsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminOrgsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminPreReceiveEnvironmentsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminPreReceiveHooksApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminRepoPreReceiveHooksApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminRulesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminScimApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminSecurityAdvisoriesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminTokenInventoryApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminUsersApi;
import io.github.piotr_janczukowicz.github.api.enterpriseteams.GitHubEnterpriseTeamApi;
import io.github.piotr_janczukowicz.github.api.enterpriseteams.GitHubEnterpriseTeamsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseteams.GitHubEnterpriseTeamsEnterpriseTeamMembersApi;
import io.github.piotr_janczukowicz.github.api.enterpriseteams.GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi;
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
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationsCustomRolesApi;
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
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHub(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void constructor_shouldConstruct_whenCallerProvided() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.users()).isNotNull();
    }

    @Test
    void activity_shouldReturnActivityApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.activity()).isInstanceOf(GitHubActivityApi.class);
    }

    @Test
    void teams_shouldReturnTeamsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.teams()).isInstanceOf(GitHubTeamsApi.class);
    }

    @Test
    void users_shouldReturnUsersApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.users()).isInstanceOf(GitHubUsersApi.class);
    }

    @Test
    void securityAdvisories_shouldReturnSecurityAdvisoriesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.securityAdvisories()).isInstanceOf(GitHubSecurityAdvisoriesApi.class);
    }

    @Test
    void search_shouldReturnSearchApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.search()).isInstanceOf(GitHubSearchApi.class);
    }

    @Test
    void secretScanning_shouldReturnSecretScanningApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.secretScanning()).isInstanceOf(GitHubSecretScanningApi.class);
    }

    @Test
    void rateLimit_shouldReturnRateLimitApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.rateLimit()).isInstanceOf(GitHubRateLimitApi.class);
    }

    @Test
    void rateLimit_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.rateLimit()).isSameAs(github.rateLimit());
    }

    @Test
    void codespaces_shouldReturnCodespacesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.codespaces()).isInstanceOf(GitHubCodespacesApi.class);
    }

    @Test
    void collaborators_shouldReturnCollaboratorsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.collaborators()).isInstanceOf(GitHubCollaboratorsApi.class);
    }

    @Test
    void copilot_shouldReturnCopilotApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.copilot()).isInstanceOf(GitHubCopilotApi.class);
    }

    @Test
    void copilotSpaces_shouldReturnCopilotSpacesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.copilotSpaces()).isInstanceOf(GitHubCopilotSpacesApi.class);
    }

    @Test
    void credentials_shouldReturnCredentialsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.credentials()).isInstanceOf(GitHubCredentialsApi.class);
    }

    @Test
    void agents_shouldReturnAgentsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.agents()).isInstanceOf(GitHubAgentsApi.class);

        assertThat(github.agentTasks()).isInstanceOf(GitHubAgentTaskApi.class);
    }

    @Test
    void agentAutomations_shouldReturnAgentAutomationApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.agentAutomations()).isInstanceOf(GitHubAgentAutomationApi.class);
    }

    @Test
    void actions_shouldReturnActionsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.actions()).isInstanceOf(GitHubActionsApi.class);
    }

    @Test
    void dependabot_shouldReturnDependabotApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.dependabot()).isInstanceOf(GitHubDependabotApi.class);
    }

    @Test
    void dependencyGraph_shouldReturnDependencyGraphApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.dependencyGraph()).isInstanceOf(GitHubDependencyGraphApi.class);
    }

    @Test
    void apps_shouldReturnAppsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.apps()).isInstanceOf(GitHubAppsApi.class);
    }

    @Test
    void campaigns_shouldReturnCampaignsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.campaigns()).isInstanceOf(GitHubCampaignsApi.class);
    }

    @Test
    void deployKeys_shouldReturnDeployKeysApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.deployKeys()).isInstanceOf(GitHubDeployKeysApi.class);
    }

    @Test
    void classroom_shouldReturnClassroomApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.classroom()).isInstanceOf(GitHubClassroomApi.class);
    }

    @Test
    void codeQuality_shouldReturnCodeQualityApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.codeQuality()).isInstanceOf(GitHubCodeQualityApi.class);
    }

    @Test
    void codeScanning_shouldReturnCodeScanningApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.codeScanning()).isInstanceOf(GitHubCodeScanningApi.class);
    }

    @Test
    void codeSecurity_shouldReturnCodeSecurityApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.codeSecurity()).isInstanceOf(GitHubCodeSecurityApi.class);
    }

    @Test
    void codeOfConduct_shouldReturnCodeOfConductApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.codeOfConduct()).isInstanceOf(GitHubCodeOfConductApi.class);
    }

    @Test
    void gists_shouldReturnGistsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.gists()).isInstanceOf(GitHubGistsApi.class);
    }

    @Test
    void gitDatabase_shouldReturnGitDatabaseApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.gitDatabase()).isInstanceOf(GitHubGitDatabaseApi.class);
    }

    @Test
    void deployments_shouldReturnDeploymentsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.deployments()).isInstanceOf(GitHubDeploymentsApi.class);
    }

    @Test
    void emojis_shouldReturnApi() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.emojis()).isInstanceOf(GitHubEmojisApi.class);
    }

    @Test
    void gitignore_shouldReturnApi() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.gitignore()).isInstanceOf(GitHubGitignoreApi.class);
    }

    @Test
    void licenses_shouldReturnApi() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.licenses()).isInstanceOf(GitHubLicensesApi.class);
    }

    @Test
    void markdown_shouldReturnMarkdownApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.markdown()).isInstanceOf(GitHubMarkdownApi.class);
    }

    @Test
    void markdown_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.markdown()).isSameAs(github.markdown());
    }

    @Test
    void meta_shouldReturnMetaApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.meta()).isInstanceOf(GitHubMetaApi.class);
    }

    @Test
    void meta_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.meta()).isSameAs(github.meta());
    }

    @Test
    void metrics_shouldReturnMetricsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.metrics()).isInstanceOf(GitHubMetricsApi.class);
    }

    @Test
    void migrations_shouldReturnMigrationsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertSoftly(softly -> {
            softly.assertThat(github.migrations()).isInstanceOf(GitHubMigrationsApi.class);
            softly.assertThat(github.migrations().orgs()).isNotNull();
        });
    }

    @Test
    void organizations_shouldReturnOrganizationsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.organizations()).isInstanceOf(GitHubOrganizationsApi.class);
    }

    @Test
    void organizationsCustomRoles_shouldReturnCustomRolesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.organizations().customRoles()).isInstanceOf(GitHubOrganizationsCustomRolesApi.class);
    }

    @Test
    void packages_shouldReturnPackagesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.packages()).isInstanceOf(GitHubPackagesApi.class);
    }

    @Test
    void pages_shouldReturnPagesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.pages()).isInstanceOf(GitHubPagesApi.class);
    }

    @Test
    void privateRegistries_shouldReturnPrivateRegistriesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.privateRegistries()).isInstanceOf(GitHubPrivateRegistriesApi.class);
    }

    @Test
    void branches_shouldReturnBranchesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.branches()).isInstanceOf(GitHubBranchesApi.class);
    }

    @Test
    void announcementBanners_shouldReturnAnnouncementBannersApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.announcementBanners()).isInstanceOf(GitHubAnnouncementBannersApi.class);
        assertThat(github.announcementBanners().enterprises())
                .isInstanceOf(GitHubAnnouncementBannersEnterprisesApi.class);
        assertThat(github.announcementBanners().organizations())
                .isInstanceOf(GitHubAnnouncementBannersOrganizationsApi.class);
    }

    @Test
    void enterpriseAdmin_shouldReturnEnterpriseAdminApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin()).isInstanceOf(GitHubEnterpriseAdminApi.class);
        assertThat(github.enterpriseAdmin().scim()).isInstanceOf(GitHubEnterpriseAdminScimApi.class);
        assertThat(github.enterpriseAdmin().enterpriseRoles())
                .isInstanceOf(GitHubEnterpriseAdminEnterpriseRolesApi.class);
    }

    @Test
    void enterpriseAdminOrganizationInstallations_shouldReturnOrganizationInstallationsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().organizationInstallations())
                .isInstanceOf(GitHubEnterpriseAdminOrganizationInstallationsApi.class);
    }

    @Test
    void enterpriseAdminAuditLog_shouldReturnAuditLogApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().auditLog()).isInstanceOf(GitHubEnterpriseAdminAuditLogApi.class);
    }

    @Test
    void enterpriseAdminCustomPropertiesForOrgs_shouldReturnCustomPropertiesForOrgsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().customPropertiesForOrgs())
                .isInstanceOf(GitHubEnterpriseAdminCustomPropertiesForOrgsApi.class);
    }

    @Test
    void enterpriseAdminLicensing_shouldReturnLicensingApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().licensing()).isInstanceOf(GitHubEnterpriseAdminLicensingApi.class);
    }

    @Test
    void enterpriseAdminPreReceiveEnvironments_shouldReturnPreReceiveEnvironmentsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().preReceiveEnvironments())
                .isInstanceOf(GitHubEnterpriseAdminPreReceiveEnvironmentsApi.class);
    }

    @Test
    void enterpriseAdminPreReceiveHooks_shouldReturnPreReceiveHooksApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().preReceiveHooks())
                .isInstanceOf(GitHubEnterpriseAdminPreReceiveHooksApi.class);
    }

    @Test
    void enterpriseAdminOrgPreReceiveHooks_shouldReturnOrgPreReceiveHooksApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().orgPreReceiveHooks())
                .isInstanceOf(GitHubEnterpriseAdminOrgPreReceiveHooksApi.class);
    }

    @Test
    void enterpriseAdminRepoPreReceiveHooks_shouldReturnRepoPreReceiveHooksApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().repoPreReceiveHooks())
                .isInstanceOf(GitHubEnterpriseAdminRepoPreReceiveHooksApi.class);
    }

    @Test
    void enterpriseAdminGlobalWebhooks_shouldReturnGlobalWebhooksApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().globalWebhooks())
                .isInstanceOf(GitHubEnterpriseAdminGlobalWebhooksApi.class);
    }

    @Test
    void enterpriseAdminUsers_shouldReturnUsersApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().users()).isInstanceOf(GitHubEnterpriseAdminUsersApi.class);
    }

    @Test
    void enterpriseAdminLiveMigrations_shouldReturnLiveMigrationsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().liveMigrations())
                .isInstanceOf(GitHubEnterpriseAdminLiveMigrationsApi.class);
    }

    @Test
    void enterpriseAdminManageGhes_shouldReturnManageGhesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().manageGhes()).isInstanceOf(GitHubEnterpriseAdminManageGhesApi.class);
    }

    @Test
    @SuppressWarnings("deprecation")
    void oauthAuthorizations_shouldReturnOauthAuthorizationsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.oauthAuthorizations()).isInstanceOf(GitHubOauthAuthorizationsApi.class);
    }

    @Test
    void enterpriseAdminLdap_shouldReturnLdapApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().ldap()).isInstanceOf(GitHubEnterpriseAdminLdapApi.class);
    }

    @Test
    void enterpriseAdminOrgs_shouldReturnOrgsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().orgs()).isInstanceOf(GitHubEnterpriseAdminOrgsApi.class);
    }

    @Test
    void enterpriseAdminAnnouncement_shouldReturnAnnouncementApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().announcement()).isInstanceOf(GitHubEnterpriseAdminAnnouncementApi.class);
    }

    @Test
    void enterpriseAdminAiControls_shouldReturnAiControlsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().aiControls()).isInstanceOf(GitHubEnterpriseAdminAiControlsApi.class);
    }

    @Test
    void enterpriseAdminCredentialAuthorizations_shouldReturnCredentialAuthorizationsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().credentialAuthorizations())
                .isInstanceOf(GitHubEnterpriseAdminCredentialAuthorizationsApi.class);
    }

    @Test
    void enterpriseAdminNetworkConfigurations_shouldReturnNetworkConfigurationsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().networkConfigurations())
                .isInstanceOf(GitHubEnterpriseAdminNetworkConfigurationsApi.class);
    }

    @Test
    void enterpriseAdminAdminStats_shouldReturnAdminStatsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().adminStats()).isInstanceOf(GitHubEnterpriseAdminAdminStatsApi.class);
    }

    @Test
    void enterpriseAdminEnterprises_shouldReturnEnterprisesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().enterprises()).isInstanceOf(GitHubEnterpriseAdminEnterprisesApi.class);
    }

    @Test
    void enterpriseAdminBypassRequests_shouldReturnBypassRequestsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().bypassRequests())
                .isInstanceOf(GitHubEnterpriseAdminBypassRequestsApi.class);
    }

    @Test
    void enterpriseAdminCodeSecurityAndAnalysis_shouldReturnCodeSecurityAndAnalysisApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().codeSecurityAndAnalysis())
                .isInstanceOf(GitHubEnterpriseAdminCodeSecurityAndAnalysisApi.class);
    }

    @Test
    void enterpriseAdminCustomProperties_shouldReturnCustomPropertiesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().customProperties())
                .isInstanceOf(GitHubEnterpriseAdminCustomPropertiesApi.class);
    }

    @Test
    void enterpriseAdminRules_shouldReturnRulesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().rules()).isInstanceOf(GitHubEnterpriseAdminRulesApi.class);
    }

    @Test
    void enterpriseAdminSecurityAdvisories_shouldReturnSecurityAdvisoriesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().securityAdvisories())
                .isInstanceOf(GitHubEnterpriseAdminSecurityAdvisoriesApi.class);
    }

    @Test
    void enterpriseAdminTokenInventory_shouldReturnTokenInventoryApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseAdmin().tokenInventory())
                .isInstanceOf(GitHubEnterpriseAdminTokenInventoryApi.class);
    }

    @Test
    void enterpriseTeams_shouldReturnEnterpriseTeamsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.enterpriseTeams()).isInstanceOf(GitHubEnterpriseTeamsApi.class);
        assertThat(github.enterpriseTeams().enterpriseTeam()).isInstanceOf(GitHubEnterpriseTeamApi.class);
        assertThat(github.enterpriseTeams().enterpriseTeamMembers())
                .isInstanceOf(GitHubEnterpriseTeamsEnterpriseTeamMembersApi.class);
        assertThat(github.enterpriseTeams().enterpriseTeamOrganizations())
                .isInstanceOf(GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi.class);
    }

    @Test
    void scim_shouldReturnScimApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.scim()).isInstanceOf(GitHubScimApi.class);
    }

    @Test
    void billing_shouldReturnBillingApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.billing()).isInstanceOf(GitHubBillingApi.class);
    }

    @Test
    void checks_shouldReturnChecksApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.checks()).isInstanceOf(GitHubChecksApi.class);
    }

    @Test
    void commits_shouldReturnCommitsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.commits()).isInstanceOf(GitHubCommitsApi.class);
    }

    @Test
    void releases_shouldReturnReleasesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.releases()).isInstanceOf(GitHubReleasesApi.class);
    }

    @Test
    void reactions_shouldReturnReactionsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.reactions()).isInstanceOf(GitHubReactionsApi.class);
    }

    @Test
    void repositories_shouldReturnReposApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertSoftly(softly -> {
            softly.assertThat(github.repositories()).isInstanceOf(GitHubRepositoriesApi.class);
            softly.assertThat(github.repositories().repository()).isNotNull();
        });
    }

    @Test
    void projects_shouldReturnProjectsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        final GitHubProjectsApi projects = github.projects();

        assertSoftly(softly -> {
            softly.assertThat(projects).isInstanceOf(GitHubProjectsApi.class);
            softly.assertThat(projects.project()).isNotNull();
            softly.assertThat(projects.drafts()).isNotNull();
            softly.assertThat(projects.fields()).isNotNull();
            softly.assertThat(projects.items()).isNotNull();
            softly.assertThat(projects.views()).isNotNull();
            softly.assertThat(projects.project()).isSameAs(projects.project());
        });
    }

    @Test
    void projects_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.projects()).isSameAs(github.projects());
    }

    @Test
    void issues_shouldReturnIssuesApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        final GitHubIssuesApi issues = github.issues();

        assertSoftly(softly -> {
            softly.assertThat(issues).isInstanceOf(GitHubIssuesApi.class);
            softly.assertThat(issues.issue()).isNotNull();
            softly.assertThat(issues.issue()).isSameAs(issues.issue());
        });
    }

    @Test
    void interactions_shouldReturnInteractionsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        final GitHubInteractionsApi interactions = github.interactions();

        assertSoftly(softly -> {
            softly.assertThat(interactions).isInstanceOf(GitHubInteractionsApi.class);
            softly.assertThat(interactions.orgs()).isNotNull();
            softly.assertThat(interactions.repos()).isNotNull();
            softly.assertThat(interactions.user()).isNotNull();
        });
    }

    @Test
    void issues_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.issues()).isSameAs(github.issues());
    }

    @Test
    void pullRequests_shouldReturnPullsApi_whenCalled() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        final GitHubPullRequestsApi pulls = github.pullRequests();

        assertSoftly(softly -> {
            softly.assertThat(pulls).isInstanceOf(GitHubPullRequestsApi.class);
            softly.assertThat(pulls.pullRequest()).isNotNull();
            softly.assertThat(pulls.comments()).isNotNull();
            softly.assertThat(pulls.reviewRequests()).isNotNull();
            softly.assertThat(pulls.reviews()).isNotNull();
            softly.assertThat(pulls.stacks()).isNotNull();
            softly.assertThat(pulls.pullRequest()).isSameAs(pulls.pullRequest());
        });
    }

    @Test
    void pullRequests_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.pullRequests()).isSameAs(github.pullRequests());
    }

    @Test
    void users_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertThat(github.users()).isSameAs(github.users());
    }

    @Test
    void repositories_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);

        final GitHub github = new GitHub(GitHubCaller.of(client));

        assertSoftly(softly -> {
            softly.assertThat(github.repositories()).isSameAs(github.repositories());
            softly.assertThat(github.repositories().repository())
                    .isSameAs(github.repositories().repository());
        });
    }

    @Test
    void users_shouldReturnSameInstance_whenCalledConcurrently() throws Exception {
        final GitHubClient client = TestClients.client(URI.create("https://api.github.com/"), transport);
        final GitHub github = new GitHub(GitHubCaller.of(client));
        final ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            final List<Future<GitHubUsersApi>> calls = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                calls.add(pool.submit(github::users));
            }
            final GitHubUsersApi first = calls.getFirst().get();
            for (final Future<GitHubUsersApi> call : calls) {
                assertThat(call.get()).isSameAs(first);
            }
        } finally {
            pool.shutdown();
            assertThat(pool.awaitTermination(5, TimeUnit.SECONDS)).isTrue();
        }
    }
}
