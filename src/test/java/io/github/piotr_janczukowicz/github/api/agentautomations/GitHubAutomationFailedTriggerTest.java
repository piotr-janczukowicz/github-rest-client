package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationFailedTriggerTest {

    @Test
    void constructor_shouldThrow_whenAutomationIdsNull() {
        final GitHubAutomationFailedTriggerBuilder builder = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .automationIds(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooSmall() {
        final GitHubAutomationFailedTriggerBuilder builder = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .automationIds(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsTooLarge() {
        final GitHubAutomationFailedTriggerBuilder builder = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .automationIds(List.of("automation_ids", "automation_ids", "automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdsDuplicated() {
        final GitHubAutomationFailedTriggerBuilder builder = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .automationIds(List.of("automation_ids", "automation_ids"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("automationIds");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooSmall() {
        final GitHubAutomationFailedTriggerBuilder builder = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .reasons(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldThrow_whenReasonsTooLarge() {
        final GitHubAutomationFailedTriggerBuilder builder = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .reasons(List.of(
                        GitHubAutomationFailedReason.REPORTED,
                        GitHubAutomationFailedReason.REPORTED,
                        GitHubAutomationFailedReason.REPORTED,
                        GitHubAutomationFailedReason.REPORTED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("reasons");
    }

    @Test
    void constructor_shouldAccept_whenReasonsRepeat() {
        final GitHubAutomationFailedTriggerBuilder builder = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .reasons(List.of(GitHubAutomationFailedReason.REPORTED, GitHubAutomationFailedReason.REPORTED));

        final GitHubAutomationFailedTrigger value = builder.build();

        assertThat(value.reasons()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenReasonsNull() {
        final GitHubAutomationFailedTrigger value = GitHubAutomationFailedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationFailedTrigger())
                .reasons(null)
                .build();

        assertThat(value.reasons()).isNull();
    }
}
