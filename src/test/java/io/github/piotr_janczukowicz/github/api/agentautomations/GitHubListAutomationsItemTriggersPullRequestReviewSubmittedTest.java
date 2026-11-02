package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldAccept_whenStatesRepeat() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED));

        final GitHubListAutomationsItemTriggersPullRequestReviewSubmitted value = builder.build();

        assertThat(value.states()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmitted value =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
