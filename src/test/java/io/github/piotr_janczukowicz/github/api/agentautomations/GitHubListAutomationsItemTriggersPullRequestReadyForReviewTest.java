package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersPullRequestReadyForReviewTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReadyForReview())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReadyForReview())
                        .paths(null);

        final GitHubListAutomationsItemTriggersPullRequestReadyForReview sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReadyForReview())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersPullRequestReadyForReview())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
