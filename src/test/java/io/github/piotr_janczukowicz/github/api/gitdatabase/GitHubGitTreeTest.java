package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitTreeTest {

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTreeBuilder.builder(GitDatabaseCatalogFixtures.gitTree())
                .sha(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenTreeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitTreeBuilder.builder(GitDatabaseCatalogFixtures.gitTree())
                .tree(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tree");
    }
}
