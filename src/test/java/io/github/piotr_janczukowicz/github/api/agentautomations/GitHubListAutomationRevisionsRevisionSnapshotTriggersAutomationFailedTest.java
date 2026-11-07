package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailed value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .reasons(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedReason.REPORTED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedReason.REPORTED))
                        .build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailed value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersAutomationFailed())
                        .reasons(null)
                        .build();

        assertThat(value.reasons()).isNull();
    }
}
