package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersPullRequestMergedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestMerged())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestMerged())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestMerged())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMerged value =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationResponseTriggersPullRequestMerged())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
