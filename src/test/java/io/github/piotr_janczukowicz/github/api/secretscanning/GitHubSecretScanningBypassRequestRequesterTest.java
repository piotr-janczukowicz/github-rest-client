package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningBypassRequestRequesterTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSecretScanningBypassRequestRequester sut =
                SecretScanningCatalogFixtures.secretScanningBypassRequestRequester();

        assertSoftly(softly -> {
            softly.assertThat(sut.actorId()).isNotNull();
            softly.assertThat(sut.actorName()).isNotNull();
        });
    }
}
