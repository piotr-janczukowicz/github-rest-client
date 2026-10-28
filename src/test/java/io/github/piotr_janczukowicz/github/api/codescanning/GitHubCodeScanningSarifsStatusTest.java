package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningSarifsStatusTest {

    @Test
    void constructor_shouldLeaveErrorsNull_whenNull() {
        final GitHubCodeScanningSarifsStatus built = GitHubCodeScanningSarifsStatusBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningSarifsStatus())
                .errors(null)
                .build();

        assertThat(built.errors()).isNull();
    }
}
