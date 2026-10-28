package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubCreateRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubCreateRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubCreateRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubCreateRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubCreateRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubCreateRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubCreateRepoAutomationTriggersWorkflowRunConclusion.FAILURE,
                                GitHubCreateRepoAutomationTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersWorkflowRunBuilder builder =
                GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
