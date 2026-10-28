package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCheckRunAnnotationTest {

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunAnnotationBuilder.builder(ChecksCatalogFixtures.createCheckRunAnnotation())
                        .path(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenAnnotationLevelNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunAnnotationBuilder.builder(ChecksCatalogFixtures.createCheckRunAnnotation())
                        .annotationLevel(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("annotationLevel");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateCheckRunAnnotationBuilder.builder(ChecksCatalogFixtures.createCheckRunAnnotation())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }
}
