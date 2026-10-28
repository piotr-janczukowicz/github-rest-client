package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.teamsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubTeamsMembersApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String SLUG = "justice-league";
    private static final String USERNAME = "octocat";
    private static final long TEAM_ID = 1L;

    private static final String MEMBERS_PATH = "/orgs/octo-org/teams/justice-league/members";
    private static final String INVITATIONS_PATH = "/orgs/octo-org/teams/justice-league/invitations";
    private static final String LEGACY_MEMBERS_PATH = "/teams/1/members";
    private static final String LEGACY_INVITATIONS_PATH = "/teams/1/invitations";
    private static final String LEGACY_MEMBER_PATH = "/teams/1/members/octocat";
    private static final String LEGACY_MEMBERSHIP_PATH = "/teams/1/memberships/octocat";

    private static final JsonType<List<GitHubTeamMember>> MEMBERS_TYPE = JsonTypes.listOf(GitHubTeamMember.class);
    private static final JsonType<List<GitHubOrganizationInvitation>> INVITATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationInvitation.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listMembers_shouldReturnMembers_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                MEMBERS_PATH,
                okJson(listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-member.json")));

        final GitHubPage<GitHubTeamMember> page;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            page = teams.api().members().listMembers(ORG, SLUG);
        }

        final List<GitHubTeamMember> expected = CODEC.decode(
                listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-member.json"), MEMBERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, MEMBERS_PATH);
    }

    @Test
    void listMembers_shouldReturnMembers_whenRoleMaintainer() throws Exception {
        HTTP.stub(
                GET,
                MEMBERS_PATH,
                okJson(listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-member.json")));

        final GitHubPage<GitHubTeamMember> page;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            page = teams.api()
                    .members()
                    .listMembers(
                            ORG,
                            SLUG,
                            GitHubListTeamMembersFilterBuilder.builder()
                                    .role(GitHubTeamMembersListRole.MAINTAINER)
                                    .build());
        }

        final List<GitHubTeamMember> expected = CODEC.decode(
                listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-member.json"), MEMBERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, MEMBERS_PATH);
        HTTP.verifyQueryParam(GET, MEMBERS_PATH, "role", "maintainer");
    }

    @Test
    void listPendingInvitations_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                INVITATIONS_PATH,
                okJson(listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "organization-invitation.json")));

        final GitHubPage<GitHubOrganizationInvitation> page;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            page = teams.api().members().listPendingInvitations(ORG, SLUG);
        }

        final List<GitHubOrganizationInvitation> expected = CODEC.decode(
                listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "organization-invitation.json"),
                INVITATIONS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, INVITATIONS_PATH);
    }

    @Test
    void listPendingInvitationsLegacy_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LEGACY_INVITATIONS_PATH,
                okJson(listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "organization-invitation.json")));

        final GitHubPage<GitHubOrganizationInvitation> page;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            page = teams.api().members().listPendingInvitationsLegacy(TEAM_ID);
        }

        final List<GitHubOrganizationInvitation> expected = CODEC.decode(
                listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "organization-invitation.json"),
                INVITATIONS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, LEGACY_INVITATIONS_PATH);
    }

    @Test
    void listMembersLegacy_shouldReturnMembers_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LEGACY_MEMBERS_PATH,
                okJson(listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-member.json")));

        final GitHubPage<GitHubTeamMember> page;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            page = teams.api().members().listMembersLegacy(TEAM_ID);
        }

        final List<GitHubTeamMember> expected = CODEC.decode(
                listJsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-member.json"), MEMBERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, LEGACY_MEMBERS_PATH);
    }

    @Test
    void isMemberLegacy_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, LEGACY_MEMBER_PATH, noContent());

        final boolean member;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            member = teams.api().members().isMemberLegacy(TEAM_ID, USERNAME);
        }

        assertThat(member).isTrue();
        HTTP.verifyRequested(GET, LEGACY_MEMBER_PATH);
    }

    @Test
    void addMemberLegacy_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, LEGACY_MEMBER_PATH, noContent());

        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            teams.api().members().addMemberLegacy(TEAM_ID, USERNAME);
        }

        HTTP.verifyRequested(PUT, LEGACY_MEMBER_PATH);
    }

    @Test
    void removeMemberLegacy_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, LEGACY_MEMBER_PATH, noContent());

        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            teams.api().members().removeMemberLegacy(TEAM_ID, USERNAME);
        }

        HTTP.verifyRequested(DELETE, LEGACY_MEMBER_PATH);
    }

    @Test
    void getMembershipLegacy_shouldReturnMembership_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LEGACY_MEMBERSHIP_PATH,
                okJson(jsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-membership.json")));

        final Optional<GitHubTeamMembership> membership;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            membership = teams.api().members().getMembershipLegacy(TEAM_ID, USERNAME);
        }

        final GitHubTeamMembership expected = CODEC.decode(
                jsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-membership.json"),
                GitHubTeamMembership.class);

        assertThat(membership).contains(expected);
        HTTP.verifyRequested(GET, LEGACY_MEMBERSHIP_PATH);
    }

    @Test
    void upsertMembershipLegacy_shouldReturnMembership_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                LEGACY_MEMBERSHIP_PATH,
                okJson(jsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-membership.json")));

        final GitHubTeamMembership membership;
        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            membership = teams.api().members().upsertMembershipLegacy(TEAM_ID, USERNAME);
        }

        final GitHubTeamMembership expected = CODEC.decode(
                jsonBytes(GitHubTeamsMembersApiIntegrationTest.class, "team-membership.json"),
                GitHubTeamMembership.class);

        assertThat(membership).isEqualTo(expected);
        HTTP.verifyRequested(PUT, LEGACY_MEMBERSHIP_PATH);
    }

    @Test
    void removeMembershipLegacy_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, LEGACY_MEMBERSHIP_PATH, noContent());

        try (final GitHubApiWireMockSupport.TeamsApiClient teams = teamsApi(HTTP)) {
            teams.api().members().removeMembershipLegacy(TEAM_ID, USERNAME);
        }

        HTTP.verifyRequested(DELETE, LEGACY_MEMBERSHIP_PATH);
    }
}
