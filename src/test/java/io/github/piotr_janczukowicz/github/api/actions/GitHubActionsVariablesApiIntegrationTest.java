package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.agents.AgentsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsVariable;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsVariablesApiIntegrationTest {

    private static final String REPO_VARIABLES_PATH = "/repos/octocat/Hello-World/actions/variables";
    private static final String REPO_VARIABLE_PATH = "/repos/octocat/Hello-World/actions/variables/DEPLOY_ENV";
    private static final String ORG_VARIABLES_PATH = "/orgs/github/actions/variables";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listRepoVariables_shouldReturnVariables_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_VARIABLES_PATH,
                okJson(jsonBytes(GitHubActionsVariablesApiIntegrationTest.class, "actions-variables.json")));

        final GitHubPage<GitHubActionsVariable> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().variables().listRepoVariables("octocat", "Hello-World");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsVariables().variables());
        HTTP.verifyQueryParam(GET, REPO_VARIABLES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_VARIABLES_PATH, "per_page", "30");
    }

    @Test
    void getRepoVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_VARIABLE_PATH,
                okJson(jsonBytes(GitHubActionsVariablesApiIntegrationTest.class, "actions-variable.json")));

        final GitHubActionsVariable variable;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            variable = actions.api()
                    .variables()
                    .getRepoVariable("octocat", "Hello-World", "DEPLOY_ENV")
                    .orElseThrow();
        }

        assertThat(variable).isEqualTo(AgentsCatalogFixtures.actionsVariable());
    }

    @Test
    void createOrgVariable_shouldSendPost_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, ORG_VARIABLES_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().variables().createOrgVariable("github", ActionsCatalogFixtures.createOrgVariablePayload());
        }

        HTTP.verifyRequested(POST, ORG_VARIABLES_PATH);
    }

    @Test
    void deleteRepoVariable_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_VARIABLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().variables().deleteRepoVariable("octocat", "Hello-World", "DEPLOY_ENV");
        }

        HTTP.verifyRequested(DELETE, REPO_VARIABLE_PATH);
    }
}
