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
class GitHubCodespacesSecretsApiTest {

    private static final String SECRET = "NPM_TOKEN";
    private static final long REPOSITORY_ID = 64_780_797L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodespacesSecretsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listForAuthenticatedUser_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-secrets-page.json")));

        final GitHubPage<GitHubCodespacesSecret> page = api().listForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CodespacesCatalogFixtures.codespacesSecretsPage().secrets());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/codespaces/secrets");
            softly.assertThat(requestUri).hasParameter("page", "1");
        });
    }

    @Test
    void listForAuthenticatedUser_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-secrets-page.json")));

        api().listForAuthenticatedUser(GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "1");
    }

    @Test
    void getPublicKeyForAuthenticatedUser_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("codespaces-user-public-key.json")));

        final GitHubCodespacesUserPublicKey key = api().getPublicKeyForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(key).isEqualTo(CodespacesCatalogFixtures.codespacesUserPublicKey());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/codespaces/secrets/public-key");
        });
    }

    @Test
    void getPublicKeyForAuthenticatedUser_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubCodespacesSecretsApi api = api();

        final Throwable thrown = catchThrowable(api::getPublicKeyForAuthenticatedUser);

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getSecretForAuthenticatedUser_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-secret.json")));

        final Optional<GitHubCodespacesSecret> secret = api().getSecretForAuthenticatedUser(SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(secret).contains(CodespacesCatalogFixtures.codespacesSecret());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/codespaces/secrets/NPM_TOKEN");
        });
    }

    @Test
    void getSecretForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodespacesSecret> secret = api().getSecretForAuthenticatedUser(SECRET);

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateSecretForAuthenticatedUser_shouldPutPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), "{}"));

        api().createOrUpdateSecretForAuthenticatedUser(
                        SECRET, CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/secrets/NPM_TOKEN");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateSecretForAuthenticatedUser_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change = api().createOrUpdateSecretForAuthenticatedUser(
                        SECRET, CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void createOrUpdateSecretForAuthenticatedUser_shouldThrow_whenPayloadNull() {
        final GitHubCodespacesSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createOrUpdateSecretForAuthenticatedUser(SECRET, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteSecretForAuthenticatedUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteSecretForAuthenticatedUser(SECRET);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/secrets/NPM_TOKEN");
        });
    }

    @Test
    void listRepositoriesForSecretForAuthenticatedUser_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("codespaces-selected-repositories-page.json")));

        final GitHubCodespacesSelectedRepositoriesPage repositories =
                api().listRepositoriesForSecretForAuthenticatedUser(SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(repositories).isEqualTo(CodespacesCatalogFixtures.codespacesSelectedRepositoriesPage());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/codespaces/secrets/NPM_TOKEN/repositories");
        });
    }

    @Test
    void setRepositoriesForSecretForAuthenticatedUser_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepositoriesForSecretForAuthenticatedUser(
                        SECRET, CodespacesCatalogFixtures.setRepositoriesForSecretForAuthenticatedUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/secrets/NPM_TOKEN/repositories");
        });
    }

    @Test
    void addRepositoryForSecretForAuthenticatedUser_shouldPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addRepositoryForSecretForAuthenticatedUser(SECRET, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/user/codespaces/secrets/NPM_TOKEN/repositories/64780797");
        });
    }

    @Test
    void removeRepositoryForSecretForAuthenticatedUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeRepositoryForSecretForAuthenticatedUser(SECRET, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/user/codespaces/secrets/NPM_TOKEN/repositories/64780797");
        });
    }

    private GitHubCodespacesSecretsApi api() {
        return new GitHubCodespacesSecretsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodespacesSecretsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
