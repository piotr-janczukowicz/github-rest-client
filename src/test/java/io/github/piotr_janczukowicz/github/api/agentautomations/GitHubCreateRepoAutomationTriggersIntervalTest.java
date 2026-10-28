package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubCreateRepoAutomationTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubCreateRepoAutomationTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceDuplicated() {
        final GitHubCreateRepoAutomationTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersInterval())
                        .cadence(List.of(
                                GitHubCreateRepoAutomationTriggersIntervalCadence.HOURLY,
                                GitHubCreateRepoAutomationTriggersIntervalCadence.HOURLY));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcBelowMinimum() {
        final GitHubCreateRepoAutomationTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcAboveMaximum() {
        final GitHubCreateRepoAutomationTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekBelowMinimum() {
        final GitHubCreateRepoAutomationTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekAboveMaximum() {
        final GitHubCreateRepoAutomationTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
