package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubGetAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubGetAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubGetAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubGetAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubGetAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubGetAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubGetAutomationResponseTriggersWorkflowRunConclusion.FAILURE,
                                GitHubGetAutomationResponseTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
