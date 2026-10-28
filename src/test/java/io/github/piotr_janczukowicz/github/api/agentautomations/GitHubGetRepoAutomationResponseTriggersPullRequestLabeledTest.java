package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersPullRequestLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestLabeled())
                        .paths(null);

        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestLabeled())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestLabeled())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestLabeled())
                        .labels(null);

        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}
