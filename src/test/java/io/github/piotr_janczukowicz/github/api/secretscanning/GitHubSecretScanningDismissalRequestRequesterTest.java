package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDismissalRequestRequesterTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSecretScanningDismissalRequestRequester sut =
                SecretScanningCatalogFixtures.secretScanningDismissalRequestRequester();

        assertSoftly(softly -> {
            softly.assertThat(sut.actorId()).isNotNull();
            softly.assertThat(sut.actorName()).isNotNull();
        });
    }
}
