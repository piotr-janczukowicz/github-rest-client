package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSecretScanningAlertsApiTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final int ALERT_NUMBER = 42;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningAlertsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createPushProtectionBypass_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-push-protection-bypass.json")));

        final GitHubSecretScanningPushProtectionBypass bypass = new GitHubSecretScanningAlertsApi(
                        TestClients.caller(transport))
                .createPushProtectionBypass(
                        OWNER, REPO, SecretScanningCatalogFixtures.createPushProtectionBypassPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(bypass).isEqualTo(SecretScanningCatalogFixtures.secretScanningPushProtectionBypass());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/repos/octocat/hello-world/secret-scanning/push-protection-bypasses");
        });
    }

    @Test
    void listOrgAlerts_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("organization-secret-scanning-alert.json") + "]"));

        final GitHubPage<GitHubOrganizationSecretScanningAlert> page =
                new GitHubSecretScanningAlertsApi(TestClients.caller(transport)).listOrgAlerts(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.organizationSecretScanningAlert());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/secret-scanning/alerts?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgAlerts_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("organization-secret-scanning-alert.json") + "]"));
        final GitHubListSecretScanningAlertsFilter filter = GitHubListSecretScanningAlertsFilterBuilder.builder()
                .state(GitHubSecretScanningAlertState.OPEN)
                .secretType("mailchimp_api_key")
                .sort(GitHubSecretScanningAlertSort.UPDATED)
                .direction(GitHubDirection.DESC)
                .build();

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport)).listOrgAlerts(ORG, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("state", "open");
            softly.assertThat(requestUri).hasParameter("secret_type", "mailchimp_api_key");
            softly.assertThat(requestUri).hasParameter("sort", "updated");
            softly.assertThat(requestUri).hasParameter("direction", "desc");
        });
    }

    @Test
    void listOrgAlerts_shouldAcceptPageRequest_whenDualPaginationUsed() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("organization-secret-scanning-alert.json") + "]"));

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport))
                .listOrgAlerts(ORG, GitHubListSecretScanningAlertsFilter.empty(), GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2");
    }

    @Test
    void listRepoAlerts_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-alert.json") + "]"));

        final GitHubPage<GitHubSecretScanningAlert> page =
                new GitHubSecretScanningAlertsApi(TestClients.caller(transport)).listRepoAlerts(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecretScanningCatalogFixtures.repoSecretScanningAlert());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/alerts?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listRepoAlerts_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-alert.json") + "]"));
        final GitHubListSecretScanningAlertsFilter filter = GitHubListSecretScanningAlertsFilterBuilder.builder()
                .state(GitHubSecretScanningAlertState.OPEN)
                .build();

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport)).listRepoAlerts(OWNER, REPO, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("state", "open");
    }

    @Test
    void listRepoAlerts_shouldAcceptPageRequest_whenDualPaginationUsed() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-alert.json") + "]"));

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport))
                .listRepoAlerts(
                        OWNER, REPO, GitHubListSecretScanningAlertsFilter.empty(), GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2");
    }

    @Test
    void listOrgAlerts_shouldUseDefaultPagination_whenFilterOnlyOverloadUsed() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("organization-secret-scanning-alert.json") + "]"));

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport))
                .listOrgAlerts(ORG, GitHubListSecretScanningAlertsFilter.empty());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "1").hasParameter("per_page", "100");
    }

    @Test
    void listAlertLocations_shouldReturnLocations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-location-commit.json") + "]"));

        final GitHubPage<GitHubSecretScanningLocation> page = new GitHubSecretScanningAlertsApi(
                        TestClients.caller(transport))
                .listAlertLocations(OWNER, REPO, ALERT_NUMBER);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecretScanningCatalogFixtures.commitLocation());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/repos/octocat/hello-world/secret-scanning/alerts/42/locations?page=1&"
                                    + TestClients.perPageQuery());
        });
    }

    @Test
    void listAlertLocations_shouldAcceptPageRequest_whenPagePaginationUsed() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-location-commit.json") + "]"));

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport))
                .listAlertLocations(OWNER, REPO, ALERT_NUMBER, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2");
    }

    @Test
    void getAlert_shouldReturnAlert_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-alert-resolved.json")));
        final GitHubSecretScanningAlertWithMetadata expected =
                SecretScanningCatalogFixtures.secretScanningAlertWithMetadata();

        final GitHubSecretScanningAlertWithMetadata alert = new GitHubSecretScanningAlertsApi(
                        TestClients.caller(transport))
                .getAlert(OWNER, REPO, ALERT_NUMBER)
                .orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(alert).isEqualTo(expected);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/alerts/42");
        });
    }

    @Test
    void getAlert_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubSecretScanningAlertWithMetadata> alert =
                new GitHubSecretScanningAlertsApi(TestClients.caller(transport)).getAlert(OWNER, REPO, ALERT_NUMBER);

        assertThat(alert).isEmpty();
    }

    @Test
    void getAlert_shouldSendHideSecretQuery_whenRequested() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-alert-open.json")));

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport))
                .getAlert(
                        OWNER,
                        REPO,
                        ALERT_NUMBER,
                        GitHubGetSecretScanningAlertFilterBuilder.builder()
                                .hideSecret(true)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("hide_secret", "true");
    }

    @Test
    void updateAlert_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-alert-resolved.json")));
        final GitHubUpdateSecretScanningAlertPayload payload =
                SecretScanningCatalogFixtures.updateSecretScanningAlertPayload();

        final GitHubSecretScanningAlertWithMetadata alert = new GitHubSecretScanningAlertsApi(
                        TestClients.caller(transport))
                .updateAlert(OWNER, REPO, ALERT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(alert).isEqualTo(SecretScanningCatalogFixtures.secretScanningAlertWithMetadata());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/alerts/42");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void getScanHistory_shouldReturnHistory_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-scan-history.json")));

        final GitHubSecretScanningScanHistory history =
                new GitHubSecretScanningAlertsApi(TestClients.caller(transport)).getScanHistory(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(history).isEqualTo(SecretScanningCatalogFixtures.secretScanningScanHistory());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/scan-history");
        });
    }

    @ParameterizedTest
    @MethodSource("nullRequestCalls")
    void validation_shouldThrow_whenRequestNull(final AlertsCall call) {
        final GitHubSecretScanningAlertsApi api = new GitHubSecretScanningAlertsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @ParameterizedTest
    @MethodSource("nullFilterCalls")
    void validation_shouldThrow_whenFilterNull(final AlertsCall call) {
        final GitHubSecretScanningAlertsApi api = new GitHubSecretScanningAlertsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource({"blankArgCalls", "unsupportedPaginationRequests"})
    void call_shouldThrowIllegalArgument_whenInvalid(final AlertsCall call, final String messageFragment) {
        final GitHubSecretScanningAlertsApi api = new GitHubSecretScanningAlertsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final AlertsCall call) {
        final GitHubSecretScanningAlertsApi api = new GitHubSecretScanningAlertsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecretScanningAlertsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<AlertsCall> nullRequestCalls() {
        return Stream.of(api -> api.listAlertLocations(OWNER, REPO, ALERT_NUMBER, null));
    }

    private static Stream<AlertsCall> nullFilterCalls() {
        return Stream.of(
                api -> api.listOrgAlerts(ORG, null),
                api -> api.listOrgAlerts(ORG, null, GitHubPageRequest.byCursor()),
                api -> api.listRepoAlerts(OWNER, REPO, null),
                api -> api.listRepoAlerts(OWNER, REPO, null, GitHubPageRequest.byCursor()),
                api -> api.getAlert(OWNER, REPO, ALERT_NUMBER, null));
    }

    private static Stream<Arguments> nullPayloadCalls() {
        return Stream.of(
                Arguments.of((AlertsCall) api -> api.updateAlert(OWNER, REPO, ALERT_NUMBER, null)),
                Arguments.of((AlertsCall) api -> api.createPushProtectionBypass(OWNER, REPO, null)));
    }

    private static Stream<Arguments> blankArgCalls() {
        final GitHubListSecretScanningAlertsFilter alertsFilter = GitHubListSecretScanningAlertsFilter.empty();
        final GitHubUpdateSecretScanningAlertPayload alertPayload =
                SecretScanningCatalogFixtures.updateSecretScanningAlertPayload();
        final GitHubCreatePushProtectionBypassPayload bypassPayload =
                SecretScanningCatalogFixtures.createPushProtectionBypassPayload();
        return Stream.of(
                Arguments.of((AlertsCall) api -> api.listOrgAlerts(" "), "org"),
                Arguments.of((AlertsCall) api -> api.listOrgAlerts(" ", alertsFilter), "org"),
                Arguments.of(
                        (AlertsCall) api -> api.listOrgAlerts(" ", alertsFilter, GitHubPageRequest.byCursor()), "org"),
                Arguments.of((AlertsCall) api -> api.listRepoAlerts(" ", REPO), "owner"),
                Arguments.of((AlertsCall) api -> api.listRepoAlerts(" ", REPO, alertsFilter), "owner"),
                Arguments.of(
                        (AlertsCall) api -> api.listRepoAlerts(" ", REPO, alertsFilter, GitHubPageRequest.byCursor()),
                        "owner"),
                Arguments.of((AlertsCall) api -> api.listRepoAlerts(OWNER, " "), "repo"),
                Arguments.of((AlertsCall) api -> api.listRepoAlerts(OWNER, " ", alertsFilter), "repo"),
                Arguments.of(
                        (AlertsCall) api -> api.listRepoAlerts(OWNER, " ", alertsFilter, GitHubPageRequest.byCursor()),
                        "repo"),
                Arguments.of((AlertsCall) api -> api.getAlert(" ", REPO, ALERT_NUMBER), "owner"),
                Arguments.of((AlertsCall) api -> api.getAlert(OWNER, " ", ALERT_NUMBER), "repo"),
                Arguments.of((AlertsCall) api -> api.listAlertLocations(" ", REPO, ALERT_NUMBER), "owner"),
                Arguments.of((AlertsCall) api -> api.listAlertLocations(OWNER, " ", ALERT_NUMBER), "repo"),
                Arguments.of(
                        (AlertsCall) api -> api.listAlertLocations(" ", REPO, ALERT_NUMBER, GitHubPageRequest.byPage()),
                        "owner"),
                Arguments.of(
                        (AlertsCall)
                                api -> api.listAlertLocations(OWNER, " ", ALERT_NUMBER, GitHubPageRequest.byPage()),
                        "repo"),
                Arguments.of((AlertsCall) api -> api.updateAlert(" ", REPO, ALERT_NUMBER, alertPayload), "owner"),
                Arguments.of((AlertsCall) api -> api.updateAlert(OWNER, " ", ALERT_NUMBER, alertPayload), "repo"),
                Arguments.of((AlertsCall) api -> api.getScanHistory(" ", REPO), "owner"),
                Arguments.of((AlertsCall) api -> api.getScanHistory(OWNER, " "), "repo"),
                Arguments.of((AlertsCall) api -> api.createPushProtectionBypass(" ", REPO, bypassPayload), "owner"),
                Arguments.of((AlertsCall) api -> api.createPushProtectionBypass(OWNER, " ", bypassPayload), "repo"));
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubListSecretScanningAlertsFilter alertsFilter = GitHubListSecretScanningAlertsFilter.empty();
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        return Stream.of(
                Arguments.of(
                        (AlertsCall) api -> api.listOrgAlerts(ORG, alertsFilter, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (AlertsCall) api -> api.listRepoAlerts(OWNER, REPO, alertsFilter, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (AlertsCall)
                                api -> api.listAlertLocations(OWNER, REPO, ALERT_NUMBER, GitHubPageRequest.byCursor()),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (AlertsCall) api -> api.listAlertLocations(OWNER, REPO, ALERT_NUMBER, unsupportedSince),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface AlertsCall {
        void execute(GitHubSecretScanningAlertsApi api) throws Exception;
    }
}
