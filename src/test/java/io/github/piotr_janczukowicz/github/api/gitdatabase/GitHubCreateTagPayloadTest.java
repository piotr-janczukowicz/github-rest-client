package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateTagPayloadTest {

    @Test
    void constructor_shouldThrow_whenTagNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateTagPayloadBuilder.builder(GitDatabaseCatalogFixtures.createTagPayload())
                        .tag(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tag");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateTagPayloadBuilder.builder(GitDatabaseCatalogFixtures.createTagPayload())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenObjectNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateTagPayloadBuilder.builder(GitDatabaseCatalogFixtures.createTagPayload())
                        .object(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("object");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateTagPayloadBuilder.builder(GitDatabaseCatalogFixtures.createTagPayload())
                        .type(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
