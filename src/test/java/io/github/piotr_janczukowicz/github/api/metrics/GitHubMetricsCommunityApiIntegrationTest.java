package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.metricsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubMetricsCommunityApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private static final String COMMUNITY_PROFILE_PATH = "/repos/octocat/Hello-World/community/profile";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getCommunityProfileMetrics_shouldReturnProfile_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMMUNITY_PROFILE_PATH,
                okJson(jsonBytes(GitHubMetricsCommunityApiIntegrationTest.class, "community-profile.json")));

        final GitHubCommunityProfile profile;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            profile = api.api().community().getCommunityProfileMetrics(OWNER, REPO);
        }

        assertThat(profile).isEqualTo(expectedCommunityProfile());
        HTTP.verifyRequested(GET, COMMUNITY_PROFILE_PATH);
    }

    @Test
    void getCommunityProfileMetrics_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMMUNITY_PROFILE_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.MetricsApiClient api = metricsApi(HTTP)) {
            final GitHubMetricsCommunityApi community = api.api().community();

            thrown = catchThrowable(() -> community.getCommunityProfileMetrics(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, COMMUNITY_PROFILE_PATH);
    }

    private static GitHubCommunityProfile expectedCommunityProfile() {
        return CODEC.decode(
                jsonBytes(GitHubMetricsCommunityApiIntegrationTest.class, "community-profile.json"),
                GitHubCommunityProfile.class);
    }
}
