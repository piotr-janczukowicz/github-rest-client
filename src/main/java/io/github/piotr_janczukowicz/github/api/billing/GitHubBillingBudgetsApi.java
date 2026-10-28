package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
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
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub billing budgets REST resource for API version {@code 2026-03-10}. */
public final class GitHubBillingBudgetsApi extends GitHubResourceApi {

    private static final PathTemplate BUDGETS = PathTemplate.of("/organizations/{org}/settings/billing/budgets");
    private static final PathTemplate BUDGET =
            PathTemplate.of("/organizations/{org}/settings/billing/budgets/{budget_id}");

    private static final GitHubListSpec<GitHubBudget> BUDGETS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubGetAllBudgets.class,
            GitHubGetAllBudgets::budgets,
            GitHubGetAllBudgets::totalCount);

    private static final PathTemplate ENTERPRISE_BUDGETS =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/budgets");
    private static final PathTemplate ENTERPRISE_BUDGET =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/budgets/{budget_id}");
    private static final PathTemplate ENTERPRISE_BUDGET_USER_STATES =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/budgets/{budget_id}/user-states");

    /**
     * Billing budgets resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBillingBudgetsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all budgets for an organization ({@code GET /organizations/{org}/settings/billing/budgets}).
     *
     * @param org organization name
     * @return first HTTP page of budgets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubPage<GitHubBudget> listBudgets(final String org) throws IOException {
        return listBudgets(org, GitHubGetAllBudgetsOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get all budgets for an organization ({@code GET /organizations/{org}/settings/billing/budgets}).
     *
     * @param org organization name
     * @param filter query filters
     * @return first HTTP page of budgets
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubPage<GitHubBudget> listBudgets(final String org, final GitHubGetAllBudgetsOrgFilter filter)
            throws IOException {
        return listBudgets(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * Get all budgets for an organization ({@code GET /organizations/{org}/settings/billing/budgets}).
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of budgets
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubPage<GitHubBudget> listBudgets(
            final String org, final GitHubGetAllBudgetsOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(BUDGETS, filter.toQueryParameters(), request, BUDGETS_SPEC, org);
    }

    /**
     * Create a budget for an organization ({@code POST /organizations/{org}/settings/billing/budgets}).
     *
     * @param org organization name
     * @return created budget
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubCreateBudget createOrganizationBudget(final String org) throws IOException {
        return createOrganizationBudget(org, null);
    }

    /**
     * Create a budget for an organization ({@code POST /organizations/{org}/settings/billing/budgets}).
     *
     * @param org organization name
     * @param payload budget to create, or {@code null} to send no body
     * @return created budget
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubCreateBudget createOrganizationBudget(
            final String org, final @Nullable GitHubCreateOrganizationBudgetPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        return caller.fetch(caller.jsonIfPresent(post(BUDGETS, org), payload), GitHubCreateBudget.class);
    }

    /**
     * Get a budget by ID for an organization ({@code GET /organizations/{org}/settings/billing/budgets/{budget_id}}).
     *
     * @param org organization name
     * @param budgetId budget ID
     * @return budget, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code budgetId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public Optional<GitHubGetBudget> getBudget(final String org, final String budgetId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        return caller.fetchOrEmpty(get(BUDGET, org, budgetId), GitHubGetBudget.class);
    }

    /**
     * Update a budget for an organization ({@code PATCH /organizations/{org}/settings/billing/budgets/{budget_id}}).
     *
     * @param org organization name
     * @param budgetId budget ID
     * @param payload fields to update
     * @return updated budget
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code budgetId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubUpdateBudget updateBudget(
            final String org, final String budgetId, final GitHubUpdateBudgetOrgPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(BUDGET, org, budgetId), payload), GitHubUpdateBudget.class);
    }

    /**
     * Delete a budget for an organization ({@code DELETE /organizations/{org}/settings/billing/budgets/{budget_id}}).
     *
     * @param org organization name
     * @param budgetId budget ID
     * @return deletion result
     * @throws IllegalArgumentException if {@code org} or {@code budgetId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubDeleteBudget deleteBudget(final String org, final String budgetId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        return caller.fetch(delete(BUDGET, org, budgetId), GitHubDeleteBudget.class);
    }

    /**
     * Get all budgets for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/budgets}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of budgets
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubBudget> listEnterpriseBudgets(final String enterprise) throws IOException {
        return listEnterpriseBudgets(enterprise, GitHubGetAllBudgetsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get all budgets for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/budgets}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return first HTTP page of budgets
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubBudget> listEnterpriseBudgets(
            final String enterprise, final GitHubGetAllBudgetsFilter filter) throws IOException {
        return listEnterpriseBudgets(enterprise, filter, GitHubPageRequest.byPage());
    }

    /**
     * Get all budgets for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/budgets}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of budgets
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubBudget> listEnterpriseBudgets(
            final String enterprise, final GitHubGetAllBudgetsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_BUDGETS, filter.toQueryParameters(), request, BUDGETS_SPEC, enterprise);
    }

    /**
     * Create a budget for an enterprise ({@code POST /enterprises/{enterprise}/settings/billing/budgets}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload budget to create
     * @return created budget
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubCreateBudget createEnterpriseBudget(final String enterprise, final GitHubCreateBudgetPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ENTERPRISE_BUDGETS, enterprise), payload), GitHubCreateBudget.class);
    }

    /**
     * Get a budget by ID for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/budgets/{budget_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param budgetId budget ID
     * @return budget, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code budgetId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetBudget> getEnterpriseBudget(final String enterprise, final String budgetId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ENTERPRISE_BUDGET, enterprise, budgetId), GitHubGetBudget.class);
    }

    /**
     * Update a budget for an enterprise ({@code PATCH /enterprises/{enterprise}/settings/billing/budgets/{budget_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param budgetId budget ID
     * @param payload fields to update
     * @return updated budget
     * @throws IllegalArgumentException if {@code enterprise} or {@code budgetId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubUpdateBudget updateEnterpriseBudget(
            final String enterprise, final String budgetId, final GitHubUpdateBudgetPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_BUDGET, enterprise, budgetId), payload), GitHubUpdateBudget.class);
    }

    /**
     * Delete a budget for an enterprise ({@code DELETE
     * /enterprises/{enterprise}/settings/billing/budgets/{budget_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param budgetId budget ID
     * @return deletion result
     * @throws IllegalArgumentException if {@code enterprise} or {@code budgetId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubDeleteBudget deleteEnterpriseBudget(final String enterprise, final String budgetId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(delete(ENTERPRISE_BUDGET, enterprise, budgetId), GitHubDeleteBudget.class);
    }

    /**
     * Get per-user states of a multi-user budget for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/budgets/{budget_id}/user-states}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param budgetId budget ID
     * @return first HTTP page of user states, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code budgetId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetBudgetUserStates> listEnterpriseBudgetUserStates(
            final String enterprise, final String budgetId) throws IOException {
        return listEnterpriseBudgetUserStates(
                enterprise, budgetId, GitHubGetBudgetUserStatesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get per-user states of a multi-user budget for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/budgets/{budget_id}/user-states}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param budgetId budget ID
     * @param filter query filters
     * @param request pagination token
     * @return requested page of user states, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code budgetId} is blank, or {@code request} is
     *     invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGetBudgetUserStates> listEnterpriseBudgetUserStates(
            final String enterprise,
            final String budgetId,
            final GitHubGetBudgetUserStatesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetchOrEmpty(
                caller.pageParameters(
                        get(ENTERPRISE_BUDGET_USER_STATES, enterprise, budgetId).withQuery(filter.toQueryParameters()),
                        request,
                        GitHubPageRequestKindValidators.PAGE),
                GitHubGetBudgetUserStates.class);
    }
}
