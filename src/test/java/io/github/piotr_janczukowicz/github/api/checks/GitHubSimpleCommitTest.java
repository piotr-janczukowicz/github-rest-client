package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSimpleCommitTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSimpleCommitBuilder.builder(ChecksCatalogFixtures.simpleCommit())
                        .id(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTreeIdNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSimpleCommitBuilder.builder(ChecksCatalogFixtures.simpleCommit())
                        .treeId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("treeId");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSimpleCommitBuilder.builder(ChecksCatalogFixtures.simpleCommit())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenTimestampNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSimpleCommitBuilder.builder(ChecksCatalogFixtures.simpleCommit())
                        .timestamp(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timestamp");
    }
}
