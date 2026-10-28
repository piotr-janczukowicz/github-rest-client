package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsApiInsightsApiIntegrationTest {

    private static final String ORG = "github";
    private static final String USER_ID = "12345";
    private static final long ACTOR_ID = 954_453L;
    private static final Instant MIN_TIMESTAMP = Instant.parse("2024-09-11T15:00:00Z");
    private static final String TIMESTAMP_INCREMENT = "5m";

    private static final String ROUTE_STATS_PATH = "/orgs/github/insights/api/route-stats/oauth_app/954453";
    private static final String SUBJECT_STATS_PATH = "/orgs/github/insights/api/subject-stats";
    private static final String SUMMARY_STATS_PATH = "/orgs/github/insights/api/summary-stats";
    private static final String SUMMARY_STATS_BY_USER_PATH = "/orgs/github/insights/api/summary-stats/users/12345";
    private static final String SUMMARY_STATS_BY_ACTOR_PATH =
            "/orgs/github/insights/api/summary-stats/oauth_app/954453";
    private static final String TIME_STATS_PATH = "/orgs/github/insights/api/time-stats";
    private static final String TIME_STATS_BY_USER_PATH = "/orgs/github/insights/api/time-stats/users/12345";
    private static final String TIME_STATS_BY_ACTOR_PATH = "/orgs/github/insights/api/time-stats/oauth_app/954453";
    private static final String USER_STATS_PATH = "/orgs/github/insights/api/user-stats/12345";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRouteStatsByActor_shouldReturnRouteStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ROUTE_STATS_PATH, okJson(json("api-insights-route-stats.json")));

        final GitHubPage<GitHubApiInsightsRouteStat> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations
                    .api()
                    .apiInsights()
                    .getRouteStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, MIN_TIMESTAMP);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRouteStats());
        HTTP.verifyRequested(GET, ROUTE_STATS_PATH);
    }

    @Test
    void getSubjectStats_shouldReturnSubjectStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SUBJECT_STATS_PATH, okJson(json("api-insights-subject-stats.json")));

        final GitHubPage<GitHubApiInsightsSubjectStat> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().apiInsights().getSubjectStats(ORG, MIN_TIMESTAMP);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedSubjectStats());
        HTTP.verifyRequested(GET, SUBJECT_STATS_PATH);
    }

    @Test
    void getSummaryStats_shouldReturnSummaryStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SUMMARY_STATS_PATH, okJson(json("api-insights-summary-stats.json")));

        final GitHubApiInsightsSummaryStats stats;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            stats = organizations.api().apiInsights().getSummaryStats(ORG, MIN_TIMESTAMP);
        }

        assertThat(stats).isEqualTo(expectedSummaryStats());
        HTTP.verifyRequested(GET, SUMMARY_STATS_PATH);
    }

    @Test
    void getSummaryStatsByUser_shouldReturnSummaryStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SUMMARY_STATS_BY_USER_PATH, okJson(json("api-insights-summary-stats.json")));

        final GitHubApiInsightsSummaryStats stats;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            stats = organizations
                    .api()
                    .apiInsights()
                    .getSummaryStatsByUser(ORG, USER_ID, MIN_TIMESTAMP)
                    .orElseThrow();
        }

        assertThat(stats).isEqualTo(expectedSummaryStats());
        HTTP.verifyRequested(GET, SUMMARY_STATS_BY_USER_PATH);
    }

    @Test
    void getSummaryStatsByActor_shouldReturnSummaryStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SUMMARY_STATS_BY_ACTOR_PATH, okJson(json("api-insights-summary-stats.json")));

        final GitHubApiInsightsSummaryStats stats;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            stats = organizations
                    .api()
                    .apiInsights()
                    .getSummaryStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, MIN_TIMESTAMP)
                    .orElseThrow();
        }

        assertThat(stats).isEqualTo(expectedSummaryStats());
        HTTP.verifyRequested(GET, SUMMARY_STATS_BY_ACTOR_PATH);
    }

    @Test
    void getTimeStats_shouldReturnTimeStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TIME_STATS_PATH, okJson(json("api-insights-time-stats.json")));

        final List<GitHubApiInsightsTimeStat> stats;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            stats = organizations.api().apiInsights().getTimeStats(ORG, MIN_TIMESTAMP, TIMESTAMP_INCREMENT);
        }

        assertThat(stats).containsExactlyElementsOf(expectedTimeStats());
        HTTP.verifyRequested(GET, TIME_STATS_PATH);
    }

    @Test
    void getTimeStatsByUser_shouldReturnTimeStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TIME_STATS_BY_USER_PATH, okJson(json("api-insights-time-stats.json")));

        final List<GitHubApiInsightsTimeStat> stats;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            stats = organizations
                    .api()
                    .apiInsights()
                    .getTimeStatsByUser(ORG, USER_ID, MIN_TIMESTAMP, TIMESTAMP_INCREMENT);
        }

        assertThat(stats).containsExactlyElementsOf(expectedTimeStats());
        HTTP.verifyRequested(GET, TIME_STATS_BY_USER_PATH);
    }

    @Test
    void getTimeStatsByActor_shouldReturnTimeStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TIME_STATS_BY_ACTOR_PATH, okJson(json("api-insights-time-stats.json")));

        final List<GitHubApiInsightsTimeStat> stats;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            stats = organizations
                    .api()
                    .apiInsights()
                    .getTimeStatsByActor(
                            ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, MIN_TIMESTAMP, TIMESTAMP_INCREMENT);
        }

        assertThat(stats).containsExactlyElementsOf(expectedTimeStats());
        HTTP.verifyRequested(GET, TIME_STATS_BY_ACTOR_PATH);
    }

    @Test
    void getUserStats_shouldReturnUserStats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_STATS_PATH, okJson(json("api-insights-user-stats.json")));

        final GitHubPage<GitHubApiInsightsUserStat> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().apiInsights().getUserStats(ORG, USER_ID, MIN_TIMESTAMP);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedUserStats());
        HTTP.verifyRequested(GET, USER_STATS_PATH);
    }

    private static byte[] json(final String fileName) {
        return jsonBytes(GitHubOrganizationsApiInsightsApiIntegrationTest.class, fileName);
    }

    private static List<GitHubApiInsightsRouteStat> expectedRouteStats() {
        return Arrays.asList(CODEC.decode(json("api-insights-route-stats.json"), GitHubApiInsightsRouteStat[].class));
    }

    private static List<GitHubApiInsightsSubjectStat> expectedSubjectStats() {
        return Arrays.asList(
                CODEC.decode(json("api-insights-subject-stats.json"), GitHubApiInsightsSubjectStat[].class));
    }

    private static GitHubApiInsightsSummaryStats expectedSummaryStats() {
        return CODEC.decode(json("api-insights-summary-stats.json"), GitHubApiInsightsSummaryStats.class);
    }

    private static List<GitHubApiInsightsTimeStat> expectedTimeStats() {
        return Arrays.asList(CODEC.decode(json("api-insights-time-stats.json"), GitHubApiInsightsTimeStat[].class));
    }

    private static List<GitHubApiInsightsUserStat> expectedUserStats() {
        return Arrays.asList(CODEC.decode(json("api-insights-user-stats.json"), GitHubApiInsightsUserStat[].class));
    }
}
