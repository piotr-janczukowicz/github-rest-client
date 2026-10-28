package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewDismissalRequestPayloadTest {

    @Test
    void gitHubReviewDismissalRequestPayload_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubReviewDismissalRequestPayload value = DependabotCatalogFixtures.reviewDismissalRequestPayload();

        assertThat(value.status()).isEqualTo(GitHubReviewDismissalRequestStatus.DENY);
        assertThat(value.message()).isEqualTo("Please review again.");
    }
}
