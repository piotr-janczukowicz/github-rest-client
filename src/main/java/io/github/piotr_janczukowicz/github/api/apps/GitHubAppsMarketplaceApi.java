package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
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
 * GitHub Marketplace listing REST resource for API version {@code 2026-03-10}.
 *
 * <p>The spec says that for the listing calls ({@code /marketplace_listing}), GitHub Apps must use a JSON web token
 * (JWT) and OAuth apps must use basic authentication with their client ID and client secret.
 */
public final class GitHubAppsMarketplaceApi extends GitHubResourceApi {

    private static final PathTemplate ACCOUNT = PathTemplate.of("/marketplace_listing/accounts/{account_id}");
    private static final PathTemplate PLANS = PathTemplate.of("/marketplace_listing/plans");
    private static final PathTemplate PLAN_ACCOUNTS = PathTemplate.of("/marketplace_listing/plans/{plan_id}/accounts");
    private static final PathTemplate STUBBED_ACCOUNT =
            PathTemplate.of("/marketplace_listing/stubbed/accounts/{account_id}");
    private static final PathTemplate STUBBED_PLANS = PathTemplate.of("/marketplace_listing/stubbed/plans");
    private static final PathTemplate STUBBED_PLAN_ACCOUNTS =
            PathTemplate.of("/marketplace_listing/stubbed/plans/{plan_id}/accounts");
    private static final PathTemplate USER_PURCHASES = PathTemplate.of("/user/marketplace_purchases");
    private static final PathTemplate USER_PURCHASES_STUBBED = PathTemplate.of("/user/marketplace_purchases/stubbed");

    private static final JsonType<List<GitHubMarketplaceListingPlan>> PLANS_TYPE =
            JsonTypes.listOf(GitHubMarketplaceListingPlan.class);
    private static final JsonType<List<GitHubMarketplacePurchase>> PURCHASES_TYPE =
            JsonTypes.listOf(GitHubMarketplacePurchase.class);
    private static final JsonType<List<GitHubUserMarketplacePurchase>> USER_PURCHASES_TYPE =
            JsonTypes.listOf(GitHubUserMarketplacePurchase.class);

    /**
     * GitHub Marketplace resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAppsMarketplaceApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get a subscription plan for an account ({@code GET /marketplace_listing/accounts/{account_id}}).
     *
     * @param accountId account id
     * @return marketplace purchase, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubMarketplacePurchase> getSubscriptionPlanForAccount(final long accountId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ACCOUNT, accountId), GitHubMarketplacePurchase.class);
    }

    /**
     * List plans ({@code GET /marketplace_listing/plans}).
     *
     * @param request pagination token
     * @return HTTP page of plans
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMarketplaceListingPlan> listPlans(final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PLANS, Map.of(), request, PLANS_TYPE);
    }

    /**
     * List accounts for a plan ({@code GET /marketplace_listing/plans/{plan_id}/accounts}).
     *
     * @param planId plan id
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of marketplace purchases
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMarketplacePurchase> listAccountsForPlan(
            final long planId, final GitHubListAccountsForPlanFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PLAN_ACCOUNTS, filter.toQueryParameters(), request, PURCHASES_TYPE, planId);
    }

    /**
     * Get a subscription plan for an account, stubbed ({@code GET /marketplace_listing/stubbed/accounts/{account_id}}).
     *
     * @param accountId account id
     * @return marketplace purchase, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubMarketplacePurchase> getSubscriptionPlanForAccountStubbed(final long accountId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(STUBBED_ACCOUNT, accountId), GitHubMarketplacePurchase.class);
    }

    /**
     * List plans, stubbed ({@code GET /marketplace_listing/stubbed/plans}).
     *
     * @param request pagination token
     * @return HTTP page of plans
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMarketplaceListingPlan> listPlansStubbed(final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(STUBBED_PLANS, Map.of(), request, PLANS_TYPE);
    }

    /**
     * List accounts for a plan, stubbed ({@code GET /marketplace_listing/stubbed/plans/{plan_id}/accounts}).
     *
     * @param planId plan id
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of marketplace purchases
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubMarketplacePurchase> listAccountsForPlanStubbed(
            final long planId, final GitHubListAccountsForPlanStubbedFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(STUBBED_PLAN_ACCOUNTS, filter.toQueryParameters(), request, PURCHASES_TYPE, planId);
    }

    /**
     * List subscriptions for the authenticated user ({@code GET /user/marketplace_purchases}).
     *
     * @param request pagination token
     * @return HTTP page of subscriptions
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubUserMarketplacePurchase> listSubscriptionsForAuthenticatedUser(
            final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_PURCHASES, Map.of(), request, USER_PURCHASES_TYPE);
    }

    /**
     * List subscriptions for the authenticated user, stubbed ({@code GET /user/marketplace_purchases/stubbed}).
     *
     * @param request pagination token
     * @return HTTP page of subscriptions
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubUserMarketplacePurchase> listSubscriptionsForAuthenticatedUserStubbed(
            final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_PURCHASES_STUBBED, Map.of(), request, USER_PURCHASES_TYPE);
    }
}
