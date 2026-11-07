package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationCompleted())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationCompleted())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationCompleted())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationCompleted())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }
}
