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

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsHostedRunnersApiIntegrationTest {

    private static final String RUNNERS_PATH = "/orgs/github/actions/hosted-runners";
    private static final String LIMITS_PATH = "/orgs/github/actions/hosted-runners/limits";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listHostedRunnersForOrg_shouldReturnRunners_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RUNNERS_PATH,
                okJson(jsonBytes(GitHubActionsHostedRunnersApiIntegrationTest.class, "actions-hosted-runners.json")));

        final GitHubPage<GitHubActionsHostedRunner> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().hostedRunners().listHostedRunnersForOrg("github");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsHostedRunners().runners());
        HTTP.verifyQueryParam(GET, RUNNERS_PATH, "page", "1");
    }

    @Test
    void createHostedRunnerForOrg_shouldReturnRunner_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                RUNNERS_PATH,
                createdJson(
                        jsonBytes(GitHubActionsHostedRunnersApiIntegrationTest.class, "actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            runner = actions.api()
                    .hostedRunners()
                    .createHostedRunnerForOrg("github", ActionsCatalogFixtures.createHostedRunnerForOrgPayload());
        }

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
        HTTP.verifyRequested(POST, RUNNERS_PATH);
    }

    @Test
    void getHostedRunnersLimitsForOrg_shouldReturnLimits_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIMITS_PATH,
                okJson(jsonBytes(
                        GitHubActionsHostedRunnersApiIntegrationTest.class, "actions-hosted-runner-limits.json")));

        final GitHubActionsHostedRunnerLimits limits;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            limits = actions.api().hostedRunners().getHostedRunnersLimitsForOrg("github");
        }

        assertThat(limits).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerLimits());
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void deleteCustomImageFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, "/orgs/github/actions/hosted-runners/images/custom/1", noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().hostedRunners().deleteCustomImageFromOrg("github", 1L);
        }

        HTTP.verifyRequested(DELETE, "/orgs/github/actions/hosted-runners/images/custom/1");
    }
}
