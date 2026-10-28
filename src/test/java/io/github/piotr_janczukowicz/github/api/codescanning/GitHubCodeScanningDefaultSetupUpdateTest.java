package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningDefaultSetupUpdateTest {

    @Test
    void constructor_shouldLeaveLanguagesNull_whenNull() {
        final GitHubCodeScanningDefaultSetupUpdate built = GitHubCodeScanningDefaultSetupUpdateBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningDefaultSetupUpdate())
                .languages(null)
                .build();

        assertThat(built.languages()).isNull();
    }
}
