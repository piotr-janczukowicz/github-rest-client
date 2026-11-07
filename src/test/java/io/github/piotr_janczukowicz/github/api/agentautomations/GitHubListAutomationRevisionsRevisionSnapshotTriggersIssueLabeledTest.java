package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeledBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeledBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersIssueLabeled())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeled value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersIssueLabeled())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }
}
