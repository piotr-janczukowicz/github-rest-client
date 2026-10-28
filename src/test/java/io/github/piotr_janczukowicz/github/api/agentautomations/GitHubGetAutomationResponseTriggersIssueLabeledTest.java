package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubGetAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubGetAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubGetAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersIssueLabeled())
                        .labels(null);

        final GitHubGetAutomationResponseTriggersIssueLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubGetAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubGetAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersIssueLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}
