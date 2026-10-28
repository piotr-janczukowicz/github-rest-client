package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubMetricsCommunityApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMetricsCommunityApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getCommunityProfileMetrics_shouldReturnProfile_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("community-profile.json")));

        final GitHubCommunityProfile profile = api().getCommunityProfileMetrics(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(profile).isEqualTo(MetricsCatalogFixtures.communityProfile());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/community/profile");
        });
    }

    @Test
    void getCommunityProfileMetrics_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubMetricsCommunityApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getCommunityProfileMetrics(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void getCommunityProfileMetrics_shouldThrow_whenOwnerBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsCommunityApi communityApi = new GitHubMetricsCommunityApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> communityApi.getCommunityProfileMetrics(" ", REPO));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("owner");
        }
    }

    @Test
    void getCommunityProfileMetrics_shouldThrow_whenRepoBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubMetricsCommunityApi communityApi = new GitHubMetricsCommunityApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> communityApi.getCommunityProfileMetrics(OWNER, " "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repo");
        }
    }

    private GitHubMetricsCommunityApi api() {
        return new GitHubMetricsCommunityApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubMetricsCommunityApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
