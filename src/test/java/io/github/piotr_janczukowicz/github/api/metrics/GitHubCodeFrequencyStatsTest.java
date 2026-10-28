package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeFrequencyStatsTest {

    @Test
    void fromJson_shouldWrapStats_whenCalled() {
        final GitHubCodeFrequencyStats stats =
                GitHubCodeFrequencyStats.fromJson(List.of(new GitHubCodeFrequencyStat(List.of(1, 2, 3))));

        assertThat(stats.stats()).hasSize(1);
    }

    @Test
    void constructor_shouldThrow_whenStatsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeFrequencyStats(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("stats");
    }

    @Test
    void toJson_shouldReturnStats_whenCalled() {
        final GitHubCodeFrequencyStats stats =
                new GitHubCodeFrequencyStats(List.of(new GitHubCodeFrequencyStat(List.of(1, 2, 3))));

        assertThat(stats.toJson()).hasSize(1);
    }
}
