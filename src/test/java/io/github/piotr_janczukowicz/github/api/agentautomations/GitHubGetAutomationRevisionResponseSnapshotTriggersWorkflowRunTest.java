package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunConclusion.FAILURE,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
