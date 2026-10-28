package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDismissalRequestDataTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSecretScanningDismissalRequestData sut =
                SecretScanningCatalogFixtures.secretScanningDismissalRequestData();

        assertSoftly(softly -> {
            softly.assertThat(sut.secretType()).isNotNull();
            softly.assertThat(sut.alertNumber()).isNotNull();
            softly.assertThat(sut.reason()).isNotNull();
        });
    }
}
