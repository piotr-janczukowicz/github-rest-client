package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
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

/**
 * GitHub Teams members REST resource for API version {@code 2026-03-10}.
 *
 * <p>Interact with team members, memberships, and invitations.
 */
public final class GitHubTeamsMembersApi extends GitHubResourceApi {

    private static final PathTemplate TEAM_MEMBERSHIP = PathTemplate.of("/teams/{team_id}/memberships/{username}");
    private static final PathTemplate TEAM_MEMBER = PathTemplate.of("/teams/{team_id}/members/{username}");
    private static final PathTemplate TEAM_MEMBERS = PathTemplate.of("/teams/{team_id}/members");
    private static final PathTemplate TEAM_INVITATIONS = PathTemplate.of("/teams/{team_id}/invitations");
    private static final PathTemplate ORG_TEAM_MEMBERSHIP =
            PathTemplate.of("/orgs/{org}/teams/{team_slug}/memberships/{username}");
    private static final PathTemplate ORG_TEAM_MEMBERS = PathTemplate.of("/orgs/{org}/teams/{team_slug}/members");
    private static final PathTemplate ORG_TEAM_INVITATIONS =
            PathTemplate.of("/orgs/{org}/teams/{team_slug}/invitations");

    private static final String DEPRECATED_SINCE = "2020-01-21";

    private static final JsonType<List<GitHubOrganizationInvitation>> INVITATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationInvitation.class);
    private static final JsonType<List<GitHubTeamMember>> MEMBERS_TYPE = JsonTypes.listOf(GitHubTeamMember.class);

    /**
     * Teams members resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubTeamsMembersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List pending team invitations ({@code GET /orgs/{org}/teams/{team_slug}/invitations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPendingInvitations(String, String,
     * GitHubPageRequest)}.
     *
     * @param org organization login
     * @param slug team slug
     * @return first HTTP page of invitations
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitations(final String org, final String slug)
            throws IOException {
        return listPendingInvitations(org, slug, GitHubPageRequest.byPage());
    }

    /**
     * List pending team invitations ({@code GET /orgs/{org}/teams/{team_slug}/invitations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param slug team slug
     * @param request pagination token
     * @return first HTTP page of invitations
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank, or {@code request} is a since or cursor
     *     variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitations(
            final String org, final String slug, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return pageList(ORG_TEAM_INVITATIONS, Map.of(), request, INVITATIONS_TYPE, org, slug);
    }

    /**
     * List team members ({@code GET /orgs/{org}/teams/{team_slug}/members}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembers(String, String, GitHubPageRequest)}.
     *
     * @param org organization login
     * @param slug team slug
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamMember> listMembers(final String org, final String slug) throws IOException {
        return listMembers(org, slug, GitHubListTeamMembersFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List team members ({@code GET /orgs/{org}/teams/{team_slug}/members}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembers(String, String, GitHubListTeamMembersFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization login
     * @param slug team slug
     * @param filter query filters
     * @return first HTTP page of members
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamMember> listMembers(
            final String org, final String slug, final GitHubListTeamMembersFilter filter) throws IOException {
        return listMembers(org, slug, filter, GitHubPageRequest.byPage());
    }

    /**
     * List team members ({@code GET /orgs/{org}/teams/{team_slug}/members}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param slug team slug
     * @param request pagination token
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank, or {@code request} is a since or cursor
     *     variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamMember> listMembers(
            final String org, final String slug, final GitHubPageRequest request) throws IOException {
        return listMembers(org, slug, GitHubListTeamMembersFilter.empty(), request);
    }

    /**
     * List team members ({@code GET /orgs/{org}/teams/{team_slug}/members}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param slug team slug
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank, or {@code request} is a since or cursor
     *     variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamMember> listMembers(
            final String org,
            final String slug,
            final GitHubListTeamMembersFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_TEAM_MEMBERS, filter.toQueryParameters(), request, MEMBERS_TYPE, org, slug);
    }

    /**
     * Get team membership for a user ({@code GET /orgs/{org}/teams/{team_slug}/memberships/{username}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param org organization login
     * @param slug team slug
     * @param username GitHub username
     * @return the membership, or empty if the user is not a team member
     * @throws IllegalArgumentException if {@code org}, {@code slug}, or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubTeamMembership> getMembership(final String org, final String slug, final String username)
            throws IOException {
        return caller.fetchOrEmpty(get(ORG_TEAM_MEMBERSHIP, org, slug, username), GitHubTeamMembership.class);
    }

    /**
     * Add or update team membership ({@code PUT /orgs/{org}/teams/{team_slug}/memberships/{username}}).
     *
     * @param org organization login
     * @param slug team slug
     * @param username GitHub username
     * @return the membership
     * @throws IllegalArgumentException if {@code org}, {@code slug}, or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubTeamMembership upsertMembership(final String org, final String slug, final String username)
            throws IOException {
        return caller.fetch(put(ORG_TEAM_MEMBERSHIP, org, slug, username), GitHubTeamMembership.class);
    }

    /**
     * Add or update team membership ({@code PUT /orgs/{org}/teams/{team_slug}/memberships/{username}}).
     *
     * @param org organization login
     * @param slug team slug
     * @param username GitHub username
     * @param payload membership role
     * @return the membership
     * @throws IllegalArgumentException if {@code org}, {@code slug}, or {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubTeamMembership upsertMembership(
            final String org, final String slug, final String username, final GitHubUpsertTeamMembershipPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(ORG_TEAM_MEMBERSHIP, org, slug, username), payload), GitHubTeamMembership.class);
    }

    /**
     * Remove team membership ({@code DELETE /orgs/{org}/teams/{team_slug}/memberships/{username}}).
     *
     * @param org organization login
     * @param slug team slug
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org}, {@code slug}, or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeMembership(final String org, final String slug, final String username) throws IOException {
        caller.send(delete(ORG_TEAM_MEMBERSHIP, org, slug, username));
    }

    /**
     * List pending team invitations (legacy) ({@code GET /teams/{team_id}/invitations}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listPendingInvitations(String, String)}.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listPendingInvitationsLegacy(long, GitHubPageRequest)}.
     *
     * @param teamId team id
     * @return first HTTP page of invitations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitationsLegacy(final long teamId) throws IOException {
        return listPendingInvitationsLegacy(teamId, GitHubPageRequest.byPage());
    }

    /**
     * List pending team invitations (legacy) ({@code GET /teams/{team_id}/invitations}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listPendingInvitations(String, String, GitHubPageRequest)}.
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param teamId team id
     * @param request pagination token
     * @return first HTTP page of invitations
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubOrganizationInvitation> listPendingInvitationsLegacy(
            final long teamId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TEAM_INVITATIONS, Map.of(), request, INVITATIONS_TYPE, teamId);
    }

    /**
     * List team members (legacy) ({@code GET /teams/{team_id}/members}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listMembers(String, String)}.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembersLegacy(long, GitHubPageRequest)}.
     *
     * @param teamId team id
     * @return first HTTP page of members
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubTeamMember> listMembersLegacy(final long teamId) throws IOException {
        return listMembersLegacy(teamId, GitHubListTeamMembersFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List team members (legacy) ({@code GET /teams/{team_id}/members}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listMembers(String, String, GitHubListTeamMembersFilter)}.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembersLegacy(long, GitHubListTeamMembersFilter,
     * GitHubPageRequest)}.
     *
     * @param teamId team id
     * @param filter query filters
     * @return first HTTP page of members
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubTeamMember> listMembersLegacy(final long teamId, final GitHubListTeamMembersFilter filter)
            throws IOException {
        return listMembersLegacy(teamId, filter, GitHubPageRequest.byPage());
    }

    /**
     * List team members (legacy) ({@code GET /teams/{team_id}/members}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listMembers(String, String, GitHubPageRequest)}.
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param teamId team id
     * @param request pagination token
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubTeamMember> listMembersLegacy(final long teamId, final GitHubPageRequest request)
            throws IOException {
        return listMembersLegacy(teamId, GitHubListTeamMembersFilter.empty(), request);
    }

    /**
     * List team members (legacy) ({@code GET /teams/{team_id}/members}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listMembers(String, String, GitHubListTeamMembersFilter,
     * GitHubPageRequest)}.
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param teamId team id
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubTeamMember> listMembersLegacy(
            final long teamId, final GitHubListTeamMembersFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TEAM_MEMBERS, filter.toQueryParameters(), request, MEMBERS_TYPE, teamId);
    }

    /**
     * Check team membership for a user (legacy) ({@code GET /teams/{team_id}/members/{username}}).
     *
     * <p><strong>Legacy.</strong> Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param teamId team id
     * @param username GitHub username
     * @return whether the user is a team member
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public boolean isMemberLegacy(final long teamId, final String username) throws IOException {
        return caller.isExisting(get(TEAM_MEMBER, teamId, username));
    }

    /**
     * Add a team member (legacy) ({@code PUT /teams/{team_id}/members/{username}}).
     *
     * <p><strong>Legacy.</strong>
     *
     * @param teamId team id
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public void addMemberLegacy(final long teamId, final String username) throws IOException {
        caller.send(put(TEAM_MEMBER, teamId, username));
    }

    /**
     * Remove a team member (legacy) ({@code DELETE /teams/{team_id}/members/{username}}).
     *
     * <p><strong>Legacy.</strong>
     *
     * @param teamId team id
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public void removeMemberLegacy(final long teamId, final String username) throws IOException {
        caller.send(delete(TEAM_MEMBER, teamId, username));
    }

    /**
     * Get team membership for a user (legacy) ({@code GET /teams/{team_id}/memberships/{username}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #getMembership(String, String, String)}.
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param teamId team id
     * @param username GitHub username
     * @return the membership, or empty if the user is not a team member
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public Optional<GitHubTeamMembership> getMembershipLegacy(final long teamId, final String username)
            throws IOException {

        return caller.fetchOrEmpty(get(TEAM_MEMBERSHIP, teamId, username), GitHubTeamMembership.class);
    }

    /**
     * Add or update team membership (legacy) ({@code PUT /teams/{team_id}/memberships/{username}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #upsertMembership(String, String, String)}.
     *
     * @param teamId team id
     * @param username GitHub username
     * @return the membership
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubTeamMembership upsertMembershipLegacy(final long teamId, final String username) throws IOException {
        return caller.fetch(put(TEAM_MEMBERSHIP, teamId, username), GitHubTeamMembership.class);
    }

    /**
     * Add or update team membership (legacy) ({@code PUT /teams/{team_id}/memberships/{username}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #upsertMembership(String, String, String,
     * GitHubUpsertTeamMembershipPayload)}.
     *
     * @param teamId team id
     * @param username GitHub username
     * @param payload membership role
     * @return the membership
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubTeamMembership upsertMembershipLegacy(
            final long teamId, final String username, final GitHubUpsertTeamMembershipPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(TEAM_MEMBERSHIP, teamId, username), payload), GitHubTeamMembership.class);
    }

    /**
     * Remove team membership (legacy) ({@code DELETE /teams/{team_id}/memberships/{username}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #removeMembership(String, String, String)}.
     *
     * @param teamId team id
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public void removeMembershipLegacy(final long teamId, final String username) throws IOException {
        caller.send(delete(TEAM_MEMBERSHIP, teamId, username));
    }
}
