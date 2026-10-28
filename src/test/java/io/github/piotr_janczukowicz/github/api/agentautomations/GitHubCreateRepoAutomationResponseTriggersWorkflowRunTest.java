package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubCreateRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE,
                                GitHubCreateRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
