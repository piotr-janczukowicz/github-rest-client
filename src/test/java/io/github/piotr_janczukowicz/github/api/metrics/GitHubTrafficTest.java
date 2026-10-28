package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTrafficTest {

    @Test
    void constructor_shouldThrow_whenTimestampNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTraffic(null, 18L, 23L));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timestamp");
    }
}
