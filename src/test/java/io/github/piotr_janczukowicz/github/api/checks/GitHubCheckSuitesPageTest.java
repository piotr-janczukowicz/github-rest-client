package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckSuitesPageTest {

    @Test
    void constructor_shouldThrow_whenCheckSuitesNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckSuitesPageBuilder.builder(ChecksCatalogFixtures.checkSuitesPage())
                        .checkSuites(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("checkSuites");
    }
}
