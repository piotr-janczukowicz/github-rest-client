package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMetricsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMetricsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void community_shouldReturnApi_whenConstructed() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsApi api = new GitHubMetricsApi(TestClients.caller(client));

            final GitHubMetricsCommunityApi community = api.community();

            assertThat(community).isNotNull();
        }
    }

    @Test
    void statistics_shouldReturnApi_whenConstructed() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsApi api = new GitHubMetricsApi(TestClients.caller(client));

            final GitHubMetricsStatisticsApi statistics = api.statistics();

            assertThat(statistics).isNotNull();
        }
    }

    @Test
    void traffic_shouldReturnApi_whenConstructed() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsApi api = new GitHubMetricsApi(TestClients.caller(client));

            final GitHubMetricsTrafficApi traffic = api.traffic();

            assertThat(traffic).isNotNull();
        }
    }
}
