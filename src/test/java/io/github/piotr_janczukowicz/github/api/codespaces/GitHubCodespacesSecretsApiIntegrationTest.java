package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codespacesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodespacesSecretsApiIntegrationTest {

    private static final String SECRET = "NPM_TOKEN";
    private static final long REPOSITORY_ID = 64_780_797L;

    private static final String USER_SECRETS_PATH = "/user/codespaces/secrets";
    private static final String USER_SECRET_PATH = USER_SECRETS_PATH + "/NPM_TOKEN";
    private static final String USER_SECRET_REPOSITORIES_PATH = USER_SECRET_PATH + "/repositories";
    private static final String USER_SECRET_REPOSITORY_PATH = USER_SECRET_REPOSITORIES_PATH + "/64780797";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForAuthenticatedUser_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_SECRETS_PATH,
                okJson(jsonBytes(GitHubCodespacesSecretsApiIntegrationTest.class, "codespaces-secrets-page.json")));

        final GitHubPage<GitHubCodespacesSecret> page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().secrets().listForAuthenticatedUser();
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        CodespacesCatalogFixtures.codespacesSecretsPage().secrets());
        HTTP.verifyRequested(GET, USER_SECRETS_PATH);
        HTTP.verifyQueryParam(GET, USER_SECRETS_PATH, "page", "1");
    }

    @Test
    void getPublicKeyForAuthenticatedUser_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_SECRETS_PATH + "/public-key",
                okJson(jsonBytes(GitHubCodespacesSecretsApiIntegrationTest.class, "codespaces-user-public-key.json")));

        final GitHubCodespacesUserPublicKey key;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            key = codespaces.api().secrets().getPublicKeyForAuthenticatedUser();
        }

        assertThat(key).isEqualTo(CodespacesCatalogFixtures.codespacesUserPublicKey());
    }

    @Test
    void getPublicKeyForAuthenticatedUser_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USER_SECRETS_PATH + "/public-key", notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            final GitHubCodespacesSecretsApi secrets = codespaces.api().secrets();

            thrown = catchThrowable(secrets::getPublicKeyForAuthenticatedUser);
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getSecretForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USER_SECRET_PATH, notFound());

        final Optional<GitHubCodespacesSecret> secret;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            secret = codespaces.api().secrets().getSecretForAuthenticatedUser(SECRET);
        }

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateSecretForAuthenticatedUser_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, USER_SECRET_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubCreateOrUpdateSecretForAuthenticatedUserPayload payload =
                CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload();

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().secrets().createOrUpdateSecretForAuthenticatedUser(SECRET, payload);
        }

        HTTP.verifyRequested(PUT, USER_SECRET_PATH);
        HTTP.verifyHeader(PUT, USER_SECRET_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, USER_SECRET_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteSecretForAuthenticatedUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().secrets().deleteSecretForAuthenticatedUser(SECRET);
        }

        HTTP.verifyRequested(DELETE, USER_SECRET_PATH);
    }

    @Test
    void listRepositoriesForSecretForAuthenticatedUser_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_SECRET_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubCodespacesSecretsApiIntegrationTest.class,
                        "codespaces-selected-repositories-page.json")));

        final GitHubCodespacesSelectedRepositoriesPage repositories;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            repositories = codespaces.api().secrets().listRepositoriesForSecretForAuthenticatedUser(SECRET);
        }

        assertThat(repositories).isEqualTo(CodespacesCatalogFixtures.codespacesSelectedRepositoriesPage());
        HTTP.verifyRequested(GET, USER_SECRET_REPOSITORIES_PATH);
    }

    @Test
    void setAddAndRemoveRepositories_shouldSendRequests_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, USER_SECRET_REPOSITORIES_PATH, noContent());
        HTTP.stub(PUT, USER_SECRET_REPOSITORY_PATH, noContent());
        HTTP.stub(DELETE, USER_SECRET_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces
                    .api()
                    .secrets()
                    .setRepositoriesForSecretForAuthenticatedUser(
                            SECRET, CodespacesCatalogFixtures.setRepositoriesForSecretForAuthenticatedUserPayload());
            codespaces.api().secrets().addRepositoryForSecretForAuthenticatedUser(SECRET, REPOSITORY_ID);
            codespaces.api().secrets().removeRepositoryForSecretForAuthenticatedUser(SECRET, REPOSITORY_ID);
        }

        HTTP.verifyRequested(PUT, USER_SECRET_REPOSITORIES_PATH);
        HTTP.verifyRequested(PUT, USER_SECRET_REPOSITORY_PATH);
        HTTP.verifyRequested(DELETE, USER_SECRET_REPOSITORY_PATH);
    }
}
