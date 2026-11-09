package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationIssueAssignedTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationIssueAssignedTriggerPayloadBuilder builder =
                GitHubAutomationIssueAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueAssignedTriggerPayload())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubAutomationIssueAssignedTriggerPayloadBuilder builder =
                GitHubAutomationIssueAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueAssignedTriggerPayload())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubAutomationIssueAssignedTriggerPayloadBuilder builder =
                GitHubAutomationIssueAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueAssignedTriggerPayload())
                        .assignees(List.of("assignees", "assignees"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldAcceptNull_whenAssigneesNull() {
        final GitHubAutomationIssueAssignedTriggerPayload value =
                GitHubAutomationIssueAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueAssignedTriggerPayload())
                        .assignees(null)
                        .build();

        assertThat(value.assignees()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenAssigneesGiven() {
        final List<String> source = new ArrayList<>(List.of("assignees"));
        final GitHubAutomationIssueAssignedTriggerPayload value =
                GitHubAutomationIssueAssignedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationIssueAssignedTriggerPayload())
                        .assignees(source)
                        .build();

        source.add("assignees");

        assertThat(value.assignees()).hasSize(source.size() - 1);
    }
}
