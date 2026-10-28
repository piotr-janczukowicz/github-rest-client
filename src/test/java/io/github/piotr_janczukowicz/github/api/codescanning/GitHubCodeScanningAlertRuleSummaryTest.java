package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertRuleSummaryTest {

    @Test
    void constructor_shouldLeaveTagsNull_whenNull() {
        final GitHubCodeScanningAlertRuleSummary built = GitHubCodeScanningAlertRuleSummaryBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertRuleSummary())
                .tags(null)
                .build();

        assertThat(built.tags()).isNull();
    }
}
