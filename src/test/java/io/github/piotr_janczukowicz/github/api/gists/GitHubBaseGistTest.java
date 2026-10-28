package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBaseGistTest {
    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenForksUrlNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).forksUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("forksUrl");
    }

    @Test
    void constructor_shouldThrow_whenCommitsUrlNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).commitsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commitsUrl");
    }

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenGitPullUrlNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).gitPullUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("gitPullUrl");
    }

    @Test
    void constructor_shouldThrow_whenGitPushUrlNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).gitPushUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("gitPushUrl");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenFilesNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).files(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("files");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCommentsUrlNull() {
        final GitHubBaseGistBuilder builder =
                GitHubBaseGistBuilder.builder(GistsCatalogFixtures.baseGist()).commentsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentsUrl");
    }
}
