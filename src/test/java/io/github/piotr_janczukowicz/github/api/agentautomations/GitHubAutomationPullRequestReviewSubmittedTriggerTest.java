package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationPullRequestReviewSubmittedTriggerTest {

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .states(List.of(
                                GitHubAutomationPullRequestReviewSubmittedState.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedState.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedState.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldAccept_whenStatesRepeat() {
        final GitHubAutomationPullRequestReviewSubmittedTriggerBuilder builder =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .states(List.of(
                                GitHubAutomationPullRequestReviewSubmittedState.APPROVED,
                                GitHubAutomationPullRequestReviewSubmittedState.APPROVED));

        final GitHubAutomationPullRequestReviewSubmittedTrigger value = builder.build();

        assertThat(value.states()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubAutomationPullRequestReviewSubmittedTrigger value =
                GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder(
                                AgentAutomationsCatalogFixtures.automationPullRequestReviewSubmittedTrigger())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
