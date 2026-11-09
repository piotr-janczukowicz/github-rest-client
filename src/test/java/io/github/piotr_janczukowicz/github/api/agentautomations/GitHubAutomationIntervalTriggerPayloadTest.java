package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationIntervalTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenCadenceNull() {
        final GitHubAutomationIntervalTriggerPayloadBuilder builder =
                GitHubAutomationIntervalTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIntervalTriggerPayload())
                        .cadence(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceTooSmall() {
        final GitHubAutomationIntervalTriggerPayloadBuilder builder =
                GitHubAutomationIntervalTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIntervalTriggerPayload())
                        .cadence(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenCadenceDuplicated() {
        final GitHubAutomationIntervalTriggerPayloadBuilder builder =
                GitHubAutomationIntervalTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIntervalTriggerPayload())
                        .cadence(List.of(
                                GitHubAutomationIntervalCadenceInput.HOURLY,
                                GitHubAutomationIntervalCadenceInput.HOURLY));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("cadence");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooSmall() {
        final GitHubAutomationIntervalTriggerPayloadBuilder builder =
                GitHubAutomationIntervalTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIntervalTriggerPayload())
                        .hourUtc(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenHourUtcTooLarge() {
        final GitHubAutomationIntervalTriggerPayloadBuilder builder =
                GitHubAutomationIntervalTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIntervalTriggerPayload())
                        .hourUtc(24);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("hourUtc");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooSmall() {
        final GitHubAutomationIntervalTriggerPayloadBuilder builder =
                GitHubAutomationIntervalTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIntervalTriggerPayload())
                        .dayOfWeek(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }

    @Test
    void constructor_shouldThrow_whenDayOfWeekTooLarge() {
        final GitHubAutomationIntervalTriggerPayloadBuilder builder =
                GitHubAutomationIntervalTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIntervalTriggerPayload())
                        .dayOfWeek(7);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("dayOfWeek");
    }
}
