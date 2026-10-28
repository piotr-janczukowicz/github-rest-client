package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubBillingUsageApiTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBillingUsageApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getAiCreditUsageReportForOrg_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-ai-credit-usage-report-org.json")));

        final GitHubBillingAiCreditUsageReportOrg report = api().getAiCreditUsageReportForOrg(
                        ORG, BillingCatalogFixtures.getGithubBillingAiCreditUsageReportOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingAiCreditUsageReportOrg());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/organizations/octo-org/settings/billing/ai_credit/usage?")
                    .contains("year=2026");
        });
    }

    @Test
    void getAiCreditUsageReportForUser_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-ai-credit-usage-report-user.json")));

        final GitHubBillingAiCreditUsageReportUser report = api().getAiCreditUsageReportForUser(
                        USERNAME, BillingCatalogFixtures.getGithubBillingAiCreditUsageReportUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingAiCreditUsageReportUser());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/users/octocat/settings/billing/ai_credit/usage?")
                    .contains("year=2026");
        });
    }

    @Test
    void getPremiumRequestUsageReportForOrg_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-premium-request-usage-report-org.json")));

        final GitHubBillingPremiumRequestUsageReportOrg report = api().getPremiumRequestUsageReportForOrg(
                        ORG, BillingCatalogFixtures.getGithubBillingPremiumRequestUsageReportOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingPremiumRequestUsageReportOrg());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/organizations/octo-org/settings/billing/premium_request/usage?")
                    .contains("year=2026");
        });
    }

    @Test
    void getPremiumRequestUsageReportForUser_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-premium-request-usage-report-user.json")));

        final GitHubBillingPremiumRequestUsageReportUser report = api().getPremiumRequestUsageReportForUser(
                        USERNAME, BillingCatalogFixtures.getGithubBillingPremiumRequestUsageReportUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingPremiumRequestUsageReportUser());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/users/octocat/settings/billing/premium_request/usage?")
                    .contains("year=2026");
        });
    }

    @Test
    void getUsageReportForOrg_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("billing-usage-report.json")));

        final GitHubBillingUsageReport report =
                api().getUsageReportForOrg(ORG, BillingCatalogFixtures.getGithubBillingUsageReportOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageReport());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/organizations/octo-org/settings/billing/usage?")
                    .contains("year=2026");
        });
    }

    @Test
    void getUsageReportForUser_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-usage-report-user.json")));

        final GitHubBillingUsageReportUser report =
                api().getUsageReportForUser(USERNAME, BillingCatalogFixtures.getGithubBillingUsageReportUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageReportUser());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/users/octocat/settings/billing/usage?")
                    .contains("year=2026");
        });
    }

    @Test
    void getUsageSummaryReportForOrg_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-usage-summary-report-org.json")));

        final GitHubBillingUsageSummaryReportOrg report = api().getUsageSummaryReportForOrg(
                        ORG, BillingCatalogFixtures.getGithubBillingUsageSummaryReportOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageSummaryReportOrg());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/organizations/octo-org/settings/billing/usage/summary?")
                    .contains("year=2026");
        });
    }

    @Test
    void getUsageSummaryReportForUser_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-usage-summary-report-user.json")));

        final GitHubBillingUsageSummaryReportUser report = api().getUsageSummaryReportForUser(
                        USERNAME, BillingCatalogFixtures.getGithubBillingUsageSummaryReportUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageSummaryReportUser());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/users/octocat/settings/billing/usage/summary?")
                    .contains("year=2026");
        });
    }

    @Test
    void getAiCreditUsageReportForOrg_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-ai-credit-usage-report-org.json")));

        final GitHubBillingAiCreditUsageReportOrg report = api().getAiCreditUsageReportForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingAiCreditUsageReportOrg());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/organizations/octo-org/settings/billing/ai_credit/usage");
        });
    }

    @Test
    void getAiCreditUsageReportForUser_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-ai-credit-usage-report-user.json")));

        final GitHubBillingAiCreditUsageReportUser report = api().getAiCreditUsageReportForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingAiCreditUsageReportUser());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/settings/billing/ai_credit/usage");
        });
    }

    @Test
    void getPremiumRequestUsageReportForOrg_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-premium-request-usage-report-org.json")));

        final GitHubBillingPremiumRequestUsageReportOrg report = api().getPremiumRequestUsageReportForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingPremiumRequestUsageReportOrg());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/organizations/octo-org/settings/billing/premium_request/usage");
        });
    }

    @Test
    void getPremiumRequestUsageReportForUser_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-premium-request-usage-report-user.json")));

        final GitHubBillingPremiumRequestUsageReportUser report = api().getPremiumRequestUsageReportForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingPremiumRequestUsageReportUser());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/settings/billing/premium_request/usage");
        });
    }

    @Test
    void getUsageReportForOrg_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("billing-usage-report.json")));

        final GitHubBillingUsageReport report = api().getUsageReportForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageReport());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/organizations/octo-org/settings/billing/usage");
        });
    }

    @Test
    void getUsageReportForUser_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-usage-report-user.json")));

        final GitHubBillingUsageReportUser report = api().getUsageReportForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageReportUser());
            softly.assertThat(requestUri).hasToString("https://api.github.com/users/octocat/settings/billing/usage");
        });
    }

    @Test
    void getUsageSummaryReportForOrg_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-usage-summary-report-org.json")));

        final GitHubBillingUsageSummaryReportOrg report = api().getUsageSummaryReportForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageSummaryReportOrg());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/organizations/octo-org/settings/billing/usage/summary");
        });
    }

    @Test
    void getUsageSummaryReportForUser_shouldUseEmptyFilter_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("billing-usage-summary-report-user.json")));

        final GitHubBillingUsageSummaryReportUser report = api().getUsageSummaryReportForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(BillingCatalogFixtures.billingUsageSummaryReportUser());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/settings/billing/usage/summary");
        });
    }

    private GitHubBillingUsageApi api() {
        return new GitHubBillingUsageApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubBillingUsageApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
