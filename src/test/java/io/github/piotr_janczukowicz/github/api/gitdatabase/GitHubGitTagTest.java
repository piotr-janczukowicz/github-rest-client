package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitTagTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTagBuilder.builder(GitDatabaseCatalogFixtures.gitTag())
                .nodeId(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenTagNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTagBuilder.builder(GitDatabaseCatalogFixtures.gitTag())
                .tag(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tag");
    }

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTagBuilder.builder(GitDatabaseCatalogFixtures.gitTag())
                .sha(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTagBuilder.builder(GitDatabaseCatalogFixtures.gitTag())
                .url(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTagBuilder.builder(GitDatabaseCatalogFixtures.gitTag())
                .message(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenTaggerNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTagBuilder.builder(GitDatabaseCatalogFixtures.gitTag())
                .tagger(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tagger");
    }

    @Test
    void constructor_shouldThrow_whenObjectNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTagBuilder.builder(GitDatabaseCatalogFixtures.gitTag())
                .object(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("object");
    }
}
