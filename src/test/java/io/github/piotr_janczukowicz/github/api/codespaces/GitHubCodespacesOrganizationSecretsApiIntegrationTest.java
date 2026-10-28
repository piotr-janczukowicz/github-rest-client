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
class GitHubCodespacesOrganizationSecretsApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String SECRET = "NPM_TOKEN";
    private static final long REPOSITORY_ID = 64_780_797L;

    private static final String ORG_SECRETS_PATH = "/orgs/octo-org/codespaces/secrets";
    private static final String ORG_SECRET_PATH = ORG_SECRETS_PATH + "/NPM_TOKEN";
    private static final String ORG_SECRET_REPOSITORIES_PATH = ORG_SECRET_PATH + "/repositories";
    private static final String ORG_SECRET_REPOSITORY_PATH = ORG_SECRET_REPOSITORIES_PATH + "/64780797";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRETS_PATH,
                okJson(jsonBytes(
                        GitHubCodespacesOrganizationSecretsApiIntegrationTest.class,
                        "org-codespaces-secrets-page.json")));

        final GitHubPage<GitHubCodespacesOrgSecret> page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().organizationSecrets().listOrgSecrets(ORG);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        CodespacesCatalogFixtures.orgCodespacesSecretsPage().secrets());
        HTTP.verifyRequested(GET, ORG_SECRETS_PATH);
        HTTP.verifyQueryParam(GET, ORG_SECRETS_PATH, "page", "1");
    }

    @Test
    void getOrgPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRETS_PATH + "/public-key",
                okJson(jsonBytes(
                        GitHubCodespacesOrganizationSecretsApiIntegrationTest.class, "codespaces-public-key.json")));

        final GitHubCodespacesPublicKey key;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            key = codespaces.api().organizationSecrets().getOrgPublicKey(ORG);
        }

        assertThat(key).isEqualTo(CodespacesCatalogFixtures.codespacesPublicKey());
    }

    @Test
    void getOrgPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_SECRETS_PATH + "/public-key", notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            final GitHubCodespacesOrganizationSecretsApi organizationSecrets =
                    codespaces.api().organizationSecrets();

            thrown = catchThrowable(() -> organizationSecrets.getOrgPublicKey(ORG));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getOrgSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_SECRET_PATH, notFound());

        final Optional<GitHubCodespacesOrgSecret> secret;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            secret = codespaces.api().organizationSecrets().getOrgSecret(ORG, SECRET);
        }

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateOrgSecret_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubCreateOrUpdateOrgSecretPayload payload = CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload();

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().organizationSecrets().createOrUpdateOrgSecret(ORG, SECRET, payload);
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_PATH);
        HTTP.verifyHeader(PUT, ORG_SECRET_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ORG_SECRET_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().organizationSecrets().deleteOrgSecret(ORG, SECRET);
        }

        HTTP.verifyRequested(DELETE, ORG_SECRET_PATH);
    }

    @Test
    void listSelectedReposForOrgSecret_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRET_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubCodespacesOrganizationSecretsApiIntegrationTest.class,
                        "codespaces-selected-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().organizationSecrets().listSelectedReposForOrgSecret(ORG, SECRET);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(CodespacesCatalogFixtures.codespacesSelectedRepositoriesPage()
                        .repositories());
        HTTP.verifyRequested(GET, ORG_SECRET_REPOSITORIES_PATH);
    }

    @Test
    void setSelectedReposAndRepositoryAccess_shouldSendRequests_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_REPOSITORIES_PATH, noContent());
        HTTP.stub(PUT, ORG_SECRET_REPOSITORY_PATH, noContent());
        HTTP.stub(DELETE, ORG_SECRET_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces
                    .api()
                    .organizationSecrets()
                    .setSelectedReposForOrgSecret(
                            ORG, SECRET, CodespacesCatalogFixtures.setSelectedReposForOrgSecretPayload());
            codespaces.api().organizationSecrets().addSelectedRepoToOrgSecret(ORG, SECRET, REPOSITORY_ID);
            codespaces.api().organizationSecrets().removeSelectedRepoFromOrgSecret(ORG, SECRET, REPOSITORY_ID);
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_REPOSITORIES_PATH);
        HTTP.verifyRequested(PUT, ORG_SECRET_REPOSITORY_PATH);
        HTTP.verifyRequested(DELETE, ORG_SECRET_REPOSITORY_PATH);
    }
}
