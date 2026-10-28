package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Enterprise Cloud billing cost centers REST resource for API version {@code 2026-03-10}. */
public final class GitHubBillingCostCentersApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_COST_CENTERS =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/cost-centers");
    private static final PathTemplate ENTERPRISE_COST_CENTER =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}");
    private static final PathTemplate ENTERPRISE_COST_CENTER_RESOURCE =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}/resource");

    /**
     * Billing cost centers resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBillingCostCentersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all cost centers for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/cost-centers}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return cost centers of the enterprise
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetAllCostCenters getAllCostCenters(final String enterprise) throws IOException {
        return getAllCostCenters(enterprise, GitHubGetAllCostCentersFilter.empty());
    }

    /**
     * Get all cost centers for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/cost-centers}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return cost centers of the enterprise
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGetAllCostCenters getAllCostCenters(
            final String enterprise, final GitHubGetAllCostCentersFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_COST_CENTERS, enterprise).withQuery(filter.toQueryParameters()),
                GitHubGetAllCostCenters.class);
    }

    /**
     * Create a cost center for an enterprise ({@code POST /enterprises/{enterprise}/settings/billing/cost-centers}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload cost center to create
     * @return created cost center
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubCreateCostCenterResponse createCostCenter(
            final String enterprise, final GitHubCreateCostCenterPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_COST_CENTERS, enterprise), payload), GitHubCreateCostCenterResponse.class);
    }

    /**
     * Get a cost center by ID for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param costCenterId cost center ID
     * @return cost center with the first HTTP page of resources, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code costCenterId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetCostCenter> getCostCenter(final String enterprise, final String costCenterId)
            throws IOException {
        return getCostCenter(enterprise, costCenterId, GitHubPageRequest.byPage());
    }

    /**
     * Get a cost center by ID for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param costCenterId cost center ID
     * @param request pagination token
     * @return cost center with the requested page of resources, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code costCenterId} is blank, or {@code request} is
     *     invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetCostCenter> getCostCenter(
            final String enterprise, final String costCenterId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetchOrEmpty(
                caller.pageParameters(
                        get(ENTERPRISE_COST_CENTER, enterprise, costCenterId),
                        request,
                        GitHubPageRequestKindValidators.PAGE),
                GitHubGetCostCenter.class);
    }

    /**
     * Update a cost center for an enterprise ({@code PATCH
     * /enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param costCenterId cost center ID
     * @param payload fields to update
     * @return updated cost center
     * @throws IllegalArgumentException if {@code enterprise} or {@code costCenterId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUpdateCostCenter updateCostCenter(
            final String enterprise, final String costCenterId, final GitHubUpdateCostCenterPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_COST_CENTER, enterprise, costCenterId), payload),
                GitHubUpdateCostCenter.class);
    }

    /**
     * Delete a cost center for an enterprise ({@code DELETE
     * /enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param costCenterId cost center ID
     * @return deletion result
     * @throws IllegalArgumentException if {@code enterprise} or {@code costCenterId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubDeleteCostCenter deleteCostCenter(final String enterprise, final String costCenterId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(delete(ENTERPRISE_COST_CENTER, enterprise, costCenterId), GitHubDeleteCostCenter.class);
    }

    /**
     * Add resources to a cost center for an enterprise ({@code POST
     * /enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}/resource}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param costCenterId cost center ID
     * @param payload resources to add
     * @return result of the addition
     * @throws IllegalArgumentException if {@code enterprise} or {@code costCenterId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubAddResourceToCostCenterResponse addResourceToCostCenter(
            final String enterprise, final String costCenterId, final GitHubAddResourceToCostCenterPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_COST_CENTER_RESOURCE, enterprise, costCenterId), payload),
                GitHubAddResourceToCostCenterResponse.class);
    }

    /**
     * Remove resources from a cost center for an enterprise ({@code DELETE
     * /enterprises/{enterprise}/settings/billing/cost-centers/{cost_center_id}/resource}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param costCenterId cost center ID
     * @param payload resources to remove
     * @return result of the removal
     * @throws IllegalArgumentException if {@code enterprise} or {@code costCenterId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRemoveResourceFromCostCenterResponse removeResourceFromCostCenter(
            final String enterprise, final String costCenterId, final GitHubRemoveResourceFromCostCenterPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(delete(ENTERPRISE_COST_CENTER_RESOURCE, enterprise, costCenterId), payload),
                GitHubRemoveResourceFromCostCenterResponse.class);
    }
}
