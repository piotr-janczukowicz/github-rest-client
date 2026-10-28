package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCvssScoreTest {

    @Test
    void constructor_shouldThrow_whenScoreBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubCvssScore(null, -0.1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("score");
    }

    @Test
    void constructor_shouldThrow_whenScoreAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubCvssScore(null, 10.1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("score");
    }

    @Test
    void constructor_shouldAcceptNullScore_whenScoreNull() {
        final GitHubCvssScore score = new GitHubCvssScore("vector", null);

        assertThat(score.score()).isNull();
    }

    @Test
    void constructor_shouldAcceptScore_whenScoreAtMinimum() {
        final GitHubCvssScore score = new GitHubCvssScore(null, 0.0);

        assertThat(score.score()).isEqualTo(0.0);
    }

    @Test
    void constructor_shouldAcceptScore_whenScoreAtMaximum() {
        final GitHubCvssScore score = new GitHubCvssScore(null, 10.0);

        assertThat(score.score()).isEqualTo(10.0);
    }
}
