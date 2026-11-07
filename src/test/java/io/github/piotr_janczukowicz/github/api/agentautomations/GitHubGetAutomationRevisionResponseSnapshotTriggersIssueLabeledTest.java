package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIssueLabeledBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIssueLabeledBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersIssueLabeled())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIssueLabeled value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersIssueLabeled())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }
}
