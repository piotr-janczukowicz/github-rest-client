package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubStargazerHistoryTest {

    @Test
    void constructor_shouldThrow_whenDaysNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubStargazerHistory(null, 19, 1_754_784_000L));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("days");
    }
}
