package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationPullRequestAssignedTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationPullRequestAssignedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubAutomationPullRequestAssignedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubAutomationPullRequestAssignedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubAutomationPullRequestAssignedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubAutomationPullRequestAssignedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .assignees(List.of("assignees", "assignees"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubAutomationPullRequestAssignedTriggerPayload value =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenPathsGiven() {
        final List<String> source = new ArrayList<>(List.of("paths"));
        final GitHubAutomationPullRequestAssignedTriggerPayload value =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .paths(source)
                        .build();

        source.add("paths");

        assertThat(value.paths()).hasSize(source.size() - 1);
    }

    @Test
    void constructor_shouldAcceptNull_whenAssigneesNull() {
        final GitHubAutomationPullRequestAssignedTriggerPayload value =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .assignees(null)
                        .build();

        assertThat(value.assignees()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenAssigneesGiven() {
        final List<String> source = new ArrayList<>(List.of("assignees"));
        final GitHubAutomationPullRequestAssignedTriggerPayload value =
                GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestAssignedTriggerPayload())
                        .assignees(source)
                        .build();

        source.add("assignees");

        assertThat(value.assignees()).hasSize(source.size() - 1);
    }
}
