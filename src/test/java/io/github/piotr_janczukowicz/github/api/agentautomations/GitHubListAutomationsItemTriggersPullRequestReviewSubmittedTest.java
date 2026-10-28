package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersPullRequestReviewSubmittedTest {

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
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .paths(null);

        final GitHubListAutomationsItemTriggersPullRequestReviewSubmitted sut = builder.build();

        assertThat(sut.paths()).isNull();
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
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
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
    void constructor_shouldThrow_whenStatesDuplicated() {
        final GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }
}
