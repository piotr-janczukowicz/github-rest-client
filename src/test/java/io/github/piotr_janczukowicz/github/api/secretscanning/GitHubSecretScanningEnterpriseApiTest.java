package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSecretScanningEnterpriseApiTest {

    private static final String ENTERPRISE = "octo-ent";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void listAlertsForEnterprise_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("organization-secret-scanning-alert.json") + "]"));

        final GitHubPage<GitHubOrganizationSecretScanningAlert> page =
                new GitHubSecretScanningAlertsApi(TestClients.caller(transport)).listAlertsForEnterprise(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.organizationSecretScanningAlert());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/enterprises/octo-ent/secret-scanning/alerts");
        });
    }

    @Test
    void listAlertsForEnterprise_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningAlertsApi(TestClients.caller(transport))
                .listAlertsForEnterprise(
                        ENTERPRISE,
                        GitHubListAlertsForEnterpriseFilterBuilder.builder()
                                .state(GitHubSecretScanningAlertState.OPEN)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("state=open");
    }

    @Test
    void listEnterpriseCustomPatterns_shouldReturnPatterns_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-custom-pattern.json") + "]"));

        final GitHubPage<GitHubSecretScanningCustomPattern> page = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .listEnterpriseCustomPatterns(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningCustomPattern());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/enterprises/octo-ent/secret-scanning/custom-patterns");
        });
    }

    @Test
    void listEnterpriseCustomPatterns_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport))
                .listEnterpriseCustomPatterns(
                        ENTERPRISE,
                        GitHubListEnterpriseCustomPatternsFilterBuilder.builder()
                                .sort(GitHubSecretScanningCustomPatternSort.NAME)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("sort=name");
    }

    @Test
    void bulkCreateEnterpriseCustomPatterns_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.CREATED.code(), json("bulk-create-enterprise-custom-patterns-result.json")));

        final GitHubBulkCreateEnterpriseCustomPatternsResult result = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .bulkCreateEnterpriseCustomPatterns(
                        ENTERPRISE, SecretScanningCatalogFixtures.bulkCreateEnterpriseCustomPatternsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result)
                    .isEqualTo(SecretScanningCatalogFixtures.bulkCreateEnterpriseCustomPatternsResult());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-ent/secret-scanning/custom-patterns");
        });
    }

    @Test
    void bulkDeleteEnterpriseCustomPatterns_shouldDeletePayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport))
                .bulkDeleteEnterpriseCustomPatterns(
                        ENTERPRISE, SecretScanningCatalogFixtures.bulkDeleteEnterpriseCustomPatternsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-ent/secret-scanning/custom-patterns");
        });
    }

    @Test
    void updateEnterpriseCustomPattern_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-custom-pattern.json")));

        final GitHubSecretScanningCustomPattern pattern = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .updateEnterpriseCustomPattern(
                        ENTERPRISE, 5L, SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(pattern).isEqualTo(SecretScanningCatalogFixtures.secretScanningCustomPattern());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-ent/secret-scanning/custom-patterns/5");
        });
    }

    @Test
    void listEnterprisePatternConfigs_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-pattern-configuration.json")));

        final GitHubSecretScanningPatternConfiguration configuration = new GitHubSecretScanningPushProtectionApi(
                        TestClients.caller(transport))
                .listEnterprisePatternConfigs(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(configuration)
                    .isEqualTo(SecretScanningCatalogFixtures.secretScanningPatternConfiguration());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/enterprises/octo-ent/secret-scanning/pattern-configurations");
        });
    }

    @Test
    void updateEnterprisePatternConfigs_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("update-enterprise-pattern-configs-result.json")));

        final GitHubUpdateEnterprisePatternConfigsResult result = new GitHubSecretScanningPushProtectionApi(
                        TestClients.caller(transport))
                .updateEnterprisePatternConfigs(
                        ENTERPRISE, SecretScanningCatalogFixtures.updateEnterprisePatternConfigsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(SecretScanningCatalogFixtures.updateEnterprisePatternConfigsResult());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-ent/secret-scanning/pattern-configurations");
        });
    }

    @ParameterizedTest
    @MethodSource("blankEnterpriseCalls")
    void call_shouldThrowIllegalArgument_whenEnterpriseBlank(final EnterpriseCall call) {
        final GitHubCaller caller = TestClients.caller(transport);

        final Throwable thrown = catchThrowable(() -> call.execute(caller));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("enterprise");
    }

    @ParameterizedTest
    @MethodSource("nullArgCalls")
    void validation_shouldThrow_whenArgumentNull(final EnterpriseCall call, final String messageFragment) {
        final GitHubCaller caller = TestClients.caller(transport);

        final Throwable thrown = catchThrowable(() -> call.execute(caller));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(messageFragment);
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecretScanningEnterpriseApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> blankEnterpriseCalls() {
        return Stream.of(
                Arguments.of((EnterpriseCall)
                        caller -> new GitHubSecretScanningAlertsApi(caller).listAlertsForEnterprise(" ")),
                Arguments.of((EnterpriseCall)
                        caller -> new GitHubSecretScanningCustomPatternsApi(caller).listEnterpriseCustomPatterns(" ")),
                Arguments.of((EnterpriseCall)
                        caller -> new GitHubSecretScanningPushProtectionApi(caller).listEnterprisePatternConfigs(" ")));
    }

    private static Stream<Arguments> nullArgCalls() {
        return Stream.of(
                Arguments.of(
                        (EnterpriseCall) caller ->
                                new GitHubSecretScanningAlertsApi(caller).listAlertsForEnterprise(ENTERPRISE, null),
                        "filter"),
                Arguments.of(
                        (EnterpriseCall) caller -> new GitHubSecretScanningCustomPatternsApi(caller)
                                .bulkCreateEnterpriseCustomPatterns(ENTERPRISE, null),
                        "payload"),
                Arguments.of(
                        (EnterpriseCall) caller -> new GitHubSecretScanningPushProtectionApi(caller)
                                .updateEnterprisePatternConfigs(ENTERPRISE, null),
                        "payload"));
    }

    @FunctionalInterface
    interface EnterpriseCall {
        void execute(GitHubCaller caller) throws Exception;
    }
}
