package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDiscussionTest {

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenCategoryNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .category(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("category");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryUrlNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .repositoryUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryUrl");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubDiscussionBuilder builder = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldLeaveLabelsNull_whenNull() {
        final GitHubDiscussion value = GitHubDiscussionBuilder.builder(ActivityCatalogFixtures.discussion())
                .labels(null)
                .build();

        assertThat(value.labels()).isNull();
    }
}
