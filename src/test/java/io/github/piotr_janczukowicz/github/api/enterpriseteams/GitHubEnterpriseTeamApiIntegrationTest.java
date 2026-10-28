package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseTeamApi;
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
class GitHubEnterpriseTeamApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String TEAMS_PATH = "/enterprises/octo-enterprise/teams";
    private static final String TEAM_PATH = "/enterprises/octo-enterprise/teams/justice-league";
    private static final String TEAM_SLUG = "justice-league";
    private static final String TEAM_JSON = "enterprise-team.json";
    private static final String TEAM_LIST_JSON = "enterprise-team-list.json";
    private static final String TEAM_WITH_MEMBER_COUNT_JSON = "enterprise-team-with-member-count.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listEnterpriseTeams_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TEAMS_PATH, okJson(jsonBytes(GitHubEnterpriseTeamApiIntegrationTest.class, TEAM_LIST_JSON)));

        final GitHubPage<GitHubEnterpriseTeam> page;
        try (final GitHubApiWireMockSupport.EnterpriseTeamApiClient api = enterpriseTeamApi(HTTP)) {
            page = api.api().listEnterpriseTeams(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(TeamCatalogFixtures.enterpriseTeam());
        HTTP.verifyRequested(GET, TEAMS_PATH);
    }

    @Test
    void createEnterpriseTeam_shouldReturnTeam_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, TEAMS_PATH, createdJson(jsonBytes(GitHubEnterpriseTeamApiIntegrationTest.class, TEAM_JSON)));

        final GitHubEnterpriseTeam team;
        try (final GitHubApiWireMockSupport.EnterpriseTeamApiClient api = enterpriseTeamApi(HTTP)) {
            team = api.api()
                    .createEnterpriseTeam(ENTERPRISE, EnterpriseTeamsCatalogFixtures.createEnterpriseTeamPayload());
        }

        assertThat(team).isEqualTo(TeamCatalogFixtures.enterpriseTeam());
        HTTP.verifyRequested(POST, TEAMS_PATH);
    }

    @Test
    void getEnterpriseTeam_shouldReturnTeamWithMemberCount_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                TEAM_PATH,
                okJson(jsonBytes(GitHubEnterpriseTeamApiIntegrationTest.class, TEAM_WITH_MEMBER_COUNT_JSON)));

        final Optional<GitHubEnterpriseTeamWithMemberCount> team;
        try (final GitHubApiWireMockSupport.EnterpriseTeamApiClient api = enterpriseTeamApi(HTTP)) {
            team = api.api().getEnterpriseTeam(ENTERPRISE, TEAM_SLUG);
        }

        assertThat(team).contains(EnterpriseTeamsCatalogFixtures.enterpriseTeamWithMemberCount());
        HTTP.verifyRequested(GET, TEAM_PATH);
    }

    @Test
    void getEnterpriseTeam_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, TEAM_PATH, notFound());

        final Optional<GitHubEnterpriseTeamWithMemberCount> team;
        try (final GitHubApiWireMockSupport.EnterpriseTeamApiClient api = enterpriseTeamApi(HTTP)) {
            team = api.api().getEnterpriseTeam(ENTERPRISE, TEAM_SLUG);
        }

        assertThat(team).isEmpty();
    }

    @Test
    void updateEnterpriseTeam_shouldReturnTeam_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, TEAM_PATH, okJson(jsonBytes(GitHubEnterpriseTeamApiIntegrationTest.class, TEAM_JSON)));

        final GitHubEnterpriseTeam team;
        try (final GitHubApiWireMockSupport.EnterpriseTeamApiClient api = enterpriseTeamApi(HTTP)) {
            team = api.api()
                    .updateEnterpriseTeam(
                            ENTERPRISE, TEAM_SLUG, EnterpriseTeamsCatalogFixtures.updateEnterpriseTeamPayload());
        }

        assertThat(team).isEqualTo(TeamCatalogFixtures.enterpriseTeam());
        HTTP.verifyRequested(PATCH, TEAM_PATH);
    }

    @Test
    void deleteEnterpriseTeam_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, TEAM_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseTeamApiClient api = enterpriseTeamApi(HTTP)) {
            api.api().deleteEnterpriseTeam(ENTERPRISE, TEAM_SLUG);
        }

        HTTP.verifyRequested(DELETE, TEAM_PATH);
    }
}
