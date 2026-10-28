package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDelegatedBypassReviewerInputTest {

    @Test
    void constructor_shouldThrow_whenReviewerTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSecretScanningDelegatedBypassReviewerInputBuilder.builder(
                        CodeSecurityCatalogFixtures.secretScanningDelegatedBypassReviewerInput())
                .reviewerType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reviewerType");
    }
}
