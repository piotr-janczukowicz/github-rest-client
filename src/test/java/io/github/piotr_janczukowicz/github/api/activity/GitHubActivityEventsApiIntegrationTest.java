package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.activityApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActivityEventsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/events";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listRepoEvents_shouldReturnEvents_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubActivityEventsApiIntegrationTest.class, "event.json")));

        final GitHubPage<GitHubEvent> page;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            page = api.api().events().listRepoEvents(OWNER, REPO);
        }

        assertThat(page.items()).containsExactly(ActivityCatalogFixtures.event());
        HTTP.verifyRequested(GET, COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listRepoEvents_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubActivityEventsApiIntegrationTest.class, "event.json")));

        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            api.api().events().listRepoEvents(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "10");
    }
}
