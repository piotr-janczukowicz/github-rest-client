package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCheckRunActionTest {

    @Test
    void constructor_shouldThrow_whenLabelNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunActionBuilder.builder(ChecksCatalogFixtures.updateCheckRunAction())
                        .label(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("label");
    }

    @Test
    void constructor_shouldThrow_whenLabelTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunActionBuilder.builder(ChecksCatalogFixtures.updateCheckRunAction())
                        .label("x".repeat(21))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("label");
    }

    @Test
    void constructor_shouldThrow_whenIdentifierNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunActionBuilder.builder(ChecksCatalogFixtures.updateCheckRunAction())
                        .identifier(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("identifier");
    }

    @Test
    void constructor_shouldThrow_whenIdentifierTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunActionBuilder.builder(ChecksCatalogFixtures.updateCheckRunAction())
                        .identifier("x".repeat(21))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("identifier");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunActionBuilder.builder(ChecksCatalogFixtures.updateCheckRunAction())
                        .description(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionTooLong() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunActionBuilder.builder(ChecksCatalogFixtures.updateCheckRunAction())
                        .description("x".repeat(41))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldAccept_whenDescriptionAtMaxLength() {
        final GitHubUpdateCheckRunAction action = GitHubUpdateCheckRunActionBuilder.builder(
                        ChecksCatalogFixtures.updateCheckRunAction())
                .description("x".repeat(40))
                .build();

        assertThat(action.description()).hasSize(40);
    }
}
