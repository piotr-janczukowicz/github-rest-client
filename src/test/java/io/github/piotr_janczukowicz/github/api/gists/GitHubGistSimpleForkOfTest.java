package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGistSimpleForkOfTest {
    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenForksUrlNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .forksUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("forksUrl");
    }

    @Test
    void constructor_shouldThrow_whenCommitsUrlNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .commitsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commitsUrl");
    }

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenGitPullUrlNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .gitPullUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("gitPullUrl");
    }

    @Test
    void constructor_shouldThrow_whenGitPushUrlNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .gitPushUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("gitPushUrl");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenFilesNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .files(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("files");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCommentsUrlNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .commentsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commentsUrl");
    }

    @Test
    void constructor_shouldLeaveForksNull_whenNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .forks(null);

        final GitHubGistSimpleForkOf result = builder.build();

        assertThat(result.forks()).isNull();
    }

    @Test
    void constructor_shouldLeaveHistoryNull_whenNull() {
        final GitHubGistSimpleForkOfBuilder builder = GitHubGistSimpleForkOfBuilder.builder(
                        GistsCatalogFixtures.gistSimpleForkOf())
                .history(null);

        final GitHubGistSimpleForkOf result = builder.build();

        assertThat(result.history()).isNull();
    }
}
