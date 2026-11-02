package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubGetRepoAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersIssueLabeled())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubGetRepoAutomationResponseTriggersIssueLabeled value =
                GitHubGetRepoAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersIssueLabeled())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }
}
