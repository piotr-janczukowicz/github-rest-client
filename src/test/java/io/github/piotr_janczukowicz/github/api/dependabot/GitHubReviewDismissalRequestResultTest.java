package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewDismissalRequestResultTest {

    @Test
    void gitHubReviewDismissalRequestResult_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubReviewDismissalRequestResult value = DependabotCatalogFixtures.reviewDismissalRequestResult();

        assertThat(value.dismissalReviewId()).isEqualTo(1L);
    }
}
