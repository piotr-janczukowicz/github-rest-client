package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubEditRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE,
                                GitHubEditRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
