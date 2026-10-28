package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.teams.GitHubOrganizationInvitation;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpResponses;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsMembersApiTest {

    private static final String ORG = "github";
    private static final String USERNAME = "octocat";
    private static final long INVITATION_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsMembersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listFailedInvitations_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + invitationJson() + "]"));

        final GitHubPage<GitHubOrganizationInvitation> page = callApi(api -> api.listFailedInvitations(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.organizationInvitation());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/failed_invitations?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listPendingInvitations_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + invitationJson() + "]"));

        final GitHubPage<GitHubOrganizationInvitation> page = callApi(api -> api.listPendingInvitations(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.organizationInvitation());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/invitations?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listPendingInvitations_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + invitationJson() + "]"));
        final GitHubOrganizationsPendingInvitationsFilter filter =
                GitHubOrganizationsPendingInvitationsFilterBuilder.builder()
                        .role(GitHubOrganizationPendingInvitationRole.ADMIN)
                        .invitationSource(GitHubOrganizationPendingInvitationSource.MEMBER)
                        .build();

        callApi(api -> api.listPendingInvitations(ORG, filter, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/github/invitations");
            softly.assertThat(request.uri()).hasParameter("role", "admin");
            softly.assertThat(request.uri()).hasParameter("invitation_source", "member");
        });
    }

    @Test
    void createInvitation_shouldPostAndReturnInvitation_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), invitationJson()));
        final GitHubCreateOrganizationInvitationPayload payload =
                OrganizationsCatalogFixtures.createOrganizationInvitationPayload();

        final GitHubOrganizationInvitation invitation = callApi(api -> api.createInvitation(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(invitation).isEqualTo(TeamCatalogFixtures.organizationInvitation());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/invitations");
        });
    }

    @Test
    void cancelInvitation_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.cancelInvitation(ORG, INVITATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/invitations/1");
        });
    }

    @Test
    void listInvitationTeams_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + teamJson() + "]"));

        final GitHubPage<GitHubTeam> page = callApi(api -> api.listInvitationTeams(ORG, INVITATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/invitations/1/teams?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listMembers_shouldReturnMembers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));

        final GitHubPage<GitHubSimpleUser> page = callApi(api -> api.listMembers(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/members?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listMembers_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));
        final GitHubOrganizationsMembersFilter filter = GitHubOrganizationsMembersFilterBuilder.builder()
                .filter(GitHubOrganizationMembersKind.TWO_FA_DISABLED)
                .role(GitHubOrganizationMembersListRole.ADMIN)
                .build();

        callApi(api -> api.listMembers(ORG, filter, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/github/members");
            softly.assertThat(request.uri()).hasParameter("filter", "2fa_disabled");
            softly.assertThat(request.uri()).hasParameter("role", "admin");
        });
    }

    @Test
    void isMember_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean member = callApi(api -> api.isMember(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(member).isTrue();
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/members/octocat");
        });
    }

    @Test
    void isMember_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean member = callApi(api -> api.isMember(ORG, "missing"));

        assertThat(member).isFalse();
    }

    @Test
    void isMember_shouldThrow_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(HttpResponses.of(
                        HttpStatus.FOUND.code(),
                        Map.of(LOCATION, List.of("https://github.com/orgs/github")),
                        HttpBody.absent()));

        final Throwable thrown = catchThrowable(() -> callApi(api -> api.isMember(ORG, USERNAME)));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(thrown)
                    .isInstanceOf(GitHubApiException.class)
                    .extracting(ex -> ((GitHubApiException) ex).statusCode())
                    .isEqualTo(HttpStatus.FOUND.code());
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/members/octocat");
        });
    }

    @Test
    void removeMember_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.removeMember(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/members/octocat");
        });
    }

    @Test
    void getMembership_shouldReturnMembership_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), membershipJson()));

        final Optional<GitHubOrgMembership> membership = callApi(api -> api.getMembership(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();
        final GitHubOrgMembership expected = OrganizationsCatalogFixtures.orgMembership();

        assertSoftly(softly -> {
            softly.assertThat(membership).contains(expected);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/memberships/octocat");
        });
    }

    @Test
    void getMembership_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrgMembership> membership = callApi(api -> api.getMembership(ORG, "missing"));

        assertThat(membership).isEmpty();
    }

    @Test
    void setMembership_shouldSendPutWithoutBody_whenPayloadOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), membershipJson()));

        final GitHubOrgMembership membership = callApi(api -> api.setMembership(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(membership).isEqualTo(OrganizationsCatalogFixtures.orgMembership());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/memberships/octocat");
        });
    }

    @Test
    void setMembership_shouldSendPutWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), membershipJson()));
        final GitHubSetOrganizationMembershipPayload payload =
                OrganizationsCatalogFixtures.setOrganizationMembershipPayload();

        callApi(api -> api.setMembership(ORG, USERNAME, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"role\":\"admin\"}");
        });
    }

    @Test
    void removeMembership_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.removeMembership(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/memberships/octocat");
        });
    }

    @Test
    void listPublicMembers_shouldReturnMembers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));

        final GitHubPage<GitHubSimpleUser> page = callApi(api -> api.listPublicMembers(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/github/public_members?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void isPublicMember_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean member = callApi(api -> api.isPublicMember(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(member).isTrue();
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/public_members/octocat");
        });
    }

    @Test
    void isPublicMember_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean member = callApi(api -> api.isPublicMember(ORG, "missing"));

        assertThat(member).isFalse();
    }

    @Test
    void setPublicMembershipForAuthenticatedUser_shouldPut_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.setPublicMembershipForAuthenticatedUser(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/public_members/octocat");
        });
    }

    @Test
    void removePublicMembershipForAuthenticatedUser_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.removePublicMembershipForAuthenticatedUser(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/public_members/octocat");
        });
    }

    @Test
    void listMembershipsForAuthenticatedUser_shouldReturnMemberships_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + membershipJson() + "]"));

        final GitHubPage<GitHubOrgMembership> page =
                callApi(GitHubOrganizationsMembersApi::listMembershipsForAuthenticatedUser);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.orgMembership());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/memberships/orgs?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listMembershipsForAuthenticatedUser_shouldSendStateFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + membershipJson() + "]"));
        final GitHubOrganizationsAuthenticatedMembershipsFilter filter =
                GitHubOrganizationsAuthenticatedMembershipsFilterBuilder.builder()
                        .state(GitHubOrgMembershipState.ACTIVE)
                        .build();

        callApi(api -> api.listMembershipsForAuthenticatedUser(filter, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/user/memberships/orgs?state=active");
    }

    @Test
    void getMembershipForAuthenticatedUser_shouldReturnMembership_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), membershipJson()));

        final Optional<GitHubOrgMembership> membership = callApi(api -> api.getMembershipForAuthenticatedUser(ORG));
        final HttpRequest request = captureSingleRequest();
        final GitHubOrgMembership expected = OrganizationsCatalogFixtures.orgMembership();

        assertSoftly(softly -> {
            softly.assertThat(membership).contains(expected);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/memberships/orgs/github");
        });
    }

    @Test
    void getMembershipForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrgMembership> membership =
                callApi(api -> api.getMembershipForAuthenticatedUser("missing"));

        assertThat(membership).isEmpty();
    }

    @Test
    void updateMembershipForAuthenticatedUser_shouldPatchWithActiveState_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), membershipJson()));
        final GitHubUpdateAuthenticatedOrganizationMembershipPayload payload =
                OrganizationsCatalogFixtures.updateAuthenticatedOrganizationMembershipPayload();

        callApi(api -> api.updateMembershipForAuthenticatedUser(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/memberships/orgs/github");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"state\":\"active\"}");
        });
    }

    @Test
    void updateMembershipForAuthenticatedUser_shouldReturnMembership_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), membershipJson()));
        final GitHubUpdateAuthenticatedOrganizationMembershipPayload payload =
                OrganizationsCatalogFixtures.updateAuthenticatedOrganizationMembershipPayload();
        final GitHubOrgMembership expected = OrganizationsCatalogFixtures.orgMembership();

        final GitHubOrgMembership membership = callApi(api -> api.updateMembershipForAuthenticatedUser(ORG, payload));

        assertThat(membership).isEqualTo(expected);
    }

    @Test
    void listPendingInvitations_shouldReturnInvitations_whenFilterOverloadUsed() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + invitationJson() + "]"));
        final GitHubOrganizationsPendingInvitationsFilter filter = GitHubOrganizationsPendingInvitationsFilter.empty();

        final GitHubPage<GitHubOrganizationInvitation> page = callApi(api -> api.listPendingInvitations(ORG, filter));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.organizationInvitation());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listPendingInvitations_shouldReturnInvitations_whenRequestOverloadUsed() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + invitationJson() + "]"));

        final GitHubPage<GitHubOrganizationInvitation> page =
                callApi(api -> api.listPendingInvitations(ORG, GitHubPageRequest.none()));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.organizationInvitation());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listMembers_shouldReturnMembers_whenFilterOverloadUsed() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));
        final GitHubOrganizationsMembersFilter filter = GitHubOrganizationsMembersFilter.empty();

        final GitHubPage<GitHubSimpleUser> page = callApi(api -> api.listMembers(ORG, filter));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listMembers_shouldReturnMembers_whenRequestOverloadUsed() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));

        final GitHubPage<GitHubSimpleUser> page = callApi(api -> api.listMembers(ORG, GitHubPageRequest.none()));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listMembershipsForAuthenticatedUser_shouldReturnMemberships_whenFilterOverloadUsed() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + membershipJson() + "]"));
        final GitHubOrganizationsAuthenticatedMembershipsFilter filter =
                GitHubOrganizationsAuthenticatedMembershipsFilter.empty();

        final GitHubPage<GitHubOrgMembership> page = callApi(api -> api.listMembershipsForAuthenticatedUser(filter));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.orgMembership());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listMembershipsForAuthenticatedUser_shouldReturnMemberships_whenRequestOverloadUsed() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + membershipJson() + "]"));

        final GitHubPage<GitHubOrgMembership> page =
                callApi(api -> api.listMembershipsForAuthenticatedUser(GitHubPageRequest.none()));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.orgMembership());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void createInvitation_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApiVoid(api -> api.createInvitation(ORG, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void setMembership_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApiVoid(api -> api.setMembership(ORG, USERNAME, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void updateMembershipForAuthenticatedUser_shouldThrow_whenPayloadNull() {
        final Throwable thrown =
                catchThrowable(() -> callApiVoid(api -> api.updateMembershipForAuthenticatedUser(ORG, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void listPendingInvitations_shouldThrow_whenFilterNull() {
        final Throwable thrown = catchThrowable(
                () -> callApiVoid(api -> api.listPendingInvitations(ORG, null, GitHubPageRequest.byPage())));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void listMembers_shouldThrow_whenFilterNull() {
        final Throwable thrown =
                catchThrowable(() -> callApiVoid(api -> api.listMembers(ORG, null, GitHubPageRequest.byPage())));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void listMembershipsForAuthenticatedUser_shouldThrow_whenFilterNull() {
        final Throwable thrown = catchThrowable(
                () -> callApiVoid(api -> api.listMembershipsForAuthenticatedUser(null, GitHubPageRequest.byPage())));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void validation_shouldThrow_whenOrgBlank(final MembersApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @ParameterizedTest
    @MethodSource("blankUsernameCalls")
    void validation_shouldThrow_whenUsernameBlank(final MembersApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("username");
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(final MembersApiCall call, final String messageFragment) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @FunctionalInterface
    interface MembersApiCall {
        void execute(GitHubOrganizationsMembersApi api) throws Exception;
    }

    @FunctionalInterface
    private interface MembersApiAction<T> {
        T apply(GitHubOrganizationsMembersApi api) throws Exception;
    }

    private void callApiVoid(final MembersApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsMembersApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final MembersApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsMembersApi(TestClients.caller(client)));
        }
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursor = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (MembersApiCall) api -> api.listFailedInvitations(ORG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersApiCall) api -> api.listFailedInvitations(ORG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersApiCall) api -> api.listPendingInvitations(ORG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersApiCall) api -> api.listPendingInvitations(ORG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersApiCall) api -> api.listInvitationTeams(ORG, INVITATION_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersApiCall) api -> api.listInvitationTeams(ORG, INVITATION_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersApiCall) api -> api.listMembers(ORG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersApiCall) api -> api.listMembers(ORG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersApiCall) api -> api.listPublicMembers(ORG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersApiCall) api -> api.listPublicMembers(ORG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (MembersApiCall) api -> api.listMembershipsForAuthenticatedUser(unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (MembersApiCall) api -> api.listMembershipsForAuthenticatedUser(unsupportedCursor),
                        "unsupported pagination request: cursor"));
    }

    private static List<MembersApiCall> blankOrgCalls() {
        return List.of(
                api -> api.listFailedInvitations(" "),
                api -> api.listPendingInvitations(" "),
                api -> api.createInvitation(" ", OrganizationsCatalogFixtures.createOrganizationInvitationPayload()),
                api -> api.cancelInvitation(" ", INVITATION_ID),
                api -> api.listInvitationTeams(" ", INVITATION_ID),
                api -> api.listMembers(" "),
                api -> api.isMember(" ", USERNAME),
                api -> api.removeMember(" ", USERNAME),
                api -> api.getMembership(" ", USERNAME),
                api -> api.setMembership(" ", USERNAME),
                api -> api.setMembership(
                        " ", USERNAME, OrganizationsCatalogFixtures.setOrganizationMembershipPayload()),
                api -> api.removeMembership(" ", USERNAME),
                api -> api.listPublicMembers(" "),
                api -> api.isPublicMember(" ", USERNAME),
                api -> api.setPublicMembershipForAuthenticatedUser(" ", USERNAME),
                api -> api.removePublicMembershipForAuthenticatedUser(" ", USERNAME),
                api -> api.getMembershipForAuthenticatedUser(" "),
                api -> api.updateMembershipForAuthenticatedUser(
                        " ", OrganizationsCatalogFixtures.updateAuthenticatedOrganizationMembershipPayload()));
    }

    private static List<MembersApiCall> blankUsernameCalls() {
        return List.of(
                api -> api.isMember(ORG, " "),
                api -> api.removeMember(ORG, " "),
                api -> api.getMembership(ORG, " "),
                api -> api.setMembership(ORG, " "),
                api -> api.setMembership(ORG, " ", OrganizationsCatalogFixtures.setOrganizationMembershipPayload()),
                api -> api.removeMembership(ORG, " "),
                api -> api.isPublicMember(ORG, " "),
                api -> api.setPublicMembershipForAuthenticatedUser(ORG, " "),
                api -> api.removePublicMembershipForAuthenticatedUser(ORG, " "));
    }

    private static String invitationJson() {
        return FileUtils.readString(GitHubOrganizationInvitation.class, "organization-invitation.json");
    }

    private static String teamJson() {
        return FileUtils.readString(GitHubTeam.class, "team.json");
    }

    private static String usersJson() {
        return FileUtils.readString(GitHubSimpleUser.class, "users-one.json");
    }

    private static String membershipJson() {
        return FileUtils.readString(GitHubOrgMembership.class, "org-membership.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
