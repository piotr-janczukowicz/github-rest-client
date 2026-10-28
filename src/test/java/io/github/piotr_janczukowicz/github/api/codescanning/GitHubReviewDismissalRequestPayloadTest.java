package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewDismissalRequestPayloadTest {

    @Test
    void gitHubReviewDismissalRequestPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubReviewDismissalRequestPayload value = CodeScanningCatalogFixtures.reviewDismissalRequestPayload();

        assertThat(value.status()).isEqualTo(GitHubReviewDismissalRequestStatus.APPROVE);
        assertThat(value.message()).isEqualTo("Looks good.");
    }
}
