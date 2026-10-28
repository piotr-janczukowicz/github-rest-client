package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.billingUsageReportsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubBillingUsageReportsApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listUsageReportExports_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/reports",
                okJson(jsonBytes(GitHubBillingUsageReportsApiIntegrationTest.class, "usage-report-export-list.json")));

        final GitHubUsageReportExportList result;
        try (final GitHubApiWireMockSupport.BillingUsageReportsApiClient api = billingUsageReportsApi(HTTP)) {
            result = api.api().listUsageReportExports("octo-enterprise");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageReportsApiIntegrationTest.class, "usage-report-export-list.json"),
                        GitHubUsageReportExportList.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/reports");
    }

    @Test
    void createUsageReportExport_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                "/enterprises/octo-enterprise/settings/billing/reports",
                okJson(jsonBytes(GitHubBillingUsageReportsApiIntegrationTest.class, "usage-report-export.json")));

        final GitHubUsageReportExport result;
        try (final GitHubApiWireMockSupport.BillingUsageReportsApiClient api = billingUsageReportsApi(HTTP)) {
            result = api.api()
                    .createUsageReportExport(
                            "octo-enterprise", BillingCatalogFixtures.createUsageReportExportPayload());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageReportsApiIntegrationTest.class, "usage-report-export.json"),
                        GitHubUsageReportExport.class));
        HTTP.verifyBody(
                POST,
                "/enterprises/octo-enterprise/settings/billing/reports",
                new String(
                        CODEC.encode(BillingCatalogFixtures.createUsageReportExportPayload()), StandardCharsets.UTF_8));
    }

    @Test
    void getUsageReportExport_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/reports/5c7b1f0e-1a2b-4c3d-8e9f-0a1b2c3d4e5f",
                okJson(jsonBytes(GitHubBillingUsageReportsApiIntegrationTest.class, "usage-report-export.json")));

        final GitHubUsageReportExport result;
        try (final GitHubApiWireMockSupport.BillingUsageReportsApiClient api = billingUsageReportsApi(HTTP)) {
            result = api.api()
                    .getUsageReportExport("octo-enterprise", "5c7b1f0e-1a2b-4c3d-8e9f-0a1b2c3d4e5f")
                    .orElseThrow();
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingUsageReportsApiIntegrationTest.class, "usage-report-export.json"),
                        GitHubUsageReportExport.class));
        HTTP.verifyRequested(
                GET, "/enterprises/octo-enterprise/settings/billing/reports/5c7b1f0e-1a2b-4c3d-8e9f-0a1b2c3d4e5f");
    }
}
