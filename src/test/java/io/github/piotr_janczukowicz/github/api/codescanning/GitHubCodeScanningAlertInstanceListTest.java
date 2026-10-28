package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertInstanceListTest {

    @Test
    void constructor_shouldLeaveClassificationsNull_whenNull() {
        final GitHubCodeScanningAlertInstanceList built = GitHubCodeScanningAlertInstanceListBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertInstanceList())
                .classifications(null)
                .build();

        assertThat(built.classifications()).isNull();
    }
}
