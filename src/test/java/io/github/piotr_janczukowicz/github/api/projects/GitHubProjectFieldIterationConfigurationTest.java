package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectFieldIterationConfigurationTest {

    @Test
    void constructor_shouldThrow_whenStartDateNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProjectFieldIterationConfiguration(null, 14, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("startDate");
    }

    @Test
    void constructor_shouldKeepNullIterations_whenAbsent() {
        final GitHubProjectFieldIterationConfiguration configuration =
                new GitHubProjectFieldIterationConfiguration("2022-04-28", 14, null);

        assertSoftly(softly -> {
            softly.assertThat(configuration.startDate()).isEqualTo("2022-04-28");
            softly.assertThat(configuration.duration()).isEqualTo(14);
            softly.assertThat(configuration.iterations()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyIterations_whenPresent() {
        final GitHubProjectFieldIterationConfigurationIteration iteration =
                new GitHubProjectFieldIterationConfigurationIteration("Sprint 1", "2022-04-28", 14);

        final GitHubProjectFieldIterationConfiguration configuration =
                new GitHubProjectFieldIterationConfiguration("2022-04-28", 14, List.of(iteration));

        assertThat(configuration.iterations()).containsExactly(iteration);
    }
}
