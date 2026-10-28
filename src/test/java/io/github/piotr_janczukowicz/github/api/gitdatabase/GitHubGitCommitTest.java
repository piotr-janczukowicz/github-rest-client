package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitCommitTest {

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .sha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .nodeId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenAuthorNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .author(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("author");
    }

    @Test
    void constructor_shouldThrow_whenCommitterNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .committer(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("committer");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenTreeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .tree(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tree");
    }

    @Test
    void constructor_shouldThrow_whenParentsNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .parents(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("parents");
    }

    @Test
    void constructor_shouldThrow_whenVerificationNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .verification(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("verification");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitBuilder.builder(GitDatabaseCatalogFixtures.gitCommit())
                        .htmlUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }
}
