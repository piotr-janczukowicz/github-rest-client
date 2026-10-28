package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsConcurrencyGroupsApiIntegrationTest {

    private static final String GROUPS_PATH = "/repos/octocat/Hello-World/actions/concurrency_groups";
    private static final String GROUP_PATH = "/repos/octocat/Hello-World/actions/concurrency_groups/deploy";
    private static final String RUN_GROUPS_PATH = "/repos/octocat/Hello-World/actions/runs/1/concurrency_groups";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listConcurrencyGroupsForRepository_shouldReturnGroups_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                GROUPS_PATH,
                okJson(jsonBytes(
                        GitHubActionsConcurrencyGroupsApiIntegrationTest.class, "concurrency-group-list.json")));

        final GitHubPage<GitHubConcurrencyGroupListEntry> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().concurrencyGroups().listConcurrencyGroupsForRepository("octocat", "Hello-World");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.concurrencyGroupList().concurrencyGroups());
        HTTP.verifyRequested(GET, GROUPS_PATH);
    }

    @Test
    void getConcurrencyGroupForRepository_shouldReturnGroup_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                GROUP_PATH,
                okJson(jsonBytes(GitHubActionsConcurrencyGroupsApiIntegrationTest.class, "concurrency-group.json")));

        final Optional<GitHubConcurrencyGroup> group;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            group = actions.api()
                    .concurrencyGroups()
                    .getConcurrencyGroupForRepository("octocat", "Hello-World", "deploy");
        }

        assertThat(group).contains(ActionsCatalogFixtures.concurrencyGroup());
        HTTP.verifyRequested(GET, GROUP_PATH);
    }

    @Test
    void listConcurrencyGroupsForWorkflowRun_shouldReturnGroups_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RUN_GROUPS_PATH,
                okJson(jsonBytes(
                        GitHubActionsConcurrencyGroupsApiIntegrationTest.class, "concurrency-group-run-list.json")));

        final GitHubPage<GitHubConcurrencyGroupRunListEntry> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().concurrencyGroups().listConcurrencyGroupsForWorkflowRun("octocat", "Hello-World", 1L);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.concurrencyGroupRunList().concurrencyGroups());
        HTTP.verifyRequested(GET, RUN_GROUPS_PATH);
    }
}
