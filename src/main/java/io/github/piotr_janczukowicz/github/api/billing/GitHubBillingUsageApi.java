package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub billing usage REST resource for API version {@code 2026-03-10}. */
public final class GitHubBillingUsageApi extends GitHubResourceApi {

    private static final PathTemplate ORG_AI_CREDIT_USAGE =
            PathTemplate.of("/organizations/{org}/settings/billing/ai_credit/usage");
    private static final PathTemplate USERS_AI_CREDIT_USAGE =
            PathTemplate.of("/users/{username}/settings/billing/ai_credit/usage");
    private static final PathTemplate ORG_PREMIUM_REQUEST_USAGE =
            PathTemplate.of("/organizations/{org}/settings/billing/premium_request/usage");
    private static final PathTemplate USERS_PREMIUM_REQUEST_USAGE =
            PathTemplate.of("/users/{username}/settings/billing/premium_request/usage");
    private static final PathTemplate ORG_USAGE = PathTemplate.of("/organizations/{org}/settings/billing/usage");
    private static final PathTemplate USERS_USAGE = PathTemplate.of("/users/{username}/settings/billing/usage");
    private static final PathTemplate ORG_USAGE_SUMMARY =
            PathTemplate.of("/organizations/{org}/settings/billing/usage/summary");
    private static final PathTemplate USERS_USAGE_SUMMARY =
            PathTemplate.of("/users/{username}/settings/billing/usage/summary");

    private static final PathTemplate ENTERPRISE_AI_CREDIT_USAGE =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/ai_credit/usage");
    private static final PathTemplate ENTERPRISE_PREMIUM_REQUEST_USAGE =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/premium_request/usage");
    private static final PathTemplate ENTERPRISE_USAGE =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/usage");
    private static final PathTemplate ENTERPRISE_USAGE_SUMMARY =
            PathTemplate.of("/enterprises/{enterprise}/settings/billing/usage/summary");

    /**
     * Billing usage resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBillingUsageApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get billing AI credit usage report for an organization ({@code GET
     * /organizations/{org}/settings/billing/ai_credit/usage}).
     *
     * @param org organization name
     * @return billing report
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingAiCreditUsageReportOrg getAiCreditUsageReportForOrg(final String org) throws IOException {
        return getAiCreditUsageReportForOrg(org, GitHubGetGithubBillingAiCreditUsageReportOrgFilter.empty());
    }

    /**
     * Get billing AI credit usage report for an organization ({@code GET
     * /organizations/{org}/settings/billing/ai_credit/usage}).
     *
     * @param org organization name
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingAiCreditUsageReportOrg getAiCreditUsageReportForOrg(
            final String org, final GitHubGetGithubBillingAiCreditUsageReportOrgFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ORG_AI_CREDIT_USAGE, org).withQuery(filter.toQueryParameters()),
                GitHubBillingAiCreditUsageReportOrg.class);
    }

    /**
     * Get billing AI credit usage report for a user ({@code GET /users/{username}/settings/billing/ai_credit/usage}).
     *
     * @param username user handle
     * @return billing report
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingAiCreditUsageReportUser getAiCreditUsageReportForUser(final String username)
            throws IOException {
        return getAiCreditUsageReportForUser(username, GitHubGetGithubBillingAiCreditUsageReportUserFilter.empty());
    }

    /**
     * Get billing AI credit usage report for a user ({@code GET /users/{username}/settings/billing/ai_credit/usage}).
     *
     * @param username user handle
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingAiCreditUsageReportUser getAiCreditUsageReportForUser(
            final String username, final GitHubGetGithubBillingAiCreditUsageReportUserFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(USERS_AI_CREDIT_USAGE, username).withQuery(filter.toQueryParameters()),
                GitHubBillingAiCreditUsageReportUser.class);
    }

    /**
     * Get billing premium request usage report for an organization ({@code GET
     * /organizations/{org}/settings/billing/premium_request/usage}).
     *
     * @param org organization name
     * @return billing report
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingPremiumRequestUsageReportOrg getPremiumRequestUsageReportForOrg(final String org)
            throws IOException {
        return getPremiumRequestUsageReportForOrg(
                org, GitHubGetGithubBillingPremiumRequestUsageReportOrgFilter.empty());
    }

    /**
     * Get billing premium request usage report for an organization ({@code GET
     * /organizations/{org}/settings/billing/premium_request/usage}).
     *
     * @param org organization name
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingPremiumRequestUsageReportOrg getPremiumRequestUsageReportForOrg(
            final String org, final GitHubGetGithubBillingPremiumRequestUsageReportOrgFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ORG_PREMIUM_REQUEST_USAGE, org).withQuery(filter.toQueryParameters()),
                GitHubBillingPremiumRequestUsageReportOrg.class);
    }

    /**
     * Get billing premium request usage report for a user ({@code GET
     * /users/{username}/settings/billing/premium_request/usage}).
     *
     * @param username user handle
     * @return billing report
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingPremiumRequestUsageReportUser getPremiumRequestUsageReportForUser(final String username)
            throws IOException {
        return getPremiumRequestUsageReportForUser(
                username, GitHubGetGithubBillingPremiumRequestUsageReportUserFilter.empty());
    }

    /**
     * Get billing premium request usage report for a user ({@code GET
     * /users/{username}/settings/billing/premium_request/usage}).
     *
     * @param username user handle
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingPremiumRequestUsageReportUser getPremiumRequestUsageReportForUser(
            final String username, final GitHubGetGithubBillingPremiumRequestUsageReportUserFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(USERS_PREMIUM_REQUEST_USAGE, username).withQuery(filter.toQueryParameters()),
                GitHubBillingPremiumRequestUsageReportUser.class);
    }

    /**
     * Get billing usage report for an organization ({@code GET /organizations/{org}/settings/billing/usage}).
     *
     * @param org organization name
     * @return billing report
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageReport getUsageReportForOrg(final String org) throws IOException {
        return getUsageReportForOrg(org, GitHubGetGithubBillingUsageReportOrgFilter.empty());
    }

    /**
     * Get billing usage report for an organization ({@code GET /organizations/{org}/settings/billing/usage}).
     *
     * @param org organization name
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageReport getUsageReportForOrg(
            final String org, final GitHubGetGithubBillingUsageReportOrgFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(get(ORG_USAGE, org).withQuery(filter.toQueryParameters()), GitHubBillingUsageReport.class);
    }

    /**
     * Get billing usage report for a user ({@code GET /users/{username}/settings/billing/usage}).
     *
     * @param username user handle
     * @return billing report
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageReportUser getUsageReportForUser(final String username) throws IOException {
        return getUsageReportForUser(username, GitHubGetGithubBillingUsageReportUserFilter.empty());
    }

    /**
     * Get billing usage report for a user ({@code GET /users/{username}/settings/billing/usage}).
     *
     * @param username user handle
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageReportUser getUsageReportForUser(
            final String username, final GitHubGetGithubBillingUsageReportUserFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(USERS_USAGE, username).withQuery(filter.toQueryParameters()), GitHubBillingUsageReportUser.class);
    }

    /**
     * Get billing usage summary for an organization ({@code GET /organizations/{org}/settings/billing/usage/summary}).
     *
     * @param org organization name
     * @return billing report
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageSummaryReportOrg getUsageSummaryReportForOrg(final String org) throws IOException {
        return getUsageSummaryReportForOrg(org, GitHubGetGithubBillingUsageSummaryReportOrgFilter.empty());
    }

    /**
     * Get billing usage summary for an organization ({@code GET /organizations/{org}/settings/billing/usage/summary}).
     *
     * @param org organization name
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageSummaryReportOrg getUsageSummaryReportForOrg(
            final String org, final GitHubGetGithubBillingUsageSummaryReportOrgFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ORG_USAGE_SUMMARY, org).withQuery(filter.toQueryParameters()),
                GitHubBillingUsageSummaryReportOrg.class);
    }

    /**
     * Get billing usage summary for a user ({@code GET /users/{username}/settings/billing/usage/summary}).
     *
     * @param username user handle
     * @return billing report
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageSummaryReportUser getUsageSummaryReportForUser(final String username) throws IOException {
        return getUsageSummaryReportForUser(username, GitHubGetGithubBillingUsageSummaryReportUserFilter.empty());
    }

    /**
     * Get billing usage summary for a user ({@code GET /users/{username}/settings/billing/usage/summary}).
     *
     * @param username user handle
     * @param filter query filters
     * @return billing report
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD}
     */
    @Flavors(GitHubFlavor.CLOUD)
    public GitHubBillingUsageSummaryReportUser getUsageSummaryReportForUser(
            final String username, final GitHubGetGithubBillingUsageSummaryReportUserFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(USERS_USAGE_SUMMARY, username).withQuery(filter.toQueryParameters()),
                GitHubBillingUsageSummaryReportUser.class);
    }

    /**
     * Get billing AI credit usage report for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/ai_credit/usage}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingAiCreditUsageReportGhe getAiCreditUsageReportForEnterprise(final String enterprise)
            throws IOException {
        return getAiCreditUsageReportForEnterprise(
                enterprise, GitHubGetGithubBillingAiCreditUsageReportGheFilter.empty());
    }

    /**
     * Get billing AI credit usage report for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/ai_credit/usage}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingAiCreditUsageReportGhe getAiCreditUsageReportForEnterprise(
            final String enterprise, final GitHubGetGithubBillingAiCreditUsageReportGheFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_AI_CREDIT_USAGE, enterprise).withQuery(filter.toQueryParameters()),
                GitHubBillingAiCreditUsageReportGhe.class);
    }

    /**
     * Get billing premium request usage report for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/premium_request/usage}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingPremiumRequestUsageReportGhe getPremiumRequestUsageReportForEnterprise(final String enterprise)
            throws IOException {
        return getPremiumRequestUsageReportForEnterprise(
                enterprise, GitHubGetGithubBillingPremiumRequestUsageReportGheFilter.empty());
    }

    /**
     * Get billing premium request usage report for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/premium_request/usage}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingPremiumRequestUsageReportGhe getPremiumRequestUsageReportForEnterprise(
            final String enterprise, final GitHubGetGithubBillingPremiumRequestUsageReportGheFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_PREMIUM_REQUEST_USAGE, enterprise).withQuery(filter.toQueryParameters()),
                GitHubBillingPremiumRequestUsageReportGhe.class);
    }

    /**
     * Get billing usage report for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/usage}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingUsageReport getUsageReportForEnterprise(final String enterprise) throws IOException {
        return getUsageReportForEnterprise(enterprise, GitHubGetGithubBillingUsageReportGheFilter.empty());
    }

    /**
     * Get billing usage report for an enterprise ({@code GET /enterprises/{enterprise}/settings/billing/usage}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingUsageReport getUsageReportForEnterprise(
            final String enterprise, final GitHubGetGithubBillingUsageReportGheFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_USAGE, enterprise).withQuery(filter.toQueryParameters()),
                GitHubBillingUsageReport.class);
    }

    /**
     * Get billing usage summary for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/usage/summary}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingUsageSummaryReportGhe getUsageSummaryReportForEnterprise(final String enterprise)
            throws IOException {
        return getUsageSummaryReportForEnterprise(
                enterprise, GitHubGetGithubBillingUsageSummaryReportGheFilter.empty());
    }

    /**
     * Get billing usage summary for an enterprise ({@code GET
     * /enterprises/{enterprise}/settings/billing/usage/summary}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @return billing report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBillingUsageSummaryReportGhe getUsageSummaryReportForEnterprise(
            final String enterprise, final GitHubGetGithubBillingUsageSummaryReportGheFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_USAGE_SUMMARY, enterprise).withQuery(filter.toQueryParameters()),
                GitHubBillingUsageSummaryReportGhe.class);
    }
}
