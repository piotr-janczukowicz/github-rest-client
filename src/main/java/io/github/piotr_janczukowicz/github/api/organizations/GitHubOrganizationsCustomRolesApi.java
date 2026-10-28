package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub organization custom roles REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsCustomRolesApi extends GitHubResourceApi {

    private static final String REMOVED_MESSAGE =
            "The organization custom repository roles operation was removed from the GitHub REST API; "
                    + "use listCustomRepoRoles instead.";
    private static final String DEPRECATED_CREATE_SINCE = "2023-03-06";

    private static final PathTemplate CUSTOM_REPO_ROLES = PathTemplate.of("/orgs/{org}/custom-repository-roles");
    private static final PathTemplate CUSTOM_REPO_ROLE =
            PathTemplate.of("/orgs/{org}/custom-repository-roles/{role_id}");
    private static final PathTemplate CUSTOM_ROLES = PathTemplate.of("/orgs/{org}/custom_roles");
    private static final PathTemplate CUSTOM_ROLE = PathTemplate.of("/orgs/{org}/custom_roles/{role_id}");
    private static final PathTemplate FINE_GRAINED_PERMISSIONS =
            PathTemplate.of("/orgs/{org}/fine_grained_permissions");
    private static final PathTemplate REPOSITORY_FINE_GRAINED_PERMISSIONS =
            PathTemplate.of("/orgs/{org}/repository-fine-grained-permissions");

    private static final JsonType<List<GitHubRepositoryFineGrainedPermission>> FINE_GRAINED_PERMISSIONS_TYPE =
            JsonTypes.listOf(GitHubRepositoryFineGrainedPermission.class);

    /**
     * Custom roles resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsCustomRolesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List custom repository roles in an organization by organization ID ({@code GET
     * /organizations/{organization_id}/custom_roles}).
     *
     * @param organizationId the unique identifier of the organization
     * @throws UnsupportedOperationException always, the operation was removed from the GitHub REST API
     * @deprecated This operation was closed and removed by GitHub and always fails without an HTTP call. Use
     *     {@link #listCustomRepoRoles(String)} instead. Deprecation date {@code 2023-02-15}; removal date
     *     {@code 2025-02-15}.
     */
    @Deprecated(since = "2023-02-15")
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void listCustomRoles(final String organizationId) {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        throw new UnsupportedOperationException(REMOVED_MESSAGE);
    }

    /**
     * List custom repository roles in an organization ({@code GET /orgs/{org}/custom-repository-roles}).
     *
     * @param org organization name
     * @return the custom repository roles of the organization
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubListCustomRepoRolesResponse listCustomRepoRoles(final String org) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(CUSTOM_REPO_ROLES, org), GitHubListCustomRepoRolesResponse.class);
    }

    /**
     * Create a custom repository role ({@code POST /orgs/{org}/custom-repository-roles}).
     *
     * @param org organization name
     * @param payload the custom repository role to create
     * @return the created custom repository role
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubOrganizationCustomRepositoryRole createCustomRepoRole(
            final String org, final GitHubCreateCustomRepoRolePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(CUSTOM_REPO_ROLES, org), payload), GitHubOrganizationCustomRepositoryRole.class);
    }

    /**
     * Get a custom repository role ({@code GET /orgs/{org}/custom-repository-roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId the unique identifier of the role
     * @return the custom repository role, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubOrganizationCustomRepositoryRole> getCustomRepoRole(final String org, final long roleId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(CUSTOM_REPO_ROLE, org, roleId), GitHubOrganizationCustomRepositoryRole.class);
    }

    /**
     * Update a custom repository role ({@code PATCH /orgs/{org}/custom-repository-roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId the unique identifier of the role
     * @param payload the fields to update
     * @return the updated custom repository role
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubOrganizationCustomRepositoryRole updateCustomRepoRole(
            final String org, final long roleId, final GitHubUpdateCustomRepoRolePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(CUSTOM_REPO_ROLE, org, roleId), payload),
                GitHubOrganizationCustomRepositoryRole.class);
    }

    /**
     * Delete a custom repository role ({@code DELETE /orgs/{org}/custom-repository-roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId the unique identifier of the role
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteCustomRepoRole(final String org, final long roleId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(CUSTOM_REPO_ROLE, org, roleId));
    }

    /**
     * Create a custom repository role ({@code POST /orgs/{org}/custom_roles}).
     *
     * @param org organization name
     * @param payload the custom repository role to create
     * @return the created custom repository role
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated The operation is closing down; use {@link #createCustomRepoRole(String,
     *     GitHubCreateCustomRepoRolePayload)} instead. Deprecation date {@code 2023-03-06}; removal date
     *     {@code 2023-09-06}.
     */
    @Deprecated(since = DEPRECATED_CREATE_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public GitHubOrganizationCustomRepositoryRole createCustomRole(
            final String org, final GitHubCreateCustomRolePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(CUSTOM_ROLES, org), payload), GitHubOrganizationCustomRepositoryRole.class);
    }

    /**
     * Get a custom repository role ({@code GET /orgs/{org}/custom_roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId the unique identifier of the role
     * @return the custom repository role, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated The operation is closing down; use {@link #getCustomRepoRole(String, long)} instead. Deprecation date
     *     {@code 2023-03-06}.
     */
    @Deprecated(since = DEPRECATED_CREATE_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubOrganizationCustomRepositoryRole> getCustomRole(final String org, final long roleId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(CUSTOM_ROLE, org, roleId), GitHubOrganizationCustomRepositoryRole.class);
    }

    /**
     * Update a custom repository role ({@code PATCH /orgs/{org}/custom_roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId the unique identifier of the role
     * @param payload the fields to update
     * @return the updated custom repository role
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated The operation is closing down; use {@link #updateCustomRepoRole(String, long,
     *     GitHubUpdateCustomRepoRolePayload)} instead. Deprecation date {@code 2023-03-06}.
     */
    @Deprecated(since = DEPRECATED_CREATE_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public GitHubOrganizationCustomRepositoryRole updateCustomRole(
            final String org, final long roleId, final GitHubUpdateCustomRolePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(CUSTOM_ROLE, org, roleId), payload), GitHubOrganizationCustomRepositoryRole.class);
    }

    /**
     * Delete a custom repository role ({@code DELETE /orgs/{org}/custom_roles/{role_id}}).
     *
     * @param org organization name
     * @param roleId the unique identifier of the role
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated The operation is closing down; use {@link #deleteCustomRepoRole(String, long)} instead. Deprecation
     *     date {@code 2023-03-06}.
     */
    @Deprecated(since = DEPRECATED_CREATE_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public void deleteCustomRole(final String org, final long roleId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(CUSTOM_ROLE, org, roleId));
    }

    /**
     * List the fine-grained permissions of repository roles ({@code GET /orgs/{org}/fine_grained_permissions}).
     *
     * @param org organization name
     * @return fine-grained permissions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated The operation is closing down; use {@link #listRepoFineGrainedPermissions(String)} instead.
     *     Deprecation date {@code 2023-03-06}.
     */
    @Deprecated(since = DEPRECATED_CREATE_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public List<GitHubRepositoryFineGrainedPermission> listFineGrainedPermissions(final String org) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(FINE_GRAINED_PERMISSIONS, org), FINE_GRAINED_PERMISSIONS_TYPE);
    }

    /**
     * List the fine-grained permissions of repository roles ({@code GET
     * /orgs/{org}/repository-fine-grained-permissions}).
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
    public List<GitHubRepositoryFineGrainedPermission> listRepoFineGrainedPermissions(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(REPOSITORY_FINE_GRAINED_PERMISSIONS, org), FINE_GRAINED_PERMISSIONS_TYPE);
    }
}
