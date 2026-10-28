package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.activityApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActivityFeedsApiIntegrationTest {

    private static final String PATH = "/feeds";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getFeeds_shouldReturnFeed_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PATH, okJson(jsonBytes(GitHubActivityFeedsApiIntegrationTest.class, "feed.json")));

        final GitHubFeed feed;
        try (final GitHubApiWireMockSupport.ActivityApiClient api = activityApi(HTTP)) {
            feed = api.api().feeds().getFeeds();
        }

        assertThat(feed).isEqualTo(ActivityCatalogFixtures.feed());
        HTTP.verifyRequested(GET, PATH);
    }
}
