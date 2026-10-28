package io.github.piotr_janczukowicz.github.api.apps;

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
import java.util.Optional;

/**
 * GitHub Apps REST resource for API version {@code 2026-03-10}.
 *
 * <p>The spec says that the app calls ({@code /app}, {@code /app/installations} and below,
 * {@code /app/installation-requests} and the installation lookups by organization, repository and user) must be made
 * with a JSON web token (JWT) of the app. A client that authenticates with another credential gets a plain HTTP 401,
 * 403 or 404.
 */
public final class GitHubAppApi extends GitHubResourceApi {

    private static final PathTemplate APP = PathTemplate.of("/app");
    private static final PathTemplate APP_MANIFEST_CONVERSIONS = PathTemplate.of("/app-manifests/{code}/conversions");
    private static final PathTemplate APP_INSTALLATION_REQUESTS = PathTemplate.of("/app/installation-requests");
    private static final PathTemplate APP_INSTALLATIONS = PathTemplate.of("/app/installations");
    private static final PathTemplate APP_INSTALLATION = PathTemplate.of("/app/installations/{installation_id}");
    private static final PathTemplate APP_INSTALLATION_ACCESS_TOKENS =
            PathTemplate.of("/app/installations/{installation_id}/access_tokens");
    private static final PathTemplate APP_INSTALLATION_SUSPENDED =
            PathTemplate.of("/app/installations/{installation_id}/suspended");
    private static final PathTemplate APPLICATION_TOKEN_SCOPED =
            PathTemplate.of("/applications/{client_id}/token/scoped");
    private static final PathTemplate APP_BY_SLUG = PathTemplate.of("/apps/{app_slug}");
    private static final PathTemplate ENTERPRISE_INSTALLATION =
            PathTemplate.of("/enterprises/{enterprise}/installation");
    private static final PathTemplate ORG_INSTALLATION = PathTemplate.of("/orgs/{org}/installation");
    private static final PathTemplate REPO_INSTALLATION = PathTemplate.of("/repos/{owner}/{repo}/installation");
    private static final PathTemplate USER_INSTALLATION = PathTemplate.of("/users/{username}/installation");

    private static final JsonType<List<GitHubIntegrationInstallationRequest>> INSTALLATION_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubIntegrationInstallationRequest.class);
    private static final JsonType<List<GitHubInstallation>> INSTALLATIONS_TYPE =
            JsonTypes.listOf(GitHubInstallation.class);

    /**
     * GitHub Apps resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAppApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the authenticated app ({@code GET /app}).
     *
     * @return authenticated app
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubIntegration getAuthenticated() throws IOException {
        return caller.fetch(get(APP), GitHubIntegration.class);
    }

    /**
     * Create a GitHub App from a manifest ({@code POST /app-manifests/{code}/conversions}).
     *
     * @param code temporary code from the manifest flow
     * @return created app with its credentials
     * @throws IllegalArgumentException if {@code code} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCreateFromManifestResponse createFromManifest(final String code) throws IOException {
        return caller.fetch(post(APP_MANIFEST_CONVERSIONS, code), GitHubCreateFromManifestResponse.class);
    }

    /**
     * List installation requests for the authenticated app ({@code GET /app/installation-requests}).
     *
     * @return first HTTP page of installation requests
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIntegrationInstallationRequest> listInstallationRequests() throws IOException {
        return listInstallationRequests(GitHubPageRequest.byPage());
    }

    /**
     * List installation requests for the authenticated app ({@code GET /app/installation-requests}).
     *
     * @param request pagination token
     * @return HTTP page of installation requests
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIntegrationInstallationRequest> listInstallationRequests(final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(APP_INSTALLATION_REQUESTS, Map.of(), request, INSTALLATION_REQUESTS_TYPE);
    }

    /**
     * List installations for the authenticated app ({@code GET /app/installations}).
     *
     * @return first HTTP page of installations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubInstallation> listInstallations() throws IOException {
        return listInstallations(GitHubListInstallationsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List installations for the authenticated app ({@code GET /app/installations}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of installations
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubInstallation> listInstallations(
            final GitHubListInstallationsFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(APP_INSTALLATIONS, filter.toQueryParameters(), request, INSTALLATIONS_TYPE);
    }

    /**
     * Get an installation for the authenticated app ({@code GET /app/installations/{installation_id}}).
     *
     * @param installationId installation id
     * @return installation, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubInstallation> getInstallation(final long installationId) throws IOException {
        return caller.fetchOrEmpty(get(APP_INSTALLATION, installationId), GitHubInstallation.class);
    }

    /**
     * Delete an installation for the authenticated app ({@code DELETE /app/installations/{installation_id}}).
     *
     * @param installationId installation id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteInstallation(final long installationId) throws IOException {
        caller.send(delete(APP_INSTALLATION, installationId));
    }

    /**
     * Create an installation access token for every repository and permission of the installation ({@code POST
     * /app/installations/{installation_id}/access_tokens}), without a request body.
     *
     * @param installationId installation id
     * @return installation access token
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubInstallationToken createInstallationAccessToken(final long installationId) throws IOException {
        return caller.fetch(post(APP_INSTALLATION_ACCESS_TOKENS, installationId), GitHubInstallationToken.class);
    }

    /**
     * Create an installation access token ({@code POST /app/installations/{installation_id}/access_tokens}).
     *
     * @param installationId installation id
     * @param payload token scope
     * @return installation access token
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubInstallationToken createInstallationAccessToken(
            final long installationId, final GitHubCreateInstallationAccessTokenPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(APP_INSTALLATION_ACCESS_TOKENS, installationId), payload),
                GitHubInstallationToken.class);
    }

    /**
     * Suspend an app installation ({@code PUT /app/installations/{installation_id}/suspended}).
     *
     * @param installationId installation id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void suspendInstallation(final long installationId) throws IOException {
        caller.send(put(APP_INSTALLATION_SUSPENDED, installationId));
    }

    /**
     * Unsuspend an app installation ({@code DELETE /app/installations/{installation_id}/suspended}).
     *
     * @param installationId installation id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void unsuspendInstallation(final long installationId) throws IOException {
        caller.send(delete(APP_INSTALLATION_SUSPENDED, installationId));
    }

    /**
     * Create a scoped access token ({@code POST /applications/{client_id}/token/scoped}).
     *
     * @param clientId OAuth client id
     * @param payload scoped token request
     * @return scoped authorization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAuthorization scopeToken(final String clientId, final GitHubScopeTokenPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(APPLICATION_TOKEN_SCOPED, clientId), payload), GitHubAuthorization.class);
    }

    /**
     * Get an app ({@code GET /apps/{app_slug}}).
     *
     * @param appSlug app slug
     * @return app, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubIntegration> getBySlug(final String appSlug) throws IOException {
        return caller.fetchOrEmpty(get(APP_BY_SLUG, appSlug), GitHubIntegration.class);
    }

    /**
     * Get an enterprise installation for the authenticated app ({@code GET /enterprises/{enterprise}/installation}).
     *
     * @param enterprise enterprise slug
     * @return installation
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubInstallation getEnterpriseInstallation(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ENTERPRISE_INSTALLATION, enterprise), GitHubInstallation.class);
    }

    /**
     * Get an organization installation for the authenticated app ({@code GET /orgs/{org}/installation}).
     *
     * @param org organization name
     * @return installation
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubInstallation getOrgInstallation(final String org) throws IOException {
        return caller.fetch(get(ORG_INSTALLATION, org), GitHubInstallation.class);
    }

    /**
     * Get a repository installation for the authenticated app ({@code GET /repos/{owner}/{repo}/installation}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return installation
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubInstallation getRepoInstallation(final String owner, final String repo) throws IOException {
        return caller.fetch(get(REPO_INSTALLATION, owner, repo), GitHubInstallation.class);
    }

    /**
     * Get a user installation for the authenticated app ({@code GET /users/{username}/installation}).
     *
     * @param username user login
     * @return installation, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubInstallation> getUserInstallation(final String username) throws IOException {
        return caller.fetchOrEmpty(get(USER_INSTALLATION, username), GitHubInstallation.class);
    }
}
