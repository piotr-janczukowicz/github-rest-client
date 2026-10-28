package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDelegatedBypassOptionsTest {

    @Test
    void constructor_shouldLeaveReviewersNull_whenNull() {
        final GitHubSecretScanningDelegatedBypassOptions options =
                GitHubSecretScanningDelegatedBypassOptionsBuilder.builder(
                                CodeSecurityCatalogFixtures.secretScanningDelegatedBypassOptions())
                        .reviewers(null)
                        .build();

        assertThat(options.reviewers()).isNull();
    }
}
