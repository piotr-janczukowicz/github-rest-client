package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.secretScanningApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@IntegrationTest
class GitHubSecretScanningApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final int ALERT_NUMBER = 42;
    private static final long PATTERN_ID = 1L;

    private static final String ORG_ALERTS_PATH = "/orgs/octo-org/secret-scanning/alerts";
    private static final String ORG_CUSTOM_PATTERNS_PATH = "/orgs/octo-org/secret-scanning/custom-patterns";
    private static final String ORG_CUSTOM_PATTERN_PATH = "/orgs/octo-org/secret-scanning/custom-patterns/1";
    private static final String ORG_PATTERN_CONFIGS_PATH = "/orgs/octo-org/secret-scanning/pattern-configurations";
    private static final String REPO_ALERTS_PATH = "/repos/octocat/hello-world/secret-scanning/alerts";
    private static final String REPO_ALERT_PATH = "/repos/octocat/hello-world/secret-scanning/alerts/42";
    private static final String REPO_ALERT_LOCATIONS_PATH =
            "/repos/octocat/hello-world/secret-scanning/alerts/42/locations";
    private static final String REPO_CUSTOM_PATTERNS_PATH =
            "/repos/octocat/hello-world/secret-scanning/custom-patterns";
    private static final String REPO_CUSTOM_PATTERN_PATH =
            "/repos/octocat/hello-world/secret-scanning/custom-patterns/1";
    private static final String REPO_PUSH_PROTECTION_BYPASSES_PATH =
            "/repos/octocat/hello-world/secret-scanning/push-protection-bypasses";
    private static final String REPO_SCAN_HISTORY_PATH = "/repos/octocat/hello-world/secret-scanning/scan-history";

    private static final JsonType<List<GitHubOrganizationSecretScanningAlert>> ORG_ALERTS_TYPE =
            JsonTypes.listOf(GitHubOrganizationSecretScanningAlert.class);
    private static final JsonType<List<GitHubSecretScanningAlert>> REPO_ALERTS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningAlert.class);
    private static final JsonType<List<GitHubSecretScanningCustomPattern>> CUSTOM_PATTERNS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningCustomPattern.class);
    private static final JsonType<List<GitHubSecretScanningLocation>> LOCATIONS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningLocation.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgAlerts_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_ALERTS_PATH,
                okJson(listJsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "organization-secret-scanning-alert.json")));

        final GitHubPage<GitHubOrganizationSecretScanningAlert> page;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            page = secretScanning.api().alerts().listOrgAlerts(ORG);
        }

        final List<GitHubOrganizationSecretScanningAlert> expected = CODEC.decode(
                listJsonBytes(GitHubSecretScanningApiIntegrationTest.class, "organization-secret-scanning-alert.json"),
                ORG_ALERTS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ORG_ALERTS_PATH);
        HTTP.verifyQueryParam(GET, ORG_ALERTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_ALERTS_PATH, "per_page", "100");
    }

    @Test
    void listOrgAlerts_shouldFollowLinkNext_whenAfterCursorPresent() throws Exception {
        HTTP.stub(
                GET,
                ORG_ALERTS_PATH,
                okJson(listJsonBytes(
                                GitHubSecretScanningApiIntegrationTest.class,
                                "organization-secret-scanning-alert.json"))
                        .header(
                                LINK,
                                linkNext(HTTP.uri(
                                        ORG_ALERTS_PATH + "?after=cursor-after-token&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "organization-secret-scanning-alert.json")));

        final GitHubPage<GitHubOrganizationSecretScanningAlert> first;
        final Optional<GitHubPage<GitHubOrganizationSecretScanningAlert>> second;
        final Optional<GitHubPage<GitHubOrganizationSecretScanningAlert>> third;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            first = secretScanning.api().alerts().listOrgAlerts(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final List<GitHubOrganizationSecretScanningAlert> expected = expectedOrgAlerts();

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected);
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected);
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_ALERTS_PATH, 2);
    }

    @Test
    void listOrgAlerts_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ORG_ALERTS_PATH,
                okJson(listJsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "organization-secret-scanning-alert.json")));
        final GitHubListSecretScanningAlertsFilter filter = GitHubListSecretScanningAlertsFilterBuilder.builder()
                .state(GitHubSecretScanningAlertState.OPEN)
                .secretType("mailchimp_api_key")
                .sort(GitHubSecretScanningAlertSort.UPDATED)
                .direction(GitHubDirection.DESC)
                .build();

        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            secretScanning.api().alerts().listOrgAlerts(ORG, filter);
        }

        HTTP.verifyRequested(GET, ORG_ALERTS_PATH);
        HTTP.verifyQueryParam(GET, ORG_ALERTS_PATH, "state", "open");
        HTTP.verifyQueryParam(GET, ORG_ALERTS_PATH, "secret_type", "mailchimp_api_key");
        HTTP.verifyQueryParam(GET, ORG_ALERTS_PATH, "sort", "updated");
        HTTP.verifyQueryParam(GET, ORG_ALERTS_PATH, "direction", "desc");
    }

    @Test
    void listRepoAlerts_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ALERTS_PATH,
                okJson(listJsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-alert.json")));

        final GitHubPage<GitHubSecretScanningAlert> page;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            page = secretScanning.api().alerts().listRepoAlerts(OWNER, REPO);
        }

        final List<GitHubSecretScanningAlert> expected = CODEC.decode(
                listJsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-alert.json"),
                REPO_ALERTS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, REPO_ALERTS_PATH);
        HTTP.verifyQueryParam(GET, REPO_ALERTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_ALERTS_PATH, "per_page", "100");
    }

    @Test
    void getAlert_shouldReturnAlert_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ALERT_PATH,
                okJson(jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-alert-open.json")));

        final GitHubSecretScanningAlertWithMetadata alert;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            alert = secretScanning
                    .api()
                    .alerts()
                    .getAlert(OWNER, REPO, ALERT_NUMBER)
                    .orElseThrow();
        }

        final GitHubSecretScanningAlertWithMetadata expected = CODEC.decode(
                jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-alert-open.json"),
                GitHubSecretScanningAlertWithMetadata.class);

        assertThat(alert).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_ALERT_PATH);
    }

    @Test
    void updateAlert_shouldReturnAlert_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                REPO_ALERT_PATH,
                okJson(jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-alert-resolved.json")));

        final GitHubSecretScanningAlertWithMetadata alert;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            alert = secretScanning
                    .api()
                    .alerts()
                    .updateAlert(
                            OWNER,
                            REPO,
                            ALERT_NUMBER,
                            SecretScanningCatalogFixtures.updateSecretScanningAlertPayload());
        }

        final GitHubSecretScanningAlertWithMetadata expected = CODEC.decode(
                jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-alert-resolved.json"),
                GitHubSecretScanningAlertWithMetadata.class);

        assertThat(alert).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, REPO_ALERT_PATH);
        HTTP.verifyHeader(PATCH, REPO_ALERT_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void listAlertLocations_shouldReturnLocations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ALERT_LOCATIONS_PATH,
                okJson(listJsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-location-commit.json")));

        final GitHubPage<GitHubSecretScanningLocation> page;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            page = secretScanning.api().alerts().listAlertLocations(OWNER, REPO, ALERT_NUMBER);
        }

        final List<GitHubSecretScanningLocation> expected = CODEC.decode(
                listJsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-location-commit.json"),
                LOCATIONS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, REPO_ALERT_LOCATIONS_PATH);
        HTTP.verifyQueryParam(GET, REPO_ALERT_LOCATIONS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_ALERT_LOCATIONS_PATH, "per_page", "100");
    }

    @Test
    void listAlertLocations_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                REPO_ALERT_LOCATIONS_PATH,
                okJson(listJsonBytes(
                                GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-location-commit.json"))
                        .header(
                                LINK,
                                linkNext(
                                        HTTP.uri(REPO_ALERT_LOCATIONS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-location-commit.json")));

        final GitHubPage<GitHubSecretScanningLocation> first;
        final Optional<GitHubPage<GitHubSecretScanningLocation>> second;
        final Optional<GitHubPage<GitHubSecretScanningLocation>> third;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            first = secretScanning.api().alerts().listAlertLocations(OWNER, REPO, ALERT_NUMBER);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final List<GitHubSecretScanningLocation> expected = expectedLocations();

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected);
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected);
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, REPO_ALERT_LOCATIONS_PATH, 2);
    }

    @ParameterizedTest
    @MethodSource("customPatternListReturnCalls")
    void listCustomPatterns_shouldReturnPatterns_whenResponseIsOk(final String path, final CustomPatternListCall call)
            throws Exception {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-custom-pattern.json")));

        final GitHubPage<GitHubSecretScanningCustomPattern> page;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            page = call.execute(secretScanning.api());
        }

        final List<GitHubSecretScanningCustomPattern> expected = CODEC.decode(
                listJsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-custom-pattern.json"),
                CUSTOM_PATTERNS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, path);
        HTTP.verifyNoQueryParam(GET, path, "per_page");
    }

    @ParameterizedTest
    @MethodSource("bulkCreateCustomPatternCalls")
    void bulkCreateCustomPatterns_shouldReturnResult_whenResponseIsCreated(
            final String path, final BulkCreateCustomPatternCall call) throws Exception {
        HTTP.stub(
                POST,
                path,
                createdJson(jsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class,
                        "secret-scanning-custom-pattern-bulk-create-response.json")));

        final GitHubBulkCreateSecretScanningCustomPatternsResult result;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            result = call.execute(secretScanning.api());
        }

        final GitHubBulkCreateSecretScanningCustomPatternsResult expected = CODEC.decode(
                jsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class,
                        "secret-scanning-custom-pattern-bulk-create-response.json"),
                GitHubBulkCreateSecretScanningCustomPatternsResult.class);

        assertThat(result).isEqualTo(expected);
        HTTP.verifyRequested(POST, path);
        HTTP.verifyHeader(POST, path, CONTENT_TYPE, "application/json");
    }

    @ParameterizedTest
    @MethodSource("bulkDeleteCustomPatternCalls")
    void bulkDeleteCustomPatterns_shouldComplete_whenResponseIsNoContent(
            final String path, final BulkDeleteCustomPatternCall call) throws Exception {
        HTTP.stub(DELETE, path, noContent());

        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            call.execute(secretScanning.api());
        }

        HTTP.verifyRequested(DELETE, path);
        HTTP.verifyHeader(DELETE, path, CONTENT_TYPE, "application/json");
    }

    @ParameterizedTest
    @MethodSource("updateCustomPatternCalls")
    void updateCustomPattern_shouldReturnPattern_whenResponseIsOk(final String path, final UpdateCustomPatternCall call)
            throws Exception {
        HTTP.stub(
                PATCH,
                path,
                okJson(jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-custom-pattern.json")));

        final GitHubSecretScanningCustomPattern pattern;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            pattern = call.execute(secretScanning.api());
        }

        final GitHubSecretScanningCustomPattern expected = CODEC.decode(
                jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-custom-pattern.json"),
                GitHubSecretScanningCustomPattern.class);

        assertThat(pattern).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, path);
        HTTP.verifyHeader(PATCH, path, CONTENT_TYPE, "application/json");
    }

    @Test
    void listOrgPatternConfigs_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_PATTERN_CONFIGS_PATH,
                okJson(jsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-pattern-configuration.json")));

        final GitHubSecretScanningPatternConfiguration configuration;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            configuration = secretScanning.api().pushProtection().listOrgPatternConfigs(ORG);
        }

        final GitHubSecretScanningPatternConfiguration expected = CODEC.decode(
                jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-pattern-configuration.json"),
                GitHubSecretScanningPatternConfiguration.class);

        assertThat(configuration).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_PATTERN_CONFIGS_PATH);
    }

    @Test
    void updateOrgPatternConfigs_shouldReturnResult_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ORG_PATTERN_CONFIGS_PATH,
                okJson(jsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class,
                        "secret-scanning-pattern-configs-update-response.json")));

        final GitHubUpdateSecretScanningPatternConfigsResult result;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            result = secretScanning
                    .api()
                    .pushProtection()
                    .updateOrgPatternConfigs(
                            ORG, SecretScanningCatalogFixtures.updateSecretScanningPatternConfigsPayload());
        }

        final GitHubUpdateSecretScanningPatternConfigsResult expected = CODEC.decode(
                jsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class,
                        "secret-scanning-pattern-configs-update-response.json"),
                GitHubUpdateSecretScanningPatternConfigsResult.class);

        assertThat(result).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, ORG_PATTERN_CONFIGS_PATH);
        HTTP.verifyHeader(PATCH, ORG_PATTERN_CONFIGS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void createPushProtectionBypass_shouldReturnBypass_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                REPO_PUSH_PROTECTION_BYPASSES_PATH,
                okJson(jsonBytes(
                        GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-push-protection-bypass.json")));

        final GitHubSecretScanningPushProtectionBypass bypass;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            bypass = secretScanning
                    .api()
                    .alerts()
                    .createPushProtectionBypass(
                            OWNER, REPO, SecretScanningCatalogFixtures.createPushProtectionBypassPayload());
        }

        final GitHubSecretScanningPushProtectionBypass expected = CODEC.decode(
                jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-push-protection-bypass.json"),
                GitHubSecretScanningPushProtectionBypass.class);

        assertThat(bypass).isEqualTo(expected);
        HTTP.verifyRequested(POST, REPO_PUSH_PROTECTION_BYPASSES_PATH);
        HTTP.verifyHeader(POST, REPO_PUSH_PROTECTION_BYPASSES_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void getScanHistory_shouldReturnHistory_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SCAN_HISTORY_PATH,
                okJson(jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-scan-history.json")));

        final GitHubSecretScanningScanHistory history;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            history = secretScanning.api().alerts().getScanHistory(OWNER, REPO);
        }

        final GitHubSecretScanningScanHistory expected = CODEC.decode(
                jsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-scan-history.json"),
                GitHubSecretScanningScanHistory.class);

        assertThat(history).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_SCAN_HISTORY_PATH);
    }

    @ParameterizedTest
    @MethodSource("notFoundCalls")
    void operation_shouldThrowNotFound_whenResponseIsNotFound(
            final HttpMethod method, final String path, final SecretScanningCall call) throws Exception {
        HTTP.stub(method, path, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.SecretScanningApiClient secretScanning = secretScanningApi(HTTP)) {
            final GitHubSecretScanningApi api = secretScanning.api();

            thrown = catchThrowable(() -> call.execute(api));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(method, path);
    }

    private static List<GitHubOrganizationSecretScanningAlert> expectedOrgAlerts() {
        return CODEC.decode(
                listJsonBytes(GitHubSecretScanningApiIntegrationTest.class, "organization-secret-scanning-alert.json"),
                ORG_ALERTS_TYPE);
    }

    private static List<GitHubSecretScanningLocation> expectedLocations() {
        return CODEC.decode(
                listJsonBytes(GitHubSecretScanningApiIntegrationTest.class, "secret-scanning-location-commit.json"),
                LOCATIONS_TYPE);
    }

    private static Stream<Arguments> customPatternListReturnCalls() {
        return Stream.of(
                Arguments.of(ORG_CUSTOM_PATTERNS_PATH, (CustomPatternListCall)
                        api -> api.customPatterns().listOrgCustomPatterns(ORG)),
                Arguments.of(REPO_CUSTOM_PATTERNS_PATH, (CustomPatternListCall)
                        api -> api.customPatterns().listRepoCustomPatterns(OWNER, REPO)));
    }

    private static Stream<Arguments> bulkCreateCustomPatternCalls() {
        return Stream.of(
                Arguments.of(ORG_CUSTOM_PATTERNS_PATH, (BulkCreateCustomPatternCall) api -> api.customPatterns()
                        .bulkCreateOrgCustomPatterns(
                                ORG, SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload())),
                Arguments.of(REPO_CUSTOM_PATTERNS_PATH, (BulkCreateCustomPatternCall) api -> api.customPatterns()
                        .bulkCreateRepoCustomPatterns(
                                OWNER,
                                REPO,
                                SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload())));
    }

    private static Stream<Arguments> bulkDeleteCustomPatternCalls() {
        return Stream.of(
                Arguments.of(ORG_CUSTOM_PATTERNS_PATH, (BulkDeleteCustomPatternCall) api -> api.customPatterns()
                        .bulkDeleteOrgCustomPatterns(
                                ORG, SecretScanningCatalogFixtures.bulkDeleteSecretScanningCustomPatternsPayload())),
                Arguments.of(REPO_CUSTOM_PATTERNS_PATH, (BulkDeleteCustomPatternCall) api -> api.customPatterns()
                        .bulkDeleteRepoCustomPatterns(
                                OWNER,
                                REPO,
                                SecretScanningCatalogFixtures.bulkDeleteSecretScanningCustomPatternsPayload())));
    }

    private static Stream<Arguments> updateCustomPatternCalls() {
        return Stream.of(
                Arguments.of(ORG_CUSTOM_PATTERN_PATH, (UpdateCustomPatternCall) api -> api.customPatterns()
                        .updateOrgCustomPattern(
                                ORG,
                                PATTERN_ID,
                                SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload())),
                Arguments.of(REPO_CUSTOM_PATTERN_PATH, (UpdateCustomPatternCall) api -> api.customPatterns()
                        .updateRepoCustomPattern(
                                OWNER,
                                REPO,
                                PATTERN_ID,
                                SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload())));
    }

    private static Stream<Arguments> notFoundCalls() {
        return Stream.of(
                Arguments.of(GET, ORG_ALERTS_PATH, (SecretScanningCall)
                        api -> api.alerts().listOrgAlerts(ORG)),
                Arguments.of(GET, ORG_CUSTOM_PATTERNS_PATH, (SecretScanningCall)
                        api -> api.customPatterns().listOrgCustomPatterns(ORG)),
                Arguments.of(POST, ORG_CUSTOM_PATTERNS_PATH, (SecretScanningCall) api -> api.customPatterns()
                        .bulkCreateOrgCustomPatterns(
                                ORG, SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload())),
                Arguments.of(DELETE, ORG_CUSTOM_PATTERNS_PATH, (SecretScanningCall) api -> api.customPatterns()
                        .bulkDeleteOrgCustomPatterns(
                                ORG, SecretScanningCatalogFixtures.bulkDeleteSecretScanningCustomPatternsPayload())),
                Arguments.of(PATCH, ORG_CUSTOM_PATTERN_PATH, (SecretScanningCall) api -> api.customPatterns()
                        .updateOrgCustomPattern(
                                ORG,
                                PATTERN_ID,
                                SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload())),
                Arguments.of(GET, ORG_PATTERN_CONFIGS_PATH, (SecretScanningCall)
                        api -> api.pushProtection().listOrgPatternConfigs(ORG)),
                Arguments.of(PATCH, ORG_PATTERN_CONFIGS_PATH, (SecretScanningCall) api -> api.pushProtection()
                        .updateOrgPatternConfigs(
                                ORG, SecretScanningCatalogFixtures.updateSecretScanningPatternConfigsPayload())),
                Arguments.of(GET, REPO_ALERTS_PATH, (SecretScanningCall)
                        api -> api.alerts().listRepoAlerts(OWNER, REPO)),
                Arguments.of(PATCH, REPO_ALERT_PATH, (SecretScanningCall) api -> api.alerts()
                        .updateAlert(
                                OWNER,
                                REPO,
                                ALERT_NUMBER,
                                SecretScanningCatalogFixtures.updateSecretScanningAlertPayload())),
                Arguments.of(GET, REPO_ALERT_LOCATIONS_PATH, (SecretScanningCall)
                        api -> api.alerts().listAlertLocations(OWNER, REPO, ALERT_NUMBER)),
                Arguments.of(GET, REPO_CUSTOM_PATTERNS_PATH, (SecretScanningCall)
                        api -> api.customPatterns().listRepoCustomPatterns(OWNER, REPO)),
                Arguments.of(POST, REPO_CUSTOM_PATTERNS_PATH, (SecretScanningCall) api -> api.customPatterns()
                        .bulkCreateRepoCustomPatterns(
                                OWNER,
                                REPO,
                                SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload())),
                Arguments.of(DELETE, REPO_CUSTOM_PATTERNS_PATH, (SecretScanningCall) api -> api.customPatterns()
                        .bulkDeleteRepoCustomPatterns(
                                OWNER,
                                REPO,
                                SecretScanningCatalogFixtures.bulkDeleteSecretScanningCustomPatternsPayload())),
                Arguments.of(PATCH, REPO_CUSTOM_PATTERN_PATH, (SecretScanningCall) api -> api.customPatterns()
                        .updateRepoCustomPattern(
                                OWNER,
                                REPO,
                                PATTERN_ID,
                                SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload())),
                Arguments.of(POST, REPO_PUSH_PROTECTION_BYPASSES_PATH, (SecretScanningCall) api -> api.alerts()
                        .createPushProtectionBypass(
                                OWNER, REPO, SecretScanningCatalogFixtures.createPushProtectionBypassPayload())),
                Arguments.of(GET, REPO_SCAN_HISTORY_PATH, (SecretScanningCall)
                        api -> api.alerts().getScanHistory(OWNER, REPO)));
    }

    @FunctionalInterface
    interface SecretScanningCall {
        void execute(GitHubSecretScanningApi api) throws Exception;
    }

    @FunctionalInterface
    interface CustomPatternListCall {
        GitHubPage<GitHubSecretScanningCustomPattern> execute(GitHubSecretScanningApi api) throws Exception;
    }

    @FunctionalInterface
    interface BulkCreateCustomPatternCall {
        GitHubBulkCreateSecretScanningCustomPatternsResult execute(GitHubSecretScanningApi api) throws Exception;
    }

    @FunctionalInterface
    interface BulkDeleteCustomPatternCall {
        void execute(GitHubSecretScanningApi api) throws Exception;
    }

    @FunctionalInterface
    interface UpdateCustomPatternCall {
        GitHubSecretScanningCustomPattern execute(GitHubSecretScanningApi api) throws Exception;
    }
}
