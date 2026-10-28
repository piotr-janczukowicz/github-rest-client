package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitTreeEntryTest {

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTreeEntryBuilder.builder(GitDatabaseCatalogFixtures.gitTreeEntry())
                        .path(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenModeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTreeEntryBuilder.builder(GitDatabaseCatalogFixtures.gitTreeEntry())
                        .mode(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("mode");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTreeEntryBuilder.builder(GitDatabaseCatalogFixtures.gitTreeEntry())
                        .type(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGitTreeEntryBuilder.builder(GitDatabaseCatalogFixtures.gitTreeEntry())
                        .sha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }
}
