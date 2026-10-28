package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.teams.GitHubOrganizationInvitation;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub organization members REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsMembersApi extends GitHubResourceApi {

    private static final PathTemplate FAILED_INVITATIONS = PathTemplate.of("/orgs/{org}/failed_invitations");
    private static final PathTemplate INVITATIONS = PathTemplate.of("/orgs/{org}/invitations");
    private static final PathTemplate INVITATION = PathTemplate.of("/orgs/{org}/invitations/{invitation_id}");
    private static final PathTemplate INVITATION_TEAMS =
            PathTemplate.of("/orgs/{org}/invitations/{invitation_id}/teams");
    private static final PathTemplate MEMBERS = PathTemplate.of("/orgs/{org}/members");
    private static final PathTemplate MEMBER = PathTemplate.of("/orgs/{org}/members/{username}");
    private static final PathTemplate MEMBERSHIP = PathTemplate.of("/orgs/{org}/memberships/{username}");
    private static final PathTemplate PUBLIC_MEMBERS = PathTemplate.of("/orgs/{org}/public_members");
    private static final PathTemplate PUBLIC_MEMBER = PathTemplate.of("/orgs/{org}/public_members/{username}");
    private static final PathTemplate USER_ORG_MEMBERSHIPS = PathTemplate.of("/user/memberships/orgs");
    private static final PathTemplate USER_ORG_MEMBERSHIP = PathTemplate.of("/user/memberships/orgs/{org}");

    private static final JsonType<List<GitHubOrganizationInvitation>> INVITATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationInvitation.class);
    private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);
    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);
    private static final JsonType<List<GitHubOrgMembership>> MEMBERSHIPS_TYPE =
            JsonTypes.listOf(GitHubOrgMembership.class);

    /**
     * Organization members resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsMembersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List failed organization invitations ({@code GET /orgs/{org}/failed_invitations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listFailedInvitations(String, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of failed invitations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listFailedInvitations(final String org) throws IOException {
        return listFailedInvitations(org, GitHubPageRequest.byPage());
    }

    /**
     * List failed organization invitations ({@code GET /orgs/{org}/failed_invitations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of failed invitations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listFailedInvitations(
            final String org, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return pageList(FAILED_INVITATIONS, Map.of(), request, INVITATIONS_TYPE, org);
    }

    /**
     * List pending organization invitations ({@code GET /orgs/{org}/invitations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPendingInvitations(String,
     * GitHubOrganizationsPendingInvitationsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of pending invitations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitations(final String org) throws IOException {
        return listPendingInvitations(
                org, GitHubOrganizationsPendingInvitationsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List pending organization invitations ({@code GET /orgs/{org}/invitations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPendingInvitations(String,
     * GitHubOrganizationsPendingInvitationsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter optional query filters
     * @return first HTTP page of pending invitations
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitations(
            final String org, final GitHubOrganizationsPendingInvitationsFilter filter) throws IOException {
        return listPendingInvitations(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List pending organization invitations ({@code GET /orgs/{org}/invitations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of pending invitations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitations(
            final String org, final GitHubPageRequest request) throws IOException {
        return listPendingInvitations(org, GitHubOrganizationsPendingInvitationsFilter.empty(), request);
    }

    /**
     * List pending organization invitations ({@code GET /orgs/{org}/invitations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of pending invitations
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitations(
            final String org, final GitHubOrganizationsPendingInvitationsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return pageList(INVITATIONS, filter.toQueryParameters(), request, INVITATIONS_TYPE, org);
    }

    /**
     * Create an organization invitation ({@code POST /orgs/{org}/invitations}).
     *
     * @param org organization name
     * @param payload invitation fields
     * @return created invitation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOrganizationInvitation createInvitation(
            final String org, final GitHubCreateOrganizationInvitationPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(INVITATIONS, org), payload), GitHubOrganizationInvitation.class);
    }

    /**
     * Cancel an organization invitation ({@code DELETE /orgs/{org}/invitations/{invitation_id}}).
     *
     * @param org organization name
     * @param invitationId invitation id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void cancelInvitation(final String org, final long invitationId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(INVITATION, org, invitationId));
    }

    /**
     * List organization invitation teams ({@code GET /orgs/{org}/invitations/{invitation_id}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listInvitationTeams(String, long, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param invitationId invitation id
     * @return first HTTP page of teams
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubTeam> listInvitationTeams(final String org, final long invitationId) throws IOException {
        return listInvitationTeams(org, invitationId, GitHubPageRequest.byPage());
    }

    /**
     * List organization invitation teams ({@code GET /orgs/{org}/invitations/{invitation_id}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param invitationId invitation id
     * @param request pagination token
     * @return first HTTP page of teams
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubTeam> listInvitationTeams(
            final String org, final long invitationId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return pageList(INVITATION_TEAMS, Map.of(), request, TEAMS_TYPE, org, invitationId);
    }

    /**
     * List organization members ({@code GET /orgs/{org}/members}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembers(String, GitHubOrganizationsMembersFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listMembers(final String org) throws IOException {
        return listMembers(org, GitHubOrganizationsMembersFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization members ({@code GET /orgs/{org}/members}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembers(String, GitHubOrganizationsMembersFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter optional query filters
     * @return first HTTP page of members
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listMembers(final String org, final GitHubOrganizationsMembersFilter filter)
            throws IOException {
        return listMembers(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List organization members ({@code GET /orgs/{org}/members}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of members
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listMembers(final String org, final GitHubPageRequest request)
            throws IOException {
        return listMembers(org, GitHubOrganizationsMembersFilter.empty(), request);
    }

    /**
     * List organization members ({@code GET /orgs/{org}/members}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of members
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listMembers(
            final String org, final GitHubOrganizationsMembersFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(MEMBERS, filter.toQueryParameters(), request, USERS_TYPE, org);
    }

    /**
     * Check organization membership for a user ({@code GET /orgs/{org}/members/{username}}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404. When the authenticated user is not a member of
     * the organization, GitHub cannot answer privately and responds with HTTP 302 to the public membership check.
     * Redirects are not followed and yield a {@link GitHubApiException}, because the answer is unknown, not
     * {@code false}: {@code username} may still be a public member. Use {@link #isPublicMember(String, String)} in that
     * case.
     *
     * @param org organization name
     * @param username GitHub username
     * @return whether the user is an organization member
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     */
    public boolean isMember(final String org, final String username) throws IOException {
        return caller.isExisting(get(MEMBER, org, username));
    }

    /**
     * Remove an organization member ({@code DELETE /orgs/{org}/members/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeMember(final String org, final String username) throws IOException {
        caller.send(delete(MEMBER, org, username));
    }

    /**
     * Get organization membership for a user ({@code GET /orgs/{org}/memberships/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @return membership, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubOrgMembership> getMembership(final String org, final String username) throws IOException {
        return caller.fetchOrEmpty(get(MEMBERSHIP, org, username), GitHubOrgMembership.class);
    }

    /**
     * Set organization membership for a user ({@code PUT /orgs/{org}/memberships/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @return updated membership
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public GitHubOrgMembership setMembership(final String org, final String username) throws IOException {
        return caller.fetch(put(MEMBERSHIP, org, username), GitHubOrgMembership.class);
    }

    /**
     * Set organization membership for a user ({@code PUT /orgs/{org}/memberships/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @param payload optional membership role
     * @return updated membership
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public GitHubOrgMembership setMembership(
            final String org, final String username, final GitHubSetOrganizationMembershipPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(MEMBERSHIP, org, username), payload), GitHubOrgMembership.class);
    }

    /**
     * Remove organization membership for a user ({@code DELETE /orgs/{org}/memberships/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeMembership(final String org, final String username) throws IOException {
        caller.send(delete(MEMBERSHIP, org, username));
    }

    /**
     * List public organization members ({@code GET /orgs/{org}/public_members}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPublicMembers(String, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of public members
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listPublicMembers(final String org) throws IOException {
        return listPublicMembers(org, GitHubPageRequest.byPage());
    }

    /**
     * List public organization members ({@code GET /orgs/{org}/public_members}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of public members
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleUser> listPublicMembers(final String org, final GitHubPageRequest request)
            throws IOException {

        return pageList(PUBLIC_MEMBERS, Map.of(), request, USERS_TYPE, org);
    }

    /**
     * Check public organization membership for a user ({@code GET /orgs/{org}/public_members/{username}}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param org organization name
     * @param username GitHub username
     * @return whether the user is a public organization member
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     */
    public boolean isPublicMember(final String org, final String username) throws IOException {
        return caller.isExisting(get(PUBLIC_MEMBER, org, username));
    }

    /**
     * Set public organization membership for the authenticated user ({@code PUT
     * /orgs/{org}/public_members/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setPublicMembershipForAuthenticatedUser(final String org, final String username) throws IOException {
        caller.send(put(PUBLIC_MEMBER, org, username));
    }

    /**
     * Remove public organization membership for the authenticated user ({@code DELETE
     * /orgs/{org}/public_members/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removePublicMembershipForAuthenticatedUser(final String org, final String username) throws IOException {
        caller.send(delete(PUBLIC_MEMBER, org, username));
    }

    /**
     * List organization memberships for the authenticated user ({@code GET /user/memberships/orgs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembershipsForAuthenticatedUser(
     * GitHubOrganizationsAuthenticatedMembershipsFilter, GitHubPageRequest)}.
     *
     * @return first HTTP page of organization memberships
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgMembership> listMembershipsForAuthenticatedUser() throws IOException {
        return listMembershipsForAuthenticatedUser(
                GitHubOrganizationsAuthenticatedMembershipsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization memberships for the authenticated user ({@code GET /user/memberships/orgs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembershipsForAuthenticatedUser(
     * GitHubOrganizationsAuthenticatedMembershipsFilter, GitHubPageRequest)}.
     *
     * @param filter optional query filters
     * @return first HTTP page of organization memberships
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgMembership> listMembershipsForAuthenticatedUser(
            final GitHubOrganizationsAuthenticatedMembershipsFilter filter) throws IOException {
        return listMembershipsForAuthenticatedUser(filter, GitHubPageRequest.byPage());
    }

    /**
     * List organization memberships for the authenticated user ({@code GET /user/memberships/orgs}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of organization memberships
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgMembership> listMembershipsForAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        return listMembershipsForAuthenticatedUser(GitHubOrganizationsAuthenticatedMembershipsFilter.empty(), request);
    }

    /**
     * List organization memberships for the authenticated user ({@code GET /user/memberships/orgs}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of organization memberships
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgMembership> listMembershipsForAuthenticatedUser(
            final GitHubOrganizationsAuthenticatedMembershipsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(USER_ORG_MEMBERSHIPS, filter.toQueryParameters(), request, MEMBERSHIPS_TYPE);
    }

    /**
     * Get an organization membership for the authenticated user ({@code GET /user/memberships/orgs/{org}}).
     *
     * @param org organization name
     * @return membership, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubOrgMembership> getMembershipForAuthenticatedUser(final String org) throws IOException {
        return caller.fetchOrEmpty(get(USER_ORG_MEMBERSHIP, org), GitHubOrgMembership.class);
    }

    /**
     * Update an organization membership for the authenticated user ({@code PATCH /user/memberships/orgs/{org}}).
     *
     * @param org organization name
     * @param payload membership state update
     * @return updated membership
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOrgMembership updateMembershipForAuthenticatedUser(
            final String org, final GitHubUpdateAuthenticatedOrganizationMembershipPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(USER_ORG_MEMBERSHIP, org), payload), GitHubOrgMembership.class);
    }
}
