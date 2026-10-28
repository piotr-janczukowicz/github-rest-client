package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubListRepoAutomationsAutomationTriggersIntervalBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersIntervalBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersIntervalBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersInterval())
                        .cadence(List.of(
                                GitHubListRepoAutomationsAutomationTriggersIntervalCadence.HOURLY,
                                GitHubListRepoAutomationsAutomationTriggersIntervalCadence.HOURLY));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcBelowMinimum() {
        final GitHubListRepoAutomationsAutomationTriggersIntervalBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcAboveMaximum() {
        final GitHubListRepoAutomationsAutomationTriggersIntervalBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekBelowMinimum() {
        final GitHubListRepoAutomationsAutomationTriggersIntervalBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekAboveMaximum() {
        final GitHubListRepoAutomationsAutomationTriggersIntervalBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
