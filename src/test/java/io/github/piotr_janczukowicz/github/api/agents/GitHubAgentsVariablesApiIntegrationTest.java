package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.agentsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsVariable;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAgentsVariablesApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String NAME = "USERNAME";
    private static final long REPOSITORY_ID = 64_780_797L;

    private static final String ORG_VARIABLES_PATH = "/orgs/octo-org/agents/variables";
    private static final String ORG_VARIABLE_PATH = ORG_VARIABLES_PATH + "/USERNAME";
    private static final String ORG_VARIABLE_REPOSITORIES_PATH = ORG_VARIABLE_PATH + "/repositories";
    private static final String ORG_VARIABLE_REPOSITORY_PATH = ORG_VARIABLE_REPOSITORIES_PATH + "/64780797";
    private static final String REPO_ORGANIZATION_VARIABLES_PATH =
            "/repos/octocat/hello-world/agents/organization-variables";
    private static final String REPO_VARIABLES_PATH = "/repos/octocat/hello-world/agents/variables";
    private static final String REPO_VARIABLE_PATH = REPO_VARIABLES_PATH + "/USERNAME";

    private static final byte[] EMPTY_OBJECT = "{}".getBytes(StandardCharsets.UTF_8);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgVariables_shouldReturnVariables_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_VARIABLES_PATH,
                okJson(jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "org-actions-variables-page.json")));

        final GitHubPage<GitHubOrganizationActionsVariable> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().variables().listOrgVariables(ORG);
        }

        final GitHubOrgActionsVariablesPage expected = CODEC.decode(
                jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "org-actions-variables-page.json"),
                GitHubOrgActionsVariablesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.variables());
        HTTP.verifyRequested(GET, ORG_VARIABLES_PATH);
        HTTP.verifyQueryParam(GET, ORG_VARIABLES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_VARIABLES_PATH, "per_page", "30");
    }

    @Test
    void createOrgVariable_shouldPost_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, ORG_VARIABLES_PATH, createdJson(EMPTY_OBJECT));

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().variables().createOrgVariable(ORG, AgentsCatalogFixtures.createOrgVariablePayload());
        }

        HTTP.verifyRequested(POST, ORG_VARIABLES_PATH);
    }

    @Test
    void getOrgVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_VARIABLE_PATH,
                okJson(jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "organization-actions-variable.json")));

        final GitHubOrganizationActionsVariable variable;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            variable = agents.api().variables().getOrgVariable(ORG, NAME).orElseThrow();
        }

        final GitHubOrganizationActionsVariable expected = CODEC.decode(
                jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "organization-actions-variable.json"),
                GitHubOrganizationActionsVariable.class);

        assertThat(variable).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_VARIABLE_PATH);
    }

    @Test
    void getOrgVariable_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_VARIABLE_PATH, notFound());

        final Optional<GitHubOrganizationActionsVariable> variable;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            variable = agents.api().variables().getOrgVariable(ORG, NAME);
        }

        assertThat(variable).isEmpty();
    }

    @Test
    void updateOrgVariable_shouldPatch_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, ORG_VARIABLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().variables().updateOrgVariable(ORG, NAME, AgentsCatalogFixtures.updateOrgVariablePayload());
        }

        HTTP.verifyRequested(PATCH, ORG_VARIABLE_PATH);
    }

    @Test
    void deleteOrgVariable_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_VARIABLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().variables().deleteOrgVariable(ORG, NAME);
        }

        HTTP.verifyRequested(DELETE, ORG_VARIABLE_PATH);
    }

    @Test
    void listSelectedReposForOrgVariable_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_VARIABLE_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubAgentsVariablesApiIntegrationTest.class,
                        "org-variable-selected-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().variables().listSelectedReposForOrgVariable(ORG, NAME);
        }

        final GitHubOrgVariableSelectedRepositoriesPage expected = CODEC.decode(
                jsonBytes(
                        GitHubAgentsVariablesApiIntegrationTest.class, "org-variable-selected-repositories-page.json"),
                GitHubOrgVariableSelectedRepositoriesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.repositories());
        HTTP.verifyRequested(GET, ORG_VARIABLE_REPOSITORIES_PATH);
    }

    @Test
    void setSelectedReposForOrgVariable_shouldPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_VARIABLE_REPOSITORIES_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api()
                    .variables()
                    .setSelectedReposForOrgVariable(
                            ORG, NAME, AgentsCatalogFixtures.setSelectedReposForOrgVariablePayload());
        }

        HTTP.verifyRequested(PUT, ORG_VARIABLE_REPOSITORIES_PATH);
    }

    @Test
    void addSelectedRepoToOrgVariable_shouldPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_VARIABLE_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().variables().addSelectedRepoToOrgVariable(ORG, NAME, REPOSITORY_ID);
        }

        HTTP.verifyRequested(PUT, ORG_VARIABLE_REPOSITORY_PATH);
    }

    @Test
    void removeSelectedRepoFromOrgVariable_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_VARIABLE_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().variables().removeSelectedRepoFromOrgVariable(ORG, NAME, REPOSITORY_ID);
        }

        HTTP.verifyRequested(DELETE, ORG_VARIABLE_REPOSITORY_PATH);
    }

    @Test
    void listRepoOrganizationVariables_shouldReturnVariables_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ORGANIZATION_VARIABLES_PATH,
                okJson(jsonBytes(
                        GitHubAgentsVariablesApiIntegrationTest.class, "repo-organization-variables-page.json")));

        final GitHubPage<GitHubActionsVariable> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().variables().listRepoOrganizationVariables(OWNER, REPO);
        }

        final GitHubRepoOrganizationVariablesPage expected = CODEC.decode(
                jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "repo-organization-variables-page.json"),
                GitHubRepoOrganizationVariablesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.variables());
        HTTP.verifyRequested(GET, REPO_ORGANIZATION_VARIABLES_PATH);
        HTTP.verifyQueryParam(GET, REPO_ORGANIZATION_VARIABLES_PATH, "per_page", "30");
    }

    @Test
    void listRepoVariables_shouldReturnVariables_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_VARIABLES_PATH,
                okJson(jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "actions-variables-page.json")));

        final GitHubPage<GitHubActionsVariable> page;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            page = agents.api().variables().listRepoVariables(OWNER, REPO);
        }

        final GitHubActionsVariablesPage expected = CODEC.decode(
                jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "actions-variables-page.json"),
                GitHubActionsVariablesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.variables());
        HTTP.verifyRequested(GET, REPO_VARIABLES_PATH);
        HTTP.verifyQueryParam(GET, REPO_VARIABLES_PATH, "per_page", "30");
    }

    @Test
    void createRepoVariable_shouldPost_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, REPO_VARIABLES_PATH, createdJson(EMPTY_OBJECT));

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().variables().createRepoVariable(OWNER, REPO, AgentsCatalogFixtures.createRepoVariablePayload());
        }

        HTTP.verifyRequested(POST, REPO_VARIABLES_PATH);
    }

    @Test
    void getRepoVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_VARIABLE_PATH,
                okJson(jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "actions-variable.json")));

        final GitHubActionsVariable variable;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            variable =
                    agents.api().variables().getRepoVariable(OWNER, REPO, NAME).orElseThrow();
        }

        final GitHubActionsVariable expected = CODEC.decode(
                jsonBytes(GitHubAgentsVariablesApiIntegrationTest.class, "actions-variable.json"),
                GitHubActionsVariable.class);

        assertThat(variable).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_VARIABLE_PATH);
    }

    @Test
    void getRepoVariable_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_VARIABLE_PATH, notFound());

        final Optional<GitHubActionsVariable> variable;
        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            variable = agents.api().variables().getRepoVariable(OWNER, REPO, NAME);
        }

        assertThat(variable).isEmpty();
    }

    @Test
    void updateRepoVariable_shouldPatch_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, REPO_VARIABLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api()
                    .variables()
                    .updateRepoVariable(OWNER, REPO, NAME, AgentsCatalogFixtures.updateRepoVariablePayload());
        }

        HTTP.verifyRequested(PATCH, REPO_VARIABLE_PATH);
    }

    @Test
    void deleteRepoVariable_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_VARIABLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentsApiClient agents = agentsApi(HTTP)) {
            agents.api().variables().deleteRepoVariable(OWNER, REPO, NAME);
        }

        HTTP.verifyRequested(DELETE, REPO_VARIABLE_PATH);
    }
}
