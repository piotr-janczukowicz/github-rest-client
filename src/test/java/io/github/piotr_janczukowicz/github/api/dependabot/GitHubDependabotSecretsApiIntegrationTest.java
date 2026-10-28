package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.dependabotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
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
class GitHubDependabotSecretsApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String SECRET = "NPM_TOKEN";
    private static final long REPOSITORY_ID = 64_780_797L;

    private static final String ORG_SECRETS_PATH = "/orgs/octo-org/dependabot/secrets";
    private static final String ORG_PUBLIC_KEY_PATH = ORG_SECRETS_PATH + "/public-key";
    private static final String ORG_SECRET_PATH = ORG_SECRETS_PATH + "/NPM_TOKEN";
    private static final String ORG_SECRET_REPOSITORIES_PATH = ORG_SECRET_PATH + "/repositories";
    private static final String ORG_SECRET_REPOSITORY_PATH = ORG_SECRET_REPOSITORIES_PATH + "/64780797";
    private static final String REPO_SECRETS_PATH = "/repos/octocat/hello-world/dependabot/secrets";
    private static final String REPO_PUBLIC_KEY_PATH = REPO_SECRETS_PATH + "/public-key";
    private static final String REPO_SECRET_PATH = REPO_SECRETS_PATH + "/NPM_TOKEN";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRETS_PATH,
                okJson(jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "org-dependabot-secrets-page.json")));

        final GitHubPage<GitHubOrgDependabotSecret> page;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            page = dependabot.api().secrets().listOrgSecrets(ORG);
        }

        final GitHubOrgDependabotSecretsPage expected = CODEC.decode(
                jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "org-dependabot-secrets-page.json"),
                GitHubOrgDependabotSecretsPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.secrets());
        HTTP.verifyRequested(GET, ORG_SECRETS_PATH);
        HTTP.verifyQueryParam(GET, ORG_SECRETS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_SECRETS_PATH, "per_page", "100");
    }

    @Test
    void getOrgPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_PUBLIC_KEY_PATH,
                okJson(jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-public-key.json")));

        final GitHubDependabotPublicKey key;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            key = dependabot.api().secrets().getOrgPublicKey(ORG);
        }

        final GitHubDependabotPublicKey expected = CODEC.decode(
                jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-public-key.json"),
                GitHubDependabotPublicKey.class);

        assertThat(key).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_PUBLIC_KEY_PATH);
    }

    @Test
    void getOrgPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_PUBLIC_KEY_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            final GitHubDependabotSecretsApi secrets = dependabot.api().secrets();

            thrown = catchThrowable(() -> secrets.getOrgPublicKey(ORG));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getOrgSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRET_PATH,
                okJson(jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "org-dependabot-secret.json")));

        final GitHubOrgDependabotSecret secret;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            secret = dependabot.api().secrets().getOrgSecret(ORG, SECRET).orElseThrow();
        }

        final GitHubOrgDependabotSecret expected = CODEC.decode(
                jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "org-dependabot-secret.json"),
                GitHubOrgDependabotSecret.class);

        assertThat(secret).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_SECRET_PATH);
    }

    @Test
    void getOrgSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_SECRET_PATH, notFound());

        final Optional<GitHubOrgDependabotSecret> secret;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            secret = dependabot.api().secrets().getOrgSecret(ORG, SECRET);
        }

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateOrgSecret_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubCreateOrUpdateOrgSecretPayload payload = DependabotCatalogFixtures.createOrUpdateOrgSecretPayload();

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().secrets().createOrUpdateOrgSecret(ORG, SECRET, payload);
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_PATH);
        HTTP.verifyHeader(PUT, ORG_SECRET_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ORG_SECRET_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createOrUpdateOrgSecret_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot
                    .api()
                    .secrets()
                    .createOrUpdateOrgSecret(ORG, SECRET, DependabotCatalogFixtures.createOrUpdateOrgSecretPayload());
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_PATH);
    }

    @Test
    void deleteOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().secrets().deleteOrgSecret(ORG, SECRET);
        }

        HTTP.verifyRequested(DELETE, ORG_SECRET_PATH);
    }

    @Test
    void listSelectedReposForOrgSecret_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRET_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubDependabotSecretsApiIntegrationTest.class,
                        "dependabot-selected-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            page = dependabot.api().secrets().listSelectedReposForOrgSecret(ORG, SECRET);
        }

        final GitHubDependabotSelectedRepositoriesPage expected = CODEC.decode(
                jsonBytes(
                        GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-selected-repositories-page.json"),
                GitHubDependabotSelectedRepositoriesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.repositories());
        HTTP.verifyRequested(GET, ORG_SECRET_REPOSITORIES_PATH);
        HTTP.verifyQueryParam(GET, ORG_SECRET_REPOSITORIES_PATH, "page", "1");
    }

    @Test
    void setSelectedReposForOrgSecret_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_REPOSITORIES_PATH, noContent());
        final GitHubSetSelectedReposForOrgSecretPayload payload =
                DependabotCatalogFixtures.setSelectedReposForOrgSecretPayload();

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().secrets().setSelectedReposForOrgSecret(ORG, SECRET, payload);
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_REPOSITORIES_PATH);
        HTTP.verifyBody(PUT, ORG_SECRET_REPOSITORIES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void addSelectedRepoToOrgSecret_shouldPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().secrets().addSelectedRepoToOrgSecret(ORG, SECRET, REPOSITORY_ID);
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_REPOSITORY_PATH);
    }

    @Test
    void removeSelectedRepoFromOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_SECRET_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().secrets().removeSelectedRepoFromOrgSecret(ORG, SECRET, REPOSITORY_ID);
        }

        HTTP.verifyRequested(DELETE, ORG_SECRET_REPOSITORY_PATH);
    }

    @Test
    void listRepoSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SECRETS_PATH,
                okJson(jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-secrets-page.json")));

        final GitHubPage<GitHubDependabotSecret> page;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            page = dependabot.api().secrets().listRepoSecrets(OWNER, REPO);
        }

        final GitHubDependabotSecretsPage expected = CODEC.decode(
                jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-secrets-page.json"),
                GitHubDependabotSecretsPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.secrets());
        HTTP.verifyRequested(GET, REPO_SECRETS_PATH);
        HTTP.verifyQueryParam(GET, REPO_SECRETS_PATH, "page", "1");
    }

    @Test
    void getRepoPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_PUBLIC_KEY_PATH,
                okJson(jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-public-key.json")));

        final GitHubDependabotPublicKey key;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            key = dependabot.api().secrets().getRepoPublicKey(OWNER, REPO);
        }

        final GitHubDependabotPublicKey expected = CODEC.decode(
                jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-public-key.json"),
                GitHubDependabotPublicKey.class);

        assertThat(key).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_PUBLIC_KEY_PATH);
    }

    @Test
    void getRepoPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_PUBLIC_KEY_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            final GitHubDependabotSecretsApi secrets = dependabot.api().secrets();

            thrown = catchThrowable(() -> secrets.getRepoPublicKey(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getRepoSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SECRET_PATH,
                okJson(jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-secret.json")));

        final GitHubDependabotSecret secret;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            secret = dependabot
                    .api()
                    .secrets()
                    .getRepoSecret(OWNER, REPO, SECRET)
                    .orElseThrow();
        }

        final GitHubDependabotSecret expected = CODEC.decode(
                jsonBytes(GitHubDependabotSecretsApiIntegrationTest.class, "dependabot-secret.json"),
                GitHubDependabotSecret.class);

        assertThat(secret).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_SECRET_PATH);
    }

    @Test
    void createOrUpdateRepoSecret_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, REPO_SECRET_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubCreateOrUpdateRepoSecretPayload payload =
                DependabotCatalogFixtures.createOrUpdateRepoSecretPayload();

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().secrets().createOrUpdateRepoSecret(OWNER, REPO, SECRET, payload);
        }

        HTTP.verifyRequested(PUT, REPO_SECRET_PATH);
        HTTP.verifyHeader(PUT, REPO_SECRET_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, REPO_SECRET_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteRepoSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            dependabot.api().secrets().deleteRepoSecret(OWNER, REPO, SECRET);
        }

        HTTP.verifyRequested(DELETE, REPO_SECRET_PATH);
    }
}
