package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersIntervalTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubListAutomationsItemTriggersIntervalBuilder builder =
                GitHubListAutomationsItemTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersInterval())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubListAutomationsItemTriggersIntervalBuilder builder =
                GitHubListAutomationsItemTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersInterval())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldAccept_whenCadenceRepeat() {
        final GitHubListAutomationsItemTriggersIntervalBuilder builder =
                GitHubListAutomationsItemTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersInterval())
                        .cadence(List.of(
                                GitHubListAutomationsItemTriggersIntervalCadence.HOURLY,
                                GitHubListAutomationsItemTriggersIntervalCadence.HOURLY));

        final GitHubListAutomationsItemTriggersInterval value = builder.build();

        assertThat(value.cadence()).hasSize(2);
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooSmall() {
        final GitHubListAutomationsItemTriggersIntervalBuilder builder =
                GitHubListAutomationsItemTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersInterval())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooLarge() {
        final GitHubListAutomationsItemTriggersIntervalBuilder builder =
                GitHubListAutomationsItemTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersInterval())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooSmall() {
        final GitHubListAutomationsItemTriggersIntervalBuilder builder =
                GitHubListAutomationsItemTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersInterval())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooLarge() {
        final GitHubListAutomationsItemTriggersIntervalBuilder builder =
                GitHubListAutomationsItemTriggersIntervalBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersInterval())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
