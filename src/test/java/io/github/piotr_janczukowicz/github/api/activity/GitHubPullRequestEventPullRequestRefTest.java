package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestEventPullRequestRefTest {

    @Test
    void constructor_shouldThrow_whenRefNull() {
        final GitHubPullRequestEventPullRequestRefBuilder builder = GitHubPullRequestEventPullRequestRefBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEventPullRequestRef())
                .ref(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldThrow_whenRepoNull() {
        final GitHubPullRequestEventPullRequestRefBuilder builder = GitHubPullRequestEventPullRequestRefBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEventPullRequestRef())
                .repo(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repo");
    }

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final GitHubPullRequestEventPullRequestRefBuilder builder = GitHubPullRequestEventPullRequestRefBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEventPullRequestRef())
                .sha(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }
}
