package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDismissalRequestOrganizationTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubSecretScanningDismissalRequestOrganization sut =
                SecretScanningCatalogFixtures.secretScanningDismissalRequestOrganization();

        assertSoftly(softly -> {
            softly.assertThat(sut.id()).isNotNull();
            softly.assertThat(sut.name()).isNotNull();
        });
    }
}
