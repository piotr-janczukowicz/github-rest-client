package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGistCommentTest {
    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubGistCommentBuilder builder = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubGistCommentBuilder builder = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubGistCommentBuilder builder = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubGistCommentBuilder builder = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubGistCommentBuilder builder = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenAuthorAssociationNull() {
        final GitHubGistCommentBuilder builder = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .authorAssociation(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("authorAssociation");
    }

    @Test
    void constructor_shouldThrow_whenBodyTooLong() {
        final GitHubGistCommentBuilder builder = GitHubGistCommentBuilder.builder(GistsCatalogFixtures.gistComment())
                .body("a".repeat(65_536));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }
}
