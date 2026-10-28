package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersIssueLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersIssueLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersIssueLabeled())
                        .labels(null);

        final GitHubCreateRepoAutomationTriggersIssueLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubCreateRepoAutomationTriggersIssueLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersIssueLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}
