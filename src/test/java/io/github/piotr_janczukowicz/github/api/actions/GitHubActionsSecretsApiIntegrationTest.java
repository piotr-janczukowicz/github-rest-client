package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.agents.AgentsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsPublicKey;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsSecret;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsSecret;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsSecretsApiIntegrationTest {

    private static final String ORG_SECRETS_PATH = "/orgs/github/actions/secrets";
    private static final String ORG_SECRET_PATH = "/orgs/github/actions/secrets/API_TOKEN";
    private static final String REPO_SECRETS_PATH = "/repos/octocat/Hello-World/actions/secrets";
    private static final String ENVIRONMENT_SECRET_PATH =
            "/repos/octocat/Hello-World/environments/production/secrets/API_TOKEN";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRETS_PATH,
                okJson(jsonBytes(GitHubActionsSecretsApiIntegrationTest.class, "organization-actions-secrets.json")));

        final GitHubPage<GitHubOrganizationActionsSecret> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().secrets().listOrgSecrets("github");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.organizationActionsSecrets().secrets());
        HTTP.verifyRequested(GET, ORG_SECRETS_PATH);
    }

    @Test
    void getOrgSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SECRET_PATH,
                okJson(jsonBytes(GitHubActionsSecretsApiIntegrationTest.class, "organization-actions-secret.json")));

        final GitHubOrganizationActionsSecret secret;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            secret = actions.api().secrets().getOrgSecret("github", "API_TOKEN").orElseThrow();
        }

        assertThat(secret).isEqualTo(OrganizationsCatalogFixtures.organizationActionsSecret());
    }

    @Test
    void getRepoPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_SECRETS_PATH + "/public-key",
                okJson(jsonBytes(GitHubActionsSecretsApiIntegrationTest.class, "actions-public-key.json")));

        final GitHubActionsPublicKey key;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            key = actions.api().secrets().getRepoPublicKey("octocat", "Hello-World");
        }

        assertThat(key).isEqualTo(AgentsCatalogFixtures.actionsPublicKey());
    }

    @Test
    void getEnvironmentSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENVIRONMENT_SECRET_PATH,
                okJson(jsonBytes(GitHubActionsSecretsApiIntegrationTest.class, "actions-secret.json")));

        final GitHubActionsSecret secret;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            secret = actions.api()
                    .secrets()
                    .getEnvironmentSecret("octocat", "Hello-World", "production", "API_TOKEN")
                    .orElseThrow();
        }

        assertThat(secret).isEqualTo(AgentsCatalogFixtures.actionsSecret());
        HTTP.verifyRequested(GET, ENVIRONMENT_SECRET_PATH);
    }

    @Test
    void deleteRepoSecret_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_SECRETS_PATH + "/API_TOKEN", noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().secrets().deleteRepoSecret("octocat", "Hello-World", "API_TOKEN");
        }

        HTTP.verifyRequested(DELETE, REPO_SECRETS_PATH + "/API_TOKEN");
    }
}
