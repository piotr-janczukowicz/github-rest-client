package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestSynchronize())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestSynchronize())
                        .paths(null);

        final GitHubEditRepoAutomationResponseTriggersPullRequestSynchronize sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestSynchronize())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestSynchronize())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
