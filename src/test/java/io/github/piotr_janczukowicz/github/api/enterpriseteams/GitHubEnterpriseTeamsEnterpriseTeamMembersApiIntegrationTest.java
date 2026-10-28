package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseTeamMembersApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.teams.GitHubEnterpriseTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseTeamsEnterpriseTeamMembersApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseTeamsEnterpriseTeamMembersApiIntegrationTest.class;
    private static final String TEAM = "justice-league";
    private static final String USERNAME = "octocat";
    private static final String USER_TEAMS_PATH = "/enterprises/octo-enterprise/members/octocat/teams";
    private static final String MEMBERSHIPS_PATH = "/enterprises/octo-enterprise/teams/justice-league/memberships";
    private static final String MEMBERSHIPS_ADD_PATH = MEMBERSHIPS_PATH + "/add";
    private static final String MEMBERSHIPS_REMOVE_PATH = MEMBERSHIPS_PATH + "/remove";
    private static final String MEMBERSHIP_PATH = MEMBERSHIPS_PATH + "/octocat";
    private static final String TEAM_LIST_JSON = "enterprise-team-list.json";
    private static final String USER_JSON = "simple-user-octocat.json";
    private static final String USER_LIST_JSON = "simple-user-list.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listTeamsForUser_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_TEAMS_PATH, okJson(jsonBytes(RESOURCES, TEAM_LIST_JSON)));

        final GitHubPage<GitHubEnterpriseTeam> page;
        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            page = api.api().listTeamsForUser(ENTERPRISE, USERNAME);
        }

        assertThat(page.items()).containsExactly(TeamCatalogFixtures.enterpriseTeam());
        HTTP.verifyRequested(GET, USER_TEAMS_PATH);
    }

    @Test
    void listMembers_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MEMBERSHIPS_PATH, okJson(jsonBytes(RESOURCES, USER_LIST_JSON)));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            page = api.api().listMembers(ENTERPRISE, TEAM);
        }

        assertThat(page.items()).containsExactly(UsersCatalogFixtures.octocat());
        HTTP.verifyRequested(GET, MEMBERSHIPS_PATH);
    }

    @Test
    void bulkAddMembers_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, MEMBERSHIPS_ADD_PATH, okJson(jsonBytes(RESOURCES, USER_LIST_JSON)));

        final List<GitHubSimpleUser> users;
        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            users = api.api()
                    .bulkAddMembers(
                            ENTERPRISE, TEAM, EnterpriseTeamsCatalogFixtures.bulkAddEnterpriseTeamMembershipsPayload());
        }

        assertThat(users).containsExactly(UsersCatalogFixtures.octocat());
        HTTP.verifyRequested(POST, MEMBERSHIPS_ADD_PATH);
    }

    @Test
    void bulkRemoveMembers_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, MEMBERSHIPS_REMOVE_PATH, okJson(jsonBytes(RESOURCES, USER_LIST_JSON)));

        final List<GitHubSimpleUser> users;
        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            users = api.api()
                    .bulkRemoveMembers(
                            ENTERPRISE,
                            TEAM,
                            EnterpriseTeamsCatalogFixtures.bulkRemoveEnterpriseTeamMembershipsPayload());
        }

        assertThat(users).containsExactly(UsersCatalogFixtures.octocat());
        HTTP.verifyRequested(POST, MEMBERSHIPS_REMOVE_PATH);
    }

    @Test
    void getMembership_shouldReturnUser_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MEMBERSHIP_PATH, okJson(jsonBytes(RESOURCES, USER_JSON)));

        final Optional<GitHubSimpleUser> user;
        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            user = api.api().getMembership(ENTERPRISE, TEAM, USERNAME);
        }

        assertThat(user).contains(UsersCatalogFixtures.octocat());
        HTTP.verifyRequested(GET, MEMBERSHIP_PATH);
    }

    @Test
    void getMembership_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MEMBERSHIP_PATH, notFound());

        final Optional<GitHubSimpleUser> user;
        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            user = api.api().getMembership(ENTERPRISE, TEAM, USERNAME);
        }

        assertThat(user).isEmpty();
    }

    @Test
    void addMembership_shouldReturnUser_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, MEMBERSHIP_PATH, createdJson(jsonBytes(RESOURCES, USER_JSON)));

        final GitHubSimpleUser user;
        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            user = api.api().addMembership(ENTERPRISE, TEAM, USERNAME);
        }

        assertThat(user).isEqualTo(UsersCatalogFixtures.octocat());
        HTTP.verifyRequested(PUT, MEMBERSHIP_PATH);
    }

    @Test
    void removeMembership_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, MEMBERSHIP_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseTeamMembersApiClient api = enterpriseTeamMembersApi(HTTP)) {
            api.api().removeMembership(ENTERPRISE, TEAM, USERNAME);
        }

        HTTP.verifyRequested(DELETE, MEMBERSHIP_PATH);
    }
}
