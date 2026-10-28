package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Metrics REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubMetricsApi {

    private final LazyValue<GitHubMetricsCommunityApi> community;
    private final LazyValue<GitHubMetricsStatisticsApi> statistics;
    private final LazyValue<GitHubMetricsTrafficApi> traffic;

    /**
     * Metrics resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMetricsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.community = new LazyValue<>(() -> new GitHubMetricsCommunityApi(caller));
        this.statistics = new LazyValue<>(() -> new GitHubMetricsStatisticsApi(caller));
        this.traffic = new LazyValue<>(() -> new GitHubMetricsTrafficApi(caller));
    }

    /**
     * Community profile metrics operations ({@code subcategory=community}).
     *
     * @return community API
     */
    public GitHubMetricsCommunityApi community() {
        return community.get();
    }

    /**
     * Repository statistics operations ({@code subcategory=statistics}).
     *
     * @return statistics API
     */
    public GitHubMetricsStatisticsApi statistics() {
        return statistics.get();
    }

    /**
     * Repository traffic operations ({@code subcategory=traffic}).
     *
     * @return traffic API
     */
    public GitHubMetricsTrafficApi traffic() {
        return traffic.get();
    }
}
