package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubTeamsTeamSyncApiTest {

    private static final String ORG = "github";
    private static final String SLUG = "justice-league";
    private static final long TEAM_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTeamsTeamSyncApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listIdpGroups_shouldReturnGroups_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("group-mapping.json")));

        final GitHubPage<GitHubGroupMappingGroup> page =
                new GitHubTeamsTeamSyncApi(TestClients.caller(transport)).listIdpGroups(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.groupMappingGroup());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/github/team-sync/groups?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listIdpGroups_shouldSendQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("group-mapping.json")));

        new GitHubTeamsTeamSyncApi(TestClients.caller(transport))
                .listIdpGroups(ORG, TeamCatalogFixtures.listIdpGroupsForOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("q", "saml-azuread-test");
    }

    @Test
    void listIdpGroups_shouldOmitPageQuery_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("group-mapping.json")));

        new GitHubTeamsTeamSyncApi(TestClients.caller(transport)).listIdpGroups(ORG, GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).isNull();
    }

    @Test
    void listIdpGroups_shouldThrow_whenFilterNull() {
        final GitHubTeamsTeamSyncApi api = new GitHubTeamsTeamSyncApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.listIdpGroups(ORG, null, GitHubPageRequest.byPage()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void listIdpGroups_shouldThrow_whenUnsupportedPagination() {
        final GitHubTeamsTeamSyncApi api = new GitHubTeamsTeamSyncApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.listIdpGroups(ORG, GitHubPageRequest.bySince()));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unsupported pagination request: since");
    }

    @Test
    void listTeamIdpGroups_shouldReturnGroupMapping_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("group-mapping.json")));

        final Optional<GitHubGroupMapping> mapping =
                new GitHubTeamsTeamSyncApi(TestClients.caller(transport)).listTeamIdpGroups(ORG, SLUG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(mapping).contains(TeamCatalogFixtures.groupMapping());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/team-sync/group-mappings");
        });
    }

    @Test
    void listTeamIdpGroups_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGroupMapping> mapping =
                new GitHubTeamsTeamSyncApi(TestClients.caller(transport)).listTeamIdpGroups(ORG, SLUG);

        assertThat(mapping).isEmpty();
    }

    @Test
    void createOrUpdateIdpGroupConnections_shouldSendPatchWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("group-mapping.json")));
        final GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload payload =
                TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsInOrgPayload();

        final GitHubGroupMapping mapping = new GitHubTeamsTeamSyncApi(TestClients.caller(transport))
                .createOrUpdateIdpGroupConnections(ORG, SLUG, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(mapping).isEqualTo(TeamCatalogFixtures.groupMapping());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"group_name\":\"saml-azuread-test\"");
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/team-sync/group-mappings");
        });
    }

    @Test
    void createOrUpdateIdpGroupConnections_shouldThrow_whenPayloadNull() {
        final GitHubTeamsTeamSyncApi api = new GitHubTeamsTeamSyncApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.createOrUpdateIdpGroupConnections(ORG, SLUG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void listTeamIdpGroupsLegacy_shouldReturnGroupMapping_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("group-mapping.json")));

        final Optional<GitHubGroupMapping> mapping =
                new GitHubTeamsTeamSyncApi(TestClients.caller(transport)).listTeamIdpGroupsLegacy(TEAM_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(mapping).contains(TeamCatalogFixtures.groupMapping());
            softly.assertThat(requestUri).hasToString("https://api.github.com/teams/1/team-sync/group-mappings");
        });
    }

    @Test
    void listTeamIdpGroupsLegacy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGroupMapping> mapping =
                new GitHubTeamsTeamSyncApi(TestClients.caller(transport)).listTeamIdpGroupsLegacy(TEAM_ID);

        assertThat(mapping).isEmpty();
    }

    @Test
    void createOrUpdateIdpGroupConnectionsLegacy_shouldSendPatchWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("group-mapping.json")));
        final GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload payload =
                TeamCatalogFixtures.createOrUpdateIdpGroupConnectionsLegacyPayload();

        final GitHubGroupMapping mapping = new GitHubTeamsTeamSyncApi(TestClients.caller(transport))
                .createOrUpdateIdpGroupConnectionsLegacy(TEAM_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(mapping).isEqualTo(TeamCatalogFixtures.groupMapping());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"synced_at\":\"2021-01-03T22:27:15Z\"");
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/team-sync/group-mappings");
        });
    }

    @Test
    void createOrUpdateIdpGroupConnectionsLegacy_shouldThrow_whenPayloadNull() {
        final GitHubTeamsTeamSyncApi api = new GitHubTeamsTeamSyncApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.createOrUpdateIdpGroupConnectionsLegacy(TEAM_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubTeamsTeamSyncApiTest.class, fileName);
    }
}
