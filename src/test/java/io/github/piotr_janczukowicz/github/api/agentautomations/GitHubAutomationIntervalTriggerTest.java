package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationIntervalTriggerTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubAutomationIntervalTriggerBuilder builder = GitHubAutomationIntervalTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIntervalTrigger())
                .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubAutomationIntervalTriggerBuilder builder = GitHubAutomationIntervalTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIntervalTrigger())
                .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldAccept_whenCadenceRepeat() {
        final GitHubAutomationIntervalTriggerBuilder builder = GitHubAutomationIntervalTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIntervalTrigger())
                .cadence(List.of(GitHubAutomationIntervalCadence.HOURLY, GitHubAutomationIntervalCadence.HOURLY));

        final GitHubAutomationIntervalTrigger value = builder.build();

        assertThat(value.cadence()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooSmall() {
        final GitHubAutomationIntervalTriggerBuilder builder = GitHubAutomationIntervalTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIntervalTrigger())
                .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooLarge() {
        final GitHubAutomationIntervalTriggerBuilder builder = GitHubAutomationIntervalTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIntervalTrigger())
                .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooSmall() {
        final GitHubAutomationIntervalTriggerBuilder builder = GitHubAutomationIntervalTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIntervalTrigger())
                .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooLarge() {
        final GitHubAutomationIntervalTriggerBuilder builder = GitHubAutomationIntervalTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIntervalTrigger())
                .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
