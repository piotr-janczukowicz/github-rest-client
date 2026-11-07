package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReopened())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReopened())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReopened())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopened value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReopened())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
