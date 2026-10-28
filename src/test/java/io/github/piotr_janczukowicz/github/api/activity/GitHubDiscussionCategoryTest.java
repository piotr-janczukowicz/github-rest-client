package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDiscussionCategoryTest {

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubDiscussionCategoryBuilder builder = GitHubDiscussionCategoryBuilder.builder(
                        ActivityCatalogFixtures.discussionCategory())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubDiscussionCategoryBuilder builder = GitHubDiscussionCategoryBuilder.builder(
                        ActivityCatalogFixtures.discussionCategory())
                .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenEmojiNull() {
        final GitHubDiscussionCategoryBuilder builder = GitHubDiscussionCategoryBuilder.builder(
                        ActivityCatalogFixtures.discussionCategory())
                .emoji(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("emoji");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubDiscussionCategoryBuilder builder = GitHubDiscussionCategoryBuilder.builder(
                        ActivityCatalogFixtures.discussionCategory())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenSlugNull() {
        final GitHubDiscussionCategoryBuilder builder = GitHubDiscussionCategoryBuilder.builder(
                        ActivityCatalogFixtures.discussionCategory())
                .slug(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("slug");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubDiscussionCategoryBuilder builder = GitHubDiscussionCategoryBuilder.builder(
                        ActivityCatalogFixtures.discussionCategory())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }
}
