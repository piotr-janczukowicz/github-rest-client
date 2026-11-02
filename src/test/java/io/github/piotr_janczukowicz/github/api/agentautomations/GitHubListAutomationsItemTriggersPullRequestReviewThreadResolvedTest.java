package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationsItemTriggersPullRequestReviewThreadResolved())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationsItemTriggersPullRequestReviewThreadResolved())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder builder =
                GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationsItemTriggersPullRequestReviewThreadResolved())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListAutomationsItemTriggersPullRequestReviewThreadResolved value =
                GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationsItemTriggersPullRequestReviewThreadResolved())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
