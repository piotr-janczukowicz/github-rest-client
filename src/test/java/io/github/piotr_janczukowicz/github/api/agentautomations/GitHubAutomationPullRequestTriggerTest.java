package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationPullRequestTriggerTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationPullRequestTriggerBuilder builder = GitHubAutomationPullRequestTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationPullRequestTrigger())
                .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubAutomationPullRequestTriggerBuilder builder = GitHubAutomationPullRequestTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationPullRequestTrigger())
                .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubAutomationPullRequestTriggerBuilder builder = GitHubAutomationPullRequestTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationPullRequestTrigger())
                .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubAutomationPullRequestTrigger value = GitHubAutomationPullRequestTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationPullRequestTrigger())
                .paths(null)
                .build();

        assertThat(value.paths()).isNull();
    }
}
