package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubGetAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubGetAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceDuplicated() {
        final GitHubGetAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersInterval())
                        .cadence(List.of(
                                GitHubGetAutomationResponseTriggersIntervalCadence.HOURLY,
                                GitHubGetAutomationResponseTriggersIntervalCadence.HOURLY));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcBelowMinimum() {
        final GitHubGetAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcAboveMaximum() {
        final GitHubGetAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekBelowMinimum() {
        final GitHubGetAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekAboveMaximum() {
        final GitHubGetAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
