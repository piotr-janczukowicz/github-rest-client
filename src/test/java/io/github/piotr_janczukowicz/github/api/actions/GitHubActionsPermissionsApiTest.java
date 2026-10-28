package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsPermissionsApiTest {

    private static final String ORG = "github";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BASE = "https://api.github.com/";
    private static final String ENTERPRISE = "octo-enterprise";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsPermissionsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getOrgActionsPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-organization-permissions.json")));

        final GitHubActionsOrganizationPermissions permissions = api().getOrgActionsPermissions(ORG);

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsOrganizationPermissions());
    }

    @Test
    void setOrgActionsPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgActionsPermissions(ORG, ActionsCatalogFixtures.setGithubActionsPermissionsOrganizationPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "orgs/github/actions/permissions");
    }

    @Test
    void getOrgArtifactAndLogRetentionSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-artifact-and-log-retention-response.json")));

        final GitHubActionsArtifactAndLogRetentionResponse settings = api().getOrgArtifactAndLogRetentionSettings(ORG);

        assertThat(settings).isEqualTo(ActionsCatalogFixtures.actionsArtifactAndLogRetentionResponse());
    }

    @Test
    void getOrgArtifactAndLogRetentionSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getOrgArtifactAndLogRetentionSettings(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setOrgArtifactAndLogRetentionSettings_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgArtifactAndLogRetentionSettings(ORG, ActionsCatalogFixtures.actionsArtifactAndLogRetention());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/artifact-and-log-retention");
    }

    @Test
    void getOrgForkPrContributorApprovalPermissions_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-fork-pr-contributor-approval.json")));

        final GitHubActionsForkPrContributorApproval policy = api().getOrgForkPrContributorApprovalPermissions(ORG);

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsForkPrContributorApproval());
    }

    @Test
    void getOrgForkPrContributorApprovalPermissions_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getOrgForkPrContributorApprovalPermissions(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setOrgForkPrContributorApprovalPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgForkPrContributorApprovalPermissions(
                        ORG, ActionsCatalogFixtures.actionsForkPrContributorApproval());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/fork-pr-contributor-approval");
    }

    @Test
    void getOrgPrivateRepoForkPrWorkflowsSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-fork-pr-workflows-private-repos.json")));

        final GitHubActionsForkPrWorkflowsPrivateRepos settings = api().getOrgPrivateRepoForkPrWorkflowsSettings(ORG);

        assertThat(settings).isEqualTo(ActionsCatalogFixtures.actionsForkPrWorkflowsPrivateRepos());
    }

    @Test
    void getOrgPrivateRepoForkPrWorkflowsSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getOrgPrivateRepoForkPrWorkflowsSettings(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setOrgPrivateRepoForkPrWorkflowsSettings_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgPrivateRepoForkPrWorkflowsSettings(
                        ORG, ActionsCatalogFixtures.actionsForkPrWorkflowsPrivateReposRequest());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/fork-pr-workflows-private-repos");
    }

    @Test
    void listSelectedRepositoriesEnabledGitHubActionsOrg_shouldReturnRepositories_whenRequestOmitted()
            throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("selected-repositories-enabled-actions.json")));

        final GitHubPage<GitHubRepository> page = api().listSelectedRepositoriesEnabledGitHubActionsOrg(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.selectedRepositoriesEnabledActions()
                        .repositories());
    }

    @Test
    void listSelectedRepositoriesEnabledGitHubActionsOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("selected-repositories-enabled-actions.json")));

        api().listSelectedRepositoriesEnabledGitHubActionsOrg(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setSelectedRepositoriesEnabledGitHubActionsOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedRepositoriesEnabledGitHubActionsOrg(
                        ORG, ActionsCatalogFixtures.setSelectedRepositoriesEnabledGithubActionsOrganizationPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/repositories");
    }

    @Test
    void enableSelectedRepositoryGitHubActionsOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableSelectedRepositoryGitHubActionsOrg(ORG, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/repositories/1");
    }

    @Test
    void disableSelectedRepositoryGitHubActionsOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableSelectedRepositoryGitHubActionsOrg(ORG, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "orgs/github/actions/permissions/repositories/1");
    }

    @Test
    void getAllowedActionsOrg_shouldReturnAllowedActions_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("selected-actions.json")));

        final GitHubSelectedActions actions = api().getAllowedActionsOrg(ORG);

        assertThat(actions).isEqualTo(ActionsCatalogFixtures.selectedActions());
    }

    @Test
    void setAllowedActionsOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setAllowedActionsOrg(ORG, ActionsCatalogFixtures.selectedActions());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/selected-actions");
    }

    @Test
    void getOrgSelfHostedRunnersPermissions_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("self-hosted-runners-settings.json")));

        final GitHubSelfHostedRunnersSettings settings = api().getOrgSelfHostedRunnersPermissions(ORG);

        assertThat(settings).isEqualTo(ActionsCatalogFixtures.selfHostedRunnersSettings());
    }

    @Test
    void getOrgSelfHostedRunnersPermissions_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getOrgSelfHostedRunnersPermissions(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setOrgSelfHostedRunnersPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgSelfHostedRunnersPermissions(
                        ORG, ActionsCatalogFixtures.setSelfHostedRunnersPermissionsOrganizationPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/self-hosted-runners");
    }

    @Test
    void listSelectedRepositoriesSelfHostedRunnersOrg_shouldReturnRepositories_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("selected-repositories-self-hosted-runners.json")));

        final GitHubPage<GitHubRepository> page = api().listSelectedRepositoriesSelfHostedRunnersOrg(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.selectedRepositoriesSelfHostedRunners()
                        .repositories());
    }

    @Test
    void listSelectedRepositoriesSelfHostedRunnersOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("selected-repositories-self-hosted-runners.json")));

        api().listSelectedRepositoriesSelfHostedRunnersOrg(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setSelectedRepositoriesSelfHostedRunnersOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedRepositoriesSelfHostedRunnersOrg(
                        ORG, ActionsCatalogFixtures.setSelectedRepositoriesSelfHostedRunnersOrganizationPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/self-hosted-runners/repositories");
    }

    @Test
    void enableSelectedRepositorySelfHostedRunnersOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableSelectedRepositorySelfHostedRunnersOrg(ORG, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/self-hosted-runners/repositories/1");
    }

    @Test
    void disableSelectedRepositorySelfHostedRunnersOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableSelectedRepositorySelfHostedRunnersOrg(ORG, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "orgs/github/actions/permissions/self-hosted-runners/repositories/1");
    }

    @Test
    void getOrgDefaultWorkflowPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-get-default-workflow-permissions.json")));

        final GitHubActionsGetDefaultWorkflowPermissions permissions = api().getOrgDefaultWorkflowPermissions(ORG);

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsGetDefaultWorkflowPermissions());
    }

    @Test
    void setOrgDefaultWorkflowPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgDefaultWorkflowPermissions(ORG, ActionsCatalogFixtures.actionsSetDefaultWorkflowPermissions());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "orgs/github/actions/permissions/workflow");
    }

    @Test
    void getRepoActionsPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-repository-permissions.json")));

        final GitHubActionsRepositoryPermissions permissions = api().getRepoActionsPermissions(OWNER, REPO);

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsRepositoryPermissions());
    }

    @Test
    void setRepoActionsPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoActionsPermissions(
                        OWNER, REPO, ActionsCatalogFixtures.setGithubActionsPermissionsRepositoryPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/actions/permissions");
    }

    @Test
    void getRepoWorkflowAccess_shouldReturnAccess_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-workflow-access-to-repository.json")));

        final GitHubActionsWorkflowAccessToRepository access = api().getRepoWorkflowAccess(OWNER, REPO);

        assertThat(access).isEqualTo(ActionsCatalogFixtures.actionsWorkflowAccessToRepository());
    }

    @Test
    void setRepoWorkflowAccess_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoWorkflowAccess(OWNER, REPO, ActionsCatalogFixtures.actionsWorkflowAccessToRepository());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/actions/permissions/access");
    }

    @Test
    void getRepoArtifactAndLogRetentionSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-artifact-and-log-retention-response.json")));

        final GitHubActionsArtifactAndLogRetentionResponse settings =
                api().getRepoArtifactAndLogRetentionSettings(OWNER, REPO);

        assertThat(settings).isEqualTo(ActionsCatalogFixtures.actionsArtifactAndLogRetentionResponse());
    }

    @Test
    void getRepoArtifactAndLogRetentionSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getRepoArtifactAndLogRetentionSettings(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setRepoArtifactAndLogRetentionSettings_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoArtifactAndLogRetentionSettings(
                        OWNER, REPO, ActionsCatalogFixtures.actionsArtifactAndLogRetention());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/actions/permissions/artifact-and-log-retention");
    }

    @Test
    void getRepoForkPrContributorApprovalPermissions_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-fork-pr-contributor-approval.json")));

        final GitHubActionsForkPrContributorApproval policy =
                api().getRepoForkPrContributorApprovalPermissions(OWNER, REPO);

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsForkPrContributorApproval());
    }

    @Test
    void getRepoForkPrContributorApprovalPermissions_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getRepoForkPrContributorApprovalPermissions(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setRepoForkPrContributorApprovalPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoForkPrContributorApprovalPermissions(
                        OWNER, REPO, ActionsCatalogFixtures.actionsForkPrContributorApproval());

        assertThat(line(captureSingleRequest()))
                .isEqualTo(
                        "PUT " + BASE + "repos/octocat/Hello-World/actions/permissions/fork-pr-contributor-approval");
    }

    @Test
    void getRepoPrivateRepoForkPrWorkflowsSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-fork-pr-workflows-private-repos.json")));

        final GitHubActionsForkPrWorkflowsPrivateRepos settings =
                api().getRepoPrivateRepoForkPrWorkflowsSettings(OWNER, REPO);

        assertThat(settings).isEqualTo(ActionsCatalogFixtures.actionsForkPrWorkflowsPrivateRepos());
    }

    @Test
    void getRepoPrivateRepoForkPrWorkflowsSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getRepoPrivateRepoForkPrWorkflowsSettings(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setRepoPrivateRepoForkPrWorkflowsSettings_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoPrivateRepoForkPrWorkflowsSettings(
                        OWNER, REPO, ActionsCatalogFixtures.actionsForkPrWorkflowsPrivateReposRequest());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE
                        + "repos/octocat/Hello-World/actions/permissions/fork-pr-workflows-private-repos");
    }

    @Test
    void getRepoAllowedActions_shouldReturnAllowedActions_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("selected-actions.json")));

        final GitHubSelectedActions actions = api().getRepoAllowedActions(OWNER, REPO);

        assertThat(actions).isEqualTo(ActionsCatalogFixtures.selectedActions());
    }

    @Test
    void setRepoAllowedActions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoAllowedActions(OWNER, REPO, ActionsCatalogFixtures.selectedActions());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/actions/permissions/selected-actions");
    }

    @Test
    void getRepoDefaultWorkflowPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-get-default-workflow-permissions.json")));

        final GitHubActionsGetDefaultWorkflowPermissions permissions =
                api().getRepoDefaultWorkflowPermissions(OWNER, REPO);

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsGetDefaultWorkflowPermissions());
    }

    @Test
    void setRepoDefaultWorkflowPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoDefaultWorkflowPermissions(
                        OWNER, REPO, ActionsCatalogFixtures.actionsSetDefaultWorkflowPermissions());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/actions/permissions/workflow");
    }

    @Test
    void getEnterpriseActionsPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-enterprise-permissions.json")));

        final GitHubActionsEnterprisePermissions permissions = api().getEnterpriseActionsPermissions(ENTERPRISE);

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsEnterprisePermissions());
    }

    @Test
    void setEnterpriseActionsPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseActionsPermissions(
                        ENTERPRISE, ActionsCatalogFixtures.setGithubActionsPermissionsEnterprisePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/permissions");
    }

    @Test
    void getEnterpriseArtifactAndLogRetentionSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-artifact-and-log-retention-response.json")));

        final GitHubActionsArtifactAndLogRetentionResponse settings =
                api().getEnterpriseArtifactAndLogRetentionSettings(ENTERPRISE);

        assertThat(settings).isEqualTo(ActionsCatalogFixtures.actionsArtifactAndLogRetentionResponse());
    }

    @Test
    void setEnterpriseArtifactAndLogRetentionSettings_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseArtifactAndLogRetentionSettings(
                        ENTERPRISE, ActionsCatalogFixtures.actionsArtifactAndLogRetention());

        assertThat(line(captureSingleRequest()))
                .isEqualTo(
                        "PUT " + BASE + "enterprises/octo-enterprise/actions/permissions/artifact-and-log-retention");
    }

    @Test
    void getEnterpriseForkPrContributorApprovalPermissions_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-fork-pr-contributor-approval.json")));

        final GitHubActionsForkPrContributorApproval policy =
                api().getEnterpriseForkPrContributorApprovalPermissions(ENTERPRISE);

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsForkPrContributorApproval());
    }

    @Test
    void setEnterpriseForkPrContributorApprovalPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseForkPrContributorApprovalPermissions(
                        ENTERPRISE, ActionsCatalogFixtures.actionsForkPrContributorApproval());

        assertThat(line(captureSingleRequest()))
                .isEqualTo(
                        "PUT " + BASE + "enterprises/octo-enterprise/actions/permissions/fork-pr-contributor-approval");
    }

    @Test
    void getEnterprisePrivateRepoForkPrWorkflowsSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-fork-pr-workflows-private-repos.json")));

        final GitHubActionsForkPrWorkflowsPrivateRepos settings =
                api().getEnterprisePrivateRepoForkPrWorkflowsSettings(ENTERPRISE);

        assertThat(settings).isEqualTo(ActionsCatalogFixtures.actionsForkPrWorkflowsPrivateRepos());
    }

    @Test
    void setEnterprisePrivateRepoForkPrWorkflowsSettings_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterprisePrivateRepoForkPrWorkflowsSettings(
                        ENTERPRISE, ActionsCatalogFixtures.actionsForkPrWorkflowsPrivateReposRequest());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE
                        + "enterprises/octo-enterprise/actions/permissions/fork-pr-workflows-private-repos");
    }

    @Test
    void listSelectedOrganizationsEnabledGitHubActionsEnterprise_shouldReturnOrganizations_whenRequestOmitted()
            throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("selected-organizations-enabled-actions.json")));

        final GitHubPage<GitHubOrganizationSimple> page =
                api().listSelectedOrganizationsEnabledGitHubActionsEnterprise(ENTERPRISE);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.selectedOrganizationsEnabledActions()
                        .organizations());
    }

    @Test
    void listSelectedOrganizationsEnabledGitHubActionsEnterprise_shouldSendPageNumber_whenRequestProvided()
            throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("selected-organizations-enabled-actions.json")));

        api().listSelectedOrganizationsEnabledGitHubActionsEnterprise(ENTERPRISE, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setSelectedOrganizationsEnabledGitHubActionsEnterprise_shouldSendPut_whenResponseIsNoContent()
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedOrganizationsEnabledGitHubActionsEnterprise(
                        ENTERPRISE,
                        ActionsCatalogFixtures.setSelectedOrganizationsEnabledGithubActionsEnterprisePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/permissions/organizations");
    }

    @Test
    void enableSelectedOrganizationGitHubActionsEnterprise_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableSelectedOrganizationGitHubActionsEnterprise(ENTERPRISE, 2L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/permissions/organizations/2");
    }

    @Test
    void disableSelectedOrganizationGitHubActionsEnterprise_shouldSendDelete_whenResponseIsNoContent()
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableSelectedOrganizationGitHubActionsEnterprise(ENTERPRISE, 2L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "enterprises/octo-enterprise/actions/permissions/organizations/2");
    }

    @Test
    void getEnterpriseAllowedActions_shouldReturnAllowedActions_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("selected-actions.json")));

        final GitHubSelectedActions actions = api().getEnterpriseAllowedActions(ENTERPRISE);

        assertThat(actions).isEqualTo(ActionsCatalogFixtures.selectedActions());
    }

    @Test
    void setEnterpriseAllowedActions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseAllowedActions(ENTERPRISE, ActionsCatalogFixtures.selectedActions());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/permissions/selected-actions");
    }

    @Test
    void getEnterpriseSelfHostedRunnersPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("self-hosted-runners-permissions-for-enterprise.json")));

        final GitHubSelfHostedRunnersPermissionsForEnterprise permissions =
                api().getEnterpriseSelfHostedRunnersPermissions(ENTERPRISE);

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.selfHostedRunnersPermissionsForEnterprise());
    }

    @Test
    void setEnterpriseSelfHostedRunnersPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseSelfHostedRunnersPermissions(
                        ENTERPRISE, ActionsCatalogFixtures.setSelfHostedRunnersPermissionsEnterprisePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/permissions/self-hosted-runners");
    }

    @Test
    void getEnterpriseDefaultWorkflowPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-get-default-workflow-permissions.json")));

        final GitHubActionsGetDefaultWorkflowPermissions permissions =
                api().getEnterpriseDefaultWorkflowPermissions(ENTERPRISE);

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsGetDefaultWorkflowPermissions());
    }

    @Test
    void setEnterpriseDefaultWorkflowPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseDefaultWorkflowPermissions(
                        ENTERPRISE, ActionsCatalogFixtures.actionsSetDefaultWorkflowPermissions());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/permissions/workflow");
    }

    private GitHubActionsPermissionsApi api() {
        return new GitHubActionsPermissionsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsPermissionsApiTest.class, fileName);
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
