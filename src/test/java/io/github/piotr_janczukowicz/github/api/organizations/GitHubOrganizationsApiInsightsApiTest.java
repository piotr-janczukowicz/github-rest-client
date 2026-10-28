package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsApiInsightsApiTest {

    private static final String ORG = "github";
    private static final String USER_ID = "12345";
    private static final long ACTOR_ID = 954_453L;
    private static final Instant MIN_TIMESTAMP = Instant.parse("2024-09-11T15:00:00Z");
    private static final String MIN_TIMESTAMP_TEXT = "2024-09-11T15:00:00Z";
    private static final Instant MAX_TIMESTAMP = Instant.parse("2024-09-18T15:00:00Z");
    private static final String MAX_TIMESTAMP_TEXT = "2024-09-18T15:00:00Z";
    private static final String TIMESTAMP_INCREMENT = "5m";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsApiInsightsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getRouteStatsByActor_shouldReturnRouteStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-route-stats.json")));
        final GitHubApiInsightsRouteStatsFilter filter = routeStatsFilter();

        final GitHubPage<GitHubApiInsightsRouteStat> page = callApi(api -> api.getRouteStatsByActor(
                ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, filter, GitHubPageRequest.byPage()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.apiInsightsRouteStat());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/insights/api/route-stats/oauth_app/954453");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("direction", "desc");
            softly.assertThat(request.uri()).hasParameter("sort", "api_route,http_method");
            softly.assertThat(request.uri()).hasParameter("api_route_substring", "/repos");
            softly.assertThat(request.uri().getQuery()).contains("page=1");
        });
    }

    @Test
    void getRouteStatsByActor_shouldUseDefaults_whenOverloadWithoutFilterOrRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-route-stats.json")));

        callApi(api -> api.getRouteStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, MIN_TIMESTAMP));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/route-stats/oauth_app/954453");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
            softly.assertThat(requestUri.getQuery()).contains("page=1");
        });
    }

    @Test
    void getRouteStatsByActor_shouldUseDefaultPage_whenOverloadWithFilterOnly() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-route-stats.json")));

        callApi(api ->
                api.getRouteStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, routeStatsFilter()));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).contains("page=1");
        });
    }

    @Test
    void getSubjectStats_shouldReturnSubjectStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-subject-stats.json")));
        final GitHubApiInsightsSubjectStatsFilter filter = subjectStatsFilter();

        final GitHubPage<GitHubApiInsightsSubjectStat> page =
                callApi(api -> api.getSubjectStats(ORG, filter, GitHubPageRequest.byPage()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.apiInsightsSubjectStat());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/insights/api/subject-stats");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("direction", "asc");
            softly.assertThat(request.uri()).hasParameter("sort", "subject_name,total_request_count");
            softly.assertThat(request.uri()).hasParameter("subject_name_substring", "Actions");
            softly.assertThat(request.uri().getQuery()).contains("page=1");
        });
    }

    @Test
    void getSubjectStats_shouldUseDefaults_whenOverloadWithoutFilterOrRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-subject-stats.json")));

        callApi(api -> api.getSubjectStats(ORG, MIN_TIMESTAMP));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/subject-stats");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
            softly.assertThat(requestUri.getQuery()).contains("page=1");
        });
    }

    @Test
    void getSubjectStats_shouldUseDefaultPage_whenOverloadWithFilterOnly() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-subject-stats.json")));

        callApi(api -> api.getSubjectStats(ORG, subjectStatsFilter()));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).contains("page=1");
        });
    }

    @Test
    void getSummaryStats_shouldReturnSummaryStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-summary-stats.json")));
        final GitHubApiInsightsTimeWindowFilter filter = timeWindowFilter();

        final GitHubApiInsightsSummaryStats stats = callApi(api -> api.getSummaryStats(ORG, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).isEqualTo(OrganizationsCatalogFixtures.apiInsightsSummaryStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/insights/api/summary-stats");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
        });
    }

    @Test
    void getSummaryStats_shouldUseEmptyFilter_whenOverloadWithoutFilter() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-summary-stats.json")));

        callApi(api -> api.getSummaryStats(ORG, MIN_TIMESTAMP));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/summary-stats");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
        });
    }

    @Test
    void getSummaryStatsByUser_shouldReturnSummaryStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-summary-stats.json")));
        final GitHubApiInsightsTimeWindowFilter filter = timeWindowFilter();

        final Optional<GitHubApiInsightsSummaryStats> stats =
                callApi(api -> api.getSummaryStatsByUser(ORG, USER_ID, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).contains(OrganizationsCatalogFixtures.apiInsightsSummaryStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/insights/api/summary-stats/users/12345");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
        });
    }

    @Test
    void getSummaryStatsByUser_shouldUseEmptyFilter_whenOverloadWithoutFilter() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-summary-stats.json")));

        callApi(api -> api.getSummaryStatsByUser(ORG, USER_ID, MIN_TIMESTAMP));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/summary-stats/users/12345");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
        });
    }

    @Test
    void getSummaryStatsByActor_shouldReturnSummaryStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-summary-stats.json")));
        final GitHubApiInsightsTimeWindowFilter filter = timeWindowFilter();

        final Optional<GitHubApiInsightsSummaryStats> stats =
                callApi(api -> api.getSummaryStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).contains(OrganizationsCatalogFixtures.apiInsightsSummaryStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/insights/api/summary-stats/oauth_app/954453");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
        });
    }

    @Test
    void getSummaryStatsByActor_shouldUseEmptyFilter_whenOverloadWithoutFilter() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("api-insights-summary-stats.json")));

        callApi(api -> api.getSummaryStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, MIN_TIMESTAMP));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString())
                    .contains("/orgs/github/insights/api/summary-stats/oauth_app/954453");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
        });
    }

    @Test
    void getSummaryStatsByUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubApiInsightsTimeWindowFilter filter = timeWindowFilter();

        final Optional<GitHubApiInsightsSummaryStats> result =
                callApi(api -> api.getSummaryStatsByUser(ORG, USER_ID, filter));

        assertThat(result).isEmpty();
    }

    @Test
    void getSummaryStatsByActor_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubApiInsightsTimeWindowFilter filter = timeWindowFilter();

        final Optional<GitHubApiInsightsSummaryStats> result =
                callApi(api -> api.getSummaryStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, filter));

        assertThat(result).isEmpty();
    }

    @Test
    void getTimeStats_shouldReturnTimeStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-time-stats.json")));
        final GitHubApiInsightsTimeStatsFilter filter = timeStatsFilter();

        final List<GitHubApiInsightsTimeStat> stats = callApi(api -> api.getTimeStats(ORG, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).containsExactlyElementsOf(OrganizationsCatalogFixtures.apiInsightsTimeStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/insights/api/time-stats");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("timestamp_increment", TIMESTAMP_INCREMENT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
        });
    }

    @Test
    void getTimeStats_shouldUseEmptyFilter_whenOverloadWithoutFilter() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-time-stats.json")));

        callApi(api -> api.getTimeStats(ORG, MIN_TIMESTAMP, TIMESTAMP_INCREMENT));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/time-stats");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri).hasParameter("timestamp_increment", TIMESTAMP_INCREMENT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
        });
    }

    @Test
    void getTimeStatsByUser_shouldReturnTimeStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-time-stats.json")));
        final GitHubApiInsightsTimeStatsFilter filter = timeStatsFilter();

        final List<GitHubApiInsightsTimeStat> stats = callApi(api -> api.getTimeStatsByUser(ORG, USER_ID, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).containsExactlyElementsOf(OrganizationsCatalogFixtures.apiInsightsTimeStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/insights/api/time-stats/users/12345");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("timestamp_increment", TIMESTAMP_INCREMENT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
        });
    }

    @Test
    void getTimeStatsByUser_shouldUseEmptyFilter_whenOverloadWithoutFilter() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-time-stats.json")));

        callApi(api -> api.getTimeStatsByUser(ORG, USER_ID, MIN_TIMESTAMP, TIMESTAMP_INCREMENT));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/time-stats/users/12345");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri).hasParameter("timestamp_increment", TIMESTAMP_INCREMENT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
        });
    }

    @Test
    void getTimeStatsByActor_shouldReturnTimeStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-time-stats.json")));
        final GitHubApiInsightsTimeStatsFilter filter = timeStatsFilter();

        final List<GitHubApiInsightsTimeStat> stats =
                callApi(api -> api.getTimeStatsByActor(ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).containsExactlyElementsOf(OrganizationsCatalogFixtures.apiInsightsTimeStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/insights/api/time-stats/oauth_app/954453");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("timestamp_increment", TIMESTAMP_INCREMENT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
        });
    }

    @Test
    void getTimeStatsByActor_shouldUseEmptyFilter_whenOverloadWithoutFilter() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-time-stats.json")));

        callApi(api -> api.getTimeStatsByActor(
                ORG, GitHubApiInsightsActorType.OAUTH_APP, ACTOR_ID, MIN_TIMESTAMP, TIMESTAMP_INCREMENT));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/time-stats/oauth_app/954453");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri).hasParameter("timestamp_increment", TIMESTAMP_INCREMENT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
        });
    }

    @Test
    void getUserStats_shouldReturnUserStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-user-stats.json")));
        final GitHubApiInsightsUserStatsFilter filter = userStatsFilter();

        final GitHubPage<GitHubApiInsightsUserStat> page =
                callApi(api -> api.getUserStats(ORG, USER_ID, filter, GitHubPageRequest.byPage()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(OrganizationsCatalogFixtures.apiInsightsUserStatWithoutIntegration());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/insights/api/user-stats/12345");
            softly.assertThat(request.uri()).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
            softly.assertThat(request.uri()).hasParameter("direction", "desc");
            softly.assertThat(request.uri()).hasParameter("sort", "last_request_timestamp");
            softly.assertThat(request.uri()).hasParameter("actor_name_substring", "GitHub");
            softly.assertThat(request.uri().getQuery()).contains("page=1");
        });
    }

    @Test
    void getUserStats_shouldUseDefaults_whenOverloadWithoutFilterOrRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-user-stats.json")));

        callApi(api -> api.getUserStats(ORG, USER_ID, MIN_TIMESTAMP));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri.toString()).contains("/orgs/github/insights/api/user-stats/12345");
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).doesNotContain("max_timestamp");
            softly.assertThat(requestUri.getQuery()).contains("page=1");
        });
    }

    @Test
    void getUserStats_shouldUseDefaultPage_whenOverloadWithFilterOnly() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-insights-user-stats.json")));

        callApi(api -> api.getUserStats(ORG, USER_ID, userStatsFilter()));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("min_timestamp", MIN_TIMESTAMP_TEXT);
            softly.assertThat(requestUri).hasParameter("max_timestamp", MAX_TIMESTAMP_TEXT);
            softly.assertThat(requestUri.getQuery()).contains("page=1");
        });
    }

    private static GitHubApiInsightsRouteStatsFilter routeStatsFilter() {
        return GitHubApiInsightsRouteStatsFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .direction(GitHubDirection.DESC)
                .sort(List.of(GitHubApiInsightsRouteStatsSort.API_ROUTE, GitHubApiInsightsRouteStatsSort.HTTP_METHOD))
                .apiRouteSubstring("/repos")
                .build();
    }

    private static GitHubApiInsightsSubjectStatsFilter subjectStatsFilter() {
        return GitHubApiInsightsSubjectStatsFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .direction(GitHubDirection.ASC)
                .sort(List.of(GitHubApiInsightsSort.SUBJECT_NAME, GitHubApiInsightsSort.TOTAL_REQUEST_COUNT))
                .subjectNameSubstring("Actions")
                .build();
    }

    private static GitHubApiInsightsUserStatsFilter userStatsFilter() {
        return GitHubApiInsightsUserStatsFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .direction(GitHubDirection.DESC)
                .sort(List.of(GitHubApiInsightsSort.LAST_REQUEST_TIMESTAMP))
                .actorNameSubstring("GitHub")
                .build();
    }

    private static GitHubApiInsightsTimeWindowFilter timeWindowFilter() {
        return GitHubApiInsightsTimeWindowFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .build();
    }

    private static GitHubApiInsightsTimeStatsFilter timeStatsFilter() {
        return GitHubApiInsightsTimeStatsFilterBuilder.builder()
                .minTimestamp(MIN_TIMESTAMP)
                .maxTimestamp(MAX_TIMESTAMP)
                .timestampIncrement(TIMESTAMP_INCREMENT)
                .build();
    }

    @FunctionalInterface
    private interface InsightsApiAction<T> {
        T apply(GitHubOrganizationsApiInsightsApi api) throws Exception;
    }

    private <T> T callApi(final InsightsApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsApiInsightsApi(TestClients.caller(client)));
        }
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationsApiInsightsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
