package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubCreateRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldAccept_whenCadenceRepeat() {
        final GitHubCreateRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersInterval())
                        .cadence(List.of(
                                GitHubCreateRepoAutomationResponseTriggersIntervalCadence.HOURLY,
                                GitHubCreateRepoAutomationResponseTriggersIntervalCadence.HOURLY));

        final GitHubCreateRepoAutomationResponseTriggersInterval value = builder.build();

        assertThat(value.cadence()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
