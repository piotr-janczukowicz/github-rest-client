package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.metricsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubMetricsStatisticsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private static final String CODE_FREQUENCY_PATH = "/repos/octocat/Hello-World/stats/code_frequency";
    private static final String COMMIT_ACTIVITY_PATH = "/repos/octocat/Hello-World/stats/commit_activity";
    private static final String CONTRIBUTORS_PATH = "/repos/octocat/Hello-World/stats/contributors";
    private static final String PARTICIPATION_PATH = "/repos/octocat/Hello-World/stats/participation";
    private static final String PUNCH_CARD_PATH = "/repos/octocat/Hello-World/stats/punch_card";

    private static final JsonType<List<GitHubCommitActivity>> COMMIT_ACTIVITY_TYPE =
            JsonTypes.listOf(GitHubCommitActivity.class);
    private static final JsonType<List<GitHubContributorActivity>> CONTRIBUTORS_TYPE =
            JsonTypes.listOf(GitHubContributorActivity.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getCodeFrequencyStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CODE_FREQUENCY_PATH,
                okJson(jsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "code-frequency-stats.json")));

        final Optional<GitHubCodeFrequencyStats> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getCodeFrequencyStats(OWNER, REPO);
        }

        final GitHubCodeFrequencyStats expected = CODEC.decode(
                jsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "code-frequency-stats.json"),
                GitHubCodeFrequencyStats.class);

        assertThat(stats).contains(expected);
        HTTP.verifyRequested(GET, CODE_FREQUENCY_PATH);
    }

    @Test
    void getCodeFrequencyStats_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        HTTP.stub(GET, CODE_FREQUENCY_PATH, accepted());

        final Optional<GitHubCodeFrequencyStats> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getCodeFrequencyStats(OWNER, REPO);
        }

        assertThat(stats).isEmpty();
        HTTP.verifyRequested(GET, CODE_FREQUENCY_PATH);
    }

    @Test
    void getCodeFrequencyStats_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, CODE_FREQUENCY_PATH, noContent());

        final Optional<GitHubCodeFrequencyStats> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getCodeFrequencyStats(OWNER, REPO);
        }

        assertThat(stats).isEmpty();
        HTTP.verifyRequested(GET, CODE_FREQUENCY_PATH);
    }

    @Test
    void getCommitActivityStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMMIT_ACTIVITY_PATH,
                okJson(listJsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "commit-activity.json")));

        final List<GitHubCommitActivity> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getCommitActivityStats(OWNER, REPO);
        }

        final List<GitHubCommitActivity> expected = CODEC.decode(
                listJsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "commit-activity.json"),
                COMMIT_ACTIVITY_TYPE);

        assertThat(stats).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, COMMIT_ACTIVITY_PATH);
    }

    @Test
    void getCommitActivityStats_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        HTTP.stub(GET, COMMIT_ACTIVITY_PATH, accepted());

        final List<GitHubCommitActivity> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getCommitActivityStats(OWNER, REPO);
        }

        assertThat(stats).isEmpty();
        HTTP.verifyRequested(GET, COMMIT_ACTIVITY_PATH);
    }

    @Test
    void getContributorsStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CONTRIBUTORS_PATH,
                okJson(listJsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "contributor-activity.json")));

        final List<GitHubContributorActivity> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getContributorsStats(OWNER, REPO);
        }

        final List<GitHubContributorActivity> expected = CODEC.decode(
                listJsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "contributor-activity.json"),
                CONTRIBUTORS_TYPE);

        assertThat(stats).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, CONTRIBUTORS_PATH);
    }

    @Test
    void getContributorsStats_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, CONTRIBUTORS_PATH, noContent());

        final List<GitHubContributorActivity> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getContributorsStats(OWNER, REPO);
        }

        assertThat(stats).isEmpty();
        HTTP.verifyRequested(GET, CONTRIBUTORS_PATH);
    }

    @Test
    void getParticipationStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PARTICIPATION_PATH,
                okJson(jsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "participation-stats.json")));

        final GitHubParticipationStats stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getParticipationStats(OWNER, REPO);
        }

        final GitHubParticipationStats expected = CODEC.decode(
                jsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "participation-stats.json"),
                GitHubParticipationStats.class);

        assertThat(stats).isEqualTo(expected);
        HTTP.verifyRequested(GET, PARTICIPATION_PATH);
    }

    @Test
    void getParticipationStats_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, PARTICIPATION_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            final GitHubMetricsStatisticsApi statistics = api.api().statistics();

            thrown = catchThrowable(() -> statistics.getParticipationStats(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, PARTICIPATION_PATH);
    }

    @Test
    void getPunchCardStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PUNCH_CARD_PATH,
                okJson(jsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "code-frequency-stats.json")));

        final Optional<GitHubPunchCardStats> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getPunchCardStats(OWNER, REPO);
        }

        final GitHubPunchCardStats expected = CODEC.decode(
                jsonBytes(GitHubMetricsStatisticsApiIntegrationTest.class, "code-frequency-stats.json"),
                GitHubPunchCardStats.class);

        assertThat(stats).contains(expected);
        HTTP.verifyRequested(GET, PUNCH_CARD_PATH);
    }

    @Test
    void getPunchCardStats_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, PUNCH_CARD_PATH, noContent());

        final Optional<GitHubPunchCardStats> stats;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            stats = api.api().statistics().getPunchCardStats(OWNER, REPO);
        }

        assertThat(stats).isEmpty();
        HTTP.verifyRequested(GET, PUNCH_CARD_PATH);
    }
}
