package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCheckRunAnnotationTest {

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunAnnotationBuilder.builder(ChecksCatalogFixtures.updateCheckRunAnnotation())
                        .path(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenAnnotationLevelNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunAnnotationBuilder.builder(ChecksCatalogFixtures.updateCheckRunAnnotation())
                        .annotationLevel(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("annotationLevel");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunAnnotationBuilder.builder(ChecksCatalogFixtures.updateCheckRunAnnotation())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }
}
