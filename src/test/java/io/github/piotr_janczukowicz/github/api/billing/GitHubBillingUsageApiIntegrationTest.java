package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.billingUsageApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubBillingUsageApiIntegrationTest {

    private static final String ORG_AI_CREDIT_PATH = "/organizations/octo-org/settings/billing/ai_credit/usage";
    private static final String USER_SUMMARY_PATH = "/users/octocat/settings/billing/usage/summary";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAiCreditUsageReportForOrg_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_AI_CREDIT_PATH,
                okJson(jsonBytes(
                        GitHubBillingUsageApiIntegrationTest.class, "billing-ai-credit-usage-report-org.json")));

        final GitHubBillingAiCreditUsageReportOrg report;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            report = api.api()
                    .getAiCreditUsageReportForOrg(
                            "octo-org", BillingCatalogFixtures.getGithubBillingAiCreditUsageReportOrgFilter());
        }

        assertThat(report)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingUsageApiIntegrationTest.class, "billing-ai-credit-usage-report-org.json"),
                        GitHubBillingAiCreditUsageReportOrg.class));
        HTTP.verifyRequested(GET, ORG_AI_CREDIT_PATH);
        HTTP.verifyQueryParam(GET, ORG_AI_CREDIT_PATH, "year", "2026");
        HTTP.verifyQueryParam(GET, ORG_AI_CREDIT_PATH, "model", "Claude Sonnet");
    }

    @Test
    void getUsageSummaryReportForUser_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_SUMMARY_PATH,
                okJson(jsonBytes(
                        GitHubBillingUsageApiIntegrationTest.class, "billing-usage-summary-report-user.json")));

        final GitHubBillingUsageSummaryReportUser report;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            report = api.api()
                    .getUsageSummaryReportForUser(
                            "octocat", BillingCatalogFixtures.getGithubBillingUsageSummaryReportUserFilter());
        }

        assertThat(report)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-summary-report-user.json"),
                        GitHubBillingUsageSummaryReportUser.class));
        HTTP.verifyRequested(GET, USER_SUMMARY_PATH);
        HTTP.verifyQueryParam(GET, USER_SUMMARY_PATH, "repository", "octo-org/octo-repo");
    }

    @Test
    void getAiCreditUsageReportForEnterprise_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/ai_credit/usage",
                okJson(jsonBytes(
                        GitHubBillingUsageApiIntegrationTest.class, "billing-ai-credit-usage-report-ghe.json")));

        final GitHubBillingAiCreditUsageReportGhe result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api()
                    .getAiCreditUsageReportForEnterprise(
                            "octo-enterprise", BillingCatalogFixtures.getGithubBillingAiCreditUsageReportGheFilter());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingUsageApiIntegrationTest.class, "billing-ai-credit-usage-report-ghe.json"),
                        GitHubBillingAiCreditUsageReportGhe.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/ai_credit/usage");
        HTTP.verifyQueryParam(GET, "/enterprises/octo-enterprise/settings/billing/ai_credit/usage", "year", "2026");
    }

    @Test
    void getPremiumRequestUsageReportForEnterprise_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/premium_request/usage",
                okJson(jsonBytes(
                        GitHubBillingUsageApiIntegrationTest.class, "billing-premium-request-usage-report-ghe.json")));

        final GitHubBillingPremiumRequestUsageReportGhe result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api()
                    .getPremiumRequestUsageReportForEnterprise(
                            "octo-enterprise",
                            BillingCatalogFixtures.getGithubBillingPremiumRequestUsageReportGheFilter());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingUsageApiIntegrationTest.class,
                                "billing-premium-request-usage-report-ghe.json"),
                        GitHubBillingPremiumRequestUsageReportGhe.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/premium_request/usage");
        HTTP.verifyQueryParam(
                GET, "/enterprises/octo-enterprise/settings/billing/premium_request/usage", "model", "gpt-5");
    }

    @Test
    void getUsageReportForEnterprise_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/usage",
                okJson(jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-report.json")));

        final GitHubBillingUsageReport result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api()
                    .getUsageReportForEnterprise(
                            "octo-enterprise", BillingCatalogFixtures.getGithubBillingUsageReportGheFilter());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-report.json"),
                        GitHubBillingUsageReport.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/usage");
        HTTP.verifyQueryParam(
                GET, "/enterprises/octo-enterprise/settings/billing/usage", "cost_center_id", "cc-1a2b3c");
    }

    @Test
    void getUsageSummaryReportForEnterprise_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/usage/summary",
                okJson(jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-summary-report-ghe.json")));

        final GitHubBillingUsageSummaryReportGhe result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api()
                    .getUsageSummaryReportForEnterprise(
                            "octo-enterprise", BillingCatalogFixtures.getGithubBillingUsageSummaryReportGheFilter());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-summary-report-ghe.json"),
                        GitHubBillingUsageSummaryReportGhe.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/usage/summary");
        HTTP.verifyQueryParam(
                GET, "/enterprises/octo-enterprise/settings/billing/usage/summary", "sku", "actions_linux");
    }

    @Test
    void getAiCreditUsageReportForEnterpriseWithoutFilter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/ai_credit/usage",
                okJson(jsonBytes(
                        GitHubBillingUsageApiIntegrationTest.class, "billing-ai-credit-usage-report-ghe.json")));

        final GitHubBillingAiCreditUsageReportGhe result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api().getAiCreditUsageReportForEnterprise("octo-enterprise");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingUsageApiIntegrationTest.class, "billing-ai-credit-usage-report-ghe.json"),
                        GitHubBillingAiCreditUsageReportGhe.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/ai_credit/usage");
    }

    @Test
    void getPremiumRequestUsageReportForEnterpriseWithoutFilter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/premium_request/usage",
                okJson(jsonBytes(
                        GitHubBillingUsageApiIntegrationTest.class, "billing-premium-request-usage-report-ghe.json")));

        final GitHubBillingPremiumRequestUsageReportGhe result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api().getPremiumRequestUsageReportForEnterprise("octo-enterprise");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingUsageApiIntegrationTest.class,
                                "billing-premium-request-usage-report-ghe.json"),
                        GitHubBillingPremiumRequestUsageReportGhe.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/premium_request/usage");
    }

    @Test
    void getUsageReportForEnterpriseWithoutFilter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/usage",
                okJson(jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-report.json")));

        final GitHubBillingUsageReport result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api().getUsageReportForEnterprise("octo-enterprise");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-report.json"),
                        GitHubBillingUsageReport.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/usage");
    }

    @Test
    void getUsageSummaryReportForEnterpriseWithoutFilter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/usage/summary",
                okJson(jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-summary-report-ghe.json")));

        final GitHubBillingUsageSummaryReportGhe result;
        try (final GitHubApiWireMockSupport.BillingUsageApiClient api = billingUsageApi(HTTP)) {
            result = api.api().getUsageSummaryReportForEnterprise("octo-enterprise");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageApiIntegrationTest.class, "billing-usage-summary-report-ghe.json"),
                        GitHubBillingUsageSummaryReportGhe.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/usage/summary");
    }
}
