package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewBypassRequestResultTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubReviewBypassRequestResult sut = SecretScanningCatalogFixtures.reviewBypassRequestResult();

        assertSoftly(softly -> softly.assertThat(sut.bypassReviewId()).isNotNull());
    }
}
