package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubEditRepoAutomationTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubEditRepoAutomationTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceDuplicated() {
        final GitHubEditRepoAutomationTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersInterval())
                        .cadence(List.of(
                                GitHubEditRepoAutomationTriggersIntervalCadence.HOURLY,
                                GitHubEditRepoAutomationTriggersIntervalCadence.HOURLY));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcBelowMinimum() {
        final GitHubEditRepoAutomationTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcAboveMaximum() {
        final GitHubEditRepoAutomationTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekBelowMinimum() {
        final GitHubEditRepoAutomationTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekAboveMaximum() {
        final GitHubEditRepoAutomationTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
