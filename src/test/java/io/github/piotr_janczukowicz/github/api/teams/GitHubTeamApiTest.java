package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubTeamApiTest {

    private static final String ORG = "github";
    private static final String SLUG = "justice-league";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long TEAM_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTeamApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json") + "]"));

        final GitHubPage<GitHubTeam> page = new GitHubTeamApi(TestClients.caller(transport)).list(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void list_shouldSendTeamTypeQuery_whenTeamTypeProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json") + "]"));

        new GitHubTeamApi(TestClients.caller(transport))
                .list(
                        ORG,
                        GitHubListTeamsFilterBuilder.builder()
                                .teamType(GitHubTeamListType.ORGANIZATION)
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("team_type", "organization");
    }

    @Test
    void list_shouldDefaultToByPage_whenTeamTypeFilterOverloadOmitsRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json") + "]"));

        new GitHubTeamApi(TestClients.caller(transport))
                .list(
                        ORG,
                        GitHubListTeamsFilterBuilder.builder()
                                .teamType(GitHubTeamListType.ENTERPRISE)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("team_type", "enterprise");
            softly.assertThat(requestUri).hasParameter("page", "1");
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void list_shouldOmitPageQuery_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json") + "]"));

        final GitHubPage<GitHubTeam> page =
                new GitHubTeamApi(TestClients.caller(transport)).list(ORG, GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri).hasToString("https://api.github.com/orgs/github/teams");
            softly.assertThat(requestUri.getQuery()).isNull();
        });
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(final TeamsCall call, final String messageFragment) {
        final GitHubTeamApi teamsApi = new GitHubTeamApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(teamsApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @Test
    void create_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("team-full.json")));
        final GitHubCreateTeamPayload payload = TeamCatalogFixtures.createTeamPayload();

        final GitHubTeamFull team = new GitHubTeamApi(TestClients.caller(transport)).create(ORG, payload);
        final HttpRequest request = captureSingleRequest();
        final GitHubTeamFull expected = TeamCatalogFixtures.teamFull();

        assertSoftly(softly -> {
            softly.assertThat(team).isEqualTo(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/teams");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void getByName_shouldReturnTeamFull_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-full.json")));

        final Optional<GitHubTeamFull> team = new GitHubTeamApi(TestClients.caller(transport)).getByName(ORG, SLUG);
        final URI requestUri = captureSingleRequest().uri();
        final GitHubTeamFull expected = TeamCatalogFixtures.teamFull();

        assertSoftly(softly -> {
            softly.assertThat(team).contains(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/orgs/github/teams/justice-league");
        });
    }

    @Test
    void getByName_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubTeamFull> team = new GitHubTeamApi(TestClients.caller(transport)).getByName(ORG, SLUG);

        assertThat(team).isEmpty();
    }

    @Test
    void update_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-full.json")));
        final GitHubUpdateTeamPayload payload = TeamCatalogFixtures.updateTeamPayload();

        final GitHubTeamFull team = new GitHubTeamApi(TestClients.caller(transport)).update(ORG, SLUG, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(team).isEqualTo(TeamCatalogFixtures.teamFull());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/teams/justice-league");
        });
    }

    @Test
    void delete_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamApi(TestClients.caller(transport)).delete(ORG, SLUG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/teams/justice-league");
        });
    }

    @Test
    void listRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        final GitHubPage<GitHubMinimalRepository> page =
                new GitHubTeamApi(TestClients.caller(transport)).listRepositories(ORG, SLUG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/repos?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listChildTeams_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json") + "]"));

        final GitHubPage<GitHubTeam> page = new GitHubTeamApi(TestClients.caller(transport)).listChildTeams(ORG, SLUG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/teams?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void hasRepositoryPermission_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean permitted =
                new GitHubTeamApi(TestClients.caller(transport)).hasRepositoryPermission(ORG, SLUG, OWNER, REPO);

        assertThat(permitted).isTrue();
    }

    @Test
    void hasRepositoryPermission_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean permitted =
                new GitHubTeamApi(TestClients.caller(transport)).hasRepositoryPermission(ORG, SLUG, OWNER, REPO);

        assertThat(permitted).isFalse();
    }

    @Test
    void getRepositoryPermission_shouldDecodeFixture_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-repository.json")));

        final Optional<GitHubTeamRepository> repository =
                new GitHubTeamApi(TestClients.caller(transport)).getRepositoryPermission(ORG, SLUG, OWNER, REPO);
        final HttpRequest request = captureSingleRequest();
        final GitHubTeamRepository expected = TeamCatalogFixtures.teamRepository();

        assertSoftly(softly -> {
            softly.assertThat(repository).contains(expected);
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.v3.repository+json");
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/repos/octocat/Hello-World");
        });
    }

    @Test
    void getRepositoryPermission_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubTeamRepository> repository =
                new GitHubTeamApi(TestClients.caller(transport)).getRepositoryPermission(ORG, SLUG, OWNER, REPO);

        assertThat(repository).isEmpty();
    }

    @Test
    void upsertRepositoryPermission_shouldSendPutWithoutBody_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamApi(TestClients.caller(transport)).upsertRepositoryPermission(ORG, SLUG, OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/repos/octocat/Hello-World");
        });
    }

    @Test
    void upsertRepositoryPermission_shouldSendPutWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubUpsertTeamRepositoryPermissionPayload payload =
                TeamCatalogFixtures.upsertTeamRepositoryPermissionPayload();

        new GitHubTeamApi(TestClients.caller(transport)).upsertRepositoryPermission(ORG, SLUG, OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"permission\":\"push\"}");
        });
    }

    @Test
    void removeRepository_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamApi(TestClients.caller(transport)).removeRepository(ORG, SLUG, OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/repos/octocat/Hello-World");
        });
    }

    @Test
    void getLegacy_shouldReturnTeamFull_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-full.json")));

        final Optional<GitHubTeamFull> team = new GitHubTeamApi(TestClients.caller(transport)).getLegacy(TEAM_ID);
        final URI requestUri = captureSingleRequest().uri();
        final GitHubTeamFull expected = TeamCatalogFixtures.teamFull();

        assertSoftly(softly -> {
            softly.assertThat(team).contains(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/teams/1");
        });
    }

    @Test
    void getLegacy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubTeamFull> team = new GitHubTeamApi(TestClients.caller(transport)).getLegacy(TEAM_ID);

        assertThat(team).isEmpty();
    }

    @Test
    void updateLegacy_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-full.json")));
        final GitHubUpdateTeamLegacyPayload payload = TeamCatalogFixtures.updateTeamLegacyPayload();

        final GitHubTeamFull team = new GitHubTeamApi(TestClients.caller(transport)).updateLegacy(TEAM_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(team).isEqualTo(TeamCatalogFixtures.teamFull());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1");
        });
    }

    @Test
    void deleteLegacy_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamApi(TestClients.caller(transport)).deleteLegacy(TEAM_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1");
        });
    }

    @Test
    void listRepositoriesLegacy_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        final GitHubPage<GitHubMinimalRepository> page =
                new GitHubTeamApi(TestClients.caller(transport)).listRepositoriesLegacy(TEAM_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/teams/1/repos?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void hasRepositoryPermissionLegacy_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean permitted =
                new GitHubTeamApi(TestClients.caller(transport)).hasRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);

        assertThat(permitted).isTrue();
    }

    @Test
    void hasRepositoryPermissionLegacy_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean permitted =
                new GitHubTeamApi(TestClients.caller(transport)).hasRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);

        assertThat(permitted).isFalse();
    }

    @Test
    void getRepositoryPermissionLegacy_shouldDecodeFixture_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-repository.json")));

        final Optional<GitHubTeamRepository> repository =
                new GitHubTeamApi(TestClients.caller(transport)).getRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);
        final HttpRequest request = captureSingleRequest();
        final GitHubTeamRepository expected = TeamCatalogFixtures.teamRepository();

        assertSoftly(softly -> {
            softly.assertThat(repository).contains(expected);
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.v3.repository+json");
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/repos/octocat/Hello-World");
        });
    }

    @Test
    void getRepositoryPermissionLegacy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubTeamRepository> repository =
                new GitHubTeamApi(TestClients.caller(transport)).getRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);

        assertThat(repository).isEmpty();
    }

    @Test
    void upsertRepositoryPermissionLegacy_shouldSendPutWithoutBody_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamApi(TestClients.caller(transport)).upsertRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/repos/octocat/Hello-World");
        });
    }

    @Test
    void upsertRepositoryPermissionLegacy_shouldSendPutWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubUpsertTeamRepositoryPermissionLegacyPayload payload =
                GitHubUpsertTeamRepositoryPermissionLegacyPayloadBuilder.builder()
                        .permission(GitHubTeamLegacyPermission.PUSH)
                        .build();

        new GitHubTeamApi(TestClients.caller(transport))
                .upsertRepositoryPermissionLegacy(TEAM_ID, OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"permission\":\"push\"}");
        });
    }

    @Test
    void removeRepositoryLegacy_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamApi(TestClients.caller(transport)).removeRepositoryLegacy(TEAM_ID, OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/repos/octocat/Hello-World");
        });
    }

    @Test
    void listChildTeamsLegacy_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json") + "]"));

        final GitHubPage<GitHubTeam> page =
                new GitHubTeamApi(TestClients.caller(transport)).listChildTeamsLegacy(TEAM_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/teams/1/teams?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForAuthenticatedUser_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-full.json") + "]"));

        final GitHubPage<GitHubTeamFull> page =
                new GitHubTeamApi(TestClients.caller(transport)).listForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.teamFull());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/user/teams?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForAuthenticatedUser_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubTeamApi teamsApi = new GitHubTeamApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(teamsApi::listForAuthenticatedUser);

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void validation_shouldThrow_whenOrgBlank(final TeamsCall call) {
        final GitHubTeamApi teamsApi = new GitHubTeamApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(teamsApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @Test
    void getByName_shouldThrow_whenSlugBlank() {
        final GitHubTeamApi teamsApi = new GitHubTeamApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> teamsApi.getByName(ORG, " "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{team_slug}");
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubTeamApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static List<TeamsCall> blankOrgCalls() {
        final GitHubCreateTeamPayload createPayload = TeamCatalogFixtures.createTeamPayload();
        final GitHubUpdateTeamPayload updatePayload = TeamCatalogFixtures.updateTeamPayload();
        final GitHubUpsertTeamRepositoryPermissionPayload repoPayload =
                TeamCatalogFixtures.upsertTeamRepositoryPermissionPayload();
        return List.of(
                api -> api.list(" "),
                api -> api.list(" ", GitHubPageRequest.byPage()),
                api -> api.list(
                        " ",
                        GitHubListTeamsFilterBuilder.builder()
                                .teamType(GitHubTeamListType.ORGANIZATION)
                                .build(),
                        GitHubPageRequest.byPage()),
                api -> api.create(" ", createPayload),
                api -> api.getByName(" ", SLUG),
                api -> api.update(" ", SLUG, updatePayload),
                api -> api.delete(" ", SLUG),
                api -> api.listRepositories(" ", SLUG),
                api -> api.hasRepositoryPermission(" ", SLUG, OWNER, REPO),
                api -> api.getRepositoryPermission(" ", SLUG, OWNER, REPO),
                api -> api.upsertRepositoryPermission(" ", SLUG, OWNER, REPO),
                api -> api.upsertRepositoryPermission(" ", SLUG, OWNER, REPO, repoPayload),
                api -> api.removeRepository(" ", SLUG, OWNER, REPO),
                api -> api.listChildTeams(" ", SLUG));
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursor = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (TeamsCall) api -> api.list(ORG, unsupportedSince), "unsupported pagination request: since"),
                Arguments.of(
                        (TeamsCall) api -> api.list(ORG, unsupportedCursor), "unsupported pagination request: cursor"),
                Arguments.of(
                        (TeamsCall) api -> api.list(
                                ORG,
                                GitHubListTeamsFilterBuilder.builder()
                                        .teamType(GitHubTeamListType.ORGANIZATION)
                                        .build(),
                                unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (TeamsCall) api -> api.list(
                                ORG,
                                GitHubListTeamsFilterBuilder.builder()
                                        .teamType(GitHubTeamListType.ORGANIZATION)
                                        .build(),
                                unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (TeamsCall) api -> api.listRepositories(ORG, SLUG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (TeamsCall) api -> api.listRepositories(ORG, SLUG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (TeamsCall) api -> api.listChildTeams(ORG, SLUG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (TeamsCall) api -> api.listChildTeams(ORG, SLUG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (TeamsCall) api -> api.listRepositoriesLegacy(TEAM_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (TeamsCall) api -> api.listRepositoriesLegacy(TEAM_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (TeamsCall) api -> api.listChildTeamsLegacy(TEAM_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (TeamsCall) api -> api.listChildTeamsLegacy(TEAM_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (TeamsCall) api -> api.listForAuthenticatedUser(unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (TeamsCall) api -> api.listForAuthenticatedUser(unsupportedCursor),
                        "unsupported pagination request: cursor"));
    }

    @FunctionalInterface
    interface TeamsCall {
        void execute(GitHubTeamApi api) throws Exception;
    }
}
