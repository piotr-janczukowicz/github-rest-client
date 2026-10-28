package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.client.HttpCall;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/**
 * GitHub Enterprise Cloud SCIM provisioning REST resource for API version {@code 2026-03-10}.
 *
 * <p>The list methods page by {@code startIndex} and {@code count}, so a walk is an offset walk: members added or
 * removed between two pages can make a record repeat or be skipped. The walk follows {@code totalResults} and has no
 * page cap unless {@code maxPages} is set on the client's pagination configuration.
 */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubScimApi extends GitHubResourceApi {

    private static final PathTemplate USERS = PathTemplate.of("/scim/v2/organizations/{org}/Users");
    private static final PathTemplate USER = PathTemplate.of("/scim/v2/organizations/{org}/Users/{scim_user_id}");

    private static final GitHubListSpec<GitHubScimUser> USERS_SPEC = GitHubListSpec.startIndexWrapper(
            GitHubScimUserList.class, GitHubScimUserList::resources, GitHubScimUserList::totalResults);

    /**
     * SCIM provisioning resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubScimApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List SCIM provisioned identities ({@code GET /scim/v2/organizations/{org}/Users}) with no filter.
     *
     * @param org the organization name. The name is not case sensitive.
     * @return first index page of provisioned users
     * @throws IllegalArgumentException if {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubScimUser> listProvisionedIdentities(final String org) throws IOException {
        return listProvisionedIdentities(org, GitHubListProvisionedIdentitiesFilter.empty());
    }

    /**
     * List SCIM provisioned identities ({@code GET /scim/v2/organizations/{org}/Users}) from the first index page.
     *
     * @param org the organization name. The name is not case sensitive.
     * @param filter query filters
     * @return first index page of provisioned users
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubScimUser> listProvisionedIdentities(
            final String org, final GitHubListProvisionedIdentitiesFilter filter) throws IOException {
        return listProvisionedIdentities(org, filter, GitHubPageRequest.byStartIndex());
    }

    /**
     * List SCIM provisioned identities ({@code GET /scim/v2/organizations/{org}/Users}). The request is a start index
     * token: the next page is derived from the body's {@code totalResults}, not from a {@code Link} header.
     *
     * @param org the organization name. The name is not case sensitive.
     * @param filter query filters
     * @param request start index pagination token
     * @return index page of provisioned users
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is empty or {@code request} is a page number, since or cursor
     *     token ({@code none()} is sent as the first index page)
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubScimUser> listProvisionedIdentities(
            final String org, final GitHubListProvisionedIdentitiesFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(USERS, filter.toQueryParameters(), request, USERS_SPEC, GitHubScimSettings.HEADERS, org);
    }

    /**
     * Provision and invite a SCIM user ({@code POST /scim/v2/organizations/{org}/Users}).
     *
     * @param org the organization name. The name is not case sensitive.
     * @param payload user to provision
     * @return provisioned user
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubScimUser provisionAndInviteUser(final String org, final GitHubProvisionAndInviteUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(scim(post(USERS, org)), payload), GitHubScimUser.class);
    }

    /**
     * Get the provisioning information of a SCIM user ({@code GET /scim/v2/organizations/{org}/Users/{scim_user_id}}).
     *
     * @param org the organization name. The name is not case sensitive.
     * @param scimUserId the unique identifier of the SCIM user
     * @return provisioned user, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubScimUser> getProvisioningInformationForUser(final String org, final String scimUserId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(scim(get(USER, org, scimUserId)), GitHubScimUser.class);
    }

    /**
     * Set the information of a SCIM provisioned user ({@code PUT /scim/v2/organizations/{org}/Users/{scim_user_id}}).
     *
     * @param org the organization name. The name is not case sensitive.
     * @param scimUserId the unique identifier of the SCIM user
     * @param payload user attributes to set
     * @return provisioned user as stored
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubScimUser setInformationForProvisionedUser(
            final String org, final String scimUserId, final GitHubSetInformationForProvisionedUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(scim(put(USER, org, scimUserId)), payload), GitHubScimUser.class);
    }

    /**
     * Update one or more attributes of a SCIM provisioned user ({@code PATCH
     * /scim/v2/organizations/{org}/Users/{scim_user_id}}).
     *
     * @param org the organization name. The name is not case sensitive.
     * @param scimUserId the unique identifier of the SCIM user
     * @param payload operations to perform
     * @return provisioned user as stored
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubScimUser updateAttributeForUser(
            final String org, final String scimUserId, final GitHubUpdateAttributeForUserPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(scim(patch(USER, org, scimUserId)), payload), GitHubScimUser.class);
    }

    /**
     * Delete a SCIM user from an organization ({@code DELETE /scim/v2/organizations/{org}/Users/{scim_user_id}}).
     *
     * @param org the organization name. The name is not case sensitive.
     * @param scimUserId the unique identifier of the SCIM user
     * @throws IllegalArgumentException if {@code org} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void deleteUserFromOrg(final String org, final String scimUserId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(scim(delete(USER, org, scimUserId)));
    }

    private static HttpCall scim(final HttpCall call) {
        return call.withHeader(ACCEPT, GitHubScimSettings.MEDIA_TYPE);
    }
}
