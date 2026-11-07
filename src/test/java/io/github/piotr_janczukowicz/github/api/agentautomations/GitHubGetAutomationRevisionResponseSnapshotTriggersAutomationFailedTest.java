package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailed value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedReason.REPORTED))
                        .build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailed value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }
}
