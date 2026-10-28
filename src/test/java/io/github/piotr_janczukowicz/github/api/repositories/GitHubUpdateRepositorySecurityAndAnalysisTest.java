package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateRepositorySecurityAndAnalysisTest {

    @Test
    void constructor_shouldRetainStatuses_whenProvided() {
        final GitHubSecurityAndAnalysisStatus enabled =
                new GitHubSecurityAndAnalysisStatus(GitHubSecurityAndAnalysisEnablement.ENABLED);
        final GitHubSecurityAndAnalysisStatus disabled =
                new GitHubSecurityAndAnalysisStatus(GitHubSecurityAndAnalysisEnablement.DISABLED);
        final GitHubSecretScanningDelegatedBypassOptions bypassOptions =
                new GitHubSecretScanningDelegatedBypassOptions(null);

        final GitHubUpdateRepositorySecurityAndAnalysis analysis = new GitHubUpdateRepositorySecurityAndAnalysis(
                enabled, enabled, disabled, enabled, enabled, enabled, enabled, enabled, enabled, bypassOptions);

        assertSoftly(softly -> {
            softly.assertThat(analysis).hasNoNullFieldsOrProperties();
            softly.assertThat(analysis.advancedSecurity()).isEqualTo(enabled);
            softly.assertThat(analysis.secretScanning()).isEqualTo(disabled);
            softly.assertThat(analysis.secretScanningDelegatedBypassOptions()).isEqualTo(bypassOptions);
        });
    }
}
