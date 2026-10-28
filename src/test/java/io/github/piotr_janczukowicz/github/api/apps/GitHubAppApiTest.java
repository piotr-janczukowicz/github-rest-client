package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallation;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubAppApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAppApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getInstallation_shouldReturnInstallation_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation.json")));

        final GitHubInstallation installation = api().getInstallation(1L).orElseThrow();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(installation).isEqualTo(OrganizationsCatalogFixtures.installation());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/app/installations/1");
        });
    }

    @Test
    void getInstallation_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubInstallation> result = api().getInstallation(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteInstallation_shouldSendDelete_whenAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().deleteInstallation(1L);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void getAuthenticated_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("integration.json")));

        api().getAuthenticated();
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void createFromManifest_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("create-from-manifest-response.json")));

        api().createFromManifest("code");
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void listInstallationRequests_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("integration-installation-request.json") + "]"));

        api().listInstallationRequests();
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void listInstallations_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("installation.json") + "]"));

        api().listInstallations();
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void createInstallationAccessToken_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation-token.json")));

        api().createInstallationAccessToken(1L, AppsCatalogFixtures.createInstallationAccessTokenPayload());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void createInstallationAccessToken_shouldSendNoBody_whenCalledWithoutPayload() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation-token.json")));

        api().createInstallationAccessToken(1L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/app/installations/1/access_tokens");
            softly.assertThat(request.body().length()).isEmpty();
        });
    }

    @Test
    void suspendInstallation_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().suspendInstallation(1L);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void unsuspendInstallation_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().unsuspendInstallation(1L);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void scopeToken_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("authorization.json")));

        api().scopeToken("Iv1.25b5d1e65ffc4022", AppsCatalogFixtures.scopeTokenPayload());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void getBySlug_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("integration.json")));

        api().getBySlug("probot-owners");
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getBySlug_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubIntegration> result = api().getBySlug("probot-owners");

        assertThat(result).isEmpty();
    }

    @Test
    void getOrgInstallation_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation.json")));

        api().getOrgInstallation("octo-org");
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getRepoInstallation_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation.json")));

        api().getRepoInstallation("octocat", "Hello-World");
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getUserInstallation_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation.json")));

        api().getUserInstallation("octocat");
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getUserInstallation_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubInstallation> result = api().getUserInstallation("octocat");

        assertThat(result).isEmpty();
    }

    private GitHubAppApi api() {
        return new GitHubAppApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubAppApi.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @Test
    void getEnterpriseInstallation_shouldReturnInstallation_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation.json")));

        final GitHubInstallation installation = api().getEnterpriseInstallation("octo-enterprise");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(installation).isEqualTo(OrganizationsCatalogFixtures.installation());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/installation");
        });
    }
}
