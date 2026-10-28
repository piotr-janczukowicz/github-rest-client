package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestSynchronize())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestSynchronize())
                        .paths(null);

        final GitHubGetRepoAutomationResponseTriggersPullRequestSynchronize sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestSynchronize())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestSynchronize())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
