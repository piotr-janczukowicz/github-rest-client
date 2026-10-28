package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitCommitParentTest {

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitParentBuilder.builder(GitDatabaseCatalogFixtures.gitCommitParent())
                        .sha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitParentBuilder.builder(GitDatabaseCatalogFixtures.gitCommitParent())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitParentBuilder.builder(GitDatabaseCatalogFixtures.gitCommitParent())
                        .htmlUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }
}
