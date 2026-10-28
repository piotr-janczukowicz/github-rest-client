package io.github.piotr_janczukowicz.github.api.ratelimit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRateLimitResourcesTest {

    private static final GitHubRateLimit RATE_LIMIT = RatelimitCatalogFixtures.rateLimit();

    @Test
    void constructor_shouldThrow_whenCoreNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRateLimitResources(
                null, RATE_LIMIT, null, null, null, null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("core");
    }

    @Test
    void constructor_shouldThrow_whenSearchNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRateLimitResources(
                RATE_LIMIT, null, null, null, null, null, null, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("search");
    }

    @Test
    void decode_shouldReadResetBeyond2038_whenResetIsALong() {
        final byte[] json =
                "{\"limit\":5000,\"remaining\":4999,\"reset\":4102444800,\"used\":1}".getBytes(StandardCharsets.UTF_8);

        final GitHubRateLimit limit = GitHubJsonCodecs.defaults().decode(json, GitHubRateLimit.class);

        assertThat(limit.reset()).isEqualTo(4_102_444_800L);
    }

    @Test
    void decode_shouldIgnoreFamiliesTheModelDoesNotKnow_whenRateLimitOverviewHasMore() {
        final String json = "{\"resources\":{"
                + "\"core\":{\"limit\":5000,\"remaining\":4999,\"reset\":1590701888,\"used\":1},"
                + "\"search\":{\"limit\":30,\"remaining\":29,\"reset\":1590701888,\"used\":1},"
                + "\"graphql\":{\"limit\":5000,\"remaining\":4000,\"reset\":1590701888,\"used\":1000},"
                + "\"code_scanning_upload\":{\"limit\":1000,\"remaining\":999,\"reset\":1590701888,\"used\":1},"
                + "\"audit_log\":{\"limit\":1750,\"remaining\":1750,\"reset\":1590701888,\"used\":0},"
                + "\"audit_log_streaming\":{\"limit\":15,\"remaining\":15,\"reset\":1590701888,\"used\":0}"
                + "},\"rate\":{\"limit\":5000,\"remaining\":4999,\"reset\":1590701888,\"used\":1}}";

        final GitHubRateLimitOverview overview = GitHubJsonCodecs.defaults()
                .decode(json.getBytes(StandardCharsets.UTF_8), GitHubRateLimitOverview.class);

        assertThat(overview).isEqualTo(RatelimitCatalogFixtures.rateLimitOverviewWithoutCodeSearch());
    }
}
