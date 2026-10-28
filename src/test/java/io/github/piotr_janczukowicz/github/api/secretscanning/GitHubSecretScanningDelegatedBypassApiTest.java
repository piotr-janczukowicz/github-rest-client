package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
class GitHubSecretScanningDelegatedBypassApiTest {

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
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningDelegatedBypassApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listEnterpriseBypassRequests_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-bypass-request.json") + "]"));

        final GitHubPage<GitHubSecretScanningBypassRequest> page = new GitHubSecretScanningDelegatedBypassApi(
                        TestClients.caller(transport))
                .listEnterpriseBypassRequests(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningBypassRequest());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/enterprises/octo-ent/bypass-requests/secret-scanning");
        });
    }

    @Test
    void listEnterpriseBypassRequests_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport))
                .listEnterpriseBypassRequests(
                        ENTERPRISE, SecretScanningCatalogFixtures.listEnterpriseBypassRequestsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.getQuery()).contains("organization_name=octo-org");
            softly.assertThat(requestUri.getQuery()).contains("request_status=open");
        });
    }

    @Test
    void listOrgBypassRequests_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-bypass-request.json") + "]"));

        final GitHubPage<GitHubSecretScanningBypassRequest> page =
                new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport)).listOrgBypassRequests(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningBypassRequest());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/orgs/octo-org/bypass-requests/secret-scanning");
        });
    }

    @Test
    void listOrgBypassRequests_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport))
                .listOrgBypassRequests(ORG, SecretScanningCatalogFixtures.listOrgBypassRequestsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("repository_name=smile").contains("time_period=week");
    }

    @Test
    void listRepoBypassRequests_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-bypass-request.json") + "]"));

        final GitHubPage<GitHubSecretScanningBypassRequest> page = new GitHubSecretScanningDelegatedBypassApi(
                        TestClients.caller(transport))
                .listRepoBypassRequests(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningBypassRequest());
            softly.assertThat(requestUri.toString())
                    .startsWith("https://api.github.com/repos/octocat/hello-world/bypass-requests/secret-scanning");
        });
    }

    @Test
    void listRepoBypassRequests_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport))
                .listRepoBypassRequests(OWNER, REPO, SecretScanningCatalogFixtures.listRepoBypassRequestsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("request_status=all").contains("reviewer=octocat");
    }

    @Test
    void listRepoBypassRequests_shouldRejectCursorRequest_whenCursorPaginationUsed() {
        final GitHubSecretScanningDelegatedBypassApi api =
                new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport));

        final GitHubListRepoBypassRequestsFilter filter = GitHubListRepoBypassRequestsFilter.empty();
        final GitHubPageRequest request = GitHubPageRequest.byCursor();

        final Throwable thrown = catchThrowable(() -> api.listRepoBypassRequests(OWNER, REPO, filter, request));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getBypassRequest_shouldReturnRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-bypass-request.json")));

        final Optional<GitHubSecretScanningBypassRequest> request = new GitHubSecretScanningDelegatedBypassApi(
                        TestClients.caller(transport))
                .getBypassRequest(OWNER, REPO, 3);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(request).contains(SecretScanningCatalogFixtures.secretScanningBypassRequest());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/bypass-requests/secret-scanning/3");
        });
    }

    @Test
    void getBypassRequest_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubSecretScanningBypassRequest> request = new GitHubSecretScanningDelegatedBypassApi(
                        TestClients.caller(transport))
                .getBypassRequest(OWNER, REPO, 3);

        assertThat(request).isEmpty();
    }

    @Test
    void reviewBypassRequest_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("review-bypass-request-result.json")));

        final GitHubReviewBypassRequestResult result = new GitHubSecretScanningDelegatedBypassApi(
                        TestClients.caller(transport))
                .reviewBypassRequest(OWNER, REPO, 3, SecretScanningCatalogFixtures.reviewBypassRequestPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(SecretScanningCatalogFixtures.reviewBypassRequestResult());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/bypass-requests/secret-scanning/3");
        });
    }

    @Test
    void dismissBypassResponse_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport))
                .dismissBypassResponse(OWNER, REPO, 7L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/bypass-responses/secret-scanning/7");
        });
    }

    @ParameterizedTest
    @MethodSource("blankArgCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final BypassCall call, final String messageFragment) {
        final GitHubSecretScanningDelegatedBypassApi api =
                new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullArgCalls")
    void validation_shouldThrow_whenArgumentNull(final BypassCall call, final String messageFragment) {
        final GitHubSecretScanningDelegatedBypassApi api =
                new GitHubSecretScanningDelegatedBypassApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(messageFragment);
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecretScanningDelegatedBypassApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> blankArgCalls() {
        return Stream.of(
                Arguments.of((BypassCall) api -> api.listEnterpriseBypassRequests(" "), "enterprise"),
                Arguments.of((BypassCall) api -> api.listOrgBypassRequests(" "), "org"),
                Arguments.of((BypassCall) api -> api.listRepoBypassRequests(" ", REPO), "owner"),
                Arguments.of((BypassCall) api -> api.getBypassRequest(OWNER, " ", 3), "repo"),
                Arguments.of((BypassCall) api -> api.dismissBypassResponse(" ", REPO, 7L), "owner"));
    }

    private static Stream<Arguments> nullArgCalls() {
        return Stream.of(
                Arguments.of((BypassCall) api -> api.listEnterpriseBypassRequests(ENTERPRISE, null), "filter"),
                Arguments.of((BypassCall) api -> api.listOrgBypassRequests(ORG, null), "filter"),
                Arguments.of((BypassCall) api -> api.listRepoBypassRequests(OWNER, REPO, null), "filter"),
                Arguments.of((BypassCall) api -> api.reviewBypassRequest(OWNER, REPO, 3, null), "payload"));
    }

    @FunctionalInterface
    interface BypassCall {
        void execute(GitHubSecretScanningDelegatedBypassApi api) throws Exception;
    }
}
