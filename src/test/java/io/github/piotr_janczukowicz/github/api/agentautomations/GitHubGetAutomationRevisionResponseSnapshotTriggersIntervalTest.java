package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshotTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshotTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshotTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshotTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshotTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshotTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldAccept_whenCadenceRepeat() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersInterval value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationRevisionResponseSnapshotTriggersInterval())
                        .cadence(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalCadence.HOURLY,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersIntervalCadence.HOURLY))
                        .build();

        assertThat(value.cadence()).hasSize(2);
    }
}
