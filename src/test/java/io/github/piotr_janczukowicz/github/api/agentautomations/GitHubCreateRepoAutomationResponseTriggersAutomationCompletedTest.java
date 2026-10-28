package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersAutomationCompletedTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersAutomationCompleted())
                        .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersAutomationCompleted())
                        .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersAutomationCompleted())
                        .automationIds(List.of("a", "a", "a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersAutomationCompleted())
                        .automationIds(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }
}
