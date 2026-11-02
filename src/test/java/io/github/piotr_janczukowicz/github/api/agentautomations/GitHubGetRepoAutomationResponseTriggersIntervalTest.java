package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubGetRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldAccept_whenCadenceRepeat() {
        final GitHubGetRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersInterval())
                        .cadence(List.of(
                                GitHubGetRepoAutomationResponseTriggersIntervalCadence.HOURLY,
                                GitHubGetRepoAutomationResponseTriggersIntervalCadence.HOURLY));

        final GitHubGetRepoAutomationResponseTriggersInterval value = builder.build();

        assertThat(value.cadence()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooLarge() {
        final GitHubGetRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooLarge() {
        final GitHubGetRepoAutomationResponseTriggersIntervalBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
