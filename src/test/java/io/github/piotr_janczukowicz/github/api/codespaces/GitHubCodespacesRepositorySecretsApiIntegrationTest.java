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
class GitHubCodespacesRepositorySecretsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String SECRET = "NPM_TOKEN";

    private static final String REPO_SECRETS_PATH = "/repos/octocat/hello-world/codespaces/secrets";
    private static final String REPO_SECRET_PATH = REPO_SECRETS_PATH + "/NPM_TOKEN";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listRepoSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SECRETS_PATH,
                okJson(jsonBytes(
                        GitHubCodespacesRepositorySecretsApiIntegrationTest.class,
                        "repo-codespaces-secrets-page.json")));

        final GitHubPage<GitHubRepoCodespacesSecret> page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().repositorySecrets().listRepoSecrets(OWNER, REPO);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        CodespacesCatalogFixtures.repoCodespacesSecretsPage().secrets());
        HTTP.verifyRequested(GET, REPO_SECRETS_PATH);
        HTTP.verifyQueryParam(GET, REPO_SECRETS_PATH, "page", "1");
    }

    @Test
    void getRepoPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SECRETS_PATH + "/public-key",
                okJson(jsonBytes(
                        GitHubCodespacesRepositorySecretsApiIntegrationTest.class, "codespaces-public-key.json")));

        final GitHubCodespacesPublicKey key;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            key = codespaces.api().repositorySecrets().getRepoPublicKey(OWNER, REPO);
        }

        assertThat(key).isEqualTo(CodespacesCatalogFixtures.codespacesPublicKey());
    }

    @Test
    void getRepoPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_SECRETS_PATH + "/public-key", notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            final GitHubCodespacesRepositorySecretsApi repositorySecrets =
                    codespaces.api().repositorySecrets();

            thrown = catchThrowable(() -> repositorySecrets.getRepoPublicKey(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getRepoSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_SECRET_PATH, notFound());

        final Optional<GitHubRepoCodespacesSecret> secret;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            secret = codespaces.api().repositorySecrets().getRepoSecret(OWNER, REPO, SECRET);
        }

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateRepoSecret_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, REPO_SECRET_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubCreateOrUpdateRepoSecretPayload payload =
                CodespacesCatalogFixtures.createOrUpdateRepoSecretPayload();

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().repositorySecrets().createOrUpdateRepoSecret(OWNER, REPO, SECRET, payload);
        }

        HTTP.verifyRequested(PUT, REPO_SECRET_PATH);
        HTTP.verifyHeader(PUT, REPO_SECRET_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, REPO_SECRET_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteRepoSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().repositorySecrets().deleteRepoSecret(OWNER, REPO, SECRET);
        }

        HTTP.verifyRequested(DELETE, REPO_SECRET_PATH);
    }
}
