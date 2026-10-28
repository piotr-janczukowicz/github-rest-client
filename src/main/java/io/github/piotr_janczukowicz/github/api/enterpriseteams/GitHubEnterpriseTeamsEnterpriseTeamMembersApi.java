package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.teams.GitHubEnterpriseTeam;
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

/** GitHub enterprise team members REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseTeamsEnterpriseTeamMembersApi extends GitHubResourceApi {

    private static final PathTemplate MEMBER_TEAMS =
            PathTemplate.of("/enterprises/{enterprise}/members/{username}/teams");
    private static final PathTemplate TEAM_MEMBERSHIPS =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/memberships");
    private static final PathTemplate TEAM_MEMBERSHIPS_ADD =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/memberships/add");
    private static final PathTemplate TEAM_MEMBERSHIPS_REMOVE =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/memberships/remove");
    private static final PathTemplate TEAM_MEMBERSHIP =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/memberships/{username}");

    private static final JsonType<List<GitHubEnterpriseTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubEnterpriseTeam.class);
    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    /**
     * Enterprise team members resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseTeamsEnterpriseTeamMembersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List enterprise teams of a user ({@code GET /enterprises/{enterprise}/members/{username}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listTeamsForUser(String, String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param username the handle for the GitHub user account
     * @return first HTTP page of enterprise teams
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseTeam> listTeamsForUser(final String enterprise, final String username)
            throws IOException {
        return listTeamsForUser(enterprise, username, GitHubPageRequest.byPage());
    }

    /**
     * List enterprise teams of a user ({@code GET /enterprises/{enterprise}/members/{username}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param username the handle for the GitHub user account
     * @param request pagination token
     * @return first HTTP page of enterprise teams
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is empty, or {@code request} is a
     *     since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseTeam> listTeamsForUser(
            final String enterprise, final String username, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(MEMBER_TEAMS, Map.of(), request, TEAMS_TYPE, enterprise, username);
    }

    /**
     * List members of an enterprise team ({@code GET /enterprises/{enterprise}/teams/{enterprise-team}/memberships}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listMembers(String, String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSimpleUser> listMembers(final String enterprise, final String enterpriseTeam)
            throws IOException {
        return listMembers(enterprise, enterpriseTeam, GitHubPageRequest.byPage());
    }

    /**
     * List members of an enterprise team ({@code GET /enterprises/{enterprise}/teams/{enterprise-team}/memberships}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param request pagination token
     * @return first HTTP page of members
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty, or {@code request} is
     *     a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubSimpleUser> listMembers(
            final String enterprise, final String enterpriseTeam, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TEAM_MEMBERSHIPS, Map.of(), request, USERS_TYPE, enterprise, enterpriseTeam);
    }

    /**
     * Add users to an enterprise team ({@code POST /enterprises/{enterprise}/teams/{enterprise-team}/memberships/add}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param payload handles of the users to add
     * @return the users that were added
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubSimpleUser> bulkAddMembers(
            final String enterprise,
            final String enterpriseTeam,
            final GitHubBulkAddEnterpriseTeamMembershipsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(TEAM_MEMBERSHIPS_ADD, enterprise, enterpriseTeam), payload), USERS_TYPE);
    }

    /**
     * Remove users from an enterprise team ({@code POST
     * /enterprises/{enterprise}/teams/{enterprise-team}/memberships/remove}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param payload handles of the users to remove
     * @return the users that were removed
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubSimpleUser> bulkRemoveMembers(
            final String enterprise,
            final String enterpriseTeam,
            final GitHubBulkRemoveEnterpriseTeamMembershipsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(TEAM_MEMBERSHIPS_REMOVE, enterprise, enterpriseTeam), payload), USERS_TYPE);
    }

    /**
     * Get the membership of a user in an enterprise team ({@code GET
     * /enterprises/{enterprise}/teams/{enterprise-team}/memberships/{username}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param username the handle for the GitHub user account
     * @return the user, or empty if the user is not a member of the team
     * @throws IllegalArgumentException if {@code enterprise}, {@code enterpriseTeam}, or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubSimpleUser> getMembership(
            final String enterprise, final String enterpriseTeam, final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(TEAM_MEMBERSHIP, enterprise, enterpriseTeam, username), GitHubSimpleUser.class);
    }

    /**
     * Add a user to an enterprise team ({@code PUT
     * /enterprises/{enterprise}/teams/{enterprise-team}/memberships/{username}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param username the handle for the GitHub user account
     * @return the user added to the team
     * @throws IllegalArgumentException if {@code enterprise}, {@code enterpriseTeam}, or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubSimpleUser addMembership(final String enterprise, final String enterpriseTeam, final String username)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(put(TEAM_MEMBERSHIP, enterprise, enterpriseTeam, username), GitHubSimpleUser.class);
    }

    /**
     * Remove a user from an enterprise team ({@code DELETE
     * /enterprises/{enterprise}/teams/{enterprise-team}/memberships/{username}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param username the handle for the GitHub user account
     * @throws IllegalArgumentException if {@code enterprise}, {@code enterpriseTeam}, or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void removeMembership(final String enterprise, final String enterpriseTeam, final String username)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(TEAM_MEMBERSHIP, enterprise, enterpriseTeam, username));
    }
}
