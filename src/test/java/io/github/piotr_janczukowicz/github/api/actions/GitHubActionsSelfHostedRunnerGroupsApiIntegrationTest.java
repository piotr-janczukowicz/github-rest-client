package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsSelfHostedRunnerGroupsApiIntegrationTest {

    private static final String GROUPS_PATH = "/orgs/github/actions/runner-groups";
    private static final String GROUP_PATH = "/orgs/github/actions/runner-groups/1";
    private static final String GROUP_REPOSITORIES_PATH = "/orgs/github/actions/runner-groups/1/repositories";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listSelfHostedRunnerGroupsForOrg_shouldReturnGroups_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                GROUPS_PATH,
                okJson(jsonBytes(GitHubActionsSelfHostedRunnerGroupsApiIntegrationTest.class, "runner-groups.json")));

        final GitHubPage<GitHubRunnerGroupsOrg> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().selfHostedRunnerGroups().listSelfHostedRunnerGroupsForOrg("github");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.runnerGroups().runnerGroups());
        HTTP.verifyRequested(GET, GROUPS_PATH);
    }

    @Test
    void getSelfHostedRunnerGroupForOrg_shouldReturnGroup_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                GROUP_PATH,
                okJson(jsonBytes(
                        GitHubActionsSelfHostedRunnerGroupsApiIntegrationTest.class, "runner-groups-org.json")));

        final GitHubRunnerGroupsOrg group;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            group = actions.api()
                    .selfHostedRunnerGroups()
                    .getSelfHostedRunnerGroupForOrg("github", 1L)
                    .orElseThrow();
        }

        assertThat(group).isEqualTo(ActionsCatalogFixtures.runnerGroupsOrg());
    }

    @Test
    void listRepoAccessToSelfHostedRunnerGroupInOrg_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                GROUP_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubActionsSelfHostedRunnerGroupsApiIntegrationTest.class,
                        "runner-group-repositories.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().selfHostedRunnerGroups().listRepoAccessToSelfHostedRunnerGroupInOrg("github", 1L);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.runnerGroupRepositories().repositories());
        HTTP.verifyRequested(GET, GROUP_REPOSITORIES_PATH);
    }

    @Test
    void deleteSelfHostedRunnerGroupFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, GROUP_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().selfHostedRunnerGroups().deleteSelfHostedRunnerGroupFromOrg("github", 1L);
        }

        HTTP.verifyRequested(DELETE, GROUP_PATH);
    }
}
