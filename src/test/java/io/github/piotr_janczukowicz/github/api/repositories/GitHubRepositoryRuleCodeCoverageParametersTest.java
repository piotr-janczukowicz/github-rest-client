package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleCodeCoverageParametersTest {

    @Test
    void constructor_shouldThrow_whenMaxCoverageDropBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleCodeCoverageParameters(-0.1, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxCoverageDrop");
    }

    @Test
    void constructor_shouldThrow_whenMaxCoverageDropAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleCodeCoverageParameters(100.1, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxCoverageDrop");
    }

    @Test
    void constructor_shouldThrow_whenMinimumCoverageBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleCodeCoverageParameters(null, -0.1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("minimumCoverage");
    }

    @Test
    void constructor_shouldThrow_whenMinimumCoverageAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryRuleCodeCoverageParameters(null, 100.1));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("minimumCoverage");
    }

    @Test
    void constructor_shouldAcceptValues_whenAtDumpBounds() {
        final GitHubRepositoryRuleCodeCoverageParameters lower =
                new GitHubRepositoryRuleCodeCoverageParameters(0.0, 0.0);
        final GitHubRepositoryRuleCodeCoverageParameters upper =
                new GitHubRepositoryRuleCodeCoverageParameters(100.0, 100.0);

        assertThat(lower.minimumCoverage()).isZero();
        assertThat(upper.maxCoverageDrop()).isEqualTo(100.0);
    }

    @Test
    void constructor_shouldLeaveFieldsNull_whenNull() {
        final GitHubRepositoryRuleCodeCoverageParameters parameters =
                new GitHubRepositoryRuleCodeCoverageParameters(null, null);

        assertThat(parameters.maxCoverageDrop()).isNull();
        assertThat(parameters.minimumCoverage()).isNull();
    }
}
