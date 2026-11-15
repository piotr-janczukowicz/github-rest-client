package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposPlanningStatusesApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRepositoriesPlanningStatusesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String PLANNING_STATUSES_PATH = "/repos/octocat/Hello-World/planning-statuses";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listPlanningStatuses_shouldReturnPlanningStatuses_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PLANNING_STATUSES_PATH,
                okJson(listJsonBytes(
                        GitHubRepositoriesPlanningStatusesApiIntegrationTest.class, "planning-status.json")));

        final List<GitHubPlanningStatus> statuses;
        try (final GitHubApiWireMockSupport.ReposPlanningStatusesApiClient api = reposPlanningStatusesApi(HTTP)) {
            statuses = api.api().listPlanningStatuses(OWNER, REPO);
        }

        assertThat(statuses).containsExactly(RepositoriesCatalogFixtures.planningStatus());
        HTTP.verifyRequested(GET, PLANNING_STATUSES_PATH);
    }
}
