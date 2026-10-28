package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSecretScanningAlertDismissalRequestsApiTest {

    private static final String ENTERPRISE = "octo-ent";
    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningAlertDismissalRequestsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listEnterpriseDismissalRequests_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-dismissal-request.json") + "]"));

        final GitHubPage<GitHubSecretScanningDismissalRequest> page = new GitHubSecretScanningAlertDismissalRequestsApi(
                        TestClients.caller(transport))
                .listEnterpriseDismissalRequests(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningDismissalRequest());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/enterprises/octo-ent/dismissal-requests/secret-scanning");
        });
    }

    @Test
    void listEnterpriseDismissalRequests_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningAlertDismissalRequestsApi(TestClients.caller(transport))
                .listEnterpriseDismissalRequests(
                        ENTERPRISE, SecretScanningCatalogFixtures.listEnterpriseDismissalRequestsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.getQuery()).contains("organization_name=octo-org");
            softly.assertThat(requestUri.getQuery()).contains("request_status=denied");
        });
    }

    @Test
    void listOrgDismissalRequests_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-dismissal-request.json") + "]"));

        final GitHubPage<GitHubSecretScanningDismissalRequest> page = new GitHubSecretScanningAlertDismissalRequestsApi(
                        TestClients.caller(transport))
                .listOrgDismissalRequests(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningDismissalRequest());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/orgs/octo-org/dismissal-requests/secret-scanning");
        });
    }

    @Test
    void listOrgDismissalRequests_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningAlertDismissalRequestsApi(TestClients.caller(transport))
                .listOrgDismissalRequests(ORG, SecretScanningCatalogFixtures.listOrgDismissalRequestsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("repository_name=smile").contains("request_status=expired");
    }

    @Test
    void listRepoDismissalRequests_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-dismissal-request.json") + "]"));

        final GitHubPage<GitHubSecretScanningDismissalRequest> page = new GitHubSecretScanningAlertDismissalRequestsApi(
                        TestClients.caller(transport))
                .listRepoDismissalRequests(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningDismissalRequest());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/repos/octocat/hello-world/dismissal-requests/secret-scanning");
        });
    }

    @Test
    void listRepoDismissalRequests_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningAlertDismissalRequestsApi(TestClients.caller(transport))
                .listRepoDismissalRequests(
                        OWNER, REPO, SecretScanningCatalogFixtures.listRepoDismissalRequestsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("request_status=open").contains("time_period=day");
    }

    @Test
    void getDismissalRequest_shouldReturnRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-dismissal-request.json")));

        final Optional<GitHubSecretScanningDismissalRequest> request =
                new GitHubSecretScanningAlertDismissalRequestsApi(TestClients.caller(transport))
                        .getDismissalRequest(OWNER, REPO, 4);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(request).contains(SecretScanningCatalogFixtures.secretScanningDismissalRequest());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/repos/octocat/hello-world/dismissal-requests/secret-scanning/4");
        });
    }

    @Test
    void getDismissalRequest_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubSecretScanningDismissalRequest> request =
                new GitHubSecretScanningAlertDismissalRequestsApi(TestClients.caller(transport))
                        .getDismissalRequest(OWNER, REPO, 4);

        assertThat(request).isEmpty();
    }

    @Test
    void reviewDismissalRequest_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("review-dismissal-request-result.json")));

        final GitHubReviewDismissalRequestResult result = new GitHubSecretScanningAlertDismissalRequestsApi(
                        TestClients.caller(transport))
                .reviewDismissalRequest(OWNER, REPO, 4, SecretScanningCatalogFixtures.reviewDismissalRequestPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(SecretScanningCatalogFixtures.reviewDismissalRequestResult());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/repos/octocat/hello-world/dismissal-requests/secret-scanning/4");
        });
    }

    @ParameterizedTest
    @MethodSource("blankArgCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final DismissalCall call, final String messageFragment) {
        final GitHubSecretScanningAlertDismissalRequestsApi api =
                new GitHubSecretScanningAlertDismissalRequestsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullArgCalls")
    void validation_shouldThrow_whenArgumentNull(final DismissalCall call, final String messageFragment) {
        final GitHubSecretScanningAlertDismissalRequestsApi api =
                new GitHubSecretScanningAlertDismissalRequestsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(messageFragment);
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecretScanningAlertDismissalRequestsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> blankArgCalls() {
        return Stream.of(
                Arguments.of((DismissalCall) api -> api.listEnterpriseDismissalRequests(" "), "enterprise"),
                Arguments.of((DismissalCall) api -> api.listOrgDismissalRequests(" "), "org"),
                Arguments.of((DismissalCall) api -> api.listRepoDismissalRequests(" ", REPO), "owner"),
                Arguments.of((DismissalCall) api -> api.getDismissalRequest(OWNER, " ", 4), "repo"));
    }

    private static Stream<Arguments> nullArgCalls() {
        return Stream.of(
                Arguments.of((DismissalCall) api -> api.listEnterpriseDismissalRequests(ENTERPRISE, null), "filter"),
                Arguments.of((DismissalCall) api -> api.listOrgDismissalRequests(ORG, null), "filter"),
                Arguments.of((DismissalCall) api -> api.reviewDismissalRequest(OWNER, REPO, 4, null), "payload"));
    }

    @FunctionalInterface
    interface DismissalCall {
        void execute(GitHubSecretScanningAlertDismissalRequestsApi api) throws Exception;
    }
}
