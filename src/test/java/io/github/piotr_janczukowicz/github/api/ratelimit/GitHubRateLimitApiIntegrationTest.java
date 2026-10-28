package io.github.piotr_janczukowicz.github.api.ratelimit;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.rateLimitApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRateLimitApiIntegrationTest {

    private static final String RATE_LIMIT_PATH = "/rate_limit";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void get_shouldReturnOverview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RATE_LIMIT_PATH,
                okJson(jsonBytes(GitHubRateLimitApiIntegrationTest.class, "rate-limit-overview.json")));

        final Optional<GitHubRateLimitOverview> overview;
        try (final GitHubApiWireMockSupport.RateLimitApiClient rateLimit = rateLimitApi(HTTP)) {
            overview = rateLimit.api().get();
        }

        final GitHubRateLimitOverview expected = CODEC.decode(
                jsonBytes(GitHubRateLimitApiIntegrationTest.class, "rate-limit-overview.json"),
                GitHubRateLimitOverview.class);

        assertThat(overview).contains(expected);
        HTTP.verifyRequested(GET, RATE_LIMIT_PATH);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, RATE_LIMIT_PATH, notFound());

        final Optional<GitHubRateLimitOverview> overview;
        try (final GitHubApiWireMockSupport.RateLimitApiClient rateLimit = rateLimitApi(HTTP)) {
            overview = rateLimit.api().get();
        }

        assertThat(overview).isEmpty();
        HTTP.verifyRequested(GET, RATE_LIMIT_PATH);
    }
}
