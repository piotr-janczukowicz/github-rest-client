package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotRunnerSettingsTest {

    @Test
    void constructor_shouldThrow_whenRunnerTypeNull() {
        final GitHubDependabotRunnerSettingsBuilder builder = GitHubDependabotRunnerSettingsBuilder.builder(
                        DependabotCatalogFixtures.dependabotRunnerSettings())
                .runnerType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerType");
    }

    @Test
    void constructor_shouldThrow_whenRunnerLabelsNull() {
        final GitHubDependabotRunnerSettingsBuilder builder = GitHubDependabotRunnerSettingsBuilder.builder(
                        DependabotCatalogFixtures.dependabotRunnerSettings())
                .runnerLabels(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerLabels");
    }
}
