package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersPullRequestLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestLabeled())
                        .paths(null);

        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestLabeled())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestLabeled())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestLabeled())
                        .labels(null);

        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}
