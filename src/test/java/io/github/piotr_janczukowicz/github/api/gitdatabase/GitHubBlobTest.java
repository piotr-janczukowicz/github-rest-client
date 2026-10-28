package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBlobTest {

    @Test
    void constructor_shouldThrow_whenContentNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBlobBuilder.builder(GitDatabaseCatalogFixtures.blob())
                .content(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("content");
    }

    @Test
    void constructor_shouldThrow_whenEncodingNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBlobBuilder.builder(GitDatabaseCatalogFixtures.blob())
                .encoding(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("encoding");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBlobBuilder.builder(GitDatabaseCatalogFixtures.blob())
                .url(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBlobBuilder.builder(GitDatabaseCatalogFixtures.blob())
                .sha(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBlobBuilder.builder(GitDatabaseCatalogFixtures.blob())
                .nodeId(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }
}
