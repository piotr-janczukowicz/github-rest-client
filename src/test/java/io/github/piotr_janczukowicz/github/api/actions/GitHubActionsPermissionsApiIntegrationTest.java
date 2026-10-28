package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsPermissionsApiIntegrationTest {

    private static final String ORG_PERMISSIONS_PATH = "/orgs/github/actions/permissions";
    private static final String REPO_PERMISSIONS_PATH = "/repos/octocat/Hello-World/actions/permissions";
    private static final String SELF_HOSTED_REPOSITORIES_PATH =
            "/orgs/github/actions/permissions/self-hosted-runners/repositories";
    private static final String ENABLED_REPOSITORY_PATH = "/orgs/github/actions/permissions/repositories/2";
    private static final String ENTERPRISE_PERMISSIONS_PATH = "/enterprises/octo-enterprise/actions/permissions";
    private static final String ENTERPRISE_ORGANIZATION_PATH =
            "/enterprises/octo-enterprise/actions/permissions/organizations/2";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getOrgActionsPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_PERMISSIONS_PATH,
                okJson(jsonBytes(
                        GitHubActionsPermissionsApiIntegrationTest.class, "actions-organization-permissions.json")));

        final GitHubActionsOrganizationPermissions permissions;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            permissions = actions.api().permissions().getOrgActionsPermissions("github");
        }

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsOrganizationPermissions());
        HTTP.verifyRequested(GET, ORG_PERMISSIONS_PATH);
    }

    @Test
    void setRepoActionsPermissions_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, REPO_PERMISSIONS_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api()
                    .permissions()
                    .setRepoActionsPermissions(
                            "octocat",
                            "Hello-World",
                            ActionsCatalogFixtures.setGithubActionsPermissionsRepositoryPayload());
        }

        HTTP.verifyRequested(PUT, REPO_PERMISSIONS_PATH);
    }

    @Test
    void listSelectedRepositoriesSelfHostedRunnersOrg_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                SELF_HOSTED_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubActionsPermissionsApiIntegrationTest.class,
                        "selected-repositories-self-hosted-runners.json")));

        final GitHubPage<GitHubRepository> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().permissions().listSelectedRepositoriesSelfHostedRunnersOrg("github");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.selectedRepositoriesSelfHostedRunners()
                        .repositories());
        HTTP.verifyRequested(GET, SELF_HOSTED_REPOSITORIES_PATH);
    }

    @Test
    void getEnterpriseActionsPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_PERMISSIONS_PATH,
                okJson(jsonBytes(
                        GitHubActionsPermissionsApiIntegrationTest.class, "actions-enterprise-permissions.json")));

        final GitHubActionsEnterprisePermissions permissions;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            permissions = actions.api().permissions().getEnterpriseActionsPermissions("octo-enterprise");
        }

        assertThat(permissions).isEqualTo(ActionsCatalogFixtures.actionsEnterprisePermissions());
        HTTP.verifyRequested(GET, ENTERPRISE_PERMISSIONS_PATH);
    }

    @Test
    void enableSelectedOrganizationGitHubActionsEnterprise_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ENTERPRISE_ORGANIZATION_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().permissions().enableSelectedOrganizationGitHubActionsEnterprise("octo-enterprise", 2L);
        }

        HTTP.verifyRequested(PUT, ENTERPRISE_ORGANIZATION_PATH);
    }

    @Test
    void enableSelectedRepositoryGitHubActionsOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ENABLED_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().permissions().enableSelectedRepositoryGitHubActionsOrg("github", 2L);
        }

        HTTP.verifyRequested(PUT, ENABLED_REPOSITORY_PATH);
    }
}
