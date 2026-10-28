package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsSecurityManagersApiIntegrationTest {

    private static final JsonType<List<GitHubTeamSimple>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeamSimple.class);

    private static final String ORG = "github";
    private static final String TEAM_SLUG = "justice-league";
    private static final String SECURITY_MANAGERS_PATH = "/orgs/github/security-managers";
    private static final String SECURITY_MANAGER_TEAM_PATH = "/orgs/github/security-managers/teams/justice-league";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnTeams_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SECURITY_MANAGERS_PATH, okJson(listJsonBytes(GitHubTeamSimple.class, "team-simple.json")));

        final List<GitHubTeamSimple> teams;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            teams = organizations.api().securityManagers().list(ORG);
        }

        final List<GitHubTeamSimple> expected =
                CODEC.decode(listJsonBytes(GitHubTeamSimple.class, "team-simple.json"), TEAMS_TYPE);

        assertThat(teams).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, SECURITY_MANAGERS_PATH);
    }

    @Test
    void addTeam_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, SECURITY_MANAGER_TEAM_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().securityManagers().addTeam(ORG, TEAM_SLUG);
        }

        HTTP.verifyRequested(PUT, SECURITY_MANAGER_TEAM_PATH);
    }

    @Test
    void removeTeam_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, SECURITY_MANAGER_TEAM_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().securityManagers().removeTeam(ORG, TEAM_SLUG);
        }

        HTTP.verifyRequested(DELETE, SECURITY_MANAGER_TEAM_PATH);
    }
}
