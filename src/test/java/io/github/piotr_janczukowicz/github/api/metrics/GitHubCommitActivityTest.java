package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommitActivityTest {

    @Test
    void constructor_shouldCopyDays_whenPresent() {
        final GitHubCommitActivity activity = new GitHubCommitActivity(List.of(1, 2), 3L, 4L);

        assertThat(activity.days()).containsExactly(1, 2);
    }

    @Test
    void constructor_shouldThrow_whenDaysNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitActivity(null, 3L, 4L));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("days");
    }
}
