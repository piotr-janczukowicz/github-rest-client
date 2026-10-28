package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.agentsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsSecret;
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
class GitHubAgentsSecretsApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String SECRET = "SECRET_TOKEN";
    private static final long REPOSITORY_ID = 64_780_797L;

    private static final String ORG_SECRETS_PATH = "/orgs/octo-org/agents/secrets";
    private static final String ORG_PUBLIC_KEY_PATH = ORG_SECRETS_PATH + "/public-key";
    private static final String ORG_SECRET_PATH = ORG_SECRETS_PATH + "/SECRET_TOKEN";
    private static final String ORG_SECRET_REPOSITORIES_PATH = ORG_SECRET_PATH + "/repositories";
    private static final String ORG_SECRET_REPOSITORY_PATH = ORG_SECRET_REPOSITORIES_PATH + "/64780797";
    private static final String REPO_ORGANIZATION_SECRETS_PATH =
            "/repos/octocat/hello-world/agents/organization-secrets";
    private static final String REPO_SECRETS_PATH = "/repos/octocat/hello-world/agents/secrets";
    private static final String REPO_PUBLIC_KEY_PATH = REPO_SECRETS_PATH + "/public-key";
    private static final String REPO_SECRET_PATH = REPO_SECRETS_PATH + "/SECRET_TOKEN";

    private static final byte[] EMPTY_OBJECT = "{}".getBytes(StandardCharsets.UTF_8);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRETS_PATH,
                okJson(jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "org-actions-secrets-page.json")));

        final GitHubPage<GitHubOrganizationActionsSecret> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().secrets().listOrgSecrets(ORG);
        }

        final GitHubOrgActionsSecretsPage expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "org-actions-secrets-page.json"),
                GitHubOrgActionsSecretsPage.class);

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
                okJson(jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-public-key.json")));

        final GitHubActionsPublicKey key;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            key = agents.api().secrets().getOrgPublicKey(ORG);
        }

        final GitHubActionsPublicKey expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-public-key.json"),
                GitHubActionsPublicKey.class);

        assertThat(key).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_PUBLIC_KEY_PATH);
    }

    @Test
    void getOrgPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_PUBLIC_KEY_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            final GitHubAgentsSecretsApi secrets = agents.api().secrets();

            thrown = catchThrowable(() -> secrets.getOrgPublicKey(ORG));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, ORG_PUBLIC_KEY_PATH);
    }

    @Test
    void getOrgSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRET_PATH,
                okJson(jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "organization-actions-secret.json")));

        final GitHubOrganizationActionsSecret secret;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            secret = agents.api().secrets().getOrgSecret(ORG, SECRET).orElseThrow();
        }

        final GitHubOrganizationActionsSecret expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "organization-actions-secret.json"),
                GitHubOrganizationActionsSecret.class);

        assertThat(secret).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_SECRET_PATH);
    }

    @Test
    void getOrgSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_SECRET_PATH, notFound());

        final Optional<GitHubOrganizationActionsSecret> secret;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            secret = agents.api().secrets().getOrgSecret(ORG, SECRET);
        }

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateOrgSecret_shouldPut_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_PATH, createdJson(EMPTY_OBJECT));

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api()
                    .secrets()
                    .createOrUpdateOrgSecret(ORG, SECRET, AgentsCatalogFixtures.createOrUpdateOrgSecretPayload());
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_PATH);
    }

    @Test
    void deleteOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().secrets().deleteOrgSecret(ORG, SECRET);
        }

        HTTP.verifyRequested(DELETE, ORG_SECRET_PATH);
    }

    @Test
    void listSelectedReposForOrgSecret_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRET_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubAgentsSecretsApiIntegrationTest.class, "org-secret-selected-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().secrets().listSelectedReposForOrgSecret(ORG, SECRET);
        }

        final GitHubOrgSecretSelectedRepositoriesPage expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "org-secret-selected-repositories-page.json"),
                GitHubOrgSecretSelectedRepositoriesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.repositories());
        HTTP.verifyRequested(GET, ORG_SECRET_REPOSITORIES_PATH);
    }

    @Test
    void setSelectedReposForOrgSecret_shouldPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_REPOSITORIES_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api()
                    .secrets()
                    .setSelectedReposForOrgSecret(
                            ORG, SECRET, AgentsCatalogFixtures.setSelectedReposForOrgSecretPayload());
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_REPOSITORIES_PATH);
    }

    @Test
    void addSelectedRepoToOrgSecret_shouldPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_SECRET_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().secrets().addSelectedRepoToOrgSecret(ORG, SECRET, REPOSITORY_ID);
        }

        HTTP.verifyRequested(PUT, ORG_SECRET_REPOSITORY_PATH);
    }

    @Test
    void removeSelectedRepoFromOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_SECRET_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().secrets().removeSelectedRepoFromOrgSecret(ORG, SECRET, REPOSITORY_ID);
        }

        HTTP.verifyRequested(DELETE, ORG_SECRET_REPOSITORY_PATH);
    }

    @Test
    void listRepoOrganizationSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ORGANIZATION_SECRETS_PATH,
                okJson(jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "repo-organization-secrets-page.json")));

        final GitHubPage<GitHubActionsSecret> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().secrets().listRepoOrganizationSecrets(OWNER, REPO);
        }

        final GitHubRepoOrganizationSecretsPage expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "repo-organization-secrets-page.json"),
                GitHubRepoOrganizationSecretsPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.secrets());
        HTTP.verifyRequested(GET, REPO_ORGANIZATION_SECRETS_PATH);
    }

    @Test
    void listRepoSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SECRETS_PATH,
                okJson(jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-secrets-page.json")));

        final GitHubPage<GitHubActionsSecret> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().secrets().listRepoSecrets(OWNER, REPO);
        }

        final GitHubActionsSecretsPage expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-secrets-page.json"),
                GitHubActionsSecretsPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.secrets());
        HTTP.verifyRequested(GET, REPO_SECRETS_PATH);
    }

    @Test
    void getRepoPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_PUBLIC_KEY_PATH,
                okJson(jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-public-key.json")));

        final GitHubActionsPublicKey key;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            key = agents.api().secrets().getRepoPublicKey(OWNER, REPO);
        }

        final GitHubActionsPublicKey expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-public-key.json"),
                GitHubActionsPublicKey.class);

        assertThat(key).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_PUBLIC_KEY_PATH);
    }

    @Test
    void getRepoPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_PUBLIC_KEY_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            final GitHubAgentsSecretsApi secrets = agents.api().secrets();

            thrown = catchThrowable(() -> secrets.getRepoPublicKey(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, REPO_PUBLIC_KEY_PATH);
    }

    @Test
    void getRepoSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SECRET_PATH,
                okJson(jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-secret.json")));

        final GitHubActionsSecret secret;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            secret = agents.api().secrets().getRepoSecret(OWNER, REPO, SECRET).orElseThrow();
        }

        final GitHubActionsSecret expected = CODEC.decode(
                jsonBytes(GitHubAgentsSecretsApiIntegrationTest.class, "actions-secret.json"),
                GitHubActionsSecret.class);

        assertThat(secret).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_SECRET_PATH);
    }

    @Test
    void getRepoSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_SECRET_PATH, notFound());

        final Optional<GitHubActionsSecret> secret;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            secret = agents.api().secrets().getRepoSecret(OWNER, REPO, SECRET);
        }

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateRepoSecret_shouldPut_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, REPO_SECRET_PATH, createdJson(EMPTY_OBJECT));

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api()
                    .secrets()
                    .createOrUpdateRepoSecret(
                            OWNER, REPO, SECRET, AgentsCatalogFixtures.createOrUpdateRepoSecretPayload());
        }

        HTTP.verifyRequested(PUT, REPO_SECRET_PATH);
    }

    @Test
    void deleteRepoSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_SECRET_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().secrets().deleteRepoSecret(OWNER, REPO, SECRET);
        }

        HTTP.verifyRequested(DELETE, REPO_SECRET_PATH);
    }
}
