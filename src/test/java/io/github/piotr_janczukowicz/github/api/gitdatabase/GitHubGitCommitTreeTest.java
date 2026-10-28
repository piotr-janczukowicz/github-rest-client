package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitCommitTreeTest {

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitTreeBuilder.builder(GitDatabaseCatalogFixtures.gitCommitTree())
                        .sha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitCommitTreeBuilder.builder(GitDatabaseCatalogFixtures.gitCommitTree())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
