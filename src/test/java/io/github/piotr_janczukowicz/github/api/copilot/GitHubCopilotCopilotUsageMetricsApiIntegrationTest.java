package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotCopilotUsageMetricsApiIntegrationTest {

    private static final String ENTERPRISE_1_DAY =
            "/enterprises/octo-enterprise/copilot/metrics/reports/enterprise-1-day";
    private static final String ENTERPRISE_28_DAY =
            "/enterprises/octo-enterprise/copilot/metrics/reports/enterprise-28-day/latest";
    private static final String ENTERPRISE_REPOS_1_DAY =
            "/enterprises/octo-enterprise/copilot/metrics/reports/repos-1-day";
    private static final String ENTERPRISE_USER_TEAMS_1_DAY =
            "/enterprises/octo-enterprise/copilot/metrics/reports/user-teams-1-day";
    private static final String ENTERPRISE_USERS_1_DAY =
            "/enterprises/octo-enterprise/copilot/metrics/reports/users-1-day";
    private static final String ENTERPRISE_USERS_28_DAY =
            "/enterprises/octo-enterprise/copilot/metrics/reports/users-28-day/latest";
    private static final String ORG_1_DAY = "/orgs/octo-org/copilot/metrics/reports/organization-1-day";
    private static final String ORG_28_DAY = "/orgs/octo-org/copilot/metrics/reports/organization-28-day/latest";
    private static final String ORG_REPOS_1_DAY = "/orgs/octo-org/copilot/metrics/reports/repos-1-day";
    private static final String ORG_USER_TEAMS_1_DAY = "/orgs/octo-org/copilot/metrics/reports/user-teams-1-day";
    private static final String ORG_USERS_1_DAY = "/orgs/octo-org/copilot/metrics/reports/users-1-day";
    private static final String ORG_USERS_28_DAY = "/orgs/octo-org/copilot/metrics/reports/users-28-day/latest";
    private static final String ONE_DAY = "copilot-usage-metrics-1-day-report.json";
    private static final String ENTERPRISE_USAGE_RECORDS = "/enterprises/octo-enterprise/copilot/usage-records";
    private static final String TWENTY_EIGHT_DAY = "copilot-usage-metrics-28-day-report.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getEnterpriseOneDayUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ENTERPRISE_1_DAY, ok(ONE_DAY));
        final GitHubCopilotEnterpriseOneDayUsageMetricsFilter filter =
                CopilotCatalogFixtures.copilotEnterpriseOneDayUsageMetricsFilter();

        final GitHubCopilotUsageMetrics1DayReport report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api().copilotUsageMetrics().getEnterpriseOneDayUsageMetrics("octo-enterprise", filter);
        }

        assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
        HTTP.verifyRequested(GET, ENTERPRISE_1_DAY);
        HTTP.verifyQueryParam(GET, ENTERPRISE_1_DAY, "day", filter.day().toString());
    }

    @Test
    void getEnterpriseUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ENTERPRISE_28_DAY, ok(TWENTY_EIGHT_DAY));

        final GitHubCopilotUsageMetrics28DayReport report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api().copilotUsageMetrics().getEnterpriseUsageMetrics("octo-enterprise");
        }

        assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
        HTTP.verifyRequested(GET, ENTERPRISE_28_DAY);
    }

    @Test
    void getEnterpriseReposOneDayReport_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ENTERPRISE_REPOS_1_DAY, ok(ONE_DAY));
        final GitHubCopilotEnterpriseReposOneDayReportFilter filter =
                CopilotCatalogFixtures.copilotEnterpriseReposOneDayReportFilter();

        final Optional<GitHubCopilotUsageMetrics1DayReport> report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api().copilotUsageMetrics().getEnterpriseReposOneDayReport("octo-enterprise", filter);
        }

        assertThat(report).contains(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
        HTTP.verifyQueryParam(GET, ENTERPRISE_REPOS_1_DAY, "day", filter.day().toString());
    }

    @Test
    void getEnterpriseReposOneDayReport_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, ENTERPRISE_REPOS_1_DAY, noContent());

        final Optional<GitHubCopilotUsageMetrics1DayReport> report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api()
                    .copilotUsageMetrics()
                    .getEnterpriseReposOneDayReport(
                            "octo-enterprise", CopilotCatalogFixtures.copilotEnterpriseReposOneDayReportFilter());
        }

        assertThat(report).isEmpty();
    }

    @Test
    void getEnterpriseUserTeamsOneDayReport_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ENTERPRISE_USER_TEAMS_1_DAY, ok(ONE_DAY));

        final GitHubCopilotUsageMetrics1DayReport report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api()
                    .copilotUsageMetrics()
                    .getEnterpriseUserTeamsOneDayReport(
                            "octo-enterprise", CopilotCatalogFixtures.copilotEnterpriseUserTeamsOneDayReportFilter());
        }

        assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
        HTTP.verifyRequested(GET, ENTERPRISE_USER_TEAMS_1_DAY);
    }

    @Test
    void getUsersOneDayUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ENTERPRISE_USERS_1_DAY, ok(ONE_DAY));

        final GitHubCopilotUsageMetrics1DayReport report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api()
                    .copilotUsageMetrics()
                    .getUsersOneDayUsageMetrics(
                            "octo-enterprise", CopilotCatalogFixtures.copilotUsersOneDayUsageMetricsFilter());
        }

        assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
        HTTP.verifyRequested(GET, ENTERPRISE_USERS_1_DAY);
    }

    @Test
    void getUsersUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ENTERPRISE_USERS_28_DAY, ok(TWENTY_EIGHT_DAY));

        final GitHubCopilotUsageMetrics28DayReport report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api().copilotUsageMetrics().getUsersUsageMetrics("octo-enterprise");
        }

        assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
        HTTP.verifyRequested(GET, ENTERPRISE_USERS_28_DAY);
    }

    @Test
    void getOrganizationOneDayUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_1_DAY, ok(ONE_DAY));
        final GitHubCopilotOrganizationOneDayUsageMetricsFilter filter =
                CopilotCatalogFixtures.copilotOrganizationOneDayUsageMetricsFilter();

        final Optional<GitHubCopilotUsageMetrics1DayReport> report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api().copilotUsageMetrics().getOrganizationOneDayUsageMetrics("octo-org", filter);
        }

        assertThat(report).contains(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
        HTTP.verifyQueryParam(GET, ORG_1_DAY, "day", filter.day().toString());
    }

    @Test
    void getOrganizationOneDayUsageMetrics_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, ORG_1_DAY, noContent());

        final Optional<GitHubCopilotUsageMetrics1DayReport> report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api()
                    .copilotUsageMetrics()
                    .getOrganizationOneDayUsageMetrics(
                            "octo-org", CopilotCatalogFixtures.copilotOrganizationOneDayUsageMetricsFilter());
        }

        assertThat(report).isEmpty();
    }

    @Test
    void getOrganizationUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_28_DAY, ok(TWENTY_EIGHT_DAY));

        final GitHubCopilotUsageMetrics28DayReport report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api().copilotUsageMetrics().getOrganizationUsageMetrics("octo-org");
        }

        assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
        HTTP.verifyRequested(GET, ORG_28_DAY);
    }

    @Test
    void getOrganizationReposOneDayReport_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, ORG_REPOS_1_DAY, noContent());

        final Optional<GitHubCopilotUsageMetrics1DayReport> report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api()
                    .copilotUsageMetrics()
                    .getOrganizationReposOneDayReport(
                            "octo-org", CopilotCatalogFixtures.copilotOrganizationReposOneDayReportFilter());
        }

        assertThat(report).isEmpty();
    }

    @Test
    void getOrganizationUserTeamsOneDayReport_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, ORG_USER_TEAMS_1_DAY, noContent());

        final Optional<GitHubCopilotUsageMetrics1DayReport> report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api()
                    .copilotUsageMetrics()
                    .getOrganizationUserTeamsOneDayReport(
                            "octo-org", CopilotCatalogFixtures.copilotOrganizationUserTeamsOneDayReportFilter());
        }

        assertThat(report).isEmpty();
    }

    @Test
    void getOrganizationUsersOneDayUsageMetrics_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, ORG_USERS_1_DAY, noContent());

        final Optional<GitHubCopilotUsageMetrics1DayReport> report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api()
                    .copilotUsageMetrics()
                    .getOrganizationUsersOneDayUsageMetrics(
                            "octo-org", CopilotCatalogFixtures.copilotOrganizationUsersOneDayUsageMetricsFilter());
        }

        assertThat(report).isEmpty();
    }

    @Test
    void getOrganizationUsersUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_USERS_28_DAY, ok(TWENTY_EIGHT_DAY));

        final GitHubCopilotUsageMetrics28DayReport report;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            report = copilot.api().copilotUsageMetrics().getOrganizationUsersUsageMetrics("octo-org");
        }

        assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
        HTTP.verifyRequested(GET, ORG_USERS_28_DAY);
    }

    private static StubHttpServer.StubResponse ok(final String fileName) {
        return okJson(jsonBytes(GitHubCopilotCopilotUsageMetricsApiIntegrationTest.class, fileName));
    }

    @Test
    void getUsageRecords_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_USAGE_RECORDS,
                okJson(jsonBytes(
                        GitHubCopilotCopilotUsageMetricsApiIntegrationTest.class, "get-usage-records-response.json")));

        final GitHubPage<GitHubGetUsageRecordsItem> page;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            page = copilot.api().copilotUsageMetrics().getUsageRecords("octo-enterprise");
        }

        assertThat(page.items()).isEqualTo(List.of(CopilotCatalogFixtures.getUsageRecordsItem()));
        HTTP.verifyRequested(GET, ENTERPRISE_USAGE_RECORDS);
    }

    @Test
    void getUsageRecords_shouldSendFilter_whenFilterGiven() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_USAGE_RECORDS,
                okJson(jsonBytes(
                        GitHubCopilotCopilotUsageMetricsApiIntegrationTest.class, "get-usage-records-response.json")));
        final GitHubGetUsageRecordsFilter filter = CopilotCatalogFixtures.getUsageRecordsFilter();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api()
                    .copilotUsageMetrics()
                    .getUsageRecords("octo-enterprise", filter, GitHubPageRequest.byCursor());
        }

        HTTP.verifyRequested(GET, ENTERPRISE_USAGE_RECORDS);
        HTTP.verifyQueryParam(GET, ENTERPRISE_USAGE_RECORDS, "phrase", "type:request");
    }
}
