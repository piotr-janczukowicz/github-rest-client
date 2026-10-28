package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckRunOutputTest {

    @Test
    void constructor_shouldThrow_whenAnnotationsUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckRunOutputBuilder.builder(ChecksCatalogFixtures.checkRunOutput())
                        .annotationsUrl(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("annotationsUrl");
    }
}
