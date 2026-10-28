package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDelegatedBypassOptionsInputTest {

    @Test
    void constructor_shouldLeaveReviewersNull_whenNull() {
        final GitHubSecretScanningDelegatedBypassOptionsInput options =
                GitHubSecretScanningDelegatedBypassOptionsInputBuilder.builder(
                                CodeSecurityCatalogFixtures.secretScanningDelegatedBypassOptionsInput())
                        .reviewers(null)
                        .build();

        assertThat(options.reviewers()).isNull();
    }
}
