package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersAutomationCompletedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubListAutomationsItemTriggersAutomationCompletedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationCompleted())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubListAutomationsItemTriggersAutomationCompletedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationCompleted())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubListAutomationsItemTriggersAutomationCompletedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationCompleted())
                        .automationIds(List.of("a", "a", "a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubListAutomationsItemTriggersAutomationCompletedBuilder builder =
                GitHubListAutomationsItemTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersAutomationCompleted())
                        .automationIds(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }
}
