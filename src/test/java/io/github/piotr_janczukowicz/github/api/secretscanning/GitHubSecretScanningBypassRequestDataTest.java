package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningBypassRequestDataTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSecretScanningBypassRequestData sut =
                SecretScanningCatalogFixtures.secretScanningBypassRequestData();

        assertSoftly(softly -> {
            softly.assertThat(sut.secretType()).isNotNull();
            softly.assertThat(sut.bypassReason()).isNotNull();
            softly.assertThat(sut.path()).isNotNull();
            softly.assertThat(sut.branch()).isNotNull();
        });
    }
}
