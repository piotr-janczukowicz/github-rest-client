package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationWorkflowRunTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenWorkflowsNull() {
        final GitHubAutomationWorkflowRunTriggerPayloadBuilder builder =
                GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationWorkflowRunTriggerPayload())
                        .workflows(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsNull() {
        final GitHubAutomationWorkflowRunTriggerPayloadBuilder builder =
                GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationWorkflowRunTriggerPayload())
                        .conclusions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsTooSmall() {
        final GitHubAutomationWorkflowRunTriggerPayloadBuilder builder =
                GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationWorkflowRunTriggerPayload())
                        .workflows(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenWorkflowsItemTooShort() {
        final GitHubAutomationWorkflowRunTriggerPayloadBuilder builder =
                GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationWorkflowRunTriggerPayload())
                        .workflows(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("workflows");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooSmall() {
        final GitHubAutomationWorkflowRunTriggerPayloadBuilder builder =
                GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationWorkflowRunTriggerPayload())
                        .conclusions(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenConclusionsTooLarge() {
        final GitHubAutomationWorkflowRunTriggerPayloadBuilder builder =
                GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationWorkflowRunTriggerPayload())
                        .conclusions(List.of(
                                GitHubAutomationWorkflowRunConclusionInput.FAILURE,
                                GitHubAutomationWorkflowRunConclusionInput.FAILURE));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("conclusions");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationWorkflowRunTriggerPayloadBuilder builder =
                GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationWorkflowRunTriggerPayload())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
