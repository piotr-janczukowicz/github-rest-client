package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.apps.GitHubAuthorization;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
@SuppressWarnings("deprecation")
class GitHubOauthAuthorizationsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOauthAuthorizationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listGrants_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("application-grant.json") + "]"));

        final GitHubPage<GitHubApplicationGrant> page = api().listGrants();

        assertThat(page.items()).containsExactly(OauthAuthorizationsCatalogFixtures.applicationGrant());
        assertThat(captureSingleRequest().uri().toString()).startsWith("https://api.github.com/applications/grants?");
    }

    @Test
    void listGrants_shouldSendClientId_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listGrants(OauthAuthorizationsCatalogFixtures.listGrantsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("client_id", "client_id");
    }

    @Test
    void listGrants_shouldThrow_whenRequestNull() {
        final GitHubOauthAuthorizationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listGrants(GitHubListGrantsFilter.empty(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void listAuthorizations_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("authorization.json") + "]"));

        final GitHubPage<GitHubAuthorization> page = api().listAuthorizations();

        assertThat(page.items()).hasSize(1);
        assertThat(captureSingleRequest().uri().toString()).startsWith("https://api.github.com/authorizations?");
    }

    @Test
    void listAuthorizations_shouldSendClientId_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listAuthorizations(OauthAuthorizationsCatalogFixtures.listAuthorizationsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("client_id", "client_id");
    }

    @Test
    void listAuthorizations_shouldThrow_whenRequestNull() {
        final GitHubOauthAuthorizationsApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.listAuthorizations(GitHubListAuthorizationsFilter.empty(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void getGrant_shouldReturnGrant_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("application-grant.json")));

        final Optional<GitHubApplicationGrant> result = api().getGrant(1L);

        assertThat(result).contains(OauthAuthorizationsCatalogFixtures.applicationGrant());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/applications/grants/1");
    }

    @Test
    void getGrant_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubApplicationGrant> result = api().getGrant(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteGrant_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteGrant(1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/applications/grants/1");
    }

    @Test
    void createAuthorization_shouldPostWithoutBody_whenCalledWithoutPayload() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("authorization.json")));

        final GitHubAuthorization result = api().createAuthorization();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(line(request)).isEqualTo("POST https://api.github.com/authorizations");
        });
    }

    @Test
    void createAuthorization_shouldPostPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("authorization.json")));

        api().createAuthorization(OauthAuthorizationsCatalogFixtures.createAuthorizationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(line(request)).isEqualTo("POST https://api.github.com/authorizations");
            softly.assertThat(request.body().isAbsent()).isFalse();
        });
    }

    @Test
    void createAuthorization_shouldThrow_whenPayloadNull() {
        final GitHubOauthAuthorizationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createAuthorization(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getOrCreateAuthorizationForApp_shouldPutPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("authorization.json")));

        api().getOrCreateAuthorizationForApp(
                        "octoapp", OauthAuthorizationsCatalogFixtures.getOrCreateAuthorizationForAppPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT https://api.github.com/authorizations/clients/octoapp");
    }

    @Test
    void getOrCreateAuthorizationForApp_shouldThrow_whenPayloadNull() {
        final GitHubOauthAuthorizationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getOrCreateAuthorizationForApp("octoapp", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getOrCreateAuthorizationForAppAndFingerprint_shouldPutPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("authorization.json")));

        api().getOrCreateAuthorizationForAppAndFingerprint(
                        "octoapp",
                        "print",
                        OauthAuthorizationsCatalogFixtures.getOrCreateAuthorizationForAppAndFingerprintPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/authorizations/clients/octoapp/print");
    }

    @Test
    void getOrCreateAuthorizationForAppAndFingerprint_shouldThrow_whenPayloadNull() {
        final GitHubOauthAuthorizationsApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.getOrCreateAuthorizationForAppAndFingerprint("octoapp", "print", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getAuthorization_shouldReturnAuthorization_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("authorization.json")));

        final Optional<GitHubAuthorization> result = api().getAuthorization(1L);
        final String requestLine = line(captureSingleRequest());

        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(requestLine).isEqualTo("GET https://api.github.com/authorizations/1");
        });
    }

    @Test
    void getAuthorization_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubAuthorization> result = api().getAuthorization(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateAuthorization_shouldPatchWithoutBody_whenCalledWithoutPayload() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("authorization.json")));

        api().updateAuthorization(1L);

        assertThat(line(captureSingleRequest())).isEqualTo("PATCH https://api.github.com/authorizations/1");
    }

    @Test
    void updateAuthorization_shouldPatchPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("authorization.json")));

        api().updateAuthorization(1L, OauthAuthorizationsCatalogFixtures.updateAuthorizationPayload());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.body().isAbsent()).isFalse();
    }

    @Test
    void updateAuthorization_shouldThrow_whenPayloadNull() {
        final GitHubOauthAuthorizationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateAuthorization(1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteAuthorization_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteAuthorization(1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/authorizations/1");
    }

    private GitHubOauthAuthorizationsApi api() {
        return new GitHubOauthAuthorizationsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOauthAuthorizationsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }
}
