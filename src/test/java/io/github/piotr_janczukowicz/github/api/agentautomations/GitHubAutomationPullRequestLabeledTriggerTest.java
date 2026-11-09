package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationPullRequestLabeledTriggerTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationPullRequestLabeledTriggerBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTrigger())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubAutomationPullRequestLabeledTriggerBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTrigger())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubAutomationPullRequestLabeledTriggerBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTrigger())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubAutomationPullRequestLabeledTriggerBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTrigger())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubAutomationPullRequestLabeledTrigger value =
                GitHubAutomationPullRequestLabeledTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTrigger())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubAutomationPullRequestLabeledTrigger value =
                GitHubAutomationPullRequestLabeledTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTrigger())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }
}
