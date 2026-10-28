package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckRunsPageTest {

    @Test
    void constructor_shouldThrow_whenCheckRunsNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCheckRunsPageBuilder.builder(ChecksCatalogFixtures.checkRunsPage())
                        .checkRuns(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("checkRuns");
    }
}
