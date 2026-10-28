package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.metricsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubMetricsTrafficApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private static final String CLONES_PATH = "/repos/octocat/Hello-World/traffic/clones";
    private static final String VIEWS_PATH = "/repos/octocat/Hello-World/traffic/views";
    private static final String PATHS_PATH = "/repos/octocat/Hello-World/traffic/popular/paths";
    private static final String REFERRERS_PATH = "/repos/octocat/Hello-World/traffic/popular/referrers";

    private static final JsonType<List<GitHubContentTraffic>> CONTENT_TRAFFIC_TYPE =
            JsonTypes.listOf(GitHubContentTraffic.class);
    private static final JsonType<List<GitHubReferrerTraffic>> REFERRER_TRAFFIC_TYPE =
            JsonTypes.listOf(GitHubReferrerTraffic.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getClones_shouldReturnTraffic_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CLONES_PATH,
                okJson(jsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "clone-traffic.json")));

        final GitHubCloneTraffic traffic;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            traffic = api.api().traffic().getClones(OWNER, REPO);
        }

        final GitHubCloneTraffic expected = CODEC.decode(
                jsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "clone-traffic.json"),
                GitHubCloneTraffic.class);

        assertThat(traffic).isEqualTo(expected);
        HTTP.verifyRequested(GET, CLONES_PATH);
        HTTP.verifyNoQueryParam(GET, CLONES_PATH, "per");
    }

    @Test
    void getClones_shouldSendPerFilter_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                CLONES_PATH,
                okJson(jsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "clone-traffic.json")));

        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            api.api()
                    .traffic()
                    .getClones(
                            OWNER,
                            REPO,
                            GitHubGetClonesFilterBuilder.builder()
                                    .per(GitHubTrafficPer.WEEK)
                                    .build());
        }

        HTTP.verifyQueryParam(GET, CLONES_PATH, "per", "week");
    }

    @Test
    void getViews_shouldReturnTraffic_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, VIEWS_PATH, okJson(jsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "view-traffic.json")));

        final GitHubViewTraffic traffic;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            traffic = api.api().traffic().getViews(OWNER, REPO);
        }

        final GitHubViewTraffic expected = CODEC.decode(
                jsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "view-traffic.json"), GitHubViewTraffic.class);

        assertThat(traffic).isEqualTo(expected);
        HTTP.verifyRequested(GET, VIEWS_PATH);
        HTTP.verifyNoQueryParam(GET, VIEWS_PATH, "per");
    }

    @Test
    void getViews_shouldSendPerFilter_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET, VIEWS_PATH, okJson(jsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "view-traffic.json")));

        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            api.api()
                    .traffic()
                    .getViews(
                            OWNER,
                            REPO,
                            GitHubGetClonesFilterBuilder.builder()
                                    .per(GitHubTrafficPer.WEEK)
                                    .build());
        }

        HTTP.verifyQueryParam(GET, VIEWS_PATH, "per", "week");
    }

    @Test
    void getTopPaths_shouldReturnPaths_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PATHS_PATH,
                okJson(listJsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "content-traffic.json")));

        final List<GitHubContentTraffic> paths;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            paths = api.api().traffic().getTopPaths(OWNER, REPO);
        }

        final List<GitHubContentTraffic> expected = CODEC.decode(
                listJsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "content-traffic.json"),
                CONTENT_TRAFFIC_TYPE);

        assertThat(paths).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, PATHS_PATH);
    }

    @Test
    void getTopReferrers_shouldReturnReferrers_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REFERRERS_PATH,
                okJson(listJsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "referrer-traffic.json")));

        final List<GitHubReferrerTraffic> referrers;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            referrers = api.api().traffic().getTopReferrers(OWNER, REPO);
        }

        final List<GitHubReferrerTraffic> expected = CODEC.decode(
                listJsonBytes(GitHubMetricsTrafficApiIntegrationTest.class, "referrer-traffic.json"),
                REFERRER_TRAFFIC_TYPE);

        assertThat(referrers).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, REFERRERS_PATH);
    }
}
