package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE,
                                GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
