package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubJobStepTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenStatusNull() {
        final String conclusion = "conclusion-sample";
        final String name = "name-sample";
        final int number = 1;
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown =
                catchThrowable(() -> new GitHubJobStep(null, conclusion, name, number, startedAt, completedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final GitHubJobStepStatus status = GitHubJobStepStatus.QUEUED;
        final String conclusion = "conclusion-sample";
        final int number = 1;
        final Instant startedAt = Instant.parse("2024-01-02T03:04:05Z");
        final Instant completedAt = Instant.parse("2024-01-02T03:04:05Z");

        final Throwable thrown =
                catchThrowable(() -> new GitHubJobStep(status, conclusion, null, number, startedAt, completedAt));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
