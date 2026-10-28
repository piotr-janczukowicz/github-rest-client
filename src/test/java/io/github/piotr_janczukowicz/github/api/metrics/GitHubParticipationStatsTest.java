package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubParticipationStatsTest {

    @Test
    void constructor_shouldCopyLists_whenPresent() {
        final GitHubParticipationStats stats = new GitHubParticipationStats(List.of(1), List.of(2));

        assertSoftly(softly -> {
            softly.assertThat(stats.all()).containsExactly(1);
            softly.assertThat(stats.owner()).containsExactly(2);
        });
    }

    @Test
    void constructor_shouldThrow_whenAllNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubParticipationStats(null, List.of(2)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("all");
    }

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubParticipationStats(List.of(1), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }
}
