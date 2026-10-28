package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertInstanceTest {

    @Test
    void constructor_shouldLeaveClassificationsNull_whenNull() {
        final GitHubCodeScanningAlertInstance built = GitHubCodeScanningAlertInstanceBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertInstance())
                .classifications(null)
                .build();

        assertThat(built.classifications()).isNull();
    }
}
