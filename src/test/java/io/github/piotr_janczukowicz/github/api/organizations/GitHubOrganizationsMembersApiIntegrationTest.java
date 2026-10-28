package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.http.HttpStatus.FOUND;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.teams.GitHubOrganizationInvitation;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsMembersApiIntegrationTest {

    private static final JsonType<List<GitHubOrganizationInvitation>> INVITATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationInvitation.class);
    private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);
    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);
    private static final JsonType<List<GitHubOrgMembership>> MEMBERSHIPS_TYPE =
            JsonTypes.listOf(GitHubOrgMembership.class);

    private static final String ORG = "github";
    private static final String USERNAME = "octocat";
    private static final long INVITATION_ID = 1L;

    private static final String FAILED_INVITATIONS_PATH = "/orgs/github/failed_invitations";
    private static final String INVITATIONS_PATH = "/orgs/github/invitations";
    private static final String INVITATION_PATH = "/orgs/github/invitations/1";
    private static final String INVITATION_TEAMS_PATH = "/orgs/github/invitations/1/teams";
    private static final String MEMBERS_PATH = "/orgs/github/members";
    private static final String MEMBER_PATH = "/orgs/github/members/octocat";
    private static final String MEMBER_MISSING_PATH = "/orgs/github/members/missing";
    private static final String MEMBERSHIP_PATH = "/orgs/github/memberships/octocat";
    private static final String MEMBERSHIP_MISSING_PATH = "/orgs/github/memberships/missing";
    private static final String PUBLIC_MEMBERS_PATH = "/orgs/github/public_members";
    private static final String PUBLIC_MEMBER_PATH = "/orgs/github/public_members/octocat";
    private static final String PUBLIC_MEMBER_MISSING_PATH = "/orgs/github/public_members/missing";
    private static final String AUTH_MEMBERSHIPS_PATH = "/user/memberships/orgs";
    private static final String AUTH_MEMBERSHIP_PATH = "/user/memberships/orgs/github";
    private static final String AUTH_MEMBERSHIP_MISSING_PATH = "/user/memberships/orgs/missing";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listFailedInvitations_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                FAILED_INVITATIONS_PATH,
                okJson(listJsonBytes(GitHubOrganizationInvitation.class, "organization-invitation.json")));

        final GitHubPage<GitHubOrganizationInvitation> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().members().listFailedInvitations(ORG);
        }

        final List<GitHubOrganizationInvitation> expected = CODEC.decode(
                listJsonBytes(GitHubOrganizationInvitation.class, "organization-invitation.json"), INVITATIONS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, FAILED_INVITATIONS_PATH);
    }

    @Test
    void listPendingInvitations_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                INVITATIONS_PATH,
                okJson(listJsonBytes(GitHubOrganizationInvitation.class, "organization-invitation.json")));

        final GitHubPage<GitHubOrganizationInvitation> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().members().listPendingInvitations(ORG);
        }

        final List<GitHubOrganizationInvitation> expected = CODEC.decode(
                listJsonBytes(GitHubOrganizationInvitation.class, "organization-invitation.json"), INVITATIONS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, INVITATIONS_PATH);
    }

    @Test
    void createInvitation_shouldReturnInvitation_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                INVITATIONS_PATH,
                createdJson(jsonBytes(GitHubOrganizationInvitation.class, "organization-invitation.json")));
        final GitHubCreateOrganizationInvitationPayload payload =
                OrganizationsCatalogFixtures.createOrganizationInvitationPayload();

        final GitHubOrganizationInvitation invitation;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            invitation = organizations.api().members().createInvitation(ORG, payload);
        }

        final GitHubOrganizationInvitation expected = CODEC.decode(
                jsonBytes(GitHubOrganizationInvitation.class, "organization-invitation.json"),
                GitHubOrganizationInvitation.class);

        assertThat(invitation).isEqualTo(expected);
        HTTP.verifyRequested(POST, INVITATIONS_PATH);
        HTTP.verifyHeader(POST, INVITATIONS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, INVITATIONS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void cancelInvitation_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, INVITATION_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().members().cancelInvitation(ORG, INVITATION_ID);
        }

        HTTP.verifyRequested(DELETE, INVITATION_PATH);
    }

    @Test
    void listInvitationTeams_shouldReturnTeams_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, INVITATION_TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));

        final GitHubPage<GitHubTeam> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().members().listInvitationTeams(ORG, INVITATION_ID);
        }

        final List<GitHubTeam> expected = CODEC.decode(listJsonBytes(GitHubTeam.class, "team.json"), TEAMS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, INVITATION_TEAMS_PATH);
    }

    @Test
    void listMembers_shouldReturnMembers_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MEMBERS_PATH, okJson(jsonBytes(GitHubSimpleUser.class, "users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().members().listMembers(ORG);
        }

        final List<GitHubSimpleUser> expected =
                CODEC.decode(jsonBytes(GitHubSimpleUser.class, "users-one.json"), USERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, MEMBERS_PATH);
    }

    @Test
    void isMember_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, MEMBER_PATH, noContent());

        final boolean member;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            member = organizations.api().members().isMember(ORG, USERNAME);
        }

        assertThat(member).isTrue();
        HTTP.verifyRequested(GET, MEMBER_PATH);
    }

    @Test
    void isMember_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MEMBER_MISSING_PATH, notFound());

        final boolean member;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            member = organizations.api().members().isMember(ORG, "missing");
        }

        assertThat(member).isFalse();
        HTTP.verifyRequested(GET, MEMBER_MISSING_PATH);
    }

    @Test
    void isMember_shouldThrow_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(MEMBER_PATH, "https://github.com/orgs/github", FOUND.code());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            final GitHubOrganizationsMembersApi members = organizations.api().members();

            thrown = catchThrowable(() -> members.isMember(ORG, USERNAME));
        }

        assertThat(thrown)
                .isInstanceOf(GitHubApiException.class)
                .extracting(ex -> ((GitHubApiException) ex).statusCode())
                .isEqualTo(FOUND.code());
        HTTP.verifyRequested(GET, MEMBER_PATH);
    }

    @Test
    void removeMember_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, MEMBER_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().members().removeMember(ORG, USERNAME);
        }

        HTTP.verifyRequested(DELETE, MEMBER_PATH);
    }

    @Test
    void getMembership_shouldReturnMembership_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MEMBERSHIP_PATH, okJson(jsonBytes(GitHubOrgMembership.class, "org-membership.json")));

        final Optional<GitHubOrgMembership> membership;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            membership = organizations.api().members().getMembership(ORG, USERNAME);
        }

        final GitHubOrgMembership expected =
                CODEC.decode(jsonBytes(GitHubOrgMembership.class, "org-membership.json"), GitHubOrgMembership.class);

        assertThat(membership).contains(expected);
        HTTP.verifyRequested(GET, MEMBERSHIP_PATH);
    }

    @Test
    void getMembership_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MEMBERSHIP_MISSING_PATH, notFound());

        final Optional<GitHubOrgMembership> membership;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            membership = organizations.api().members().getMembership(ORG, "missing");
        }

        assertThat(membership).isEmpty();
        HTTP.verifyRequested(GET, MEMBERSHIP_MISSING_PATH);
    }

    @Test
    void setMembership_shouldReturnMembership_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, MEMBERSHIP_PATH, okJson(jsonBytes(GitHubOrgMembership.class, "org-membership.json")));

        final GitHubOrgMembership membership;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            membership = organizations.api().members().setMembership(ORG, USERNAME);
        }

        final GitHubOrgMembership expected =
                CODEC.decode(jsonBytes(GitHubOrgMembership.class, "org-membership.json"), GitHubOrgMembership.class);

        assertThat(membership).isEqualTo(expected);
        HTTP.verifyRequested(PUT, MEMBERSHIP_PATH);
    }

    @Test
    void removeMembership_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, MEMBERSHIP_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().members().removeMembership(ORG, USERNAME);
        }

        HTTP.verifyRequested(DELETE, MEMBERSHIP_PATH);
    }

    @Test
    void listPublicMembers_shouldReturnMembers_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PUBLIC_MEMBERS_PATH, okJson(jsonBytes(GitHubSimpleUser.class, "users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().members().listPublicMembers(ORG);
        }

        final List<GitHubSimpleUser> expected =
                CODEC.decode(jsonBytes(GitHubSimpleUser.class, "users-one.json"), USERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, PUBLIC_MEMBERS_PATH);
    }

    @Test
    void isPublicMember_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, PUBLIC_MEMBER_PATH, noContent());

        final boolean member;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            member = organizations.api().members().isPublicMember(ORG, USERNAME);
        }

        assertThat(member).isTrue();
        HTTP.verifyRequested(GET, PUBLIC_MEMBER_PATH);
    }

    @Test
    void isPublicMember_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, PUBLIC_MEMBER_MISSING_PATH, notFound());

        final boolean member;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            member = organizations.api().members().isPublicMember(ORG, "missing");
        }

        assertThat(member).isFalse();
        HTTP.verifyRequested(GET, PUBLIC_MEMBER_MISSING_PATH);
    }

    @Test
    void setPublicMembershipForAuthenticatedUser_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, PUBLIC_MEMBER_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().members().setPublicMembershipForAuthenticatedUser(ORG, USERNAME);
        }

        HTTP.verifyRequested(PUT, PUBLIC_MEMBER_PATH);
    }

    @Test
    void removePublicMembershipForAuthenticatedUser_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PUBLIC_MEMBER_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().members().removePublicMembershipForAuthenticatedUser(ORG, USERNAME);
        }

        HTTP.verifyRequested(DELETE, PUBLIC_MEMBER_PATH);
    }

    @Test
    void listMembershipsForAuthenticatedUser_shouldReturnMemberships_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, AUTH_MEMBERSHIPS_PATH, okJson(listJsonBytes(GitHubOrgMembership.class, "org-membership.json")));

        final GitHubPage<GitHubOrgMembership> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().members().listMembershipsForAuthenticatedUser();
        }

        final List<GitHubOrgMembership> expected =
                CODEC.decode(listJsonBytes(GitHubOrgMembership.class, "org-membership.json"), MEMBERSHIPS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, AUTH_MEMBERSHIPS_PATH);
    }

    @Test
    void getMembershipForAuthenticatedUser_shouldReturnMembership_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, AUTH_MEMBERSHIP_PATH, okJson(jsonBytes(GitHubOrgMembership.class, "org-membership.json")));

        final Optional<GitHubOrgMembership> membership;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            membership = organizations.api().members().getMembershipForAuthenticatedUser(ORG);
        }

        final GitHubOrgMembership expected =
                CODEC.decode(jsonBytes(GitHubOrgMembership.class, "org-membership.json"), GitHubOrgMembership.class);

        assertThat(membership).contains(expected);
        HTTP.verifyRequested(GET, AUTH_MEMBERSHIP_PATH);
    }

    @Test
    void getMembershipForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, AUTH_MEMBERSHIP_MISSING_PATH, notFound());

        final Optional<GitHubOrgMembership> membership;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            membership = organizations.api().members().getMembershipForAuthenticatedUser("missing");
        }

        assertThat(membership).isEmpty();
        HTTP.verifyRequested(GET, AUTH_MEMBERSHIP_MISSING_PATH);
    }

    @Test
    void updateMembershipForAuthenticatedUser_shouldReturnMembership_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, AUTH_MEMBERSHIP_PATH, okJson(jsonBytes(GitHubOrgMembership.class, "org-membership.json")));
        final GitHubUpdateAuthenticatedOrganizationMembershipPayload payload =
                OrganizationsCatalogFixtures.updateAuthenticatedOrganizationMembershipPayload();

        final GitHubOrgMembership membership;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            membership = organizations.api().members().updateMembershipForAuthenticatedUser(ORG, payload);
        }

        final GitHubOrgMembership expected =
                CODEC.decode(jsonBytes(GitHubOrgMembership.class, "org-membership.json"), GitHubOrgMembership.class);

        assertThat(membership).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, AUTH_MEMBERSHIP_PATH);
        HTTP.verifyHeader(PATCH, AUTH_MEMBERSHIP_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, AUTH_MEMBERSHIP_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateMembershipForAuthenticatedUser_shouldReturnMembership_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                PATCH, AUTH_MEMBERSHIP_PATH, acceptedJson(jsonBytes(GitHubOrgMembership.class, "org-membership.json")));
        final GitHubUpdateAuthenticatedOrganizationMembershipPayload payload =
                OrganizationsCatalogFixtures.updateAuthenticatedOrganizationMembershipPayload();

        final GitHubOrgMembership membership;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            membership = organizations.api().members().updateMembershipForAuthenticatedUser(ORG, payload);
        }

        final GitHubOrgMembership expected =
                CODEC.decode(jsonBytes(GitHubOrgMembership.class, "org-membership.json"), GitHubOrgMembership.class);

        assertThat(membership).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, AUTH_MEMBERSHIP_PATH);
    }
}
