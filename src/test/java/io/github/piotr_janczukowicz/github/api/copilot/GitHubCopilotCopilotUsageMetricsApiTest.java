package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCopilotCopilotUsageMetricsApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";
    private static final String BASE = "https://api.github.com";
    private static final String ONE_DAY = "copilot-usage-metrics-1-day-report.json";
    private static final String TWENTY_EIGHT_DAY = "copilot-usage-metrics-28-day-report.json";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotCopilotUsageMetricsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getEnterpriseOneDayUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));
        final GitHubCopilotEnterpriseOneDayUsageMetricsFilter filter =
                CopilotCatalogFixtures.copilotEnterpriseOneDayUsageMetricsFilter();

        final GitHubCopilotUsageMetrics1DayReport report = api().getEnterpriseOneDayUsageMetrics(ENTERPRISE, filter);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/enterprises/octo-enterprise/copilot/metrics/reports/enterprise-1-day");
            softly.assertThat(request.uri().getQuery()).isEqualTo("day=" + filter.day());
        });
    }

    @Test
    void getEnterpriseUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(TWENTY_EIGHT_DAY)));

        final GitHubCopilotUsageMetrics28DayReport report = api().getEnterpriseUsageMetrics(ENTERPRISE);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/enterprises/octo-enterprise/copilot/metrics/reports/enterprise-28-day/latest");
        });
    }

    @Test
    void getEnterpriseReposOneDayReport_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));
        final GitHubCopilotEnterpriseReposOneDayReportFilter filter =
                CopilotCatalogFixtures.copilotEnterpriseReposOneDayReportFilter();

        final Optional<GitHubCopilotUsageMetrics1DayReport> report =
                api().getEnterpriseReposOneDayReport(ENTERPRISE, filter);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).contains(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/enterprises/octo-enterprise/copilot/metrics/reports/repos-1-day");
            softly.assertThat(request.uri().getQuery()).isEqualTo("day=" + filter.day());
        });
    }

    @Test
    void getEnterpriseReposOneDayReport_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubCopilotUsageMetrics1DayReport> report = api().getEnterpriseReposOneDayReport(
                        ENTERPRISE, CopilotCatalogFixtures.copilotEnterpriseReposOneDayReportFilter());

        assertThat(report).isEmpty();
    }

    @Test
    void getEnterpriseUserTeamsOneDayReport_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));

        final GitHubCopilotUsageMetrics1DayReport report = api().getEnterpriseUserTeamsOneDayReport(
                        ENTERPRISE, CopilotCatalogFixtures.copilotEnterpriseUserTeamsOneDayReportFilter());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/enterprises/octo-enterprise/copilot/metrics/reports/user-teams-1-day");
        });
    }

    @Test
    void getUsersOneDayUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));

        final GitHubCopilotUsageMetrics1DayReport report = api().getUsersOneDayUsageMetrics(
                        ENTERPRISE, CopilotCatalogFixtures.copilotUsersOneDayUsageMetricsFilter());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/enterprises/octo-enterprise/copilot/metrics/reports/users-1-day");
        });
    }

    @Test
    void getUsersUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(TWENTY_EIGHT_DAY)));

        final GitHubCopilotUsageMetrics28DayReport report = api().getUsersUsageMetrics(ENTERPRISE);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/enterprises/octo-enterprise/copilot/metrics/reports/users-28-day/latest");
        });
    }

    @Test
    void getOrganizationOneDayUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));
        final GitHubCopilotOrganizationOneDayUsageMetricsFilter filter =
                CopilotCatalogFixtures.copilotOrganizationOneDayUsageMetricsFilter();

        final Optional<GitHubCopilotUsageMetrics1DayReport> report =
                api().getOrganizationOneDayUsageMetrics(ORG, filter);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).contains(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/copilot/metrics/reports/organization-1-day");
            softly.assertThat(request.uri().getQuery()).isEqualTo("day=" + filter.day());
        });
    }

    @Test
    void getOrganizationUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(TWENTY_EIGHT_DAY)));

        final GitHubCopilotUsageMetrics28DayReport report = api().getOrganizationUsageMetrics(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/copilot/metrics/reports/organization-28-day/latest");
        });
    }

    @Test
    void getOrganizationReposOneDayReport_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));

        final Optional<GitHubCopilotUsageMetrics1DayReport> report = api().getOrganizationReposOneDayReport(
                        ORG, CopilotCatalogFixtures.copilotOrganizationReposOneDayReportFilter());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).contains(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/copilot/metrics/reports/repos-1-day");
        });
    }

    @Test
    void getOrganizationUserTeamsOneDayReport_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));

        final Optional<GitHubCopilotUsageMetrics1DayReport> report = api().getOrganizationUserTeamsOneDayReport(
                        ORG, CopilotCatalogFixtures.copilotOrganizationUserTeamsOneDayReportFilter());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).contains(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/copilot/metrics/reports/user-teams-1-day");
        });
    }

    @Test
    void getOrganizationUsersOneDayUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(ONE_DAY)));

        final Optional<GitHubCopilotUsageMetrics1DayReport> report = api().getOrganizationUsersOneDayUsageMetrics(
                        ORG, CopilotCatalogFixtures.copilotOrganizationUsersOneDayUsageMetricsFilter());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).contains(CopilotCatalogFixtures.copilotUsageMetrics1DayReport());
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/copilot/metrics/reports/users-1-day");
        });
    }

    @Test
    void getOrganizationUsersUsageMetrics_shouldReturnReport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(TWENTY_EIGHT_DAY)));

        final GitHubCopilotUsageMetrics28DayReport report = api().getOrganizationUsersUsageMetrics(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(report).isEqualTo(CopilotCatalogFixtures.copilotUsageMetrics28DayReport());
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/copilot/metrics/reports/users-28-day/latest");
        });
    }

    @Test
    void getOrganizationOneDayUsageMetrics_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        assertThat(api().getOrganizationOneDayUsageMetrics(
                                ORG, CopilotCatalogFixtures.copilotOrganizationOneDayUsageMetricsFilter()))
                .isEmpty();
    }

    @Test
    void getOrganizationReposOneDayReport_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        assertThat(api().getOrganizationReposOneDayReport(
                                ORG, CopilotCatalogFixtures.copilotOrganizationReposOneDayReportFilter()))
                .isEmpty();
    }

    @Test
    void getOrganizationUserTeamsOneDayReport_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        assertThat(api().getOrganizationUserTeamsOneDayReport(
                                ORG, CopilotCatalogFixtures.copilotOrganizationUserTeamsOneDayReportFilter()))
                .isEmpty();
    }

    @Test
    void getOrganizationUsersOneDayUsageMetrics_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        assertThat(api().getOrganizationUsersOneDayUsageMetrics(
                                ORG, CopilotCatalogFixtures.copilotOrganizationUsersOneDayUsageMetricsFilter()))
                .isEmpty();
    }

    @Test
    void getEnterpriseOneDayUsageMetrics_shouldThrow_whenFilterNull() {
        final GitHubCopilotCopilotUsageMetricsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getEnterpriseOneDayUsageMetrics(ENTERPRISE, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenPathBlank(final ApiCall call, final String messageFragment) {
        final GitHubCopilotCopilotUsageMetricsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.getEnterpriseUsageMetrics(" "), "enterprise"),
                Arguments.of((ApiCall) api -> api.getOrganizationUsageMetrics(" "), "org"),
                Arguments.of((ApiCall) api -> api.getUsersUsageMetrics(" "), "enterprise"),
                Arguments.of((ApiCall) api -> api.getOrganizationUsersUsageMetrics(" "), "org"));
    }

    private GitHubCopilotCopilotUsageMetricsApi api() {
        return new GitHubCopilotCopilotUsageMetricsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotCopilotUsageMetricsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotCopilotUsageMetricsApi api) throws Exception;
    }
}
