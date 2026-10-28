package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubEditRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersInterval())
                        .cadence(List.of(
                                GitHubEditRepoAutomationResponseTriggersIntervalCadence.HOURLY,
                                GitHubEditRepoAutomationResponseTriggersIntervalCadence.HOURLY));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcBelowMinimum() {
        final GitHubEditRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcAboveMaximum() {
        final GitHubEditRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekBelowMinimum() {
        final GitHubEditRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekAboveMaximum() {
        final GitHubEditRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
