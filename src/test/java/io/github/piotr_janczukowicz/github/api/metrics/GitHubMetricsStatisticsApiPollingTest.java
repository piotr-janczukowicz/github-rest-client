package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.client.HttpAcceptedPolling;
import io.github.piotr_janczukowicz.http.client.HttpComputed;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.io.InterruptedIOException;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMetricsStatisticsApiPollingTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final HttpAcceptedPolling NO_WAIT = new HttpAcceptedPolling(5, Duration.ZERO);

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void getCodeFrequencyStats_shouldReturnStatsAfterAccepted_whenDataBecomesReady() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), "{}"),
                        response(HttpStatus.ACCEPTED.code(), "{}"),
                        response(HttpStatus.OK.code(), json("code-frequency-stats.json")));

        final HttpComputed<GitHubCodeFrequencyStats> stats = api().getCodeFrequencyStats(OWNER, REPO, NO_WAIT);

        assertThat(stats).isInstanceOf(HttpComputed.Ready.class);
        verify(transport, times(3)).execute(any());
    }

    @Test
    void getCodeFrequencyStats_shouldReturnEmpty_whenStillAcceptedAfterLastAttempt() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));

        final HttpComputed<GitHubCodeFrequencyStats> stats =
                api().getCodeFrequencyStats(OWNER, REPO, new HttpAcceptedPolling(3, Duration.ZERO));

        assertThat(stats).isInstanceOf(HttpComputed.NotReady.class);
        verify(transport, times(3)).execute(any());
    }

    @Test
    void getCodeFrequencyStats_shouldWaitBetweenTries_whenDelayIsSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), "{}"),
                        response(HttpStatus.OK.code(), json("code-frequency-stats.json")));

        final HttpComputed<GitHubCodeFrequencyStats> stats =
                api().getCodeFrequencyStats(OWNER, REPO, new HttpAcceptedPolling(2, Duration.ofMillis(5)));

        assertThat(stats).isInstanceOf(HttpComputed.Ready.class);
        verify(transport, times(2)).execute(any());
    }

    @Test
    void getCodeFrequencyStats_shouldSendOnce_whenSingleAttempt() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));

        final HttpComputed<GitHubCodeFrequencyStats> stats =
                api().getCodeFrequencyStats(OWNER, REPO, new HttpAcceptedPolling(1, Duration.ofSeconds(30)));

        assertThat(stats).isInstanceOf(HttpComputed.NotReady.class);
        verify(transport, times(1)).execute(any());
    }

    @Test
    void getCodeFrequencyStats_shouldReportNoData_whenBodyIsEmptyObject() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "{}"));

        final HttpComputed<GitHubCodeFrequencyStats> stats = api().getCodeFrequencyStats(OWNER, REPO, NO_WAIT);

        assertThat(stats).isInstanceOf(HttpComputed.NoData.class);
    }

    @Test
    void getCodeFrequencyStats_shouldNotPoll_whenNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final HttpComputed<GitHubCodeFrequencyStats> stats = api().getCodeFrequencyStats(OWNER, REPO, NO_WAIT);

        assertThat(stats).isInstanceOf(HttpComputed.NoData.class);
        verify(transport, times(1)).execute(any());
    }

    @Test
    void getCodeFrequencyStats_shouldThrowWithoutPolling_whenResponseIsError() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubMetricsStatisticsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getCodeFrequencyStats(OWNER, REPO, NO_WAIT));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        verify(transport, times(1)).execute(any());
    }

    @Test
    void getCodeFrequencyStats_shouldThrowInterrupted_whenThreadIsInterruptedWhileWaiting() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));
        final HttpAcceptedPolling slow = new HttpAcceptedPolling(3, Duration.ofSeconds(30));

        Thread.currentThread().interrupt();
        final Throwable thrown;
        final boolean flagRestored;
        try {
            final GitHubMetricsStatisticsApi api = api();

            thrown = catchThrowable(() -> api.getCodeFrequencyStats(OWNER, REPO, slow));
            flagRestored = Thread.currentThread().isInterrupted();
        } finally {
            Thread.interrupted();
        }

        assertThat(thrown).isInstanceOf(InterruptedIOException.class);
        assertThat(flagRestored).isTrue();
        verify(transport, times(1)).execute(any());
    }

    @Test
    void getCommitActivityStats_shouldReturnStatsAfterAccepted_whenDataBecomesReady() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), "{}"),
                        response(HttpStatus.OK.code(), "[" + json("commit-activity.json") + "]"));

        final HttpComputed<List<GitHubCommitActivity>> stats = api().getCommitActivityStats(OWNER, REPO, NO_WAIT);

        assertThat(stats.toOptional())
                .hasValueSatisfying(list -> assertThat(list).hasSize(1));
        verify(transport, times(2)).execute(any());
    }

    @Test
    void getContributorsStats_shouldReturnStatsAfterAccepted_whenDataBecomesReady() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), "{}"),
                        response(HttpStatus.OK.code(), "[" + json("contributor-activity.json") + "]"));

        final HttpComputed<List<GitHubContributorActivity>> stats = api().getContributorsStats(OWNER, REPO, NO_WAIT);

        assertThat(stats.toOptional())
                .hasValueSatisfying(list -> assertThat(list).hasSize(1));
        verify(transport, times(2)).execute(any());
    }

    @Test
    void methods_shouldThrowNullPointer_whenPollingNull() {
        final GitHubMetricsStatisticsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getCodeFrequencyStats(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("polling");
        assertThat(catchThrowable(() -> api.getCommitActivityStats(OWNER, REPO, null)))
                .isInstanceOf(NullPointerException.class);
        assertThat(catchThrowable(() -> api.getContributorsStats(OWNER, REPO, null)))
                .isInstanceOf(NullPointerException.class);
    }

    private GitHubMetricsStatisticsApi api() {
        return new GitHubMetricsStatisticsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubMetricsStatisticsApiTest.class, fileName);
    }
}
