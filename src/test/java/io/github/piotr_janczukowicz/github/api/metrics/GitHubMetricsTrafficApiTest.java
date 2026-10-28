package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubMetricsTrafficApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMetricsTrafficApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getClones_shouldReturnTraffic_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("clone-traffic.json")));

        final GitHubCloneTraffic traffic = api().getClones(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(traffic).isEqualTo(MetricsCatalogFixtures.cloneTraffic());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/traffic/clones");
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void getClones_shouldSendPerFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("clone-traffic.json")));

        api().getClones(
                        OWNER,
                        REPO,
                        GitHubGetClonesFilterBuilder.builder()
                                .per(GitHubTrafficPer.WEEK)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("per", "week");
    }

    @Test
    void getViews_shouldReturnTraffic_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("view-traffic.json")));

        final GitHubViewTraffic traffic = api().getViews(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(traffic).isEqualTo(MetricsCatalogFixtures.viewTraffic());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/traffic/views");
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void getViews_shouldSendPerFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("view-traffic.json")));

        api().getViews(
                        OWNER,
                        REPO,
                        GitHubGetClonesFilterBuilder.builder()
                                .per(GitHubTrafficPer.WEEK)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("per", "week");
    }

    @Test
    void getClones_shouldThrow_whenFilterNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsTrafficApi trafficApi = new GitHubMetricsTrafficApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> trafficApi.getClones(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    @Test
    void getClones_shouldThrow_whenOwnerBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsTrafficApi trafficApi = new GitHubMetricsTrafficApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> trafficApi.getClones(" ", REPO));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("owner");
        }
    }

    @Test
    void getClones_shouldThrow_whenRepoBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsTrafficApi trafficApi = new GitHubMetricsTrafficApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> trafficApi.getClones(OWNER, " "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repo");
        }
    }

    @Test
    void getTopPaths_shouldReturnPaths_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("content-traffic.json") + "]"));

        final List<GitHubContentTraffic> paths = api().getTopPaths(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(paths).containsExactly(MetricsCatalogFixtures.contentTraffic());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/traffic/popular/paths");
        });
    }

    @Test
    void getTopReferrers_shouldReturnReferrers_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("referrer-traffic.json") + "]"));

        final List<GitHubReferrerTraffic> referrers = api().getTopReferrers(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(referrers).containsExactly(MetricsCatalogFixtures.referrerTraffic());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/traffic/popular/referrers");
        });
    }

    private GitHubMetricsTrafficApi api() {
        return new GitHubMetricsTrafficApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubMetricsTrafficApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
