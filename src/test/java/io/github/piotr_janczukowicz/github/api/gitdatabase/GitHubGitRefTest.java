package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGitRefTest {

    @Test
    void constructor_shouldThrow_whenRefNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitRefBuilder.builder(GitDatabaseCatalogFixtures.gitRef())
                .ref(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitRefBuilder.builder(GitDatabaseCatalogFixtures.gitRef())
                .nodeId(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitRefBuilder.builder(GitDatabaseCatalogFixtures.gitRef())
                .url(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenObjectNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGitRefBuilder.builder(GitDatabaseCatalogFixtures.gitRef())
                .object(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("object");
    }
}
