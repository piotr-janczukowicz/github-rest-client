package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubMetricsStatisticsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMetricsStatisticsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getCodeFrequencyStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-frequency-stats.json")));

        final Optional<GitHubCodeFrequencyStats> stats = api().getCodeFrequencyStats(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).contains(MetricsCatalogFixtures.codeFrequencyStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stats/code_frequency");
        });
    }

    @Test
    void getCodeFrequencyStats_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        final Optional<GitHubCodeFrequencyStats> stats = api().getCodeFrequencyStats(OWNER, REPO);

        assertThat(stats).isEmpty();
    }

    @Test
    void getCommitActivityStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("commit-activity.json") + "]"));

        final List<GitHubCommitActivity> stats = api().getCommitActivityStats(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).containsExactly(MetricsCatalogFixtures.commitActivity());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stats/commit_activity");
        });
    }

    @Test
    void getCommitActivityStats_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final List<GitHubCommitActivity> stats = api().getCommitActivityStats(OWNER, REPO);

        assertThat(stats).isEmpty();
    }

    @Test
    void getContributorsStats_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        final List<GitHubContributorActivity> stats = api().getContributorsStats(OWNER, REPO);

        assertThat(stats).isEmpty();
    }

    @Test
    void getContributorsStats_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final List<GitHubContributorActivity> stats = api().getContributorsStats(OWNER, REPO);

        assertThat(stats).isEmpty();
    }

    @Test
    void getParticipationStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("participation-stats.json")));

        final GitHubParticipationStats stats = api().getParticipationStats(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).isEqualTo(MetricsCatalogFixtures.participationStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stats/participation");
        });
    }

    @Test
    void getParticipationStats_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubMetricsStatisticsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getParticipationStats(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getPunchCardStats_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubPunchCardStats> stats = api().getPunchCardStats(OWNER, REPO);

        assertThat(stats).isEmpty();
    }

    @Test
    void getCodeFrequencyStats_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubCodeFrequencyStats> stats = api().getCodeFrequencyStats(OWNER, REPO);

        assertThat(stats).isEmpty();
    }

    @Test
    void getCommitActivityStats_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        final List<GitHubCommitActivity> stats = api().getCommitActivityStats(OWNER, REPO);

        assertThat(stats).isEmpty();
    }

    @Test
    void getContributorsStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("contributor-activity.json") + "]"));

        final List<GitHubContributorActivity> stats = api().getContributorsStats(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).containsExactly(MetricsCatalogFixtures.contributorActivity());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stats/contributors");
        });
    }

    @Test
    void getPunchCardStats_shouldReturnStats_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("punch-card-stats.json")));

        final Optional<GitHubPunchCardStats> stats = api().getPunchCardStats(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stats).contains(MetricsCatalogFixtures.punchCardStats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stats/punch_card");
        });
    }

    @Test
    void getCodeFrequencyStats_shouldThrow_whenOwnerBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsStatisticsApi statisticsApi = new GitHubMetricsStatisticsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> statisticsApi.getCodeFrequencyStats(" ", REPO));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("owner");
        }
    }

    @Test
    void getCodeFrequencyStats_shouldThrow_whenRepoBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsStatisticsApi statisticsApi = new GitHubMetricsStatisticsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> statisticsApi.getCodeFrequencyStats(OWNER, " "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repo");
        }
    }

    private GitHubMetricsStatisticsApi api() {
        return new GitHubMetricsStatisticsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubMetricsStatisticsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
