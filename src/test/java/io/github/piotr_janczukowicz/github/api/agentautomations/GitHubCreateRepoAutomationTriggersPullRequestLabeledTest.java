package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersPullRequestLabeledTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestLabeled())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestLabeled())
                        .paths(null);

        final GitHubCreateRepoAutomationTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestLabeled())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestLabeled())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestLabeled())
                        .labels(null);

        final GitHubCreateRepoAutomationTriggersPullRequestLabeled sut = builder.build();

        assertThat(sut.labels()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestLabeled())
                        .labels(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }
}
