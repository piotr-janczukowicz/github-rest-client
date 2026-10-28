package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCodespacesRepositorySecretsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String SECRET = "NPM_TOKEN";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodespacesRepositorySecretsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listRepoSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repo-codespaces-secrets-page.json")));

        final GitHubPage<GitHubRepoCodespacesSecret> page = api().listRepoSecrets(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(CodespacesCatalogFixtures.repoCodespacesSecretsPage()
                            .secrets());
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/hello-world/codespaces/secrets");
            softly.assertThat(requestUri).hasParameter("page", "1");
        });
    }

    @Test
    void listRepoSecrets_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repo-codespaces-secrets-page.json")));

        api().listRepoSecrets(OWNER, REPO, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "1");
    }

    @Test
    void getRepoPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-public-key.json")));

        final GitHubCodespacesPublicKey key = api().getRepoPublicKey(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(key).isEqualTo(CodespacesCatalogFixtures.codespacesPublicKey());
            softly.assertThat(requestUri.getPath())
                    .isEqualTo("/repos/octocat/hello-world/codespaces/secrets/public-key");
        });
    }

    @Test
    void getRepoPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubCodespacesRepositorySecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getRepoPublicKey(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getRepoSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repo-codespaces-secret.json")));

        final Optional<GitHubRepoCodespacesSecret> secret = api().getRepoSecret(OWNER, REPO, SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(secret).contains(CodespacesCatalogFixtures.repoCodespacesSecret());
            softly.assertThat(requestUri.getPath())
                    .isEqualTo("/repos/octocat/hello-world/codespaces/secrets/NPM_TOKEN");
        });
    }

    @Test
    void getRepoSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRepoCodespacesSecret> secret = api().getRepoSecret(OWNER, REPO, SECRET);

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateRepoSecret_shouldPutPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), "{}"));

        api().createOrUpdateRepoSecret(
                        OWNER, REPO, SECRET, CodespacesCatalogFixtures.createOrUpdateRepoSecretPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/hello-world/codespaces/secrets/NPM_TOKEN");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateRepoSecret_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change = api().createOrUpdateRepoSecret(
                        OWNER, REPO, SECRET, CodespacesCatalogFixtures.createOrUpdateRepoSecretPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void createOrUpdateRepoSecret_shouldThrow_whenPayloadNull() {
        final GitHubCodespacesRepositorySecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createOrUpdateRepoSecret(OWNER, REPO, SECRET, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteRepoSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoSecret(OWNER, REPO, SECRET);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/hello-world/codespaces/secrets/NPM_TOKEN");
        });
    }

    private GitHubCodespacesRepositorySecretsApi api() {
        return new GitHubCodespacesRepositorySecretsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodespacesRepositorySecretsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
