package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCheckRunImageTest {

    @Test
    void constructor_shouldThrow_whenAltNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunImageBuilder.builder(ChecksCatalogFixtures.updateCheckRunImage())
                        .alt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("alt");
    }

    @Test
    void constructor_shouldThrow_whenImageUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateCheckRunImageBuilder.builder(ChecksCatalogFixtures.updateCheckRunImage())
                        .imageUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("imageUrl");
    }
}
