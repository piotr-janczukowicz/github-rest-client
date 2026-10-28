package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallation;
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

/** GitHub Enterprise Cloud organization installations REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminOrganizationInstallationsApi extends GitHubResourceApi {

    private static final PathTemplate INSTALLABLE_ORGANIZATIONS =
            PathTemplate.of("/enterprises/{enterprise}/apps/installable_organizations");
    private static final PathTemplate INSTALLABLE_ORGANIZATION_REPOSITORIES =
            PathTemplate.of("/enterprises/{enterprise}/apps/installable_organizations/{org}/accessible_repositories");
    private static final PathTemplate ORGANIZATION_INSTALLATIONS =
            PathTemplate.of("/enterprises/{enterprise}/apps/organizations/{org}/installations");
    private static final PathTemplate ORGANIZATION_INSTALLATION =
            PathTemplate.of("/enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}");
    private static final PathTemplate INSTALLATION_REPOSITORIES = PathTemplate.of(
            "/enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories");
    private static final PathTemplate INSTALLATION_REPOSITORIES_ADD = PathTemplate.of(
            "/enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories/add");
    private static final PathTemplate INSTALLATION_REPOSITORIES_REMOVE = PathTemplate.of(
            "/enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories/remove");

    private static final JsonType<List<GitHubInstallableOrganization>> INSTALLABLE_ORGANIZATIONS_TYPE =
            JsonTypes.listOf(GitHubInstallableOrganization.class);
    private static final JsonType<List<GitHubAccessibleRepository>> ACCESSIBLE_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubAccessibleRepository.class);
    private static final JsonType<List<GitHubEnterpriseOrganizationInstallation>> ORGANIZATION_INSTALLATIONS_TYPE =
            JsonTypes.listOf(GitHubEnterpriseOrganizationInstallation.class);

    /**
     * Organization installations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminOrganizationInstallationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the enterprise-owned organizations on which a GitHub App can be installed ({@code GET
     * /enterprises/{enterprise}/apps/installable_organizations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listInstallableOrganizations(String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubInstallableOrganization> listInstallableOrganizations(final String enterprise)
            throws IOException {
        return listInstallableOrganizations(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List the enterprise-owned organizations on which a GitHub App can be installed ({@code GET
     * /enterprises/{enterprise}/apps/installable_organizations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token
     * @return HTTP page of organizations
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubInstallableOrganization> listInstallableOrganizations(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(INSTALLABLE_ORGANIZATIONS, Map.of(), request, INSTALLABLE_ORGANIZATIONS_TYPE, enterprise);
    }

    /**
     * List the repositories of an enterprise-owned organization that a GitHub App can access ({@code GET
     * /enterprises/{enterprise}/apps/installable_organizations/{org}/accessible_repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listInstallableOrganizationAccessibleRepositories(String,
     * String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAccessibleRepository> listInstallableOrganizationAccessibleRepositories(
            final String enterprise, final String org) throws IOException {
        return listInstallableOrganizationAccessibleRepositories(enterprise, org, GitHubPageRequest.byPage());
    }

    /**
     * List the repositories of an enterprise-owned organization that a GitHub App can access ({@code GET
     * /enterprises/{enterprise}/apps/installable_organizations/{org}/accessible_repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAccessibleRepository> listInstallableOrganizationAccessibleRepositories(
            final String enterprise, final String org, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                INSTALLABLE_ORGANIZATION_REPOSITORIES,
                Map.of(),
                request,
                ACCESSIBLE_REPOSITORIES_TYPE,
                enterprise,
                org);
    }

    /**
     * List the installations of a GitHub App on an enterprise-owned organization ({@code GET
     * /enterprises/{enterprise}/apps/organizations/{org}/installations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOrganizationInstallations(String, String,
     * GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @return first HTTP page of installations
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubEnterpriseOrganizationInstallation> listOrganizationInstallations(
            final String enterprise, final String org) throws IOException {
        return listOrganizationInstallations(enterprise, org, GitHubPageRequest.byPage());
    }

    /**
     * List the installations of a GitHub App on an enterprise-owned organization ({@code GET
     * /enterprises/{enterprise}/apps/organizations/{org}/installations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param request pagination token
     * @return HTTP page of installations
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubEnterpriseOrganizationInstallation> listOrganizationInstallations(
            final String enterprise, final String org, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                ORGANIZATION_INSTALLATIONS, Map.of(), request, ORGANIZATION_INSTALLATIONS_TYPE, enterprise, org);
    }

    /**
     * Install a GitHub App on an enterprise-owned organization ({@code POST
     * /enterprises/{enterprise}/apps/organizations/{org}/installations}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param payload the client identifier and repository selection of the installation
     * @return the created installation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubInstallation createInstallation(
            final String enterprise, final String org, final GitHubCreateInstallationPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORGANIZATION_INSTALLATIONS, enterprise, org), payload), GitHubInstallation.class);
    }

    /**
     * Delete an installation of a GitHub App on an enterprise-owned organization ({@code DELETE
     * /enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param installationId the unique identifier of the installation
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteInstallation(final String enterprise, final String org, final long installationId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ORGANIZATION_INSTALLATION, enterprise, org, installationId));
    }

    /**
     * List the repositories accessible to an installation ({@code GET
     * /enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listInstallationRepositories(String, String, long,
     * GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param installationId the unique identifier of the installation
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAccessibleRepository> listInstallationRepositories(
            final String enterprise, final String org, final long installationId) throws IOException {
        return listInstallationRepositories(enterprise, org, installationId, GitHubPageRequest.byPage());
    }

    /**
     * List the repositories accessible to an installation ({@code GET
     * /enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param installationId the unique identifier of the installation
     * @param request pagination token
     * @return HTTP page of repositories
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAccessibleRepository> listInstallationRepositories(
            final String enterprise, final String org, final long installationId, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                INSTALLATION_REPOSITORIES,
                Map.of(),
                request,
                ACCESSIBLE_REPOSITORIES_TYPE,
                enterprise,
                org,
                installationId);
    }

    /**
     * Change the repository access selection of an installation ({@code PATCH
     * /enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param installationId the unique identifier of the installation
     * @param payload whether all or only selected repositories are accessible
     * @return the updated installation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubInstallation changeInstallationRepositoryAccessSelection(
            final String enterprise,
            final String org,
            final long installationId,
            final GitHubChangeRepositoryAccessSelectionPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(INSTALLATION_REPOSITORIES, enterprise, org, installationId), payload),
                GitHubInstallation.class);
    }

    /**
     * Grant repository access to an installation ({@code PATCH
     * /enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories/add}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param installationId the unique identifier of the installation
     * @param payload the names of the repositories to grant access to
     * @return the repositories the installation can now access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubAccessibleRepository> grantRepositoryAccessToInstallation(
            final String enterprise,
            final String org,
            final long installationId,
            final GitHubGrantRepositoryAccessPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(INSTALLATION_REPOSITORIES_ADD, enterprise, org, installationId), payload),
                ACCESSIBLE_REPOSITORIES_TYPE);
    }

    /**
     * Remove repository access from an installation ({@code PATCH
     * /enterprises/{enterprise}/apps/organizations/{org}/installations/{installation_id}/repositories/remove}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name
     * @param installationId the unique identifier of the installation
     * @param payload the names of the repositories to remove access from
     * @return the repositories the installation can still access
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubAccessibleRepository> removeRepositoryAccessFromInstallation(
            final String enterprise,
            final String org,
            final long installationId,
            final GitHubRemoveRepositoryAccessPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(INSTALLATION_REPOSITORIES_REMOVE, enterprise, org, installationId), payload),
                ACCESSIBLE_REPOSITORIES_TYPE);
    }
}
