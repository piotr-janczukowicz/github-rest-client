package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub organizations REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationApi extends GitHubResourceApi {

    private static final PathTemplate USERS_ORGS = PathTemplate.of("/users/{username}/orgs");
    private static final PathTemplate SECURITY_PRODUCT_ENABLEMENT =
            PathTemplate.of("/orgs/{org}/{security_product}/{enablement}");
    private static final PathTemplate SETTINGS_IMMUTABLE_RELEASES_REPOSITORY =
            PathTemplate.of("/orgs/{org}/settings/immutable-releases/repositories/{repository_id}");
    private static final PathTemplate SETTINGS_IMMUTABLE_RELEASES_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/settings/immutable-releases/repositories");
    private static final PathTemplate SETTINGS_IMMUTABLE_RELEASES =
            PathTemplate.of("/orgs/{org}/settings/immutable-releases");
    private static final PathTemplate INSTALLATIONS = PathTemplate.of("/orgs/{org}/installations");
    private static final PathTemplate ORG = PathTemplate.of("/orgs/{org}");
    private static final PathTemplate ORGANIZATIONS = PathTemplate.of("/organizations");
    private static final PathTemplate USER_ORGS = PathTemplate.of("/user/orgs");
    private static final PathTemplate AUDIT_LOG = PathTemplate.of("/orgs/{org}/audit-log");
    private static final PathTemplate CREDENTIAL_AUTHORIZATIONS =
            PathTemplate.of("/orgs/{org}/credential-authorizations");
    private static final PathTemplate CREDENTIAL_AUTHORIZATIONS_REVOKE_ALL =
            PathTemplate.of("/orgs/{org}/credential-authorizations/revoke-all");
    private static final PathTemplate CREDENTIAL_AUTHORIZATIONS_REVOKE_TYPE =
            PathTemplate.of("/orgs/{org}/credential-authorizations/revoke-credential-type");
    private static final PathTemplate CREDENTIAL_AUTHORIZATION =
            PathTemplate.of("/orgs/{org}/credential-authorizations/{credential_id}");
    private static final PathTemplate CREDENTIAL_AUTHORIZATIONS_USER =
            PathTemplate.of("/orgs/{org}/credential-authorizations/{username}/revoke");
    private static final PathTemplate CREDENTIAL_AUTHORIZATIONS_USER_TYPE =
            PathTemplate.of("/orgs/{org}/credential-authorizations/{username}/revoke-credential-type");

    private static final GitHubListSpec<GitHubAuditLogEvent> AUDIT_LOG_SPEC =
            GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, JsonTypes.listOf(GitHubAuditLogEvent.class));
    private static final JsonType<List<GitHubCredentialAuthorization>> CREDENTIAL_AUTHORIZATIONS_TYPE =
            JsonTypes.listOf(GitHubCredentialAuthorization.class);

    private static final String PARAM_SECURITY_PRODUCT = "securityProduct";
    private static final String PARAM_ENABLEMENT = "enablement";

    private static final JsonType<List<GitHubOrganizationSimple>> ORGANIZATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationSimple.class);

    /**
     * Organizations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organizations ({@code GET /organizations}).
     *
     * <p>Uses {@link GitHubPageRequest#none()}. See {@link #list(GitHubPageRequest)}.
     *
     * @return first HTTP page of organizations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSimple> list() throws IOException {
        return list(GitHubPageRequest.none());
    }

    /**
     * List organizations ({@code GET /organizations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest} only; page and cursor variants are
     * rejected. Pagination is powered exclusively by the {@code since} parameter.
     *
     * @param request pagination token
     * @return first HTTP page of organizations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a page or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSimple> list(final GitHubPageRequest request) throws IOException {
        return list(ORGANIZATIONS, Map.of(), request, GitHubListSpec.since(ORGANIZATIONS_TYPE));
    }

    /**
     * Get an organization ({@code GET /orgs/{org}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param org organization name
     * @return the organization, or empty if GitHub has no such organization
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubOrganizationFull> get(final String org) throws IOException {
        return caller.fetchOrEmpty(super.get(ORG, org), GitHubOrganizationFull.class);
    }

    /**
     * Update an organization ({@code PATCH /orgs/{org}}).
     *
     * @param org organization name
     * @param payload organization updates
     * @return the updated organization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOrganizationFull update(final String org, final GitHubUpdateOrganizationPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ORG, org), payload), GitHubOrganizationFull.class);
    }

    /**
     * Delete an organization ({@code DELETE /orgs/{org}}).
     *
     * @param org organization name
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void delete(final String org) throws IOException {
        caller.send(super.delete(ORG, org));
    }

    /**
     * List app installations for an organization ({@code GET /orgs/{org}/installations}).
     *
     * @param org organization name
     * @return first HTTP page of app installations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubInstallation> listAppInstallations(final String org) throws IOException {
        return listAppInstallations(org, GitHubPageRequest.byPage());
    }

    /**
     * List app installations for an organization ({@code GET /orgs/{org}/installations}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of app installations
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubInstallation> listAppInstallations(final String org, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                INSTALLATIONS,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubOrganizationInstallationsPage.class,
                        GitHubOrganizationInstallationsPage::installations,
                        GitHubOrganizationInstallationsPage::totalCount),
                org);
    }

    /**
     * Get immutable releases settings for an organization ({@code GET /orgs/{org}/settings/immutable-releases}).
     *
     * @param org organization name
     * @return immutable releases settings
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubImmutableReleasesOrganizationSettings getImmutableReleasesSettings(final String org)
            throws IOException {

        return caller.fetch(
                super.get(SETTINGS_IMMUTABLE_RELEASES, org), GitHubImmutableReleasesOrganizationSettings.class);
    }

    /**
     * Set immutable releases settings for an organization ({@code PUT /orgs/{org}/settings/immutable-releases}).
     *
     * @param org organization name
     * @param payload immutable releases settings
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setImmutableReleasesSettings(final String org, final GitHubSetImmutableReleasesSettingsPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(SETTINGS_IMMUTABLE_RELEASES, org), payload));
    }

    /**
     * List selected repositories for immutable releases enforcement ({@code GET
     * /orgs/{org}/settings/immutable-releases/repositories}).
     *
     * @param org organization name
     * @return first HTTP page of selected repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> getImmutableReleasesSettingsRepositories(final String org)
            throws IOException {
        return getImmutableReleasesSettingsRepositories(org, GitHubPageRequest.byPage());
    }

    /**
     * List selected repositories for immutable releases enforcement ({@code GET
     * /orgs/{org}/settings/immutable-releases/repositories}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of selected repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> getImmutableReleasesSettingsRepositories(
            final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(
                SETTINGS_IMMUTABLE_RELEASES_REPOSITORIES,
                Map.of(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.PAGE,
                        GitHubImmutableReleasesSelectedRepositoriesPage.class,
                        GitHubImmutableReleasesSelectedRepositoriesPage::repositories,
                        GitHubImmutableReleasesSelectedRepositoriesPage::totalCount),
                org);
    }

    /**
     * Set selected repositories for immutable releases enforcement ({@code PUT
     * /orgs/{org}/settings/immutable-releases/repositories}).
     *
     * @param org organization name
     * @param payload selected repository ids
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setImmutableReleasesSettingsRepositories(
            final String org, final GitHubSetImmutableReleasesSelectedRepositoriesPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(SETTINGS_IMMUTABLE_RELEASES_REPOSITORIES, org), payload));
    }

    /**
     * Enable a selected repository for immutable releases in an organization ({@code PUT
     * /orgs/{org}/settings/immutable-releases/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void enableSelectedRepositoryImmutableReleases(final String org, final long repositoryId)
            throws IOException {

        caller.send(put(SETTINGS_IMMUTABLE_RELEASES_REPOSITORY, org, repositoryId));
    }

    /**
     * Disable a selected repository for immutable releases in an organization ({@code DELETE
     * /orgs/{org}/settings/immutable-releases/repositories/{repository_id}}).
     *
     * @param org organization name
     * @param repositoryId repository id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void disableSelectedRepositoryImmutableReleases(final String org, final long repositoryId)
            throws IOException {

        caller.send(super.delete(SETTINGS_IMMUTABLE_RELEASES_REPOSITORY, org, repositoryId));
    }

    /**
     * Enable or disable a security feature for an organization ({@code POST
     * /orgs/{org}/{security_product}/{enablement}}).
     *
     * @param org organization name
     * @param securityProduct security product
     * @param enablement enablement action
     * @throws NullPointerException if {@code securityProduct} or {@code enablement} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Closing down notice: the ability to enable or disable a security feature for all eligible
     *     repositories in an organization is closing down. Prefer code security configurations. Deprecation date
     *     {@code 2024-07-22}; removal date {@code 2025-07-22}.
     */
    @Deprecated(since = "2024-07-22")
    public void enableOrDisableSecurityProductOnAllOrgRepos(
            final String org,
            final GitHubOrganizationSecurityProduct securityProduct,
            final GitHubOrganizationSecurityProductEnablement enablement)
            throws IOException {
        enableOrDisableSecurityProductOnAllOrgRepos(org, securityProduct, enablement, null);
    }

    /**
     * Enable or disable a security feature for an organization ({@code POST
     * /orgs/{org}/{security_product}/{enablement}}).
     *
     * @param org organization name
     * @param securityProduct security product
     * @param enablement enablement action
     * @param payload optional request body
     * @throws NullPointerException if {@code securityProduct} or {@code enablement} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Closing down notice: the ability to enable or disable a security feature for all eligible
     *     repositories in an organization is closing down. Prefer code security configurations. Deprecation date
     *     {@code 2024-07-22}; removal date {@code 2025-07-22}.
     */
    @Deprecated(since = "2024-07-22")
    public void enableOrDisableSecurityProductOnAllOrgRepos(
            final String org,
            final GitHubOrganizationSecurityProduct securityProduct,
            final GitHubOrganizationSecurityProductEnablement enablement,
            final @Nullable GitHubEnableOrganizationSecurityProductPayload payload)
            throws IOException {
        requireNonNull(securityProduct, PARAM_SECURITY_PRODUCT);
        requireNonNull(enablement, PARAM_ENABLEMENT);

        caller.send(caller.jsonIfPresent(
                post(SECURITY_PRODUCT_ENABLEMENT, org, securityProduct.wireValue(), enablement.wireValue()), payload));
    }

    /**
     * List organizations for the authenticated user ({@code GET /user/orgs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listForAuthenticatedUser(GitHubPageRequest)}.
     *
     * @return first HTTP page of organizations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSimple> listForAuthenticatedUser() throws IOException {
        return listForAuthenticatedUser(GitHubPageRequest.byPage());
    }

    /**
     * List organizations for the authenticated user ({@code GET /user/orgs}).
     *
     * @param request pagination token
     * @return first HTTP page of organizations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSimple> listForAuthenticatedUser(final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_ORGS, Map.of(), request, ORGANIZATIONS_TYPE);
    }

    /**
     * List organizations for a user ({@code GET /users/{username}/orgs}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listForUser(String, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSimple> listForUser(final String username) throws IOException {
        return listForUser(username, GitHubPageRequest.byPage());
    }

    /**
     * List organizations for a user ({@code GET /users/{username}/orgs}).
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSimple> listForUser(final String username, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_ORGS, Map.of(), request, ORGANIZATIONS_TYPE, username);
    }

    /**
     * Get the audit log of an organization ({@code GET /orgs/{org}/audit-log}) with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #getAuditLog(String, GitHubGetAuditLogFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of audit log events
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAuditLogEvent> getAuditLog(final String org) throws IOException {
        return getAuditLog(org, GitHubGetAuditLogFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * Get the audit log of an organization ({@code GET /orgs/{org}/audit-log}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} only.
     *
     * @param org organization name
     * @param filter query filters
     * @param request cursor pagination token
     * @return page of audit log events
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is not a cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubAuditLogEvent> getAuditLog(
            final String org, final GitHubGetAuditLogFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(AUDIT_LOG, filter.toQueryParameters(), request, AUDIT_LOG_SPEC, org);
    }

    /**
     * List credential authorizations of an organization with SAML SSO ({@code GET
     * /orgs/{org}/credential-authorizations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listSamlSsoAuthorizations(String,
     * GitHubListSamlSsoAuthorizationsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of credential authorizations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubCredentialAuthorization> listSamlSsoAuthorizations(final String org) throws IOException {
        return listSamlSsoAuthorizations(
                org, GitHubListSamlSsoAuthorizationsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List credential authorizations of an organization with SAML SSO ({@code GET
     * /orgs/{org}/credential-authorizations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter query filters
     * @param request page pagination token
     * @return page of credential authorizations
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubCredentialAuthorization> listSamlSsoAuthorizations(
            final String org, final GitHubListSamlSsoAuthorizationsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                CREDENTIAL_AUTHORIZATIONS, filter.toQueryParameters(), request, CREDENTIAL_AUTHORIZATIONS_TYPE, org);
    }

    /**
     * Revoke all credential authorizations of an organization ({@code POST
     * /orgs/{org}/credential-authorizations/revoke-all}).
     *
     * @param org organization name
     * @return the queued revocation
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeAllCredentialAuthorizationsResponse revokeAllCredentialAuthorizations(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                post(CREDENTIAL_AUTHORIZATIONS_REVOKE_ALL, org), GitHubRevokeAllCredentialAuthorizationsResponse.class);
    }

    /**
     * Revoke a credential type of an organization ({@code POST
     * /orgs/{org}/credential-authorizations/revoke-credential-type}).
     *
     * @param org organization name
     * @param payload the credential type to revoke
     * @return the queued revocation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeCredentialTypeResponse revokeCredentialType(
            final String org, final GitHubRevokeCredentialTypePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(CREDENTIAL_AUTHORIZATIONS_REVOKE_TYPE, org), payload),
                GitHubRevokeCredentialTypeResponse.class);
    }

    /**
     * Remove the SAML SSO authorization of a credential ({@code DELETE
     * /orgs/{org}/credential-authorizations/{credential_id}}).
     *
     * @param org organization name
     * @param credentialId unique identifier of the credential authorization
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void removeSamlSsoAuthorization(final String org, final long credentialId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(CREDENTIAL_AUTHORIZATION, org, credentialId));
    }

    /**
     * Revoke the credential authorizations of a user ({@code POST
     * /orgs/{org}/credential-authorizations/{username}/revoke}).
     *
     * @param org organization name
     * @param username the handle for the user account
     * @return the queued revocation
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeCredentialAuthorizationsForUserResponse revokeCredentialAuthorizationsForUser(
            final String org, final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                post(CREDENTIAL_AUTHORIZATIONS_USER, org, username),
                GitHubRevokeCredentialAuthorizationsForUserResponse.class);
    }

    /**
     * Revoke a credential type of a user ({@code POST
     * /orgs/{org}/credential-authorizations/{username}/revoke-credential-type}).
     *
     * @param org organization name
     * @param username the handle for the user account
     * @param payload the credential type to revoke
     * @return the queued revocation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeCredentialTypeForUserResponse revokeCredentialTypeForUser(
            final String org, final String username, final GitHubRevokeCredentialTypeForUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(CREDENTIAL_AUTHORIZATIONS_USER_TYPE, org, username), payload),
                GitHubRevokeCredentialTypeForUserResponse.class);
    }
}
