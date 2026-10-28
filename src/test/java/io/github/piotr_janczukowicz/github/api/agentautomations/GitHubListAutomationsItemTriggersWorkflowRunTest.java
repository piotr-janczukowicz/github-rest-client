package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersWorkflowRunTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubListAutomationsItemTriggersWorkflowRunBuilder builder =
                GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersWorkflowRun())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubListAutomationsItemTriggersWorkflowRunBuilder builder =
                GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersWorkflowRun())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubListAutomationsItemTriggersWorkflowRunBuilder builder =
                GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersWorkflowRun())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubListAutomationsItemTriggersWorkflowRunBuilder builder =
                GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersWorkflowRun())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubListAutomationsItemTriggersWorkflowRunBuilder builder =
                GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersWorkflowRun())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubListAutomationsItemTriggersWorkflowRunBuilder builder =
                GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersWorkflowRun())
                        .conclusions(List.of(
                                GitHubListAutomationsItemTriggersWorkflowRunConclusion.FAILURE,
                                GitHubListAutomationsItemTriggersWorkflowRunConclusion.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersWorkflowRunBuilder builder =
                GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersWorkflowRun())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
