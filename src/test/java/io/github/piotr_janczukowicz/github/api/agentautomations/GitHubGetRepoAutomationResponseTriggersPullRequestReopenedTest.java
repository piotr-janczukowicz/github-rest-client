package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersPullRequestReopenedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestReopened())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestReopened())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestReopened())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReopened value =
                GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestReopened())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
