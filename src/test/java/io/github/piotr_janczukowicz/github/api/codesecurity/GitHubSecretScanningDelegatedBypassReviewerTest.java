package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningDelegatedBypassReviewerTest {

    @Test
    void constructor_shouldThrow_whenReviewerTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSecretScanningDelegatedBypassReviewerBuilder.builder(
                        CodeSecurityCatalogFixtures.secretScanningDelegatedBypassReviewer())
                .reviewerType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reviewerType");
    }
}
