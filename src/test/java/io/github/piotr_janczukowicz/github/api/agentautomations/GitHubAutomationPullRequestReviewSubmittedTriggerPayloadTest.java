package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationPullRequestReviewSubmittedTriggerPayloadTest {

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .states(List.of(
                                GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesDuplicated() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .states(List.of(
                                GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayload value =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenPathsGiven() {
        final List<String> source = new ArrayList<>(List.of("paths"));
        final GitHubAutomationPullRequestReviewSubmittedTriggerPayload value =
                GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTriggerPayload())
                        .paths(source)
                        .build();

        source.add("paths");

        assertThat(value.paths()).hasSize(source.size() - 1);
    }
}
