package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersIssueLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersIssueLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubEditRepoAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersIssueLabeled())
                        .labels(null);

        final GitHubEditRepoAutomationResponseTriggersIssueLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersIssueLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIssueLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersIssueLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}
