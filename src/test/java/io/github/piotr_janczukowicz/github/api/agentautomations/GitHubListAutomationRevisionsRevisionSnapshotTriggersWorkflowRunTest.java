package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunConclusion.FAILURE,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
