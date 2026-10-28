package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersPullRequestLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestLabeled())
                        .paths(null);

        final GitHubEditRepoAutomationTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestLabeled())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestLabeled())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestLabeled())
                        .labels(null);

        final GitHubEditRepoAutomationTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}
