package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningDefaultSetupTest {

    @Test
    void constructor_shouldLeaveLanguagesNull_whenNull() {
        final GitHubCodeScanningDefaultSetup built = GitHubCodeScanningDefaultSetupBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningDefaultSetup())
                .languages(null)
                .build();

        assertThat(built.languages()).isNull();
    }
}
