package io.github.piotr_janczukowicz.github.api.organizations;

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

/** GitHub organization roles REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsOrganizationRolesApi extends GitHubResourceApi {

    private static final PathTemplate ORGANIZATION_ROLES = PathTemplate.of("/orgs/{org}/organization-roles");
    private static final PathTemplate ORGANIZATION_ROLES_TEAM =
            PathTemplate.of("/orgs/{org}/organization-roles/teams/{team_slug}");
    private static final PathTemplate ORGANIZATION_ROLES_TEAM_ROLE =
            PathTemplate.of("/orgs/{org}/organization-roles/teams/{team_slug}/{role_id}");
    private static final PathTemplate ORGANIZATION_ROLES_USER =
            PathTemplate.of("/orgs/{org}/organization-roles/users/{username}");
    private static final PathTemplate ORGANIZATION_ROLES_USER_ROLE =
            PathTemplate.of("/orgs/{org}/organization-roles/users/{username}/{role_id}");
    private static final PathTemplate ORGANIZATION_ROLE = PathTemplate.of("/orgs/{org}/organization-roles/{role_id}");
    private static final PathTemplate ORGANIZATION_ROLE_TEAMS =
            PathTemplate.of("/orgs/{org}/organization-roles/{role_id}/teams");
    private static final PathTemplate ORGANIZATION_ROLE_USERS =
            PathTemplate.of("/orgs/{org}/organization-roles/{role_id}/users");
    private static final PathTemplate ORGANIZATION_FINE_GRAINED_PERMISSIONS =
            PathTemplate.of("/orgs/{org}/organization-fine-grained-permissions");

    private static final JsonType<List<GitHubTeamRoleAssignment>> TEAM_ROLE_ASSIGNMENTS_TYPE =
            JsonTypes.listOf(GitHubTeamRoleAssignment.class);
    private static final JsonType<List<GitHubUserRoleAssignment>> USER_ROLE_ASSIGNMENTS_TYPE =
            JsonTypes.listOf(GitHubUserRoleAssignment.class);
    private static final JsonType<List<GitHubOrganizationFineGrainedPermission>> ORGANIZATION_FINE_GRAINED_TYPE =
            JsonTypes.listOf(GitHubOrganizationFineGrainedPermission.class);

    /**
     * Organization roles resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsOrganizationRolesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization roles for an organization ({@code GET /orgs/{org}/organization-roles}).
     *
     * @param org organization name
     * @return organization roles page
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOrganizationRolesPage list(final String org) throws IOException {
        return caller.fetch(get(ORGANIZATION_ROLES, org), GitHubOrganizationRolesPage.class);
    }

    /**
     * Remove all organization roles for a team ({@code DELETE /orgs/{org}/organization-roles/teams/{team_slug}}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void revokeAllForTeam(final String org, final String teamSlug) throws IOException {
        caller.send(delete(ORGANIZATION_ROLES_TEAM, org, teamSlug));
    }

    /**
     * Assign an organization role to a team ({@code PUT /orgs/{org}/organization-roles/teams/{team_slug}/{role_id}}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @param roleId organization role id
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void assignTeam(final String org, final String teamSlug, final long roleId) throws IOException {
        caller.send(put(ORGANIZATION_ROLES_TEAM_ROLE, org, teamSlug, roleId));
    }

    /**
     * Remove an organization role from a team ({@code DELETE
     * /orgs/{org}/organization-roles/teams/{team_slug}/{role_id}}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @param roleId organization role id
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void revokeTeam(final String org, final String teamSlug, final long roleId) throws IOException {
        caller.send(delete(ORGANIZATION_ROLES_TEAM_ROLE, org, teamSlug, roleId));
    }

    /**
     * Remove all organization roles for a user ({@code DELETE /orgs/{org}/organization-roles/users/{username}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void revokeAllForUser(final String org, final String username) throws IOException {
        caller.send(delete(ORGANIZATION_ROLES_USER, org, username));
    }

    /**
     * Assign an organization role to a user ({@code PUT /orgs/{org}/organization-roles/users/{username}/{role_id}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @param roleId organization role id
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void assignUser(final String org, final String username, final long roleId) throws IOException {
        caller.send(put(ORGANIZATION_ROLES_USER_ROLE, org, username, roleId));
    }

    /**
     * Remove an organization role from a user ({@code DELETE
     * /orgs/{org}/organization-roles/users/{username}/{role_id}}).
     *
     * @param org organization name
     * @param username GitHub username
     * @param roleId organization role id
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void revokeUser(final String org, final String username, final long roleId) throws IOException {
        caller.send(delete(ORGANIZATION_ROLES_USER_ROLE, org, username, roleId));
    }

    /**
     * Get an organization role ({@code GET /orgs/{org}/organization-roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId organization role id
     * @return organization role, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubOrganizationRole> get(final String org, final long roleId) throws IOException {
        return caller.fetchOrEmpty(get(ORGANIZATION_ROLE, org, roleId), GitHubOrganizationRole.class);
    }

    /**
     * List teams assigned to an organization role ({@code GET /orgs/{org}/organization-roles/{role_id}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listTeams(String, long, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param roleId organization role id
     * @return first HTTP page of team role assignments
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamRoleAssignment> listTeams(final String org, final long roleId) throws IOException {
        return listTeams(org, roleId, GitHubPageRequest.byPage());
    }

    /**
     * List teams assigned to an organization role ({@code GET /orgs/{org}/organization-roles/{role_id}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param roleId organization role id
     * @param request pagination token
     * @return first HTTP page of team role assignments
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamRoleAssignment> listTeams(
            final String org, final long roleId, final GitHubPageRequest request) throws IOException {

        return pageList(ORGANIZATION_ROLE_TEAMS, Map.of(), request, TEAM_ROLE_ASSIGNMENTS_TYPE, org, roleId);
    }

    /**
     * List users assigned to an organization role ({@code GET /orgs/{org}/organization-roles/{role_id}/users}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listUsers(String, long, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param roleId organization role id
     * @return first HTTP page of user role assignments
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubUserRoleAssignment> listUsers(final String org, final long roleId) throws IOException {
        return listUsers(org, roleId, GitHubPageRequest.byPage());
    }

    /**
     * List users assigned to an organization role ({@code GET /orgs/{org}/organization-roles/{role_id}/users}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param roleId organization role id
     * @param request pagination token
     * @return first HTTP page of user role assignments
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubUserRoleAssignment> listUsers(
            final String org, final long roleId, final GitHubPageRequest request) throws IOException {

        return pageList(ORGANIZATION_ROLE_USERS, Map.of(), request, USER_ROLE_ASSIGNMENTS_TYPE, org, roleId);
    }

    /**
     * List the fine-grained permissions that can be assigned to organization roles ({@code GET
     * /orgs/{org}/organization-fine-grained-permissions}).
     *
     * @param org organization name
     * @return fine-grained permissions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubOrganizationFineGrainedPermission> listOrganizationFineGrainedPermissions(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ORGANIZATION_FINE_GRAINED_PERMISSIONS, org), ORGANIZATION_FINE_GRAINED_TYPE);
    }

    /**
     * Create a custom organization role ({@code POST /orgs/{org}/organization-roles}).
     *
     * @param org organization name
     * @param payload the custom role to create
     * @return the created organization role
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubOrganizationRole createCustomOrganizationRole(
            final String org, final GitHubCreateCustomOrganizationRolePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORGANIZATION_ROLES, org), payload), GitHubOrganizationRole.class);
    }

    /**
     * Update a custom organization role ({@code PATCH /orgs/{org}/organization-roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId organization role id
     * @param payload the fields to update
     * @return the updated organization role
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubOrganizationRole patchCustomOrganizationRole(
            final String org, final long roleId, final GitHubPatchCustomOrganizationRolePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ORGANIZATION_ROLE, org, roleId), payload), GitHubOrganizationRole.class);
    }

    /**
     * Delete a custom organization role ({@code DELETE /orgs/{org}/organization-roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId organization role id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteCustomOrganizationRole(final String org, final long roleId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ORGANIZATION_ROLE, org, roleId));
    }
}
