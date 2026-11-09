package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationPullRequestLabeledTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationPullRequestLabeledTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubAutomationPullRequestLabeledTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubAutomationPullRequestLabeledTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenLabelsDuplicated() {
        final GitHubAutomationPullRequestLabeledTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .labels(List.of("labels", "labels"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("labels");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubAutomationPullRequestLabeledTriggerPayload value =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenPathsGiven() {
        final List<String> source = new ArrayList<>(List.of("paths"));
        final GitHubAutomationPullRequestLabeledTriggerPayload value =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .paths(source)
                        .build();

        source.add("paths");

        assertThat(value.paths()).hasSize(source.size() - 1);
    }

    @Test
    void constructor_shouldAcceptNull_whenLabelsNull() {
        final GitHubAutomationPullRequestLabeledTriggerPayload value =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .labels(null)
                        .build();

        assertThat(value.labels()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenLabelsGiven() {
        final List<String> source = new ArrayList<>(List.of("labels"));
        final GitHubAutomationPullRequestLabeledTriggerPayload value =
                GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestLabeledTriggerPayload())
                        .labels(source)
                        .build();

        source.add("labels");

        assertThat(value.labels()).hasSize(source.size() - 1);
    }
}
