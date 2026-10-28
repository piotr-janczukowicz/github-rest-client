package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubEditRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubEditRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubEditRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubEditRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubEditRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubEditRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubEditRepoAutomationTriggersWorkflowRunConclusion.FAILURE,
                                GitHubEditRepoAutomationTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
