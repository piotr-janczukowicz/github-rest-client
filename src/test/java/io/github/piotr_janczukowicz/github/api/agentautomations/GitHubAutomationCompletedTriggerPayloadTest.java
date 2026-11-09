package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationCompletedTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubAutomationCompletedTriggerPayloadBuilder builder =
                GitHubAutomationCompletedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationCompletedTriggerPayload())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubAutomationCompletedTriggerPayloadBuilder builder =
                GitHubAutomationCompletedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationCompletedTriggerPayload())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubAutomationCompletedTriggerPayloadBuilder builder =
                GitHubAutomationCompletedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationCompletedTriggerPayload())
                        .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubAutomationCompletedTriggerPayloadBuilder builder =
                GitHubAutomationCompletedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationCompletedTriggerPayload())
                        .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }
}
