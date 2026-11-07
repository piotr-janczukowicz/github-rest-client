package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldAccept_whenCadenceRepeat() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersInterval value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersInterval())
                        .cadence(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalCadence.HOURLY,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersIntervalCadence.HOURLY))
                        .build();

        assertThat(value.cadence()).hasSize(2);
    }
}
