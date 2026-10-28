package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.billing.GitHubAdvancedSecurityActiveCommitters;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Enterprise Cloud and Server licensing REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminLicensingApi extends GitHubResourceApi {

    private static final PathTemplate LICENSE = PathTemplate.of("/enterprise/settings/license");
    private static final PathTemplate CONSUMED_LICENSES =
            PathTemplate.of("/enterprises/{enterprise}/consumed-licenses");
    private static final PathTemplate LICENSE_SYNC_STATUS =
            PathTemplate.of("/enterprises/{enterprise}/license-sync-status");
    private static final PathTemplate ADVANCED_SECURITY_BILLING =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/advanced-security");
    private static final PathTemplate VISUAL_STUDIO_SUBSCRIPTIONS =
            PathTemplate.of("/enterprises/{enterprise}/visual-studio-subscriptions");
    private static final PathTemplate VISUAL_STUDIO_SUBSCRIPTION =
            PathTemplate.of("/enterprises/{enterprise}/visual-studio-subscriptions/{visual_studio_subscription_id}");

    private static final GitHubListSpec<GitHubVisualStudioSubscriptionAssignment> VISUAL_STUDIO_SUBSCRIPTIONS_SPEC =
            GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubVisualStudioSubscriptionsPage.class,
                    GitHubVisualStudioSubscriptionsPage::visualStudioSubscriptions,
                    GitHubVisualStudioSubscriptionsPage::totalCount);

    /**
     * Licensing resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminLicensingApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the license consumption of all users of an enterprise ({@code GET
     * /enterprises/{enterprise}/consumed-licenses}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #getConsumedLicenses(String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of consumed licenses
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetConsumedLicenses getConsumedLicenses(final String enterprise) throws IOException {
        return getConsumedLicenses(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List the license consumption of all users of an enterprise ({@code GET
     * /enterprises/{enterprise}/consumed-licenses}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token for the {@code users} list
     * @return consumed licenses with the requested page of users
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetConsumedLicenses getConsumedLicenses(final String enterprise, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetch(
                caller.pageParameters(
                        get(CONSUMED_LICENSES, enterprise), request, GitHubPageRequestKindValidators.PAGE),
                GitHubGetConsumedLicenses.class);
    }

    /**
     * Get the status of the license sync jobs of an enterprise ({@code GET
     * /enterprises/{enterprise}/license-sync-status}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return license sync status
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetLicenseSyncStatus getLicenseSyncStatus(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(LICENSE_SYNC_STATUS, enterprise), GitHubGetLicenseSyncStatus.class);
    }

    /**
     * Get GitHub Advanced Security active committers of an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/advanced-security}) with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #getAdvancedSecurityBilling(String,
     * GitHubGetGithubAdvancedSecurityBillingGheFilter, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return active committers with the first page of repositories
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAdvancedSecurityActiveCommitters getAdvancedSecurityBilling(final String enterprise)
            throws IOException {
        return getAdvancedSecurityBilling(
                enterprise, GitHubGetGithubAdvancedSecurityBillingGheFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get GitHub Advanced Security active committers of an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/advanced-security}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request pagination token for the {@code repositories} list
     * @return active committers with the requested page of repositories
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAdvancedSecurityActiveCommitters getAdvancedSecurityBilling(
            final String enterprise,
            final GitHubGetGithubAdvancedSecurityBillingGheFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetch(
                caller.pageParameters(
                        get(ADVANCED_SECURITY_BILLING, enterprise).withQuery(filter.toQueryParameters()),
                        request,
                        GitHubPageRequestKindValidators.PAGE),
                GitHubAdvancedSecurityActiveCommitters.class);
    }

    /**
     * List the Visual Studio subscriptions of an enterprise ({@code GET
     * /enterprises/{enterprise}/visual-studio-subscriptions}) with no filter.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #listVisualStudioSubscriptions(String,
     * GitHubListVssFilter, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of Visual Studio subscriptions
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubVisualStudioSubscriptionAssignment> listVisualStudioSubscriptions(final String enterprise)
            throws IOException {
        return listVisualStudioSubscriptions(enterprise, GitHubListVssFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List the Visual Studio subscriptions of an enterprise ({@code GET
     * /enterprises/{enterprise}/visual-studio-subscriptions}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of Visual Studio subscriptions
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubVisualStudioSubscriptionAssignment> listVisualStudioSubscriptions(
            final String enterprise, final GitHubListVssFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                VISUAL_STUDIO_SUBSCRIPTIONS,
                filter.toQueryParameters(),
                request,
                VISUAL_STUDIO_SUBSCRIPTIONS_SPEC,
                enterprise);
    }

    /**
     * Match a Visual Studio subscription manually to a user ({@code PUT
     * /enterprises/{enterprise}/visual-studio-subscriptions/{visual_studio_subscription_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param visualStudioSubscriptionId the identifier of the Visual Studio subscription
     * @param payload the user to match the subscription to
     * @return the updated Visual Studio subscription assignment
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code visualStudioSubscriptionId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubVisualStudioSubscriptionAssignment updateVisualStudioSubscriptionManualMatch(
            final String enterprise,
            final String visualStudioSubscriptionId,
            final GitHubUpdateVssManualMatchPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(VISUAL_STUDIO_SUBSCRIPTION, enterprise, visualStudioSubscriptionId), payload),
                GitHubVisualStudioSubscriptionAssignment.class);
    }

    /**
     * Remove the manual match of a Visual Studio subscription ({@code DELETE
     * /enterprises/{enterprise}/visual-studio-subscriptions/{visual_studio_subscription_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param visualStudioSubscriptionId the identifier of the Visual Studio subscription
     * @return the Visual Studio subscription assignment without a manual match
     * @throws IllegalArgumentException if {@code enterprise} or {@code visualStudioSubscriptionId} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubVisualStudioSubscriptionAssignment deleteVisualStudioSubscriptionManualMatch(
            final String enterprise, final String visualStudioSubscriptionId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                delete(VISUAL_STUDIO_SUBSCRIPTION, enterprise, visualStudioSubscriptionId),
                GitHubVisualStudioSubscriptionAssignment.class);
    }

    /**
     * Get the license information of the instance ({@code GET /enterprise/settings/license}).
     *
     * @return seats, kind and expiration of the license
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLicenseInfo getLicenseInformation() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(LICENSE), GitHubLicenseInfo.class);
    }
}
