package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.teams.GitHubEnterpriseTeam;
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

/** GitHub Enterprise Cloud enterprise roles REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminEnterpriseRolesApi extends GitHubResourceApi {

    private static final PathTemplate ROLES = PathTemplate.of("/enterprises/{enterprise}/enterprise-roles");
    private static final PathTemplate ROLE = PathTemplate.of("/enterprises/{enterprise}/enterprise-roles/{role_id}");
    private static final PathTemplate ROLE_TEAMS =
            PathTemplate.of("/enterprises/{enterprise}/enterprise-roles/{role_id}/teams");
    private static final PathTemplate ROLE_USERS =
            PathTemplate.of("/enterprises/{enterprise}/enterprise-roles/{role_id}/users");
    private static final PathTemplate TEAM_ROLES =
            PathTemplate.of("/enterprises/{enterprise}/enterprise-roles/teams/{team_slug}");
    private static final PathTemplate TEAM_ROLE =
            PathTemplate.of("/enterprises/{enterprise}/enterprise-roles/teams/{team_slug}/{role_id}");
    private static final PathTemplate USER_ROLES =
            PathTemplate.of("/enterprises/{enterprise}/enterprise-roles/users/{username}");
    private static final PathTemplate USER_ROLE =
            PathTemplate.of("/enterprises/{enterprise}/enterprise-roles/users/{username}/{role_id}");

    private static final JsonType<List<GitHubEnterpriseTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubEnterpriseTeam.class);
    private static final JsonType<List<GitHubEnterpriseUserRoleAssignment>> USERS_TYPE =
            JsonTypes.listOf(GitHubEnterpriseUserRoleAssignment.class);

    /**
     * Enterprise roles resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminEnterpriseRolesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the enterprise roles available to an enterprise ({@code GET /enterprises/{enterprise}/enterprise-roles}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return enterprise roles with their total count
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubEnterpriseRoleList listEnterpriseRoles(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(ROLES, enterprise), GitHubEnterpriseRoleList.class);
    }

    /**
     * Get an enterprise role ({@code GET /enterprises/{enterprise}/enterprise-roles/{role_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param roleId the unique identifier of the role
     * @return enterprise role, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubEnterpriseRole> getEnterpriseRole(final String enterprise, final long roleId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ROLE, enterprise, roleId), GitHubEnterpriseRole.class);
    }

    /**
     * List the teams assigned to an enterprise role ({@code GET
     * /enterprises/{enterprise}/enterprise-roles/{role_id}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listEnterpriseRoleTeams(String, long,
     * GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param roleId the unique identifier of the role
     * @return first HTTP page of teams
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseTeam> listEnterpriseRoleTeams(final String enterprise, final long roleId)
            throws IOException {
        return listEnterpriseRoleTeams(enterprise, roleId, GitHubPageRequest.byPage());
    }

    /**
     * List the teams assigned to an enterprise role ({@code GET
     * /enterprises/{enterprise}/enterprise-roles/{role_id}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param roleId the unique identifier of the role
     * @param request pagination token
     * @return HTTP page of teams
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseTeam> listEnterpriseRoleTeams(
            final String enterprise, final long roleId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ROLE_TEAMS, Map.of(), request, TEAMS_TYPE, enterprise, roleId);
    }

    /**
     * List the users assigned to an enterprise role ({@code GET
     * /enterprises/{enterprise}/enterprise-roles/{role_id}/users}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listEnterpriseRoleUsers(String, long,
     * GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param roleId the unique identifier of the role
     * @return first HTTP page of user role assignments
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseUserRoleAssignment> listEnterpriseRoleUsers(
            final String enterprise, final long roleId) throws IOException {
        return listEnterpriseRoleUsers(enterprise, roleId, GitHubPageRequest.byPage());
    }

    /**
     * List the users assigned to an enterprise role ({@code GET
     * /enterprises/{enterprise}/enterprise-roles/{role_id}/users}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param roleId the unique identifier of the role
     * @param request pagination token
     * @return HTTP page of user role assignments
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubEnterpriseUserRoleAssignment> listEnterpriseRoleUsers(
            final String enterprise, final long roleId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ROLE_USERS, Map.of(), request, USERS_TYPE, enterprise, roleId);
    }

    /**
     * Assign an enterprise role to a team ({@code PUT
     * /enterprises/{enterprise}/enterprise-roles/teams/{team_slug}/{role_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamSlug the slug of the enterprise team name
     * @param roleId the unique identifier of the role
     * @throws IllegalArgumentException if {@code enterprise} or {@code teamSlug} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void assignTeamToEnterpriseRole(final String enterprise, final String teamSlug, final long roleId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(put(TEAM_ROLE, enterprise, teamSlug, roleId));
    }

    /**
     * Remove an enterprise role from a team ({@code DELETE
     * /enterprises/{enterprise}/enterprise-roles/teams/{team_slug}/{role_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamSlug the slug of the enterprise team name
     * @param roleId the unique identifier of the role
     * @throws IllegalArgumentException if {@code enterprise} or {@code teamSlug} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void revokeEnterpriseRoleTeam(final String enterprise, final String teamSlug, final long roleId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(TEAM_ROLE, enterprise, teamSlug, roleId));
    }

    /**
     * Remove all enterprise roles from a team ({@code DELETE
     * /enterprises/{enterprise}/enterprise-roles/teams/{team_slug}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamSlug the slug of the enterprise team name
     * @throws IllegalArgumentException if {@code enterprise} or {@code teamSlug} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void revokeAllEnterpriseRolesTeam(final String enterprise, final String teamSlug) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(TEAM_ROLES, enterprise, teamSlug));
    }

    /**
     * Assign an enterprise role to a user ({@code PUT
     * /enterprises/{enterprise}/enterprise-roles/users/{username}/{role_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param username the handle for the GitHub user account
     * @param roleId the unique identifier of the role
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void assignEnterpriseRoleToUser(final String enterprise, final String username, final long roleId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(put(USER_ROLE, enterprise, username, roleId));
    }

    /**
     * Remove an enterprise role from a user ({@code DELETE
     * /enterprises/{enterprise}/enterprise-roles/users/{username}/{role_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param username the handle for the GitHub user account
     * @param roleId the unique identifier of the role
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void removeEnterpriseUserRoleAssignment(final String enterprise, final String username, final long roleId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(USER_ROLE, enterprise, username, roleId));
    }

    /**
     * Remove all enterprise roles from a user ({@code DELETE
     * /enterprises/{enterprise}/enterprise-roles/users/{username}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param username the handle for the GitHub user account
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void removeAllEnterpriseRolesFromUser(final String enterprise, final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(USER_ROLES, enterprise, username));
    }
}
