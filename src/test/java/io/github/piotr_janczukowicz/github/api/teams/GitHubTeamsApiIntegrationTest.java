package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.teamsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubTeamsApiIntegrationTest {

    @Nested
    class Crud {
        private static final String ORG = "octo-org";
        private static final String SLUG = "justice-league";

        private static final String TEAMS_PATH = "/orgs/octo-org/teams";
        private static final String TEAM_PATH = "/orgs/octo-org/teams/justice-league";
        private static final String CHILD_TEAMS_PATH = "/orgs/octo-org/teams/justice-league/teams";

        private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void list_shouldReturnTeams_whenResponseIsOk() throws Exception {
            http.stub(GET, TEAMS_PATH, okJson(listJsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team.json")));

            final GitHubPage<GitHubTeam> page;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                page = teams.api().team().list(ORG);
            }

            final List<GitHubTeam> expected =
                    CODEC.decode(listJsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team.json"), TEAMS_TYPE);

            assertThat(page.items()).containsExactlyElementsOf(expected);
            http.verifyRequested(GET, TEAMS_PATH);
        }

        @Test
        void list_shouldReturnTeams_whenTeamTypeOrganization() throws Exception {
            http.stub(GET, TEAMS_PATH, okJson(listJsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team.json")));

            final GitHubPage<GitHubTeam> page;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                page = teams.api()
                        .team()
                        .list(
                                ORG,
                                GitHubListTeamsFilterBuilder.builder()
                                        .teamType(GitHubTeamListType.ORGANIZATION)
                                        .build(),
                                GitHubPageRequest.byPage());
            }

            final List<GitHubTeam> expected =
                    CODEC.decode(listJsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team.json"), TEAMS_TYPE);

            assertThat(page.items()).containsExactlyElementsOf(expected);
            http.verifyRequested(GET, TEAMS_PATH);
            http.verifyQueryParam(GET, TEAMS_PATH, "team_type", "organization");
        }

        @Test
        void create_shouldReturnTeamFull_whenResponseIsCreated() throws Exception {
            http.stub(
                    POST,
                    TEAMS_PATH,
                    createdJson(jsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team-full.json")));
            final GitHubCreateTeamPayload payload = TeamCatalogFixtures.createTeamPayload();

            final GitHubTeamFull team;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                team = teams.api().team().create(ORG, payload);
            }

            final GitHubTeamFull expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team-full.json"), GitHubTeamFull.class);

            assertThat(team).isEqualTo(expected);
            http.verifyHeader(POST, TEAMS_PATH, CONTENT_TYPE, "application/json");
        }

        @Test
        void getByName_shouldReturnTeamFull_whenResponseIsOk() throws Exception {
            http.stub(GET, TEAM_PATH, okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team-full.json")));

            final Optional<GitHubTeamFull> team;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                team = teams.api().team().getByName(ORG, SLUG);
            }

            final GitHubTeamFull expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team-full.json"), GitHubTeamFull.class);

            assertThat(team).contains(expected);
            http.verifyRequested(GET, TEAM_PATH);
        }

        @Test
        void getByName_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
            http.stub(GET, TEAM_PATH, notFound());

            final Optional<GitHubTeamFull> team;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                team = teams.api().team().getByName(ORG, SLUG);
            }

            assertThat(team).isEmpty();
            http.verifyRequested(GET, TEAM_PATH);
        }

        @Test
        void update_shouldReturnTeamFull_whenResponseIsOk() throws Exception {
            http.stub(PATCH, TEAM_PATH, okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team-full.json")));
            final GitHubUpdateTeamPayload payload = TeamCatalogFixtures.updateTeamPayload();

            final GitHubTeamFull team;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                team = teams.api().team().update(ORG, SLUG, payload);
            }

            final GitHubTeamFull expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team-full.json"), GitHubTeamFull.class);

            assertThat(team).isEqualTo(expected);
            http.verifyHeader(PATCH, TEAM_PATH, CONTENT_TYPE, "application/json");
        }

        @Test
        void delete_shouldSucceed_whenResponseIsNoContent() throws Exception {
            http.stub(DELETE, TEAM_PATH, noContent());

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().team().delete(ORG, SLUG);
            }

            http.verifyRequested(DELETE, TEAM_PATH);
        }

        @Test
        void listChildTeams_shouldReturnTeams_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    CHILD_TEAMS_PATH,
                    okJson(listJsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team.json")));

            final GitHubPage<GitHubTeam> page;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                page = teams.api().team().listChildTeams(ORG, SLUG);
            }

            final List<GitHubTeam> expected =
                    CODEC.decode(listJsonBytes(GitHubTeamsApiIntegrationTest.Crud.class, "team.json"), TEAMS_TYPE);

            assertThat(page.items()).containsExactlyElementsOf(expected);
            http.verifyRequested(GET, CHILD_TEAMS_PATH);
        }
    }

    @Nested
    class Auth {
        private static final String USER_TEAMS_PATH = "/user/teams";

        private static final JsonType<List<GitHubTeamFull>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeamFull.class);

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void listForAuthenticatedUser_shouldReturnTeams_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    USER_TEAMS_PATH,
                    okJson(listJsonBytes(GitHubTeamsApiIntegrationTest.Auth.class, "team-full.json")));

            final GitHubPage<GitHubTeamFull> page;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                page = teams.api().team().listForAuthenticatedUser();
            }

            final List<GitHubTeamFull> expected =
                    CODEC.decode(listJsonBytes(GitHubTeamsApiIntegrationTest.Auth.class, "team-full.json"), TEAMS_TYPE);

            assertThat(page.items()).containsExactlyElementsOf(expected);
            http.verifyRequested(GET, USER_TEAMS_PATH);
        }

        @Test
        void listForAuthenticatedUser_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
            http.stub(GET, USER_TEAMS_PATH, notFound());

            final Throwable thrown;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                thrown = catchThrowable(teams.api().team()::listForAuthenticatedUser);
            }

            assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
            http.verifyRequested(GET, USER_TEAMS_PATH);
        }
    }

    @Nested
    class Membership {
        private static final String ORG = "octo-org";
        private static final String SLUG = "justice-league";
        private static final String USERNAME = "octocat";
        private static final String MISSING_USERNAME = "missing";

        private static final String MEMBERSHIP_PATH = "/orgs/octo-org/teams/justice-league/memberships/octocat";
        private static final String MISSING_MEMBERSHIP_PATH = "/orgs/octo-org/teams/justice-league/memberships/missing";

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void getMembership_shouldReturnMembership_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    MEMBERSHIP_PATH,
                    okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Membership.class, "team-membership.json")));

            final Optional<GitHubTeamMembership> membership;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                membership = teams.api().members().getMembership(ORG, SLUG, USERNAME);
            }

            final GitHubTeamMembership expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Membership.class, "team-membership.json"),
                    GitHubTeamMembership.class);

            assertThat(membership).contains(expected);
            http.verifyRequested(GET, MEMBERSHIP_PATH);
        }

        @Test
        void getMembership_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
            http.stub(GET, MISSING_MEMBERSHIP_PATH, notFound());

            final Optional<GitHubTeamMembership> membership;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                membership = teams.api().members().getMembership(ORG, SLUG, MISSING_USERNAME);
            }

            assertThat(membership).isEmpty();
            http.verifyRequested(GET, MISSING_MEMBERSHIP_PATH);
        }

        @Test
        void upsertMembership_shouldReturnMembership_whenNoBody() throws Exception {
            http.stub(
                    PUT,
                    MEMBERSHIP_PATH,
                    okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Membership.class, "team-membership.json")));

            final GitHubTeamMembership membership;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                membership = teams.api().members().upsertMembership(ORG, SLUG, USERNAME);
            }

            final GitHubTeamMembership expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Membership.class, "team-membership.json"),
                    GitHubTeamMembership.class);

            assertThat(membership).isEqualTo(expected);
            http.verifyRequested(PUT, MEMBERSHIP_PATH);
        }

        @Test
        void upsertMembership_shouldReturnMembership_whenPayloadPresent() throws Exception {
            http.stub(
                    PUT,
                    MEMBERSHIP_PATH,
                    okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Membership.class, "team-membership.json")));
            final GitHubUpsertTeamMembershipPayload payload = TeamCatalogFixtures.upsertTeamMembershipPayload();

            final GitHubTeamMembership membership;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                membership = teams.api().members().upsertMembership(ORG, SLUG, USERNAME, payload);
            }

            final GitHubTeamMembership expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Membership.class, "team-membership.json"),
                    GitHubTeamMembership.class);

            assertThat(membership).isEqualTo(expected);
            http.verifyHeader(PUT, MEMBERSHIP_PATH, CONTENT_TYPE, "application/json");
        }

        @Test
        void removeMembership_shouldSucceed_whenResponseIsNoContent() throws Exception {
            http.stub(DELETE, MEMBERSHIP_PATH, noContent());

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().members().removeMembership(ORG, SLUG, USERNAME);
            }

            http.verifyRequested(DELETE, MEMBERSHIP_PATH);
        }
    }

    @Nested
    class Pagination {
        private static final String ORG = "octo-org";
        private static final String TEAMS_PATH = "/orgs/octo-org/teams";

        private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void list_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
            http.stub(
                    GET,
                    TEAMS_PATH,
                    okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Pagination.class, "teams-one.json"))
                            .header(
                                    LINK,
                                    linkNext(http.uri("/orgs/octo-org/teams?page=2&" + TestClients.perPageQuery()))),
                    okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Pagination.class, "teams-two.json")));

            final GitHubPage<GitHubTeam> first;
            final Optional<GitHubPage<GitHubTeam>> second;
            final Optional<GitHubPage<GitHubTeam>> third;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                first = teams.api().team().list(ORG);
                second = first.next();
                third = second.isPresent() ? second.get().next() : Optional.empty();
            }

            assertSoftly(softly -> {
                softly.assertThat(first.items()).containsExactlyElementsOf(expectedTeams("teams-one.json"));
                softly.assertThat(second).isPresent();
                softly.assertThat(second.orElseThrow().items())
                        .containsExactlyElementsOf(expectedTeams("teams-two.json"));
                softly.assertThat(third).isEmpty();
            });
            http.verifyRequested(GET, TEAMS_PATH, 2);
        }

        private static List<GitHubTeam> expectedTeams(final String file) {
            return CODEC.decode(jsonBytes(GitHubTeamsApiIntegrationTest.Pagination.class, file), TEAMS_TYPE);
        }
    }

    @Nested
    class Repositories {
        private static final String ORG = "github";
        private static final String SLUG = "justice-league";
        private static final String OWNER = "octocat";
        private static final String REPO = "Hello-World";

        private static final String REPOS_PATH = "/orgs/github/teams/justice-league/repos";
        private static final String REPO_PATH = "/orgs/github/teams/justice-league/repos/octocat/Hello-World";

        private static final JsonType<List<GitHubMinimalRepository>> REPOS_TYPE =
                JsonTypes.listOf(GitHubMinimalRepository.class);

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void listRepositories_shouldReturnRepos_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    REPOS_PATH,
                    okJson(listJsonBytes(GitHubTeamsApiIntegrationTest.Repositories.class, "minimal-repository.json")));

            final GitHubPage<GitHubMinimalRepository> page;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                page = teams.api().team().listRepositories(ORG, SLUG);
            }

            final List<GitHubMinimalRepository> expected = CODEC.decode(
                    listJsonBytes(GitHubTeamsApiIntegrationTest.Repositories.class, "minimal-repository.json"),
                    REPOS_TYPE);

            assertThat(page.items()).containsExactlyElementsOf(expected);
            http.verifyRequested(GET, REPOS_PATH);
        }

        @Test
        void hasRepositoryPermission_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
            http.stub(GET, REPO_PATH, noContent());

            final boolean permitted;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                permitted = teams.api().team().hasRepositoryPermission(ORG, SLUG, OWNER, REPO);
            }

            assertThat(permitted).isTrue();
            http.verifyRequested(GET, REPO_PATH);
        }

        @Test
        void hasRepositoryPermission_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
            http.stub(GET, REPO_PATH, notFound());

            final boolean permitted;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                permitted = teams.api().team().hasRepositoryPermission(ORG, SLUG, OWNER, REPO);
            }

            assertThat(permitted).isFalse();
            http.verifyRequested(GET, REPO_PATH);
        }

        @Test
        void getRepositoryPermission_shouldReturnRepository_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    REPO_PATH,
                    okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Repositories.class, "team-repository.json")));

            final Optional<GitHubTeamRepository> repository;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                repository = teams.api().team().getRepositoryPermission(ORG, SLUG, OWNER, REPO);
            }

            final GitHubTeamRepository expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Repositories.class, "team-repository.json"),
                    GitHubTeamRepository.class);

            assertThat(repository).contains(expected);
            http.verifyHeader(GET, REPO_PATH, ACCEPT, "application/vnd.github.v3.repository+json");
        }

        @Test
        void getRepositoryPermission_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
            http.stub(GET, REPO_PATH, notFound());

            final Optional<GitHubTeamRepository> repository;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                repository = teams.api().team().getRepositoryPermission(ORG, SLUG, OWNER, REPO);
            }

            assertThat(repository).isEmpty();
            http.verifyRequested(GET, REPO_PATH);
        }

        @Test
        void upsertRepositoryPermission_shouldSucceed_whenNoBody() throws Exception {
            http.stub(PUT, REPO_PATH, noContent());

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().team().upsertRepositoryPermission(ORG, SLUG, OWNER, REPO);
            }

            http.verifyRequested(PUT, REPO_PATH);
        }

        @Test
        void upsertRepositoryPermission_shouldSucceed_whenPayloadPresent() throws Exception {
            http.stub(PUT, REPO_PATH, noContent());
            final GitHubUpsertTeamRepositoryPermissionPayload payload =
                    TeamCatalogFixtures.upsertTeamRepositoryPermissionPayload();

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().team().upsertRepositoryPermission(ORG, SLUG, OWNER, REPO, payload);
            }

            http.verifyHeader(PUT, REPO_PATH, CONTENT_TYPE, "application/json");
        }

        @Test
        void removeRepository_shouldSucceed_whenResponseIsNoContent() throws Exception {
            http.stub(DELETE, REPO_PATH, noContent());

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().team().removeRepository(ORG, SLUG, OWNER, REPO);
            }

            http.verifyRequested(DELETE, REPO_PATH);
        }
    }

    @Nested
    class Legacy {
        private static final long TEAM_ID = 1L;
        private static final String OWNER = "octocat";
        private static final String REPO = "Hello-World";

        private static final String TEAM_PATH = "/teams/1";
        private static final String REPOS_PATH = "/teams/1/repos";
        private static final String REPO_PATH = "/teams/1/repos/octocat/Hello-World";
        private static final String CHILD_TEAMS_PATH = "/teams/1/teams";

        private static final JsonType<List<GitHubMinimalRepository>> REPOS_TYPE =
                JsonTypes.listOf(GitHubMinimalRepository.class);
        private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void getLegacy_shouldReturnTeamFull_whenResponseIsOk() throws Exception {
            http.stub(GET, TEAM_PATH, okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team-full.json")));

            final Optional<GitHubTeamFull> team;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                team = teams.api().team().getLegacy(TEAM_ID);
            }

            final GitHubTeamFull expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team-full.json"), GitHubTeamFull.class);

            assertThat(team).contains(expected);
            http.verifyRequested(GET, TEAM_PATH);
        }

        @Test
        void updateLegacy_shouldReturnTeamFull_whenResponseIsOk() throws Exception {
            http.stub(
                    PATCH, TEAM_PATH, okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team-full.json")));
            final GitHubUpdateTeamLegacyPayload payload = TeamCatalogFixtures.updateTeamLegacyPayload();

            final GitHubTeamFull team;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                team = teams.api().team().updateLegacy(TEAM_ID, payload);
            }

            final GitHubTeamFull expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team-full.json"), GitHubTeamFull.class);

            assertThat(team).isEqualTo(expected);
            http.verifyHeader(PATCH, TEAM_PATH, CONTENT_TYPE, "application/json");
        }

        @Test
        void deleteLegacy_shouldSucceed_whenResponseIsNoContent() throws Exception {
            http.stub(DELETE, TEAM_PATH, noContent());

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().team().deleteLegacy(TEAM_ID);
            }

            http.verifyRequested(DELETE, TEAM_PATH);
        }

        @Test
        void listRepositoriesLegacy_shouldReturnRepos_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    REPOS_PATH,
                    okJson(listJsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "minimal-repository.json")));

            final GitHubPage<GitHubMinimalRepository> page;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                page = teams.api().team().listRepositoriesLegacy(TEAM_ID);
            }

            final List<GitHubMinimalRepository> expected = CODEC.decode(
                    listJsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "minimal-repository.json"), REPOS_TYPE);

            assertThat(page.items()).containsExactlyElementsOf(expected);
            http.verifyRequested(GET, REPOS_PATH);
        }

        @Test
        void hasRepositoryPermissionLegacy_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
            http.stub(GET, REPO_PATH, noContent());

            final boolean permitted;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                permitted = teams.api().team().hasRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);
            }

            assertThat(permitted).isTrue();
            http.verifyRequested(GET, REPO_PATH);
        }

        @Test
        void getRepositoryPermissionLegacy_shouldReturnRepository_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    REPO_PATH,
                    okJson(jsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team-repository.json")));

            final Optional<GitHubTeamRepository> repository;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                repository = teams.api().team().getRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);
            }

            final GitHubTeamRepository expected = CODEC.decode(
                    jsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team-repository.json"),
                    GitHubTeamRepository.class);

            assertThat(repository).contains(expected);
            http.verifyHeader(GET, REPO_PATH, ACCEPT, "application/vnd.github.v3.repository+json");
        }

        @Test
        void upsertRepositoryPermissionLegacy_shouldSucceed_whenNoBody() throws Exception {
            http.stub(PUT, REPO_PATH, noContent());

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().team().upsertRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);
            }

            http.verifyRequested(PUT, REPO_PATH);
        }

        @Test
        void removeRepositoryLegacy_shouldSucceed_whenResponseIsNoContent() throws Exception {
            http.stub(DELETE, REPO_PATH, noContent());

            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                teams.api().team().removeRepositoryLegacy(TEAM_ID, OWNER, REPO);
            }

            http.verifyRequested(DELETE, REPO_PATH);
        }

        @Test
        void listChildTeamsLegacy_shouldReturnTeams_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    CHILD_TEAMS_PATH,
                    okJson(listJsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team.json")));

            final GitHubPage<GitHubTeam> page;
            try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(http)) {
                page = teams.api().team().listChildTeamsLegacy(TEAM_ID);
            }

            final List<GitHubTeam> expected =
                    CODEC.decode(listJsonBytes(GitHubTeamsApiIntegrationTest.Legacy.class, "team.json"), TEAMS_TYPE);

            assertThat(page.items()).containsExactlyElementsOf(expected);
            http.verifyRequested(GET, CHILD_TEAMS_PATH);
        }
    }
}
