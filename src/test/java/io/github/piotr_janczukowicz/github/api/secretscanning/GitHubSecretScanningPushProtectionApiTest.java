package io.github.piotr_janczukowicz.github.api.secretscanning;

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
import java.net.URI;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSecretScanningPushProtectionApiTest {

    private static final String ORG = "octo-org";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningPushProtectionApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgPatternConfigs_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-pattern-configuration.json")));

        final GitHubSecretScanningPatternConfiguration configuration =
                new GitHubSecretScanningPushProtectionApi(TestClients.caller(transport)).listOrgPatternConfigs(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(configuration)
                    .isEqualTo(SecretScanningCatalogFixtures.secretScanningPatternConfiguration());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/secret-scanning/pattern-configurations");
        });
    }

    @Test
    void updateOrgPatternConfigs_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("secret-scanning-pattern-configs-update-response.json")));

        final GitHubUpdateSecretScanningPatternConfigsResult result = new GitHubSecretScanningPushProtectionApi(
                        TestClients.caller(transport))
                .updateOrgPatternConfigs(
                        ORG, SecretScanningCatalogFixtures.updateSecretScanningPatternConfigsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result)
                    .isEqualTo(SecretScanningCatalogFixtures.updateSecretScanningPatternConfigsResult());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/secret-scanning/pattern-configurations");
        });
    }

    @ParameterizedTest
    @MethodSource("blankArgCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final PushProtectionCall call, final String messageFragment) {
        final GitHubSecretScanningPushProtectionApi api =
                new GitHubSecretScanningPushProtectionApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final PushProtectionCall call) {
        final GitHubSecretScanningPushProtectionApi api =
                new GitHubSecretScanningPushProtectionApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecretScanningPushProtectionApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> nullPayloadCalls() {
        return Stream.of(Arguments.of((PushProtectionCall) api -> api.updateOrgPatternConfigs(ORG, null)));
    }

    private static Stream<Arguments> blankArgCalls() {
        final GitHubUpdateSecretScanningPatternConfigsPayload patternConfigsPayload =
                SecretScanningCatalogFixtures.updateSecretScanningPatternConfigsPayload();
        return Stream.of(
                Arguments.of((PushProtectionCall) api -> api.listOrgPatternConfigs(" "), "org"),
                Arguments.of(
                        (PushProtectionCall) api -> api.updateOrgPatternConfigs(" ", patternConfigsPayload), "org"));
    }

    @FunctionalInterface
    interface PushProtectionCall {
        void execute(GitHubSecretScanningPushProtectionApi api) throws Exception;
    }
}
