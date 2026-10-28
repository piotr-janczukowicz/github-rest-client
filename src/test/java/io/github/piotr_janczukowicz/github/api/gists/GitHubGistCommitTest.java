package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGistCommitTest {
    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubGistCommitBuilder builder = GitHubGistCommitBuilder.builder(GistsCatalogFixtures.gistCommit())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenVersionNull() {
        final GitHubGistCommitBuilder builder = GitHubGistCommitBuilder.builder(GistsCatalogFixtures.gistCommit())
                .version(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("version");
    }

    @Test
    void constructor_shouldThrow_whenChangeStatusNull() {
        final GitHubGistCommitBuilder builder = GitHubGistCommitBuilder.builder(GistsCatalogFixtures.gistCommit())
                .changeStatus(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("changeStatus");
    }

    @Test
    void constructor_shouldThrow_whenCommittedAtNull() {
        final GitHubGistCommitBuilder builder = GitHubGistCommitBuilder.builder(GistsCatalogFixtures.gistCommit())
                .committedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("committedAt");
    }
}
