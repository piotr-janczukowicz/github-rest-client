package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDismissalRequestRepositoryTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSecretScanningDismissalRequestRepository sut =
                SecretScanningCatalogFixtures.secretScanningDismissalRequestRepository();

        assertSoftly(softly -> {
            softly.assertThat(sut.id()).isNotNull();
            softly.assertThat(sut.name()).isNotNull();
            softly.assertThat(sut.fullName()).isNotNull();
        });
    }
}
