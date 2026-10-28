package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewDismissalRequestResultTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubReviewDismissalRequestResult sut = SecretScanningCatalogFixtures.reviewDismissalRequestResult();

        assertSoftly(softly -> softly.assertThat(sut.dismissalReviewId()).isNotNull());
    }
}
