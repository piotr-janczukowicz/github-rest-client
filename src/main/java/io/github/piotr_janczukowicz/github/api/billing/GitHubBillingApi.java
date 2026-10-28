package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Billing REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubBillingApi {

    private final LazyValue<GitHubBillingBudgetsApi> budgets;
    private final LazyValue<GitHubBillingUsageApi> usage;
    private final LazyValue<GitHubBillingCostCentersApi> costCenters;
    private final LazyValue<GitHubBillingUsageReportsApi> usageReports;
    private final LazyValue<GitHubBillingBillingApi> billing;

    /**
     * Billing resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBillingApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.budgets = new LazyValue<>(() -> new GitHubBillingBudgetsApi(caller));
        this.usage = new LazyValue<>(() -> new GitHubBillingUsageApi(caller));
        this.costCenters = new LazyValue<>(() -> new GitHubBillingCostCentersApi(caller));
        this.usageReports = new LazyValue<>(() -> new GitHubBillingUsageReportsApi(caller));
        this.billing = new LazyValue<>(() -> new GitHubBillingBillingApi(caller));
    }

    /**
     * Budget operations ({@code subcategory=budgets}).
     *
     * @return budgets API
     */
    public GitHubBillingBudgetsApi budgets() {
        return budgets.get();
    }

    /**
     * Billing usage operations ({@code subcategory=usage}).
     *
     * @return usage API
     */
    public GitHubBillingUsageApi usage() {
        return usage.get();
    }

    /**
     * Cost center operations ({@code subcategory=cost-centers}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return cost centers API
     */
    public GitHubBillingCostCentersApi costCenters() {
        return costCenters.get();
    }

    /**
     * Billing usage report export operations ({@code subcategory=usage-reports}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return usage reports API
     */
    public GitHubBillingUsageReportsApi usageReports() {
        return usageReports.get();
    }

    /**
     * Billing advanced security operations ({@code subcategory=billing}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return billing API
     */
    public GitHubBillingBillingApi billing() {
        return billing.get();
    }
}
