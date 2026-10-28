package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRefPayloadTest {

    @Test
    void constructor_shouldThrow_whenRefNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateRefPayloadBuilder.builder(GitDatabaseCatalogFixtures.createRefPayload())
                        .ref(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldThrow_whenShaNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateRefPayloadBuilder.builder(GitDatabaseCatalogFixtures.createRefPayload())
                        .sha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sha");
    }
}
