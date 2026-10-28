package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDiscussionEventTest {

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final GitHubDiscussionEventBuilder builder = GitHubDiscussionEventBuilder.builder(
                        ActivityCatalogFixtures.discussionEvent())
                .action(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }

    @Test
    void constructor_shouldThrow_whenDiscussionNull() {
        final GitHubDiscussionEventBuilder builder = GitHubDiscussionEventBuilder.builder(
                        ActivityCatalogFixtures.discussionEvent())
                .discussion(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("discussion");
    }
}
