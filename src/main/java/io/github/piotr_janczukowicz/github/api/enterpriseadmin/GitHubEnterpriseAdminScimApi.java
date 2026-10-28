package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.scim.GitHubScimSettings;
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
 * GitHub Enterprise Cloud enterprise SCIM provisioning REST resource for API version {@code 2026-03-10}.
 *
 * <p>The list methods page by {@code startIndex} and {@code count}, so a walk is an offset walk: members added or
 * removed between two pages can make a record repeat or be skipped. The walk follows {@code totalResults} and has no
 * page cap unless {@code maxPages} is set on the client's pagination configuration.
 */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubEnterpriseAdminScimApi extends GitHubResourceApi {

    private static final PathTemplate GROUPS = PathTemplate.of("/scim/v2/enterprises/{enterprise}/Groups");
    private static final PathTemplate GROUP =
            PathTemplate.of("/scim/v2/enterprises/{enterprise}/Groups/{scim_group_id}");
    private static final PathTemplate USERS = PathTemplate.of("/scim/v2/enterprises/{enterprise}/Users");
    private static final PathTemplate USER = PathTemplate.of("/scim/v2/enterprises/{enterprise}/Users/{scim_user_id}");

    private static final GitHubListSpec<GitHubScimEnterpriseGroupResponse> GROUPS_SPEC =
            GitHubListSpec.startIndexWrapper(
                    GitHubScimEnterpriseGroupList.class,
                    GitHubScimEnterpriseGroupList::resources,
                    GitHubScimEnterpriseGroupList::totalResults);
    private static final GitHubListSpec<GitHubScimEnterpriseUserResponse> USERS_SPEC = GitHubListSpec.startIndexWrapper(
            GitHubScimEnterpriseUserList.class,
            GitHubScimEnterpriseUserList::resources,
            GitHubScimEnterpriseUserList::totalResults);

    /**
     * Enterprise SCIM provisioning resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminScimApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List provisioned SCIM groups of an enterprise ({@code GET /scim/v2/enterprises/{enterprise}/Groups}) with no
     * filter.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first index page of provisioned groups
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubScimEnterpriseGroupResponse> listProvisionedGroups(final String enterprise)
            throws IOException {
        return listProvisionedGroups(enterprise, GitHubListProvisionedGroupsEnterpriseFilter.empty());
    }

    /**
     * List provisioned SCIM groups of an enterprise ({@code GET /scim/v2/enterprises/{enterprise}/Groups}) from the
     * first index page.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return first index page of provisioned groups
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubScimEnterpriseGroupResponse> listProvisionedGroups(
            final String enterprise, final GitHubListProvisionedGroupsEnterpriseFilter filter) throws IOException {
        return listProvisionedGroups(enterprise, filter, GitHubPageRequest.byStartIndex());
    }

    /**
     * List provisioned SCIM groups of an enterprise ({@code GET /scim/v2/enterprises/{enterprise}/Groups}). The request
     * is a start index token: the next page is derived from the body's {@code totalResults}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request start index pagination token
     * @return index page of provisioned groups
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty or {@code request} is a page number, since or
     *     cursor token ({@code none()} is sent as the first index page)
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubScimEnterpriseGroupResponse> listProvisionedGroups(
            final String enterprise,
            final GitHubListProvisionedGroupsEnterpriseFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(GROUPS, filter.toQueryParameters(), request, GROUPS_SPEC, GitHubScimSettings.HEADERS, enterprise);
    }

    /**
     * Provision a SCIM group in an enterprise ({@code POST /scim/v2/enterprises/{enterprise}/Groups}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload group to provision
     * @return provisioned group
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubScimEnterpriseGroupResponse provisionGroup(
            final String enterprise, final GitHubScimEnterpriseGroup payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(scim(post(GROUPS, enterprise)), payload), GitHubScimEnterpriseGroupResponse.class);
    }

    /**
     * Get the provisioning information of a SCIM group ({@code GET
     * /scim/v2/enterprises/{enterprise}/Groups/{scim_group_id}}) with no filter.
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimGroupId the unique identifier of the SCIM group
     * @return provisioned group, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimGroupId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubScimEnterpriseGroupResponse> getProvisioningInformationForGroup(
            final String enterprise, final String scimGroupId) throws IOException {
        return getProvisioningInformationForGroup(
                enterprise, scimGroupId, GitHubGetProvisioningInformationForEnterpriseGroupFilter.empty());
    }

    /**
     * Get the provisioning information of a SCIM group ({@code GET
     * /scim/v2/enterprises/{enterprise}/Groups/{scim_group_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimGroupId the unique identifier of the SCIM group
     * @param filter query filters
     * @return provisioned group, or empty on HTTP 404
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimGroupId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubScimEnterpriseGroupResponse> getProvisioningInformationForGroup(
            final String enterprise,
            final String scimGroupId,
            final GitHubGetProvisioningInformationForEnterpriseGroupFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                scim(get(GROUP, enterprise, scimGroupId)).withQuery(filter.toQueryParameters()),
                GitHubScimEnterpriseGroupResponse.class);
    }

    /**
     * Set the information of a SCIM provisioned group ({@code PUT
     * /scim/v2/enterprises/{enterprise}/Groups/{scim_group_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimGroupId the unique identifier of the SCIM group
     * @param payload group attributes to set
     * @return provisioned group as stored
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimGroupId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubScimEnterpriseGroupResponse setInformationForProvisionedGroup(
            final String enterprise, final String scimGroupId, final GitHubScimEnterpriseGroup payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(scim(put(GROUP, enterprise, scimGroupId)), payload),
                GitHubScimEnterpriseGroupResponse.class);
    }

    /**
     * Update one or more attributes of a SCIM provisioned group ({@code PATCH
     * /scim/v2/enterprises/{enterprise}/Groups/{scim_group_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimGroupId the unique identifier of the SCIM group
     * @param payload operations to perform
     * @return provisioned group as stored
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimGroupId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubScimEnterpriseGroupResponse updateAttributeForGroup(
            final String enterprise, final String scimGroupId, final GitHubPatchSchema payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(scim(patch(GROUP, enterprise, scimGroupId)), payload),
                GitHubScimEnterpriseGroupResponse.class);
    }

    /**
     * Delete a SCIM group from an enterprise ({@code DELETE /scim/v2/enterprises/{enterprise}/Groups/{scim_group_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimGroupId the unique identifier of the SCIM group
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimGroupId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteScimGroup(final String enterprise, final String scimGroupId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(scim(delete(GROUP, enterprise, scimGroupId)));
    }

    /**
     * List provisioned SCIM identities of an enterprise ({@code GET /scim/v2/enterprises/{enterprise}/Users}) with no
     * filter.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first index page of provisioned users
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubScimEnterpriseUserResponse> listProvisionedIdentities(final String enterprise)
            throws IOException {
        return listProvisionedIdentities(enterprise, GitHubListProvisionedIdentitiesEnterpriseFilter.empty());
    }

    /**
     * List provisioned SCIM identities of an enterprise ({@code GET /scim/v2/enterprises/{enterprise}/Users}) from the
     * first index page.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return first index page of provisioned users
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubScimEnterpriseUserResponse> listProvisionedIdentities(
            final String enterprise, final GitHubListProvisionedIdentitiesEnterpriseFilter filter) throws IOException {
        return listProvisionedIdentities(enterprise, filter, GitHubPageRequest.byStartIndex());
    }

    /**
     * List provisioned SCIM identities of an enterprise ({@code GET /scim/v2/enterprises/{enterprise}/Users}). The
     * request is a start index token: the next page is derived from the body's {@code totalResults}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request start index pagination token
     * @return index page of provisioned users
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty or {@code request} is a page number, since or
     *     cursor token ({@code none()} is sent as the first index page)
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubScimEnterpriseUserResponse> listProvisionedIdentities(
            final String enterprise,
            final GitHubListProvisionedIdentitiesEnterpriseFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(USERS, filter.toQueryParameters(), request, USERS_SPEC, GitHubScimSettings.HEADERS, enterprise);
    }

    /**
     * Provision a SCIM user in an enterprise ({@code POST /scim/v2/enterprises/{enterprise}/Users}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload user to provision
     * @return provisioned user
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubScimEnterpriseUserResponse provisionUser(
            final String enterprise, final GitHubScimEnterpriseUser payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(scim(post(USERS, enterprise)), payload), GitHubScimEnterpriseUserResponse.class);
    }

    /**
     * Get the provisioning information of a SCIM user ({@code GET
     * /scim/v2/enterprises/{enterprise}/Users/{scim_user_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimUserId the unique identifier of the SCIM user
     * @return provisioned user, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubScimEnterpriseUserResponse> getProvisioningInformationForUser(
            final String enterprise, final String scimUserId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(scim(get(USER, enterprise, scimUserId)), GitHubScimEnterpriseUserResponse.class);
    }

    /**
     * Set the information of a SCIM provisioned user ({@code PUT
     * /scim/v2/enterprises/{enterprise}/Users/{scim_user_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimUserId the unique identifier of the SCIM user
     * @param payload user attributes to set
     * @return provisioned user as stored
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubScimEnterpriseUserResponse setInformationForProvisionedUser(
            final String enterprise, final String scimUserId, final GitHubScimEnterpriseUser payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(scim(put(USER, enterprise, scimUserId)), payload), GitHubScimEnterpriseUserResponse.class);
    }

    /**
     * Update one or more attributes of a SCIM provisioned user ({@code PATCH
     * /scim/v2/enterprises/{enterprise}/Users/{scim_user_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimUserId the unique identifier of the SCIM user
     * @param payload operations to perform
     * @return provisioned user as stored
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubScimEnterpriseUserResponse updateAttributeForUser(
            final String enterprise, final String scimUserId, final GitHubPatchSchema payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(scim(patch(USER, enterprise, scimUserId)), payload),
                GitHubScimEnterpriseUserResponse.class);
    }

    /**
     * Delete a SCIM user from an enterprise ({@code DELETE /scim/v2/enterprises/{enterprise}/Users/{scim_user_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param scimUserId the unique identifier of the SCIM user
     * @throws IllegalArgumentException if {@code enterprise} or {@code scimUserId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteUser(final String enterprise, final String scimUserId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(scim(delete(USER, enterprise, scimUserId)));
    }

    private static HttpCall scim(final HttpCall call) {
        return call.withHeader(ACCEPT, GitHubScimSettings.MEDIA_TYPE);
    }
}
