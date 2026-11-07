package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestLabeled())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestLabeled())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestLabeled())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeled value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestLabeled())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeled value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestLabeled())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }
}
