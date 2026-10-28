package io.github.piotr_janczukowicz.github.api.metrics;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.client.HttpAcceptedPolling;
import io.github.piotr_janczukowicz.http.client.HttpComputed;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub repository statistics REST resource for API version {@code 2026-03-10}. */
public final class GitHubMetricsStatisticsApi extends GitHubResourceApi {

    private static final PathTemplate STATS_PUNCH_CARD = PathTemplate.of("/repos/{owner}/{repo}/stats/punch_card");
    private static final PathTemplate STATS_PARTICIPATION =
            PathTemplate.of("/repos/{owner}/{repo}/stats/participation");
    private static final PathTemplate STATS_CONTRIBUTORS = PathTemplate.of("/repos/{owner}/{repo}/stats/contributors");
    private static final PathTemplate STATS_COMMIT_ACTIVITY =
            PathTemplate.of("/repos/{owner}/{repo}/stats/commit_activity");
    private static final PathTemplate STATS_CODE_FREQUENCY =
            PathTemplate.of("/repos/{owner}/{repo}/stats/code_frequency");

    private static final JsonType<List<GitHubCommitActivity>> COMMIT_ACTIVITY_TYPE =
            JsonTypes.listOf(GitHubCommitActivity.class);
    private static final JsonType<List<GitHubContributorActivity>> CONTRIBUTORS_TYPE =
            JsonTypes.listOf(GitHubContributorActivity.class);

    /**
     * Statistics resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMetricsStatisticsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get code frequency stats ({@code GET /repos/{owner}/{repo}/stats/code_frequency}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return weekly code frequency stats, or empty while computing (HTTP 202), when empty (HTTP 204), or no body or
     *     {@code {}}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeFrequencyStats> getCodeFrequencyStats(final String owner, final String repo)
            throws IOException {

        return caller.fetchUnless(
                get(STATS_CODE_FREQUENCY, owner, repo),
                GitHubCodeFrequencyStats.class,
                HttpStatus.ACCEPTED,
                HttpStatus.NO_CONTENT);
    }

    /**
     * Get code frequency stats ({@code GET /repos/{owner}/{repo}/stats/code_frequency}), waiting while GitHub is still
     * computing the statistics (HTTP 202), sending the request again as {@code polling} says.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param polling number of tries and the pause between them; see {@link HttpAcceptedPolling#defaults()}
     * @return weekly code frequency stats, {@link HttpComputed.NotReady} when GitHub is still computing after the last
     *     try, or {@link HttpComputed.NoData} when it answers HTTP 204
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails, or the thread is interrupted while waiting
     * @throws GitHubApiException if the response is unsuccessful
     * @throws NullPointerException if {@code polling} is {@code null}
     */
    public HttpComputed<GitHubCodeFrequencyStats> getCodeFrequencyStats(
            final String owner, final String repo, final HttpAcceptedPolling polling) throws IOException {
        return caller.fetchWhenReady(get(STATS_CODE_FREQUENCY, owner, repo), GitHubCodeFrequencyStats.class, polling);
    }

    /**
     * Get commit activity stats ({@code GET /repos/{owner}/{repo}/stats/commit_activity}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return weekly commit activity, or an empty list while computing (HTTP 202), when empty (HTTP 204), or no body or
     *     {@code {}}; use the polling overload to tell these cases apart
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubCommitActivity> getCommitActivityStats(final String owner, final String repo) throws IOException {

        return caller.fetchUnless(
                        get(STATS_COMMIT_ACTIVITY, owner, repo),
                        COMMIT_ACTIVITY_TYPE,
                        HttpStatus.ACCEPTED,
                        HttpStatus.NO_CONTENT)
                .orElseGet(List::of);
    }

    /**
     * Get commit activity stats ({@code GET /repos/{owner}/{repo}/stats/commit_activity}), waiting while GitHub is
     * still computing the statistics (HTTP 202), sending the request again as {@code polling} says.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param polling number of tries and the pause between them; see {@link HttpAcceptedPolling#defaults()}
     * @return weekly commit activity, {@link HttpComputed.NotReady} when GitHub is still computing after the last try,
     *     or {@link HttpComputed.NoData} when it answers HTTP 204
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails, or the thread is interrupted while waiting
     * @throws GitHubApiException if the response is unsuccessful
     * @throws NullPointerException if {@code polling} is {@code null}
     */
    public HttpComputed<List<GitHubCommitActivity>> getCommitActivityStats(
            final String owner, final String repo, final HttpAcceptedPolling polling) throws IOException {
        return caller.fetchWhenReady(get(STATS_COMMIT_ACTIVITY, owner, repo), COMMIT_ACTIVITY_TYPE, polling);
    }

    /**
     * Get contributors stats ({@code GET /repos/{owner}/{repo}/stats/contributors}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return contributor activity, or an empty list while computing (HTTP 202), when empty (HTTP 204), or no body or
     *     {@code {}}; use the polling overload to tell these cases apart
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubContributorActivity> getContributorsStats(final String owner, final String repo)
            throws IOException {

        return caller.fetchUnless(
                        get(STATS_CONTRIBUTORS, owner, repo),
                        CONTRIBUTORS_TYPE,
                        HttpStatus.ACCEPTED,
                        HttpStatus.NO_CONTENT)
                .orElseGet(List::of);
    }

    /**
     * Get contributors stats ({@code GET /repos/{owner}/{repo}/stats/contributors}), waiting while GitHub is still
     * computing the statistics (HTTP 202), sending the request again as {@code polling} says.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param polling number of tries and the pause between them; see {@link HttpAcceptedPolling#defaults()}
     * @return contributor activity, {@link HttpComputed.NotReady} when GitHub is still computing after the last try, or
     *     {@link HttpComputed.NoData} when it answers HTTP 204
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails, or the thread is interrupted while waiting
     * @throws GitHubApiException if the response is unsuccessful
     * @throws NullPointerException if {@code polling} is {@code null}
     */
    public HttpComputed<List<GitHubContributorActivity>> getContributorsStats(
            final String owner, final String repo, final HttpAcceptedPolling polling) throws IOException {
        return caller.fetchWhenReady(get(STATS_CONTRIBUTORS, owner, repo), CONTRIBUTORS_TYPE, polling);
    }

    /**
     * Get participation stats ({@code GET /repos/{owner}/{repo}/stats/participation}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return participation stats
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubParticipationStats getParticipationStats(final String owner, final String repo) throws IOException {

        return caller.fetch(get(STATS_PARTICIPATION, owner, repo), GitHubParticipationStats.class);
    }

    /**
     * Get punch card stats ({@code GET /repos/{owner}/{repo}/stats/punch_card}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return punch card stats, or empty when empty (HTTP 204) or no body or {@code {}}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubPunchCardStats> getPunchCardStats(final String owner, final String repo) throws IOException {
        return caller.fetchUnless(
                get(STATS_PUNCH_CARD, owner, repo), GitHubPunchCardStats.class, HttpStatus.NO_CONTENT);
    }
}
