package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminEnterpriseRolesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.teams.GitHubEnterpriseTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminEnterpriseRolesApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminEnterpriseRolesApiIntegrationTest.class;
    private static final String ROLES_PATH = "/enterprises/octo-enterprise/enterprise-roles";
    private static final String ROLE_PATH = ROLES_PATH + "/1";
    private static final String ROLE_TEAMS_PATH = ROLE_PATH + "/teams";
    private static final String ROLE_USERS_PATH = ROLE_PATH + "/users";
    private static final String TEAM_ROLES_PATH = ROLES_PATH + "/teams/justice-league";
    private static final String TEAM_ROLE_PATH = TEAM_ROLES_PATH + "/1";
    private static final String USER_ROLES_PATH = ROLES_PATH + "/users/monalisa";
    private static final String USER_ROLE_PATH = USER_ROLES_PATH + "/1";
    private static final String ROLE_JSON = "enterprise-role.json";
    private static final String ROLE_LIST_JSON = "enterprise-role-list.json";
    private static final String TEAM_LIST_JSON = "enterprise-team-list.json";
    private static final String USER_ROLE_LIST_JSON = "enterprise-user-role-assignment-list.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listEnterpriseRoles_shouldReturnRoleList_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ROLES_PATH, okJson(jsonBytes(RESOURCES, ROLE_LIST_JSON)));

        final GitHubEnterpriseRoleList roles;
        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            roles = api.api().listEnterpriseRoles(ENTERPRISE);
        }

        assertThat(roles).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseRoleList());
        HTTP.verifyRequested(GET, ROLES_PATH);
    }

    @Test
    void getEnterpriseRole_shouldReturnRole_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ROLE_PATH, okJson(jsonBytes(RESOURCES, ROLE_JSON)));

        final Optional<GitHubEnterpriseRole> role;
        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            role = api.api().getEnterpriseRole(ENTERPRISE, 1L);
        }

        assertThat(role).contains(EnterpriseAdminCatalogFixtures.enterpriseRole());
        HTTP.verifyRequested(GET, ROLE_PATH);
    }

    @Test
    void getEnterpriseRole_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ROLE_PATH, notFound());

        final Optional<GitHubEnterpriseRole> role;
        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            role = api.api().getEnterpriseRole(ENTERPRISE, 1L);
        }

        assertThat(role).isEmpty();
    }

    @Test
    void listEnterpriseRoleTeams_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ROLE_TEAMS_PATH, okJson(jsonBytes(RESOURCES, TEAM_LIST_JSON)));

        final GitHubPage<GitHubEnterpriseTeam> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            page = api.api().listEnterpriseRoleTeams(ENTERPRISE, 1L);
        }

        assertThat(page.items()).containsExactly(TeamCatalogFixtures.enterpriseTeam());
        HTTP.verifyRequested(GET, ROLE_TEAMS_PATH);
    }

    @Test
    void listEnterpriseRoleUsers_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ROLE_USERS_PATH, okJson(jsonBytes(RESOURCES, USER_ROLE_LIST_JSON)));

        final GitHubPage<GitHubEnterpriseUserRoleAssignment> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            page = api.api().listEnterpriseRoleUsers(ENTERPRISE, 1L);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.enterpriseUserRoleAssignment());
        HTTP.verifyRequested(GET, ROLE_USERS_PATH);
    }

    @Test
    void assignTeamToEnterpriseRole_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, TEAM_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            api.api().assignTeamToEnterpriseRole(ENTERPRISE, "justice-league", 1L);
        }

        HTTP.verifyRequested(PUT, TEAM_ROLE_PATH);
    }

    @Test
    void revokeEnterpriseRoleTeam_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, TEAM_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            api.api().revokeEnterpriseRoleTeam(ENTERPRISE, "justice-league", 1L);
        }

        HTTP.verifyRequested(DELETE, TEAM_ROLE_PATH);
    }

    @Test
    void revokeAllEnterpriseRolesTeam_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, TEAM_ROLES_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            api.api().revokeAllEnterpriseRolesTeam(ENTERPRISE, "justice-league");
        }

        HTTP.verifyRequested(DELETE, TEAM_ROLES_PATH);
    }

    @Test
    void assignEnterpriseRoleToUser_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, USER_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            api.api().assignEnterpriseRoleToUser(ENTERPRISE, "monalisa", 1L);
        }

        HTTP.verifyRequested(PUT, USER_ROLE_PATH);
    }

    @Test
    void removeEnterpriseUserRoleAssignment_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            api.api().removeEnterpriseUserRoleAssignment(ENTERPRISE, "monalisa", 1L);
        }

        HTTP.verifyRequested(DELETE, USER_ROLE_PATH);
    }

    @Test
    void removeAllEnterpriseRolesFromUser_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_ROLES_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminEnterpriseRolesApiClient api =
                enterpriseAdminEnterpriseRolesApi(HTTP)) {
            api.api().removeAllEnterpriseRolesFromUser(ENTERPRISE, "monalisa");
        }

        HTTP.verifyRequested(DELETE, USER_ROLES_PATH);
    }
}
