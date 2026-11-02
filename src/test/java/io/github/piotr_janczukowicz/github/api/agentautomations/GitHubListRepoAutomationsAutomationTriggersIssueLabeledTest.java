package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersIssueLabeledBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersIssueLabeledBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersIssueLabeled())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubListRepoAutomationsAutomationTriggersIssueLabeled value =
                GitHubListRepoAutomationsAutomationTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationsAutomationTriggersIssueLabeled())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }
}
