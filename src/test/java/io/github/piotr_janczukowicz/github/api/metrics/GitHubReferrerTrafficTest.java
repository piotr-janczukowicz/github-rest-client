package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReferrerTrafficTest {

    @Test
    void constructor_shouldStoreReferrer_whenCalled() {
        final GitHubReferrerTraffic traffic = new GitHubReferrerTraffic("Google", 4L, 3L);

        assertThat(traffic.referrer()).isEqualTo("Google");
    }

    @Test
    void constructor_shouldThrow_whenReferrerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReferrerTraffic(null, 4L, 3L));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("referrer");
    }
}
