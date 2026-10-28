package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersIssueAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersIssueAssignedBuilder builder =
                GitHubListAutomationsItemTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersIssueAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubListAutomationsItemTriggersIssueAssignedBuilder builder =
                GitHubListAutomationsItemTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersIssueAssigned())
                        .assignees(null);

        final GitHubListAutomationsItemTriggersIssueAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubListAutomationsItemTriggersIssueAssignedBuilder builder =
                GitHubListAutomationsItemTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersIssueAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubListAutomationsItemTriggersIssueAssignedBuilder builder =
                GitHubListAutomationsItemTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersIssueAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
