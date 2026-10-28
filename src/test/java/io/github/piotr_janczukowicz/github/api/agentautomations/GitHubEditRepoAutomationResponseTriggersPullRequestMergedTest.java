package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersPullRequestMergedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestMerged())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestMerged())
                        .paths(null);

        final GitHubEditRepoAutomationResponseTriggersPullRequestMerged sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestMerged())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersPullRequestMerged())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
