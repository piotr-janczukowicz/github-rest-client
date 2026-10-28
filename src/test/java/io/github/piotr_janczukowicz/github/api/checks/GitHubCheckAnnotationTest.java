package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckAnnotationTest {

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckAnnotationBuilder.builder(ChecksCatalogFixtures.checkAnnotation())
                        .path(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenBlobHrefNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckAnnotationBuilder.builder(ChecksCatalogFixtures.checkAnnotation())
                        .blobHref(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("blobHref");
    }
}
