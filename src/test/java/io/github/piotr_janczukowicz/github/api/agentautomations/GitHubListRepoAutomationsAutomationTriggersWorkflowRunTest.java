package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion.FAILURE,
                                GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
