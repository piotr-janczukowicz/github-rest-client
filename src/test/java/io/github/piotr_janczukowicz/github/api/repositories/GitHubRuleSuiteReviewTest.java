package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuiteReviewTest {

    @Test
    void constructor_shouldStoreState_whenBuilt() {
        final GitHubRuleSuiteReview review =
                new GitHubRuleSuiteReview(80L, new GitHubRuleSuiteReviewUser(2L, "hubot", "Bot"), "APPROVED");

        assertThat(review.state()).isEqualTo("APPROVED");
    }
}
