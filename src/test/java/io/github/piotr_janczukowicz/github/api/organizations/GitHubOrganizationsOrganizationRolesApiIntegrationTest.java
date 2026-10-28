package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsOrganizationRolesApiIntegrationTest {

    private static final JsonType<List<GitHubTeamRoleAssignment>> TEAM_ROLE_ASSIGNMENTS_TYPE =
            JsonTypes.listOf(GitHubTeamRoleAssignment.class);
    private static final JsonType<List<GitHubUserRoleAssignment>> USER_ROLE_ASSIGNMENTS_TYPE =
            JsonTypes.listOf(GitHubUserRoleAssignment.class);

    private static final String ORG = "github";
    private static final long ROLE_ID = 8030L;
    private static final String TEAM_SLUG = "justice-league";
    private static final String USERNAME = "octocat";

    private static final String ORGANIZATION_ROLES_PATH = "/orgs/github/organization-roles";
    private static final String ORGANIZATION_ROLE_PATH = "/orgs/github/organization-roles/8030";
    private static final String ORGANIZATION_ROLE_MISSING_PATH = "/orgs/github/organization-roles/9999";
    private static final String ORGANIZATION_ROLE_TEAMS_PATH = "/orgs/github/organization-roles/8030/teams";
    private static final String ORGANIZATION_ROLE_USERS_PATH = "/orgs/github/organization-roles/8030/users";
    private static final String TEAM_ROLES_PATH = "/orgs/github/organization-roles/teams/justice-league";
    private static final String TEAM_ROLE_PATH = "/orgs/github/organization-roles/teams/justice-league/8030";
    private static final String USER_ROLES_PATH = "/orgs/github/organization-roles/users/octocat";
    private static final String USER_ROLE_PATH = "/orgs/github/organization-roles/users/octocat/8030";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnOrganizationRolesPage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORGANIZATION_ROLES_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsOrganizationRolesApiIntegrationTest.class, "organization-roles-page.json")));

        final GitHubOrganizationRolesPage page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().organizationRoles().list(ORG);
        }

        final GitHubOrganizationRolesPage expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsOrganizationRolesApiIntegrationTest.class, "organization-roles-page.json"),
                GitHubOrganizationRolesPage.class);

        assertThat(page).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORGANIZATION_ROLES_PATH);
    }

    @Test
    void revokeAllForTeam_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, TEAM_ROLES_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organizationRoles().revokeAllForTeam(ORG, TEAM_SLUG);
        }

        HTTP.verifyRequested(DELETE, TEAM_ROLES_PATH);
    }

    @Test
    void assignTeam_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, TEAM_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organizationRoles().assignTeam(ORG, TEAM_SLUG, ROLE_ID);
        }

        HTTP.verifyRequested(PUT, TEAM_ROLE_PATH);
    }

    @Test
    void revokeTeam_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, TEAM_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organizationRoles().revokeTeam(ORG, TEAM_SLUG, ROLE_ID);
        }

        HTTP.verifyRequested(DELETE, TEAM_ROLE_PATH);
    }

    @Test
    void revokeAllForUser_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_ROLES_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organizationRoles().revokeAllForUser(ORG, USERNAME);
        }

        HTTP.verifyRequested(DELETE, USER_ROLES_PATH);
    }

    @Test
    void assignUser_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, USER_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organizationRoles().assignUser(ORG, USERNAME, ROLE_ID);
        }

        HTTP.verifyRequested(PUT, USER_ROLE_PATH);
    }

    @Test
    void revokeUser_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_ROLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organizationRoles().revokeUser(ORG, USERNAME, ROLE_ID);
        }

        HTTP.verifyRequested(DELETE, USER_ROLE_PATH);
    }

    @Test
    void get_shouldReturnOrganizationRole_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORGANIZATION_ROLE_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsOrganizationRolesApiIntegrationTest.class, "organization-role.json")));

        final Optional<GitHubOrganizationRole> role;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            role = organizations.api().organizationRoles().get(ORG, ROLE_ID);
        }

        final GitHubOrganizationRole expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsOrganizationRolesApiIntegrationTest.class, "organization-role.json"),
                GitHubOrganizationRole.class);

        assertThat(role).contains(expected);
        HTTP.verifyRequested(GET, ORGANIZATION_ROLE_PATH);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORGANIZATION_ROLE_MISSING_PATH, notFound());

        final Optional<GitHubOrganizationRole> role;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            role = organizations.api().organizationRoles().get(ORG, 9999L);
        }

        assertThat(role).isEmpty();
        HTTP.verifyRequested(GET, ORGANIZATION_ROLE_MISSING_PATH);
    }

    @Test
    void listTeams_shouldReturnTeamRoleAssignments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORGANIZATION_ROLE_TEAMS_PATH,
                okJson(listJsonBytes(GitHubTeamRoleAssignment.class, "team-role-assignment.json")));

        final GitHubPage<GitHubTeamRoleAssignment> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().organizationRoles().listTeams(ORG, ROLE_ID);
        }

        final List<GitHubTeamRoleAssignment> expected = CODEC.decode(
                listJsonBytes(GitHubTeamRoleAssignment.class, "team-role-assignment.json"), TEAM_ROLE_ASSIGNMENTS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ORGANIZATION_ROLE_TEAMS_PATH);
        HTTP.verifyQueryParam(GET, ORGANIZATION_ROLE_TEAMS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORGANIZATION_ROLE_TEAMS_PATH, "per_page", "100");
    }

    @Test
    void listUsers_shouldReturnUserRoleAssignments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORGANIZATION_ROLE_USERS_PATH,
                okJson(listJsonBytes(GitHubUserRoleAssignment.class, "user-role-assignment.json")));

        final GitHubPage<GitHubUserRoleAssignment> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().organizationRoles().listUsers(ORG, ROLE_ID);
        }

        final List<GitHubUserRoleAssignment> expected = CODEC.decode(
                listJsonBytes(GitHubUserRoleAssignment.class, "user-role-assignment.json"), USER_ROLE_ASSIGNMENTS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ORGANIZATION_ROLE_USERS_PATH);
        HTTP.verifyQueryParam(GET, ORGANIZATION_ROLE_USERS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORGANIZATION_ROLE_USERS_PATH, "per_page", "100");
    }
}
