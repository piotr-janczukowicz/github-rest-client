package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationWorkflowRunTriggerTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubAutomationWorkflowRunTriggerBuilder builder = GitHubAutomationWorkflowRunTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationWorkflowRunTrigger())
                .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubAutomationWorkflowRunTriggerBuilder builder = GitHubAutomationWorkflowRunTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationWorkflowRunTrigger())
                .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubAutomationWorkflowRunTriggerBuilder builder = GitHubAutomationWorkflowRunTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationWorkflowRunTrigger())
                .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubAutomationWorkflowRunTriggerBuilder builder = GitHubAutomationWorkflowRunTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationWorkflowRunTrigger())
                .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubAutomationWorkflowRunTriggerBuilder builder = GitHubAutomationWorkflowRunTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationWorkflowRunTrigger())
                .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubAutomationWorkflowRunTriggerBuilder builder = GitHubAutomationWorkflowRunTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationWorkflowRunTrigger())
                .conclusions(List.of(
                        GitHubAutomationWorkflowRunConclusion.FAILURE, GitHubAutomationWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationWorkflowRunTriggerBuilder builder = GitHubAutomationWorkflowRunTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationWorkflowRunTrigger())
                .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
