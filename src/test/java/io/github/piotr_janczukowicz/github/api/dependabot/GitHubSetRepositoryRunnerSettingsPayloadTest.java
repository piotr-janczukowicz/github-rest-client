package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetRepositoryRunnerSettingsPayloadTest {

    @Test
    void constructor_shouldThrow_whenRunnerTypeNull() {
        final GitHubSetRepositoryRunnerSettingsPayloadBuilder builder =
                GitHubSetRepositoryRunnerSettingsPayloadBuilder.builder(
                                DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload())
                        .runnerType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("runnerType");
    }

    @Test
    void constructor_shouldThrow_whenRunnerLabelsTooSmall() {
        final GitHubSetRepositoryRunnerSettingsPayloadBuilder builder =
                GitHubSetRepositoryRunnerSettingsPayloadBuilder.builder(
                                DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload())
                        .runnerLabels(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("runnerLabels");
    }

    @Test
    void constructor_shouldThrow_whenRunnerLabelsTooLarge() {
        final GitHubSetRepositoryRunnerSettingsPayloadBuilder builder =
                GitHubSetRepositoryRunnerSettingsPayloadBuilder.builder(
                                DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload())
                        .runnerLabels(List.of("runner_labels", "runner_labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("runnerLabels");
    }

    @Test
    void constructor_shouldAcceptNull_whenRunnerLabelsNull() {
        final GitHubSetRepositoryRunnerSettingsPayload value = GitHubSetRepositoryRunnerSettingsPayloadBuilder.builder(
                        DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload())
                .runnerLabels(null)
                .build();

        assertThat(value.runnerLabels()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenRunnerLabelsGiven() {
        final List<String> source = new ArrayList<>(List.of("runner_labels"));
        final GitHubSetRepositoryRunnerSettingsPayload value = GitHubSetRepositoryRunnerSettingsPayloadBuilder.builder(
                        DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload())
                .runnerLabels(source)
                .build();

        source.add("runner_labels");

        assertThat(value.runnerLabels()).hasSize(source.size() - 1);
    }
}
