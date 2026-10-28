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
class GitHubTeamsMembersApiTest {

    private static final String ORG = "github";
    private static final String SLUG = "justice-league";
    private static final String USERNAME = "octocat";
    private static final long TEAM_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTeamsMembersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listMembers_shouldThrow_whenFilterNull() {
        final GitHubTeamsMembersApi teamsApi = new GitHubTeamsMembersApi(TestClients.caller(transport));

        final Throwable thrown =
                catchThrowable(() -> teamsApi.listMembers(ORG, SLUG, (GitHubListTeamMembersFilter) null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(final MembersCall call, final String messageFragment) {
        final GitHubTeamsMembersApi teamsApi = new GitHubTeamsMembersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(teamsApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @Test
    void listPendingInvitations_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-invitation.json") + "]"));

        final GitHubPage<GitHubOrganizationInvitation> page =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).listPendingInvitations(ORG, SLUG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.organizationInvitation());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/invitations?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listMembers_shouldReturnMembers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        final GitHubPage<GitHubTeamMember> page =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).listMembers(ORG, SLUG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.teamMember());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/members?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listMembers_shouldSendRoleQuery_whenRoleFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        new GitHubTeamsMembersApi(TestClients.caller(transport))
                .listMembers(
                        ORG,
                        SLUG,
                        GitHubListTeamMembersFilterBuilder.builder()
                                .role(GitHubTeamMembersListRole.MAINTAINER)
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("role", "maintainer");
    }

    @Test
    void listMembers_shouldDefaultToByPage_whenRoleFilterOverloadOmitsRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        new GitHubTeamsMembersApi(TestClients.caller(transport))
                .listMembers(
                        ORG,
                        SLUG,
                        GitHubListTeamMembersFilterBuilder.builder()
                                .role(GitHubTeamMembersListRole.MEMBER)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("role", "member");
            softly.assertThat(requestUri).hasParameter("page", "1");
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void listMembers_shouldOmitPageQuery_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        final GitHubPage<GitHubTeamMember> page = new GitHubTeamsMembersApi(TestClients.caller(transport))
                .listMembers(ORG, SLUG, GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.teamMember());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/members");
            softly.assertThat(requestUri.getQuery()).isNull();
        });
    }

    @Test
    void getMembership_shouldReturnMembership_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-membership.json")));

        final Optional<GitHubTeamMembership> membership =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).getMembership(ORG, SLUG, USERNAME);
        final URI requestUri = captureSingleRequest().uri();
        final GitHubTeamMembership expected = TeamCatalogFixtures.teamMembership();

        assertSoftly(softly -> {
            softly.assertThat(membership).contains(expected);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/memberships/octocat");
        });
    }

    @Test
    void getMembership_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubTeamMembership> membership =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).getMembership(ORG, SLUG, USERNAME);

        assertThat(membership).isEmpty();
    }

    @Test
    void upsertMembership_shouldSendPutWithoutBody_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-membership.json")));

        final GitHubTeamMembership membership =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).upsertMembership(ORG, SLUG, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(membership).isEqualTo(TeamCatalogFixtures.teamMembership());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/memberships/octocat");
        });
    }

    @Test
    void upsertMembership_shouldSendPutWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-membership.json")));
        final GitHubUpsertTeamMembershipPayload payload = TeamCatalogFixtures.upsertTeamMembershipPayload();

        new GitHubTeamsMembersApi(TestClients.caller(transport)).upsertMembership(ORG, SLUG, USERNAME, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"role\":\"maintainer\"}");
        });
    }

    @Test
    void removeMembership_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamsMembersApi(TestClients.caller(transport)).removeMembership(ORG, SLUG, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/teams/justice-league/memberships/octocat");
        });
    }

    @Test
    void listPendingInvitationsLegacy_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-invitation.json") + "]"));

        final GitHubPage<GitHubOrganizationInvitation> page =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).listPendingInvitationsLegacy(TEAM_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.organizationInvitation());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/teams/1/invitations?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listMembersLegacy_shouldReturnMembers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        final GitHubPage<GitHubTeamMember> page =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).listMembersLegacy(TEAM_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.teamMember());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/teams/1/members?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listMembersLegacy_shouldSendRoleQuery_whenRoleFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        new GitHubTeamsMembersApi(TestClients.caller(transport))
                .listMembersLegacy(
                        TEAM_ID,
                        GitHubListTeamMembersFilterBuilder.builder()
                                .role(GitHubTeamMembersListRole.MAINTAINER)
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("role", "maintainer");
    }

    @Test
    void listMembersLegacy_shouldDefaultToByPage_whenRoleFilterOverloadOmitsRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        new GitHubTeamsMembersApi(TestClients.caller(transport))
                .listMembersLegacy(
                        TEAM_ID,
                        GitHubListTeamMembersFilterBuilder.builder()
                                .role(GitHubTeamMembersListRole.ALL)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("role", "all");
            softly.assertThat(requestUri).hasParameter("page", "1");
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void listMembersLegacy_shouldOmitPageQuery_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("team-member.json") + "]"));

        final GitHubPage<GitHubTeamMember> page = new GitHubTeamsMembersApi(TestClients.caller(transport))
                .listMembersLegacy(TEAM_ID, GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.teamMember());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri).hasToString("https://api.github.com/teams/1/members");
            softly.assertThat(requestUri.getQuery()).isNull();
        });
    }

    @Test
    void isMemberLegacy_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean member =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).isMemberLegacy(TEAM_ID, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(member).isTrue();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/members/octocat");
        });
    }

    @Test
    void isMemberLegacy_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean member =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).isMemberLegacy(TEAM_ID, USERNAME);

        assertThat(member).isFalse();
    }

    @Test
    void addMemberLegacy_shouldSendPut_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamsMembersApi(TestClients.caller(transport)).addMemberLegacy(TEAM_ID, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/members/octocat");
        });
    }

    @Test
    void removeMemberLegacy_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamsMembersApi(TestClients.caller(transport)).removeMemberLegacy(TEAM_ID, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/members/octocat");
        });
    }

    @Test
    void getMembershipLegacy_shouldReturnMembership_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-membership.json")));

        final Optional<GitHubTeamMembership> membership =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).getMembershipLegacy(TEAM_ID, USERNAME);
        final URI requestUri = captureSingleRequest().uri();
        final GitHubTeamMembership expected = TeamCatalogFixtures.teamMembership();

        assertSoftly(softly -> {
            softly.assertThat(membership).contains(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/teams/1/memberships/octocat");
        });
    }

    @Test
    void getMembershipLegacy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubTeamMembership> membership =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).getMembershipLegacy(TEAM_ID, USERNAME);

        assertThat(membership).isEmpty();
    }

    @Test
    void upsertMembershipLegacy_shouldSendPutWithoutBody_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-membership.json")));

        final GitHubTeamMembership membership =
                new GitHubTeamsMembersApi(TestClients.caller(transport)).upsertMembershipLegacy(TEAM_ID, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(membership).isEqualTo(TeamCatalogFixtures.teamMembership());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/memberships/octocat");
        });
    }

    @Test
    void upsertMembershipLegacy_shouldSendPutWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("team-membership.json")));
        final GitHubUpsertTeamMembershipPayload payload = TeamCatalogFixtures.upsertTeamMembershipPayload();

        new GitHubTeamsMembersApi(TestClients.caller(transport)).upsertMembershipLegacy(TEAM_ID, USERNAME, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"role\":\"maintainer\"}");
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/memberships/octocat");
        });
    }

    @Test
    void removeMembershipLegacy_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubTeamsMembersApi(TestClients.caller(transport)).removeMembershipLegacy(TEAM_ID, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/teams/1/memberships/octocat");
        });
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void validation_shouldThrow_whenOrgBlank(final MembersCall call) {
        final GitHubTeamsMembersApi teamsApi = new GitHubTeamsMembersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(teamsApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubTeamsMembersApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static List<MembersCall> blankOrgCalls() {
        final GitHubUpsertTeamMembershipPayload membershipPayload = TeamCatalogFixtures.upsertTeamMembershipPayload();
        return List.of(
                api -> api.listPendingInvitations(" ", SLUG),
                api -> api.listMembers(" ", SLUG),
                api -> api.listMembers(
                        " ",
                        SLUG,
                        GitHubListTeamMembersFilterBuilder.builder()
                                .role(GitHubTeamMembersListRole.MEMBER)
                                .build()),
                api -> api.getMembership(" ", SLUG, USERNAME),
                api -> api.upsertMembership(" ", SLUG, USERNAME),
                api -> api.upsertMembership(" ", SLUG, USERNAME, membershipPayload),
                api -> api.removeMembership(" ", SLUG, USERNAME));
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursor = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (MembersCall) api -> api.listPendingInvitations(ORG, SLUG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersCall) api -> api.listPendingInvitations(ORG, SLUG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersCall) api -> api.listMembers(ORG, SLUG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersCall) api -> api.listMembers(ORG, SLUG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersCall) api -> api.listMembers(
                                ORG,
                                SLUG,
                                GitHubListTeamMembersFilterBuilder.builder()
                                        .role(GitHubTeamMembersListRole.MEMBER)
                                        .build(),
                                unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersCall) api -> api.listMembers(
                                ORG,
                                SLUG,
                                GitHubListTeamMembersFilterBuilder.builder()
                                        .role(GitHubTeamMembersListRole.MEMBER)
                                        .build(),
                                unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersCall) api -> api.listPendingInvitationsLegacy(TEAM_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersCall) api -> api.listPendingInvitationsLegacy(TEAM_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersCall) api -> api.listMembersLegacy(TEAM_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersCall) api -> api.listMembersLegacy(TEAM_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"));
    }

    @FunctionalInterface
    interface MembersCall {
        void execute(GitHubTeamsMembersApi api) throws Exception;
    }
}
