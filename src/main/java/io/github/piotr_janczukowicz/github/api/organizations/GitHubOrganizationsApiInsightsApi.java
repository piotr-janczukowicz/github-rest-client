package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** GitHub organization API Insights REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsApiInsightsApi extends GitHubResourceApi {

    private static final PathTemplate USER_STATS = PathTemplate.of("/orgs/{org}/insights/api/user-stats/{user_id}");
    private static final PathTemplate TIME_STATS_ACTOR =
            PathTemplate.of("/orgs/{org}/insights/api/time-stats/{actor_type}/{actor_id}");
    private static final PathTemplate TIME_STATS_USER =
            PathTemplate.of("/orgs/{org}/insights/api/time-stats/users/{user_id}");
    private static final PathTemplate TIME_STATS = PathTemplate.of("/orgs/{org}/insights/api/time-stats");
    private static final PathTemplate SUMMARY_STATS_ACTOR =
            PathTemplate.of("/orgs/{org}/insights/api/summary-stats/{actor_type}/{actor_id}");
    private static final PathTemplate SUMMARY_STATS_USER =
            PathTemplate.of("/orgs/{org}/insights/api/summary-stats/users/{user_id}");
    private static final PathTemplate SUMMARY_STATS = PathTemplate.of("/orgs/{org}/insights/api/summary-stats");
    private static final PathTemplate SUBJECT_STATS = PathTemplate.of("/orgs/{org}/insights/api/subject-stats");
    private static final PathTemplate ROUTE_STATS =
            PathTemplate.of("/orgs/{org}/insights/api/route-stats/{actor_type}/{actor_id}");

    private static final String PARAM_ACTOR_TYPE = "actorType";

    private static final JsonType<List<GitHubApiInsightsRouteStat>> ROUTE_STATS_TYPE =
            JsonTypes.listOf(GitHubApiInsightsRouteStat.class);
    private static final JsonType<List<GitHubApiInsightsSubjectStat>> SUBJECT_STATS_TYPE =
            JsonTypes.listOf(GitHubApiInsightsSubjectStat.class);
    private static final JsonType<List<GitHubApiInsightsTimeStat>> TIME_STATS_TYPE =
            JsonTypes.listOf(GitHubApiInsightsTimeStat.class);
    private static final JsonType<List<GitHubApiInsightsUserStat>> USER_STATS_TYPE =
            JsonTypes.listOf(GitHubApiInsightsUserStat.class);

    /**
     * API Insights resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsApiInsightsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get route stats by actor ({@code GET /orgs/{org}/insights/api/route-stats/{actor_type}/{actor_id}}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and {@link GitHubApiInsightsRouteStatsFilter#of(Instant)}. See
     * {@link #getRouteStatsByActor(String, GitHubApiInsightsActorType, long, GitHubApiInsightsRouteStatsFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @param actorType actor type path segment
     * @param actorId actor id
     * @param minTimestamp minimum timestamp for the query window
     * @return first HTTP page of route stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code actorType} or {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsRouteStat> getRouteStatsByActor(
            final String org,
            final GitHubApiInsightsActorType actorType,
            final long actorId,
            final Instant minTimestamp)
            throws IOException {
        return getRouteStatsByActor(org, actorType, actorId, GitHubApiInsightsRouteStatsFilter.of(minTimestamp));
    }

    /**
     * Get route stats by actor ({@code GET /orgs/{org}/insights/api/route-stats/{actor_type}/{actor_id}}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #getRouteStatsByActor(String, GitHubApiInsightsActorType,
     * long, GitHubApiInsightsRouteStatsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param actorType actor type path segment
     * @param actorId actor id
     * @param filter query filters
     * @return first HTTP page of route stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code actorType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsRouteStat> getRouteStatsByActor(
            final String org,
            final GitHubApiInsightsActorType actorType,
            final long actorId,
            final GitHubApiInsightsRouteStatsFilter filter)
            throws IOException {
        return getRouteStatsByActor(org, actorType, actorId, filter, GitHubPageRequest.byPage());
    }

    /**
     * Get route stats by actor ({@code GET /orgs/{org}/insights/api/route-stats/{actor_type}/{actor_id}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param actorType actor type path segment
     * @param actorId actor id
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of route stats
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code actorType}, {@code filter}, or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsRouteStat> getRouteStatsByActor(
            final String org,
            final GitHubApiInsightsActorType actorType,
            final long actorId,
            final GitHubApiInsightsRouteStatsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(actorType, PARAM_ACTOR_TYPE);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                ROUTE_STATS,
                filter.toQueryParameters(),
                request,
                ROUTE_STATS_TYPE,
                org,
                actorType.wireValue(),
                actorId);
    }

    /**
     * Get subject stats ({@code GET /orgs/{org}/insights/api/subject-stats}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and {@link GitHubApiInsightsSubjectStatsFilter#of(Instant)}. See
     * {@link #getSubjectStats(String, GitHubApiInsightsSubjectStatsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param minTimestamp minimum timestamp for the query window
     * @return first HTTP page of subject stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsSubjectStat> getSubjectStats(final String org, final Instant minTimestamp)
            throws IOException {
        return getSubjectStats(org, GitHubApiInsightsSubjectStatsFilter.of(minTimestamp));
    }

    /**
     * Get subject stats ({@code GET /orgs/{org}/insights/api/subject-stats}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #getSubjectStats(String,
     * GitHubApiInsightsSubjectStatsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter query filters
     * @return first HTTP page of subject stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsSubjectStat> getSubjectStats(
            final String org, final GitHubApiInsightsSubjectStatsFilter filter) throws IOException {
        return getSubjectStats(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * Get subject stats ({@code GET /orgs/{org}/insights/api/subject-stats}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of subject stats
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsSubjectStat> getSubjectStats(
            final String org, final GitHubApiInsightsSubjectStatsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(SUBJECT_STATS, filter.toQueryParameters(), request, SUBJECT_STATS_TYPE, org);
    }

    /**
     * Get summary stats ({@code GET /orgs/{org}/insights/api/summary-stats}).
     *
     * <p>Uses {@link GitHubApiInsightsTimeWindowFilter#of(Instant)}. See {@link #getSummaryStats(String,
     * GitHubApiInsightsTimeWindowFilter)}.
     *
     * @param org organization name
     * @param minTimestamp minimum timestamp for the query window
     * @return summary stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubApiInsightsSummaryStats getSummaryStats(final String org, final Instant minTimestamp)
            throws IOException {
        return getSummaryStats(org, GitHubApiInsightsTimeWindowFilter.of(minTimestamp));
    }

    /**
     * Get summary stats ({@code GET /orgs/{org}/insights/api/summary-stats}).
     *
     * @param org organization name
     * @param filter query filters
     * @return summary stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubApiInsightsSummaryStats getSummaryStats(
            final String org, final GitHubApiInsightsTimeWindowFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(SUMMARY_STATS, org).withQuery(filter.toQueryParameters()), GitHubApiInsightsSummaryStats.class);
    }

    /**
     * Get summary stats by user ({@code GET /orgs/{org}/insights/api/summary-stats/users/{user_id}}).
     *
     * <p>Uses {@link GitHubApiInsightsTimeWindowFilter#of(Instant)}. See {@link #getSummaryStatsByUser(String, String,
     * GitHubApiInsightsTimeWindowFilter)}.
     *
     * @param org organization name
     * @param userId user id
     * @param minTimestamp minimum timestamp for the query window
     * @return summary stats, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code userId} is blank
     * @throws NullPointerException if {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubApiInsightsSummaryStats> getSummaryStatsByUser(
            final String org, final String userId, final Instant minTimestamp) throws IOException {
        return getSummaryStatsByUser(org, userId, GitHubApiInsightsTimeWindowFilter.of(minTimestamp));
    }

    /**
     * Get summary stats by user ({@code GET /orgs/{org}/insights/api/summary-stats/users/{user_id}}).
     *
     * @param org organization name
     * @param userId user id
     * @param filter query filters
     * @return summary stats, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code userId} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubApiInsightsSummaryStats> getSummaryStatsByUser(
            final String org, final String userId, final GitHubApiInsightsTimeWindowFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(SUMMARY_STATS_USER, org, userId).withQuery(filter.toQueryParameters()),
                GitHubApiInsightsSummaryStats.class);
    }

    /**
     * Get summary stats by actor ({@code GET /orgs/{org}/insights/api/summary-stats/{actor_type}/{actor_id}}).
     *
     * <p>Uses {@link GitHubApiInsightsTimeWindowFilter#of(Instant)}. See {@link #getSummaryStatsByActor(String,
     * GitHubApiInsightsActorType, long, GitHubApiInsightsTimeWindowFilter)}.
     *
     * @param org organization name
     * @param actorType actor type path segment
     * @param actorId actor id
     * @param minTimestamp minimum timestamp for the query window
     * @return summary stats, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code actorType} or {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubApiInsightsSummaryStats> getSummaryStatsByActor(
            final String org,
            final GitHubApiInsightsActorType actorType,
            final long actorId,
            final Instant minTimestamp)
            throws IOException {
        return getSummaryStatsByActor(org, actorType, actorId, GitHubApiInsightsTimeWindowFilter.of(minTimestamp));
    }

    /**
     * Get summary stats by actor ({@code GET /orgs/{org}/insights/api/summary-stats/{actor_type}/{actor_id}}).
     *
     * @param org organization name
     * @param actorType actor type path segment
     * @param actorId actor id
     * @param filter query filters
     * @return summary stats, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code actorType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubApiInsightsSummaryStats> getSummaryStatsByActor(
            final String org,
            final GitHubApiInsightsActorType actorType,
            final long actorId,
            final GitHubApiInsightsTimeWindowFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(actorType, PARAM_ACTOR_TYPE);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(SUMMARY_STATS_ACTOR, org, actorType.wireValue(), actorId).withQuery(filter.toQueryParameters()),
                GitHubApiInsightsSummaryStats.class);
    }

    /**
     * Get time stats ({@code GET /orgs/{org}/insights/api/time-stats}).
     *
     * <p>Uses {@link GitHubApiInsightsTimeStatsFilter#of(Instant, String)}. See {@link #getTimeStats(String,
     * GitHubApiInsightsTimeStatsFilter)}.
     *
     * @param org organization name
     * @param minTimestamp minimum timestamp for the query window
     * @param timestampIncrement timestamp increment for bucketing
     * @return time stats
     * @throws IllegalArgumentException if {@code org} or {@code timestampIncrement} is blank
     * @throws NullPointerException if {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubApiInsightsTimeStat> getTimeStats(
            final String org, final Instant minTimestamp, final String timestampIncrement) throws IOException {
        return getTimeStats(org, GitHubApiInsightsTimeStatsFilter.of(minTimestamp, timestampIncrement));
    }

    /**
     * Get time stats ({@code GET /orgs/{org}/insights/api/time-stats}).
     *
     * @param org organization name
     * @param filter query filters
     * @return time stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubApiInsightsTimeStat> getTimeStats(final String org, final GitHubApiInsightsTimeStatsFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(get(TIME_STATS, org).withQuery(filter.toQueryParameters()), TIME_STATS_TYPE);
    }

    /**
     * Get time stats by user ({@code GET /orgs/{org}/insights/api/time-stats/users/{user_id}}).
     *
     * <p>Uses {@link GitHubApiInsightsTimeStatsFilter#of(Instant, String)}. See {@link #getTimeStatsByUser(String,
     * String, GitHubApiInsightsTimeStatsFilter)}.
     *
     * @param org organization name
     * @param userId user id
     * @param minTimestamp minimum timestamp for the query window
     * @param timestampIncrement timestamp increment for bucketing
     * @return time stats
     * @throws IllegalArgumentException if {@code org}, {@code userId}, or {@code timestampIncrement} is blank
     * @throws NullPointerException if {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubApiInsightsTimeStat> getTimeStatsByUser(
            final String org, final String userId, final Instant minTimestamp, final String timestampIncrement)
            throws IOException {
        return getTimeStatsByUser(org, userId, GitHubApiInsightsTimeStatsFilter.of(minTimestamp, timestampIncrement));
    }

    /**
     * Get time stats by user ({@code GET /orgs/{org}/insights/api/time-stats/users/{user_id}}).
     *
     * @param org organization name
     * @param userId user id
     * @param filter query filters
     * @return time stats
     * @throws IllegalArgumentException if {@code org} or {@code userId} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubApiInsightsTimeStat> getTimeStatsByUser(
            final String org, final String userId, final GitHubApiInsightsTimeStatsFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(get(TIME_STATS_USER, org, userId).withQuery(filter.toQueryParameters()), TIME_STATS_TYPE);
    }

    /**
     * Get time stats by actor ({@code GET /orgs/{org}/insights/api/time-stats/{actor_type}/{actor_id}}).
     *
     * <p>Uses {@link GitHubApiInsightsTimeStatsFilter#of(Instant, String)}. See {@link #getTimeStatsByActor(String,
     * GitHubApiInsightsActorType, long, GitHubApiInsightsTimeStatsFilter)}.
     *
     * @param org organization name
     * @param actorType actor type path segment
     * @param actorId actor id
     * @param minTimestamp minimum timestamp for the query window
     * @param timestampIncrement timestamp increment for bucketing
     * @return time stats
     * @throws IllegalArgumentException if {@code org} or {@code timestampIncrement} is blank
     * @throws NullPointerException if {@code actorType} or {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubApiInsightsTimeStat> getTimeStatsByActor(
            final String org,
            final GitHubApiInsightsActorType actorType,
            final long actorId,
            final Instant minTimestamp,
            final String timestampIncrement)
            throws IOException {
        return getTimeStatsByActor(
                org, actorType, actorId, GitHubApiInsightsTimeStatsFilter.of(minTimestamp, timestampIncrement));
    }

    /**
     * Get time stats by actor ({@code GET /orgs/{org}/insights/api/time-stats/{actor_type}/{actor_id}}).
     *
     * @param org organization name
     * @param actorType actor type path segment
     * @param actorId actor id
     * @param filter query filters
     * @return time stats
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code actorType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubApiInsightsTimeStat> getTimeStatsByActor(
            final String org,
            final GitHubApiInsightsActorType actorType,
            final long actorId,
            final GitHubApiInsightsTimeStatsFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(actorType, PARAM_ACTOR_TYPE);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(TIME_STATS_ACTOR, org, actorType.wireValue(), actorId).withQuery(filter.toQueryParameters()),
                TIME_STATS_TYPE);
    }

    /**
     * Get user stats ({@code GET /orgs/{org}/insights/api/user-stats/{user_id}}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and {@link GitHubApiInsightsUserStatsFilter#of(Instant)}. See
     * {@link #getUserStats(String, String, GitHubApiInsightsUserStatsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param userId user id
     * @param minTimestamp minimum timestamp for the query window
     * @return first HTTP page of user stats
     * @throws IllegalArgumentException if {@code org} or {@code userId} is blank
     * @throws NullPointerException if {@code minTimestamp} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsUserStat> getUserStats(
            final String org, final String userId, final Instant minTimestamp) throws IOException {
        return getUserStats(org, userId, GitHubApiInsightsUserStatsFilter.of(minTimestamp));
    }

    /**
     * Get user stats ({@code GET /orgs/{org}/insights/api/user-stats/{user_id}}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #getUserStats(String, String,
     * GitHubApiInsightsUserStatsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param userId user id
     * @param filter query filters
     * @return first HTTP page of user stats
     * @throws IllegalArgumentException if {@code org} or {@code userId} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsUserStat> getUserStats(
            final String org, final String userId, final GitHubApiInsightsUserStatsFilter filter) throws IOException {
        return getUserStats(org, userId, filter, GitHubPageRequest.byPage());
    }

    /**
     * Get user stats ({@code GET /orgs/{org}/insights/api/user-stats/{user_id}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param userId user id
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of user stats
     * @throws IllegalArgumentException if {@code org} or {@code userId} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubApiInsightsUserStat> getUserStats(
            final String org,
            final String userId,
            final GitHubApiInsightsUserStatsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_STATS, filter.toQueryParameters(), request, USER_STATS_TYPE, org, userId);
    }
}
