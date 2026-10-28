package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.apps.GitHubAuthorization;
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
import java.util.Optional;

/**
 * OAuth Authorizations REST resource for API version {@code 2026-03-10}, which only GitHub Enterprise Server still
 * carries.
 *
 * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow. Deprecation
 *     date {@code 2020-02-14}; removal date {@code 2020-11-13}.
 */
@Deprecated(since = "2020-02-14")
public final class GitHubOauthAuthorizationsApi extends GitHubResourceApi {

    private static final String DEPRECATED_SINCE = "2020-02-14";

    private static final PathTemplate GRANTS = PathTemplate.of("/applications/grants");
    private static final PathTemplate GRANT = PathTemplate.of("/applications/grants/{grant_id}");
    private static final PathTemplate AUTHORIZATIONS = PathTemplate.of("/authorizations");
    private static final PathTemplate CLIENT_AUTHORIZATION = PathTemplate.of("/authorizations/clients/{client_id}");
    private static final PathTemplate FINGERPRINT_AUTHORIZATION =
            PathTemplate.of("/authorizations/clients/{client_id}/{fingerprint}");
    private static final PathTemplate AUTHORIZATION = PathTemplate.of("/authorizations/{authorization_id}");

    private static final JsonType<List<GitHubApplicationGrant>> GRANTS_TYPE =
            JsonTypes.listOf(GitHubApplicationGrant.class);
    private static final JsonType<List<GitHubAuthorization>> AUTHORIZATIONS_TYPE =
            JsonTypes.listOf(GitHubAuthorization.class);

    /**
     * OAuth Authorizations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOauthAuthorizationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List your grants ({@code GET /applications/grants}).
     *
     * @return first HTTP page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubApplicationGrant> listGrants() throws IOException {
        return listGrants(GitHubListGrantsFilter.empty());
    }

    /**
     * List your grants ({@code GET /applications/grants}).
     *
     * @param filter filters
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubApplicationGrant> listGrants(final GitHubListGrantsFilter filter) throws IOException {
        return listGrants(filter, GitHubPageRequest.byPage());
    }

    /**
     * List your grants ({@code GET /applications/grants}).
     *
     * @param filter filters
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubApplicationGrant> listGrants(
            final GitHubListGrantsFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(GRANTS, filter.toQueryParameters(), request, GRANTS_TYPE);
    }

    /**
     * List your authorizations ({@code GET /authorizations}).
     *
     * @return first HTTP page
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubAuthorization> listAuthorizations() throws IOException {
        return listAuthorizations(GitHubListAuthorizationsFilter.empty());
    }

    /**
     * List your authorizations ({@code GET /authorizations}).
     *
     * @param filter filters
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubAuthorization> listAuthorizations(final GitHubListAuthorizationsFilter filter)
            throws IOException {
        return listAuthorizations(filter, GitHubPageRequest.byPage());
    }

    /**
     * List your authorizations ({@code GET /authorizations}).
     *
     * @param filter filters
     * @param request pagination token
     * @return first HTTP page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubAuthorization> listAuthorizations(
            final GitHubListAuthorizationsFilter filter, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(AUTHORIZATIONS, filter.toQueryParameters(), request, AUTHORIZATIONS_TYPE);
    }

    /**
     * Get a single grant ({@code GET /applications/grants/{grant_id}}).
     *
     * @param grantId grant id
     * @return the grant, or empty if it does not exist
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubApplicationGrant> getGrant(final long grantId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(GRANT, grantId), GitHubApplicationGrant.class);
    }

    /**
     * Delete a grant ({@code DELETE /applications/grants/{grant_id}}).
     *
     * @param grantId grant id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public void deleteGrant(final long grantId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(GRANT, grantId));
    }

    /**
     * Create a new authorization ({@code POST /authorizations}).
     *
     * @return the authorization
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubAuthorization createAuthorization() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(post(AUTHORIZATIONS), GitHubAuthorization.class);
    }

    /**
     * Create a new authorization ({@code POST /authorizations}).
     *
     * @param payload request body
     * @return the authorization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubAuthorization createAuthorization(final GitHubCreateAuthorizationPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(AUTHORIZATIONS), payload), GitHubAuthorization.class);
    }

    /**
     * Get or create an authorization for a specific app ({@code PUT /authorizations/clients/{client_id}}).
     *
     * @param clientId OAuth app client id
     * @param payload request body
     * @return the authorization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code clientId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubAuthorization getOrCreateAuthorizationForApp(
            final String clientId, final GitHubGetOrCreateAuthorizationForAppPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(CLIENT_AUTHORIZATION, clientId), payload), GitHubAuthorization.class);
    }

    /**
     * Get or create an authorization for a specific app and fingerprint ({@code PUT
     * /authorizations/clients/{client_id}/{fingerprint}}).
     *
     * @param clientId OAuth app client id
     * @param fingerprint fingerprint
     * @param payload request body
     * @return the authorization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code clientId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubAuthorization getOrCreateAuthorizationForAppAndFingerprint(
            final String clientId,
            final String fingerprint,
            final GitHubGetOrCreateAuthorizationForAppAndFingerprintPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(FINGERPRINT_AUTHORIZATION, clientId, fingerprint), payload), GitHubAuthorization.class);
    }

    /**
     * Get a single authorization ({@code GET /authorizations/{authorization_id}}).
     *
     * @param authorizationId authorization id
     * @return the authorization, or empty if it does not exist
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubAuthorization> getAuthorization(final long authorizationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(AUTHORIZATION, authorizationId), GitHubAuthorization.class);
    }

    /**
     * Update an existing authorization ({@code PATCH /authorizations/{authorization_id}}).
     *
     * @param authorizationId authorization id
     * @return the authorization
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubAuthorization updateAuthorization(final long authorizationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(patch(AUTHORIZATION, authorizationId), GitHubAuthorization.class);
    }

    /**
     * Update an existing authorization ({@code PATCH /authorizations/{authorization_id}}).
     *
     * @param authorizationId authorization id
     * @param payload request body
     * @return the authorization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public GitHubAuthorization updateAuthorization(
            final long authorizationId, final GitHubUpdateAuthorizationPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(AUTHORIZATION, authorizationId), payload), GitHubAuthorization.class);
    }

    /**
     * Delete an authorization ({@code DELETE /authorizations/{authorization_id}}).
     *
     * @param authorizationId authorization id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow.
     *     Deprecation date {@code 2020-02-14}; removal date {@code 2020-11-13}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHES)
    public void deleteAuthorization(final long authorizationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(AUTHORIZATION, authorizationId));
    }
}
