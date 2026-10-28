package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectFieldIterationConfigurationIterationTest {

    @Test
    void constructor_shouldKeepComponents_whenCalled() {
        final GitHubProjectFieldIterationConfigurationIteration iteration =
                new GitHubProjectFieldIterationConfigurationIteration("Sprint 1", "2022-04-28", 14);

        assertSoftly(softly -> {
            softly.assertThat(iteration.title()).isEqualTo("Sprint 1");
            softly.assertThat(iteration.startDate()).isEqualTo("2022-04-28");
            softly.assertThat(iteration.duration()).isEqualTo(14);
        });
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubProjectFieldIterationConfigurationIteration(null, "2022-04-28", 14));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenStartDateNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubProjectFieldIterationConfigurationIteration("Sprint 1", null, 14));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("startDate");
    }
}
