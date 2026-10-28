package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import org.jspecify.annotations.Nullable;

/** GitHub Enterprise Cloud credential authorization REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminCredentialAuthorizationsApi extends GitHubResourceApi {

    private static final PathTemplate CREDENTIAL_AUTHORIZATIONS =
            PathTemplate.of("/enterprises/{enterprise}/credential-authorizations");
    private static final PathTemplate REVOKE_ALL =
            PathTemplate.of("/enterprises/{enterprise}/credential-authorizations/revoke-all");
    private static final PathTemplate REVOKE_CREDENTIAL_TYPE =
            PathTemplate.of("/enterprises/{enterprise}/credential-authorizations/revoke-credential-type");
    private static final PathTemplate REVOKE_USER =
            PathTemplate.of("/enterprises/{enterprise}/credential-authorizations/{username}/revoke");
    private static final PathTemplate REVOKE_USER_CREDENTIAL_TYPE =
            PathTemplate.of("/enterprises/{enterprise}/credential-authorizations/{username}/revoke-credential-type");

    /**
     * Credential authorization resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminCredentialAuthorizationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Grant credential authorizations to organizations of an enterprise ({@code POST
     * /enterprises/{enterprise}/credential-authorizations}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the credential and the organizations to authorize it for
     * @return the granted credential authorizations
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGrantCredentialAuthorizationsResponse grantCredentialAuthorizations(
            final String enterprise, final GitHubGrantCredentialAuthorizationsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(CREDENTIAL_AUTHORIZATIONS, enterprise), payload),
                GitHubGrantCredentialAuthorizationsResponse.class);
    }

    /**
     * Revoke delegated credential authorizations of a GitHub App integration ({@code DELETE
     * /enterprises/{enterprise}/credential-authorizations}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters selecting the integration
     * @return the queued revocation
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeDelegatedCredentialAuthorizationsResponse revokeDelegatedCredentialAuthorizations(
            final String enterprise, final GitHubRevokeDelegatedCredentialAuthorizationsFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                delete(CREDENTIAL_AUTHORIZATIONS, enterprise).withQuery(filter.toQueryParameters()),
                GitHubRevokeDelegatedCredentialAuthorizationsResponse.class);
    }

    /**
     * Revoke all credential authorizations of an enterprise ({@code POST
     * /enterprises/{enterprise}/credential-authorizations/revoke-all}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload whether to revoke the credentials as well, or {@code null} to send no body
     * @return the queued revocation
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeAllCredentialAuthorizationsResponse revokeAllCredentialAuthorizations(
            final String enterprise, final @Nullable GitHubRevokeAllCredentialAuthorizationsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                caller.jsonIfPresent(post(REVOKE_ALL, enterprise), payload),
                GitHubRevokeAllCredentialAuthorizationsResponse.class);
    }

    /**
     * Revoke a credential type of an enterprise ({@code POST
     * /enterprises/{enterprise}/credential-authorizations/revoke-credential-type}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the credential type to revoke
     * @return the queued revocation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeCredentialTypeForEnterpriseResponse revokeCredentialTypeForEnterprise(
            final String enterprise, final GitHubRevokeCredentialTypeForEnterprisePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REVOKE_CREDENTIAL_TYPE, enterprise), payload),
                GitHubRevokeCredentialTypeForEnterpriseResponse.class);
    }

    /**
     * Revoke the credential authorizations of a user ({@code POST
     * /enterprises/{enterprise}/credential-authorizations/{username}/revoke}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param username the handle for the user account
     * @param payload whether to revoke the credentials as well, or {@code null} to send no body
     * @return the queued revocation
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeCredentialAuthorizationsForUserResponse revokeCredentialAuthorizationsForUser(
            final String enterprise,
            final String username,
            final @Nullable GitHubRevokeCredentialAuthorizationsForUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                caller.jsonIfPresent(post(REVOKE_USER, enterprise, username), payload),
                GitHubRevokeCredentialAuthorizationsForUserResponse.class);
    }

    /**
     * Revoke a credential type of a user ({@code POST
     * /enterprises/{enterprise}/credential-authorizations/{username}/revoke-credential-type}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param username the handle for the user account
     * @param payload the credential type to revoke
     * @return the queued revocation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code username} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRevokeCredentialTypeForUserResponse revokeCredentialTypeForUser(
            final String enterprise, final String username, final GitHubRevokeCredentialTypeForUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REVOKE_USER_CREDENTIAL_TYPE, enterprise, username), payload),
                GitHubRevokeCredentialTypeForUserResponse.class);
    }
}
