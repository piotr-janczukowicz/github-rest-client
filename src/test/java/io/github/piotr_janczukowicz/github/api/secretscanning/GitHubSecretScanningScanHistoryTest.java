package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningScanHistoryTest {

    @Test
    void constructor_shouldCopyLists_whenListsProvided() {
        final GitHubSecretScanningScan scan = new GitHubSecretScanningScan("incremental", "completed", null, null);
        final GitHubSecretScanningCustomPatternBackfillScan backfillScan =
                new GitHubSecretScanningCustomPatternBackfillScan(
                        "custom_pattern_backfill", "completed", null, null, "pattern", "organization");

        final GitHubSecretScanningScanHistory history = new GitHubSecretScanningScanHistory(
                List.of(scan), List.of(scan), List.of(scan), List.of(backfillScan), List.of(scan));

        assertSoftly(softly -> {
            softly.assertThat(history.incrementalScans()).containsExactly(scan);
            softly.assertThat(history.patternUpdateScans()).containsExactly(scan);
            softly.assertThat(history.backfillScans()).containsExactly(scan);
            softly.assertThat(history.customPatternBackfillScans()).containsExactly(backfillScan);
            softly.assertThat(history.genericSecretsBackfillScans()).containsExactly(scan);
        });
    }

    @Test
    void constructor_shouldAcceptNullLists_whenNoScansPresent() {
        final GitHubSecretScanningScanHistory history =
                new GitHubSecretScanningScanHistory(null, null, null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(history.incrementalScans()).isNull();
            softly.assertThat(history.patternUpdateScans()).isNull();
            softly.assertThat(history.backfillScans()).isNull();
            softly.assertThat(history.customPatternBackfillScans()).isNull();
            softly.assertThat(history.genericSecretsBackfillScans()).isNull();
        });
    }
}
