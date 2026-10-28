package io.github.piotr_janczukowicz.github.api.ratelimit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRateLimitOverviewTest {

    @Test
    void constructor_shouldThrow_whenResourcesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRateLimitOverview(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("resources");
    }
}
