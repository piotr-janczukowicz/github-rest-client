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
class GitHubTeamsExternalGroupsApiTest {

    private static final String ORG = "github";
    private static final String SLUG = "justice-league";
    private static final long GROUP_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTeamsExternalGroupsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void externalIdpGroupInfo_shouldReturnGroup_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("external-group.json")));

        final Optional<GitHubExternalGroup> group =
                new GitHubTeamsExternalGroupsApi(TestClients.caller(transport)).externalIdpGroupInfo(ORG, GROUP_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(group).contains(TeamCatalogFixtures.externalGroup());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/github/external-group/1?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void externalIdpGroupInfo_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubExternalGroup> group = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport))
                .externalIdpGroupInfo(ORG, GROUP_ID, GitHubPageRequest.byPage());

        assertThat(group).isEmpty();
    }

    @Test
    void externalIdpGroupInfo_shouldThrow_whenRequestNull() {
        final GitHubTeamsExternalGroupsApi api = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.externalIdpGroupInfo(ORG, GROUP_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void listExternalIdpGroups_shouldReturnGroups_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("external-groups.json")));

        final GitHubPage<GitHubExternalGroupsGroup> page =
                new GitHubTeamsExternalGroupsApi(TestClients.caller(transport)).listExternalIdpGroups(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.externalGroupsGroup());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/github/external-groups?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listExternalIdpGroups_shouldSendDisplayNameQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("external-groups.json")));

        new GitHubTeamsExternalGroupsApi(TestClients.caller(transport))
                .listExternalIdpGroups(ORG, TeamCatalogFixtures.listExternalIdpGroupsForOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("display_name", "group-azuread-test");
    }

    @Test
    void listExternalIdpGroups_shouldOmitPageQuery_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("external-groups.json")));

        new GitHubTeamsExternalGroupsApi(TestClients.caller(transport))
                .listExternalIdpGroups(ORG, GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).isNull();
    }

    @Test
    void listExternalIdpGroups_shouldThrow_whenFilterNull() {
        final GitHubTeamsExternalGroupsApi api = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.listExternalIdpGroups(ORG, null, GitHubPageRequest.byPage()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void listExternalIdpGroups_shouldThrow_whenUnsupportedPagination() {
        final GitHubTeamsExternalGroupsApi api = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.listExternalIdpGroups(ORG, GitHubPageRequest.bySince()));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unsupported pagination request: since");
    }

    @Test
    void listLinkedExternalIdpGroupsToTeam_shouldReturnGroups_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("external-groups.json")));

        final Optional<GitHubExternalGroups> groups = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport))
                .listLinkedExternalIdpGroupsToTeam(ORG, SLUG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(groups).contains(TeamCatalogFixtures.externalGroups());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/external-groups");
        });
    }

    @Test
    void listLinkedExternalIdpGroupsToTeam_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubExternalGroups> groups = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport))
                .listLinkedExternalIdpGroupsToTeam(ORG, SLUG);

        assertThat(groups).isEmpty();
    }

    @Test
    void linkExternalIdpGroupToTeam_shouldSendPatchWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("external-group.json")));
        final GitHubLinkExternalIdpGroupToTeamForOrgPayload payload =
                TeamCatalogFixtures.linkExternalIdpGroupToTeamForOrgPayload();

        final GitHubExternalGroup group = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport))
                .linkExternalIdpGroupToTeam(ORG, SLUG, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(group).isEqualTo(TeamCatalogFixtures.externalGroup());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"group_id\":123}");
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/external-groups");
        });
    }

    @Test
    void linkExternalIdpGroupToTeam_shouldThrow_whenPayloadNull() {
        final GitHubTeamsExternalGroupsApi api = new GitHubTeamsExternalGroupsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.linkExternalIdpGroupToTeam(ORG, SLUG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void unlinkExternalIdpGroupFromTeam_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamsExternalGroupsApi(TestClients.caller(transport)).unlinkExternalIdpGroupFromTeam(ORG, SLUG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/external-groups");
        });
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubTeamsExternalGroupsApiTest.class, fileName);
    }
}
