package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPunchCardStatsTest {

    @Test
    void fromJson_shouldCopyStats_whenPresent() {
        final GitHubPunchCardStats stats =
                GitHubPunchCardStats.fromJson(List.of(new GitHubCodeFrequencyStat(List.of(0, 0, 1))));

        assertThat(stats.stats()).hasSize(1);
    }

    @Test
    void constructor_shouldThrow_whenStatsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPunchCardStats(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("stats");
    }

    @Test
    void toJson_shouldReturnStats_whenCalled() {
        final GitHubPunchCardStats stats =
                new GitHubPunchCardStats(List.of(new GitHubCodeFrequencyStat(List.of(0, 0, 1))));

        assertThat(stats.toJson()).hasSize(1);
    }
}
