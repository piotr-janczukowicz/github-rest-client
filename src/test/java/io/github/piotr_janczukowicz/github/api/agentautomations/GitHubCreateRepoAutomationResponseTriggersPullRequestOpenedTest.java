package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestOpened())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestOpened())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestOpened())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestOpened value =
                GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestOpened())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
