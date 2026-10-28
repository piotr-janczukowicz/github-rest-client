package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestEventTest {

    @Test
    void constructor_shouldThrow_whenPullRequestNull() {
        final GitHubPullRequestEventBuilder builder = GitHubPullRequestEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEvent())
                .pullRequest(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pullRequest");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubPullRequestEvent event = GitHubPullRequestEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEvent())
                .assignees(null)
                .build();

        assertThat(event.assignees()).isNull();
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubPullRequestEvent event = GitHubPullRequestEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEvent())
                .labels(null)
                .build();

        assertThat(event.labels()).isNull();
    }
}
