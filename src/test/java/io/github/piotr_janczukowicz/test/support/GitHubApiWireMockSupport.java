package io.github.piotr_janczukowicz.test.support;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpStatus.ACCEPTED;
import static io.github.piotr_janczukowicz.http.HttpStatus.CREATED;
import static io.github.piotr_janczukowicz.http.HttpStatus.FORBIDDEN;
import static io.github.piotr_janczukowicz.http.HttpStatus.MULTI_STATUS;
import static io.github.piotr_janczukowicz.http.HttpStatus.NOT_FOUND;
import static io.github.piotr_janczukowicz.http.HttpStatus.NO_CONTENT;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.http.HttpStatus.SERVICE_UNAVAILABLE;
import static io.github.piotr_janczukowicz.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;

import io.github.piotr_janczukowicz.github.api.actions.GitHubActionsApi;
import io.github.piotr_janczukowicz.github.api.activity.GitHubActivityApi;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubAgentAutomationApi;
import io.github.piotr_janczukowicz.github.api.agents.GitHubAgentsApi;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubAgentTaskApi;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncementBannersEnterprisesApi;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncementBannersOrganizationsApi;
import io.github.piotr_janczukowicz.github.api.billing.GitHubBillingBillingApi;
import io.github.piotr_janczukowicz.github.api.billing.GitHubBillingBudgetsApi;
import io.github.piotr_janczukowicz.github.api.billing.GitHubBillingCostCentersApi;
import io.github.piotr_janczukowicz.github.api.billing.GitHubBillingUsageApi;
import io.github.piotr_janczukowicz.github.api.billing.GitHubBillingUsageReportsApi;
import io.github.piotr_janczukowicz.github.api.branches.GitHubBranchesApi;
import io.github.piotr_janczukowicz.github.api.campaigns.GitHubCampaignsApi;
import io.github.piotr_janczukowicz.github.api.checks.GitHubChecksApi;
import io.github.piotr_janczukowicz.github.api.codequality.GitHubCodeQualityApi;
import io.github.piotr_janczukowicz.github.api.codescanning.GitHubCodeScanningCodeScanningApi;
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
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminAuditLogApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminBypassRequestsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCodeSecurityAndAnalysisApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCredentialAuthorizationsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCustomPropertiesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminCustomPropertiesForOrgsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminEnterpriseRolesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminEnterprisesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminLicensingApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminNetworkConfigurationsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminOrganizationInstallationsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminRulesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminScimApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminSecurityAdvisoriesApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminTokenInventoryApi;
import io.github.piotr_janczukowicz.github.api.enterpriseteams.GitHubEnterpriseTeamApi;
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
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationsApi;
import io.github.piotr_janczukowicz.github.api.packages.GitHubPackagesApi;
import io.github.piotr_janczukowicz.github.api.pages.GitHubPagesApi;
import io.github.piotr_janczukowicz.github.api.privateregistries.GitHubPrivateRegistriesApi;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectsApi;
import io.github.piotr_janczukowicz.github.api.ratelimit.GitHubRateLimitApi;
import io.github.piotr_janczukowicz.github.api.reactions.GitHubReactionsApi;
import io.github.piotr_janczukowicz.github.api.releases.GitHubReleasesApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesAttestationsApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesAutolinksApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesContentsApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesCustomPropertiesApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesForksApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesIssueTypesApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesRuleSuitesApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesRulesApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoriesWebhooksApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryApi;
import io.github.piotr_janczukowicz.github.api.scim.GitHubScimApi;
import io.github.piotr_janczukowicz.github.api.search.GitHubSearchApi;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningApi;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubSecurityAdvisoriesApi;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamsApi;
import io.github.piotr_janczukowicz.github.api.users.GitHubUsersApi;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.TestGitHubClients;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.http.transport.HttpTransports;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.pagination.PaginationConfiguration;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public final class GitHubApiWireMockSupport {

    public static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    private GitHubApiWireMockSupport() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static WireMockClient client(final StubHttpServer http) {
        return client(http, PaginationConfiguration.defaults());
    }

    public static WireMockClient client(final StubHttpServer http, final PaginationConfiguration pagination) {
        final HttpTransport transport = HttpTransports.okHttp();
        final GitHubClient client =
                TestGitHubClients.raw(transport, TestClients.configuration(http.baseUri()), pagination);
        return new WireMockClient(transport, client);
    }

    public static UsersApiClient usersApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubUsersApi api = new GitHubUsersApi(wireMock.caller());
        return new UsersApiClient(wireMock, api);
    }

    public static OrganizationsApiClient organizationsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubOrganizationsApi api = new GitHubOrganizationsApi(wireMock.caller());
        return new OrganizationsApiClient(wireMock, api);
    }

    public static TeamsApiClient teamsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubTeamsApi api = new GitHubTeamsApi(wireMock.caller());
        return new TeamsApiClient(wireMock, api);
    }

    public static SecurityAdvisoriesApiClient securityAdvisoriesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubSecurityAdvisoriesApi api = new GitHubSecurityAdvisoriesApi(wireMock.caller());
        return new SecurityAdvisoriesApiClient(wireMock, api);
    }

    public static SecretScanningApiClient secretScanningApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubSecretScanningApi api = new GitHubSecretScanningApi(wireMock.caller());
        return new SecretScanningApiClient(wireMock, api);
    }

    public static SearchApiClient searchApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubSearchApi api = new GitHubSearchApi(wireMock.caller());
        return new SearchApiClient(wireMock, api);
    }

    public static RateLimitApiClient rateLimitApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRateLimitApi api = new GitHubRateLimitApi(wireMock.caller());
        return new RateLimitApiClient(wireMock, api);
    }

    public static EmojisApiClient emojisApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEmojisApi api = new GitHubEmojisApi(wireMock.caller());
        return new EmojisApiClient(wireMock, api);
    }

    public static GitignoreApiClient gitignoreApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubGitignoreApi api = new GitHubGitignoreApi(wireMock.caller());
        return new GitignoreApiClient(wireMock, api);
    }

    public static LicensesApiClient licensesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubLicensesApi api = new GitHubLicensesApi(wireMock.caller());
        return new LicensesApiClient(wireMock, api);
    }

    public static MarkdownApiClient markdownApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubMarkdownApi api = new GitHubMarkdownApi(wireMock.caller());
        return new MarkdownApiClient(wireMock, api);
    }

    public static MetaApiClient metaApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubMetaApi api = new GitHubMetaApi(wireMock.caller());
        return new MetaApiClient(wireMock, api);
    }

    public static ReactionsApiClient reactionsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubReactionsApi api = new GitHubReactionsApi(wireMock.caller());
        return new ReactionsApiClient(wireMock, api);
    }

    public static ReposCustomPropertiesApiClient reposCustomPropertiesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesCustomPropertiesApi api = new GitHubRepositoriesCustomPropertiesApi(wireMock.caller());
        return new ReposCustomPropertiesApiClient(wireMock, api);
    }

    public static ReposAttestationsApiClient reposAttestationsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesAttestationsApi api = new GitHubRepositoriesAttestationsApi(wireMock.caller());
        return new ReposAttestationsApiClient(wireMock, api);
    }

    public static ReposAutolinksApiClient reposAutolinksApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesAutolinksApi api = new GitHubRepositoriesAutolinksApi(wireMock.caller());
        return new ReposAutolinksApiClient(wireMock, api);
    }

    public static ReposForksApiClient reposForksApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesForksApi api = new GitHubRepositoriesForksApi(wireMock.caller());
        return new ReposForksApiClient(wireMock, api);
    }

    public static ReposIssueTypesApiClient reposIssueTypesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesIssueTypesApi api = new GitHubRepositoriesIssueTypesApi(wireMock.caller());
        return new ReposIssueTypesApiClient(wireMock, api);
    }

    public static BranchesApiClient branchesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubBranchesApi api = new GitHubBranchesApi(wireMock.caller());
        return new BranchesApiClient(wireMock, api);
    }

    public static CollaboratorsApiClient collaboratorsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCollaboratorsApi api = new GitHubCollaboratorsApi(wireMock.caller());
        return new CollaboratorsApiClient(wireMock, api);
    }

    public static CommitsApiClient commitsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCommitsApi api = new GitHubCommitsApi(wireMock.caller());
        return new CommitsApiClient(wireMock, api);
    }

    public static ReposContentsApiClient reposContentsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesContentsApi api = new GitHubRepositoriesContentsApi(wireMock.caller());
        return new ReposContentsApiClient(wireMock, api);
    }

    public static DeploymentsApiClient deploymentsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubDeploymentsApi api = new GitHubDeploymentsApi(wireMock.caller());
        return new DeploymentsApiClient(wireMock, api);
    }

    public static ChecksApiClient checksApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubChecksApi api = new GitHubChecksApi(wireMock.caller());
        return new ChecksApiClient(wireMock, api);
    }

    public static CopilotApiClient copilotApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCopilotApi api = new GitHubCopilotApi(wireMock.caller());
        return new CopilotApiClient(wireMock, api);
    }

    public static CopilotSpacesApiClient copilotSpacesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCopilotSpacesApi api = new GitHubCopilotSpacesApi(wireMock.caller());
        return new CopilotSpacesApiClient(wireMock, api);
    }

    public static CodespacesApiClient codespacesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCodespacesApi api = new GitHubCodespacesApi(wireMock.caller());
        return new CodespacesApiClient(wireMock, api);
    }

    public static ActionsApiClient actionsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubActionsApi api = new GitHubActionsApi(wireMock.caller());
        return new ActionsApiClient(wireMock, api);
    }

    public static AgentsApiClient agentsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubAgentsApi api = new GitHubAgentsApi(wireMock.caller());
        return new AgentsApiClient(wireMock, api);
    }

    public static DependabotApiClient dependabotApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubDependabotApi api = new GitHubDependabotApi(wireMock.caller());
        return new DependabotApiClient(wireMock, api);
    }

    public static DependencyGraphApiClient dependencyGraphApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubDependencyGraphApi api = new GitHubDependencyGraphApi(wireMock.caller());
        return new DependencyGraphApiClient(wireMock, api);
    }

    public static IssuesApiClient issuesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubIssuesApi api = new GitHubIssuesApi(wireMock.caller());
        return new IssuesApiClient(wireMock, api);
    }

    public static InteractionsApiClient interactionsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubInteractionsApi api = new GitHubInteractionsApi(wireMock.caller());
        return new InteractionsApiClient(wireMock, api);
    }

    public static GistsApiClient gistsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubGistsApi api = new GitHubGistsApi(wireMock.caller());
        return new GistsApiClient(wireMock, api);
    }

    public static GitDatabaseApiClient gitDatabaseApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubGitDatabaseApi api = new GitHubGitDatabaseApi(wireMock.caller());
        return new GitDatabaseApiClient(wireMock, api);
    }

    public static CredentialsApiClient credentialsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCredentialsApi api = new GitHubCredentialsApi(wireMock.caller());
        return new CredentialsApiClient(wireMock, api);
    }

    public static ActivityApiClient activityApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubActivityApi api = new GitHubActivityApi(wireMock.caller());
        return new ActivityApiClient(wireMock, api);
    }

    public static DeployKeysApiClient deployKeysApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubDeployKeysApi api = new GitHubDeployKeysApi(wireMock.caller());
        return new DeployKeysApiClient(wireMock, api);
    }

    public static AnnouncementBannersEnterprisesApiClient announcementBannersEnterprisesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubAnnouncementBannersEnterprisesApi api =
                new GitHubAnnouncementBannersEnterprisesApi(wireMock.caller());
        return new AnnouncementBannersEnterprisesApiClient(wireMock, api);
    }

    public static AnnouncementBannersOrganizationsApiClient announcementBannersOrganizationsApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubAnnouncementBannersOrganizationsApi api =
                new GitHubAnnouncementBannersOrganizationsApi(wireMock.caller());
        return new AnnouncementBannersOrganizationsApiClient(wireMock, api);
    }

    public static ScimApiClient scimApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubScimApi api = new GitHubScimApi(wireMock.caller());
        return new ScimApiClient(wireMock, api);
    }

    public static EnterpriseAdminScimApiClient enterpriseAdminScimApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminScimApi api = new GitHubEnterpriseAdminScimApi(wireMock.caller());
        return new EnterpriseAdminScimApiClient(wireMock, api);
    }

    public static EnterpriseAdminEnterpriseRolesApiClient enterpriseAdminEnterpriseRolesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminEnterpriseRolesApi api =
                new GitHubEnterpriseAdminEnterpriseRolesApi(wireMock.caller());
        return new EnterpriseAdminEnterpriseRolesApiClient(wireMock, api);
    }

    public static EnterpriseAdminOrganizationInstallationsApiClient enterpriseAdminOrganizationInstallationsApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminOrganizationInstallationsApi api =
                new GitHubEnterpriseAdminOrganizationInstallationsApi(wireMock.caller());
        return new EnterpriseAdminOrganizationInstallationsApiClient(wireMock, api);
    }

    public static EnterpriseAdminLicensingApiClient enterpriseAdminLicensingApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminLicensingApi api = new GitHubEnterpriseAdminLicensingApi(wireMock.caller());
        return new EnterpriseAdminLicensingApiClient(wireMock, api);
    }

    public static EnterpriseAdminAiControlsApiClient enterpriseAdminAiControlsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminAiControlsApi api = new GitHubEnterpriseAdminAiControlsApi(wireMock.caller());
        return new EnterpriseAdminAiControlsApiClient(wireMock, api);
    }

    public static EnterpriseAdminCredentialAuthorizationsApiClient enterpriseAdminCredentialAuthorizationsApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminCredentialAuthorizationsApi api =
                new GitHubEnterpriseAdminCredentialAuthorizationsApi(wireMock.caller());
        return new EnterpriseAdminCredentialAuthorizationsApiClient(wireMock, api);
    }

    public static EnterpriseAdminNetworkConfigurationsApiClient enterpriseAdminNetworkConfigurationsApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminNetworkConfigurationsApi api =
                new GitHubEnterpriseAdminNetworkConfigurationsApi(wireMock.caller());
        return new EnterpriseAdminNetworkConfigurationsApiClient(wireMock, api);
    }

    public static EnterpriseAdminAuditLogApiClient enterpriseAdminAuditLogApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminAuditLogApi api = new GitHubEnterpriseAdminAuditLogApi(wireMock.caller());
        return new EnterpriseAdminAuditLogApiClient(wireMock, api);
    }

    public static EnterpriseAdminCustomPropertiesForOrgsApiClient enterpriseAdminCustomPropertiesForOrgsApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminCustomPropertiesForOrgsApi api =
                new GitHubEnterpriseAdminCustomPropertiesForOrgsApi(wireMock.caller());
        return new EnterpriseAdminCustomPropertiesForOrgsApiClient(wireMock, api);
    }

    public static EnterpriseAdminAdminStatsApiClient enterpriseAdminAdminStatsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminAdminStatsApi api = new GitHubEnterpriseAdminAdminStatsApi(wireMock.caller());
        return new EnterpriseAdminAdminStatsApiClient(wireMock, api);
    }

    public static EnterpriseAdminEnterprisesApiClient enterpriseAdminEnterprisesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminEnterprisesApi api = new GitHubEnterpriseAdminEnterprisesApi(wireMock.caller());
        return new EnterpriseAdminEnterprisesApiClient(wireMock, api);
    }

    public static EnterpriseAdminBypassRequestsApiClient enterpriseAdminBypassRequestsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminBypassRequestsApi api =
                new GitHubEnterpriseAdminBypassRequestsApi(wireMock.caller());
        return new EnterpriseAdminBypassRequestsApiClient(wireMock, api);
    }

    public static EnterpriseAdminCodeSecurityAndAnalysisApiClient enterpriseAdminCodeSecurityAndAnalysisApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminCodeSecurityAndAnalysisApi api =
                new GitHubEnterpriseAdminCodeSecurityAndAnalysisApi(wireMock.caller());
        return new EnterpriseAdminCodeSecurityAndAnalysisApiClient(wireMock, api);
    }

    public static EnterpriseAdminCustomPropertiesApiClient enterpriseAdminCustomPropertiesApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminCustomPropertiesApi api =
                new GitHubEnterpriseAdminCustomPropertiesApi(wireMock.caller());
        return new EnterpriseAdminCustomPropertiesApiClient(wireMock, api);
    }

    public static EnterpriseAdminRulesApiClient enterpriseAdminRulesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminRulesApi api = new GitHubEnterpriseAdminRulesApi(wireMock.caller());
        return new EnterpriseAdminRulesApiClient(wireMock, api);
    }

    public static EnterpriseAdminSecurityAdvisoriesApiClient enterpriseAdminSecurityAdvisoriesApi(
            final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminSecurityAdvisoriesApi api =
                new GitHubEnterpriseAdminSecurityAdvisoriesApi(wireMock.caller());
        return new EnterpriseAdminSecurityAdvisoriesApiClient(wireMock, api);
    }

    public static EnterpriseAdminTokenInventoryApiClient enterpriseAdminTokenInventoryApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseAdminTokenInventoryApi api =
                new GitHubEnterpriseAdminTokenInventoryApi(wireMock.caller());
        return new EnterpriseAdminTokenInventoryApiClient(wireMock, api);
    }

    public static EnterpriseTeamApiClient enterpriseTeamApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseTeamApi api = new GitHubEnterpriseTeamApi(wireMock.caller());
        return new EnterpriseTeamApiClient(wireMock, api);
    }

    public static EnterpriseTeamMembersApiClient enterpriseTeamMembersApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseTeamsEnterpriseTeamMembersApi api =
                new GitHubEnterpriseTeamsEnterpriseTeamMembersApi(wireMock.caller());
        return new EnterpriseTeamMembersApiClient(wireMock, api);
    }

    public static EnterpriseTeamOrganizationsApiClient enterpriseTeamOrganizationsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi api =
                new GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi(wireMock.caller());
        return new EnterpriseTeamOrganizationsApiClient(wireMock, api);
    }

    public static AgentTasksApiClient agentTasksApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubAgentTaskApi api = new GitHubAgentTaskApi(wireMock.caller());
        return new AgentTasksApiClient(wireMock, api);
    }

    public static AgentAutomationsApiClient agentAutomationsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubAgentAutomationApi api = new GitHubAgentAutomationApi(wireMock.caller());
        return new AgentAutomationsApiClient(wireMock, api);
    }

    public static BillingCostCentersApiClient billingCostCentersApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubBillingCostCentersApi api = new GitHubBillingCostCentersApi(wireMock.caller());
        return new BillingCostCentersApiClient(wireMock, api);
    }

    public static BillingUsageReportsApiClient billingUsageReportsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubBillingUsageReportsApi api = new GitHubBillingUsageReportsApi(wireMock.caller());
        return new BillingUsageReportsApiClient(wireMock, api);
    }

    public static BillingBillingApiClient billingBillingApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubBillingBillingApi api = new GitHubBillingBillingApi(wireMock.caller());
        return new BillingBillingApiClient(wireMock, api);
    }

    public static BillingBudgetsApiClient billingBudgetsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubBillingBudgetsApi api = new GitHubBillingBudgetsApi(wireMock.caller());
        return new BillingBudgetsApiClient(wireMock, api);
    }

    public static BillingUsageApiClient billingUsageApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubBillingUsageApi api = new GitHubBillingUsageApi(wireMock.caller());
        return new BillingUsageApiClient(wireMock, api);
    }

    public static CampaignsApiClient campaignsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCampaignsApi api = new GitHubCampaignsApi(wireMock.caller());
        return new CampaignsApiClient(wireMock, api);
    }

    public static CodeQualityApiClient codeQualityApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCodeQualityApi api = new GitHubCodeQualityApi(wireMock.caller());
        return new CodeQualityApiClient(wireMock, api);
    }

    public static CodeScanningApiClient codeScanningApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCodeScanningCodeScanningApi api = new GitHubCodeScanningCodeScanningApi(wireMock.caller());
        return new CodeScanningApiClient(wireMock, api);
    }

    public static CodeSecurityApiClient codeSecurityApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCodeSecurityApi api = new GitHubCodeSecurityApi(wireMock.caller());
        return new CodeSecurityApiClient(wireMock, api);
    }

    public static CodeOfConductApiClient codeOfConductApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubCodeOfConductApi api = new GitHubCodeOfConductApi(wireMock.caller());
        return new CodeOfConductApiClient(wireMock, api);
    }

    public static PagesApiClient pagesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubPagesApi api = new GitHubPagesApi(wireMock.caller());
        return new PagesApiClient(wireMock, api);
    }

    public static PackagesApiClient packagesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubPackagesApi api = new GitHubPackagesApi(wireMock.caller());
        return new PackagesApiClient(wireMock, api);
    }

    public static PrivateRegistriesApiClient privateRegistriesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubPrivateRegistriesApi api = new GitHubPrivateRegistriesApi(wireMock.caller());
        return new PrivateRegistriesApiClient(wireMock, api);
    }

    public static ProjectsApiClient projectsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubProjectsApi api = new GitHubProjectsApi(wireMock.caller());
        return new ProjectsApiClient(wireMock, api);
    }

    public static ReleasesApiClient releasesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubReleasesApi api = new GitHubReleasesApi(wireMock.caller());
        return new ReleasesApiClient(wireMock, api);
    }

    public static ReposRepositoriesApiClient reposRepositoriesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoryApi api = new GitHubRepositoryApi(wireMock.caller());
        return new ReposRepositoriesApiClient(wireMock, api);
    }

    public static ReposRulesApiClient reposRulesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesRulesApi api = new GitHubRepositoriesRulesApi(wireMock.caller());
        return new ReposRulesApiClient(wireMock, api);
    }

    public static ReposRuleSuitesApiClient reposRuleSuitesApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesRuleSuitesApi api = new GitHubRepositoriesRuleSuitesApi(wireMock.caller());
        return new ReposRuleSuitesApiClient(wireMock, api);
    }

    public static MetricsApiClient metricsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubMetricsApi api = new GitHubMetricsApi(wireMock.caller());
        return new MetricsApiClient(wireMock, api);
    }

    public static MigrationsApiClient migrationsApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubMigrationsApi api = new GitHubMigrationsApi(wireMock.caller());
        return new MigrationsApiClient(wireMock, api);
    }

    public static ReposWebhooksApiClient reposWebhooksApi(final StubHttpServer http) {
        final WireMockClient wireMock = client(http);
        final GitHubRepositoriesWebhooksApi api = new GitHubRepositoriesWebhooksApi(wireMock.caller());
        return new ReposWebhooksApiClient(wireMock, api);
    }

    public static byte[] jsonBytes(final Class<?> anchor, final String file) {
        return FileUtils.readBytes(anchor, file);
    }

    public static String json(final Class<?> anchor, final String file) {
        return FileUtils.readString(anchor, file);
    }

    public static byte[] listJsonBytes(final Class<?> anchor, final String file) {
        return ('[' + json(anchor, file) + ']').getBytes(StandardCharsets.UTF_8);
    }

    public static StubHttpServer.StubResponse okJson(final byte[] body) {
        return status(OK).header(CONTENT_TYPE, "application/json").body(body);
    }

    public static StubHttpServer.StubResponse createdJson(final byte[] body) {
        return status(CREATED).header(CONTENT_TYPE, "application/json").body(body);
    }

    public static StubHttpServer.StubResponse acceptedJson(final byte[] body) {
        return status(ACCEPTED).header(CONTENT_TYPE, "application/json").body(body);
    }

    public static StubHttpServer.StubResponse multiStatusJson(final byte[] body) {
        return status(MULTI_STATUS).header(CONTENT_TYPE, "application/json").body(body);
    }

    public static StubHttpServer.StubResponse accepted() {
        return status(ACCEPTED);
    }

    public static StubHttpServer.StubResponse noContent() {
        return status(NO_CONTENT);
    }

    public static StubHttpServer.StubResponse notFound() {
        return status(NOT_FOUND);
    }

    public static StubHttpServer.StubResponse forbidden() {
        return status(FORBIDDEN);
    }

    public static StubHttpServer.StubResponse unprocessable() {
        return status(UNPROCESSABLE_ENTITY);
    }

    public static StubHttpServer.StubResponse serviceUnavailable() {
        return status(SERVICE_UNAVAILABLE);
    }

    public static String linkNext(final URI nextUri) {
        return "<" + nextUri + ">; rel=\"next\"";
    }

    public record WireMockClient(HttpTransport transport, GitHubClient client) implements AutoCloseable {

        public GitHubCaller caller() {
            return GitHubCaller.of(client);
        }

        @Override
        public void close() {
            client.close();
            transport.close();
        }
    }

    public record UsersApiClient(WireMockClient wireMock, GitHubUsersApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record OrganizationsApiClient(WireMockClient wireMock, GitHubOrganizationsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record MigrationsApiClient(WireMockClient wireMock, GitHubMigrationsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record TeamsApiClient(WireMockClient wireMock, GitHubTeamsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record SecurityAdvisoriesApiClient(WireMockClient wireMock, GitHubSecurityAdvisoriesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record SecretScanningApiClient(WireMockClient wireMock, GitHubSecretScanningApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record SearchApiClient(WireMockClient wireMock, GitHubSearchApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record RateLimitApiClient(WireMockClient wireMock, GitHubRateLimitApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EmojisApiClient(WireMockClient wireMock, GitHubEmojisApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record GitignoreApiClient(WireMockClient wireMock, GitHubGitignoreApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record LicensesApiClient(WireMockClient wireMock, GitHubLicensesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record InteractionsApiClient(WireMockClient wireMock, GitHubInteractionsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record IssuesApiClient(WireMockClient wireMock, GitHubIssuesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record MarkdownApiClient(WireMockClient wireMock, GitHubMarkdownApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record MetaApiClient(WireMockClient wireMock, GitHubMetaApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReactionsApiClient(WireMockClient wireMock, GitHubReactionsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposCustomPropertiesApiClient(WireMockClient wireMock, GitHubRepositoriesCustomPropertiesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposAttestationsApiClient(WireMockClient wireMock, GitHubRepositoriesAttestationsApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposAutolinksApiClient(WireMockClient wireMock, GitHubRepositoriesAutolinksApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposForksApiClient(WireMockClient wireMock, GitHubRepositoriesForksApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposIssueTypesApiClient(WireMockClient wireMock, GitHubRepositoriesIssueTypesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record BranchesApiClient(WireMockClient wireMock, GitHubBranchesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CollaboratorsApiClient(WireMockClient wireMock, GitHubCollaboratorsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CommitsApiClient(WireMockClient wireMock, GitHubCommitsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposContentsApiClient(WireMockClient wireMock, GitHubRepositoriesContentsApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CopilotApiClient(WireMockClient wireMock, GitHubCopilotApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CopilotSpacesApiClient(WireMockClient wireMock, GitHubCopilotSpacesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CodespacesApiClient(WireMockClient wireMock, GitHubCodespacesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ActionsApiClient(WireMockClient wireMock, GitHubActionsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record AgentsApiClient(WireMockClient wireMock, GitHubAgentsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record DependabotApiClient(WireMockClient wireMock, GitHubDependabotApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record DependencyGraphApiClient(WireMockClient wireMock, GitHubDependencyGraphApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ChecksApiClient(WireMockClient wireMock, GitHubChecksApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record DeploymentsApiClient(WireMockClient wireMock, GitHubDeploymentsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CredentialsApiClient(WireMockClient wireMock, GitHubCredentialsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CodeOfConductApiClient(WireMockClient wireMock, GitHubCodeOfConductApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record BillingCostCentersApiClient(WireMockClient wireMock, GitHubBillingCostCentersApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record BillingUsageReportsApiClient(WireMockClient wireMock, GitHubBillingUsageReportsApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record BillingBillingApiClient(WireMockClient wireMock, GitHubBillingBillingApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record BillingBudgetsApiClient(WireMockClient wireMock, GitHubBillingBudgetsApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record BillingUsageApiClient(WireMockClient wireMock, GitHubBillingUsageApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record AgentTasksApiClient(WireMockClient wireMock, GitHubAgentTaskApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record AgentAutomationsApiClient(WireMockClient wireMock, GitHubAgentAutomationApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ActivityApiClient(WireMockClient wireMock, GitHubActivityApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record DeployKeysApiClient(WireMockClient wireMock, GitHubDeployKeysApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record AnnouncementBannersEnterprisesApiClient(
            WireMockClient wireMock, GitHubAnnouncementBannersEnterprisesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminScimApiClient(WireMockClient wireMock, GitHubEnterpriseAdminScimApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminEnterpriseRolesApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminEnterpriseRolesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminOrganizationInstallationsApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminOrganizationInstallationsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminLicensingApiClient(WireMockClient wireMock, GitHubEnterpriseAdminLicensingApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminAiControlsApiClient(WireMockClient wireMock, GitHubEnterpriseAdminAiControlsApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminCredentialAuthorizationsApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminCredentialAuthorizationsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminNetworkConfigurationsApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminNetworkConfigurationsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminAdminStatsApiClient(WireMockClient wireMock, GitHubEnterpriseAdminAdminStatsApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminEnterprisesApiClient(WireMockClient wireMock, GitHubEnterpriseAdminEnterprisesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminBypassRequestsApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminBypassRequestsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminCodeSecurityAndAnalysisApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminCodeSecurityAndAnalysisApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminCustomPropertiesApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminCustomPropertiesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminRulesApiClient(WireMockClient wireMock, GitHubEnterpriseAdminRulesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminSecurityAdvisoriesApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminSecurityAdvisoriesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminTokenInventoryApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminTokenInventoryApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminAuditLogApiClient(WireMockClient wireMock, GitHubEnterpriseAdminAuditLogApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseAdminCustomPropertiesForOrgsApiClient(
            WireMockClient wireMock, GitHubEnterpriseAdminCustomPropertiesForOrgsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseTeamApiClient(WireMockClient wireMock, GitHubEnterpriseTeamApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseTeamMembersApiClient(
            WireMockClient wireMock, GitHubEnterpriseTeamsEnterpriseTeamMembersApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record EnterpriseTeamOrganizationsApiClient(
            WireMockClient wireMock, GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record AnnouncementBannersOrganizationsApiClient(
            WireMockClient wireMock, GitHubAnnouncementBannersOrganizationsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ScimApiClient(WireMockClient wireMock, GitHubScimApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CampaignsApiClient(WireMockClient wireMock, GitHubCampaignsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CodeQualityApiClient(WireMockClient wireMock, GitHubCodeQualityApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CodeScanningApiClient(WireMockClient wireMock, GitHubCodeScanningCodeScanningApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record CodeSecurityApiClient(WireMockClient wireMock, GitHubCodeSecurityApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record GistsApiClient(WireMockClient wireMock, GitHubGistsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record GitDatabaseApiClient(WireMockClient wireMock, GitHubGitDatabaseApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record PagesApiClient(WireMockClient wireMock, GitHubPagesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record PackagesApiClient(WireMockClient wireMock, GitHubPackagesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record PrivateRegistriesApiClient(WireMockClient wireMock, GitHubPrivateRegistriesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ProjectsApiClient(WireMockClient wireMock, GitHubProjectsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReleasesApiClient(WireMockClient wireMock, GitHubReleasesApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposRepositoriesApiClient(WireMockClient wireMock, GitHubRepositoryApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposRulesApiClient(WireMockClient wireMock, GitHubRepositoriesRulesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposRuleSuitesApiClient(WireMockClient wireMock, GitHubRepositoriesRuleSuitesApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record MetricsApiClient(WireMockClient wireMock, GitHubMetricsApi api) implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }

    public record ReposWebhooksApiClient(WireMockClient wireMock, GitHubRepositoriesWebhooksApi api)
            implements AutoCloseable {

        public HttpTransport transport() {
            return wireMock.transport();
        }

        public GitHubClient client() {
            return wireMock.client();
        }

        public GitHubCaller caller() {
            return wireMock.caller();
        }

        @Override
        public void close() throws Exception {
            wireMock.close();
        }
    }
}
