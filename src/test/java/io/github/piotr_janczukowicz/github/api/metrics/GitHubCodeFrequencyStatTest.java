package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeFrequencyStatTest {

    @Test
    void fromJson_shouldWrapValues_whenCalled() {
        final GitHubCodeFrequencyStat stat = GitHubCodeFrequencyStat.fromJson(List.of(1, 2, 3));

        assertThat(stat.values()).containsExactly(1, 2, 3);
    }

    @Test
    void constructor_shouldThrow_whenValuesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeFrequencyStat(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("values");
    }

    @Test
    void toJson_shouldReturnValues_whenCalled() {
        final GitHubCodeFrequencyStat stat = new GitHubCodeFrequencyStat(List.of(4, 5));

        assertThat(stat.toJson()).containsExactly(4, 5);
    }

    @Test
    void day_shouldThrow_whenListEmpty() {
        final GitHubCodeFrequencyStat stat = new GitHubCodeFrequencyStat(List.of());

        final Throwable thrown = catchThrowable(stat::day);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("day");
    }

    @Test
    void commits_shouldThrow_whenListTooShort() {
        final GitHubCodeFrequencyStat stat = new GitHubCodeFrequencyStat(List.of(1, 2));

        final Throwable thrown = catchThrowable(stat::commits);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commits");
    }

    @Test
    void accessors_shouldReturnCodeFrequencyTriple_whenTriplePresent() {
        final GitHubCodeFrequencyStat stat = new GitHubCodeFrequencyStat(List.of(10, 20, 30));

        assertSoftly(softly -> {
            softly.assertThat(stat.week()).isEqualTo(10);
            softly.assertThat(stat.additions()).isEqualTo(20);
            softly.assertThat(stat.deletions()).isEqualTo(30);
        });
    }

    @Test
    void accessors_shouldReturnPunchCardTriple_whenTriplePresent() {
        final GitHubCodeFrequencyStat stat = new GitHubCodeFrequencyStat(List.of(4, 5, 6));

        assertSoftly(softly -> {
            softly.assertThat(stat.day()).isEqualTo(4);
            softly.assertThat(stat.hour()).isEqualTo(5);
            softly.assertThat(stat.commits()).isEqualTo(6);
        });
    }
}
