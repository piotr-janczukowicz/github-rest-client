package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProtectedBranchPullRequestReviewTest {

    @Test
    void constructor_shouldThrow_whenRequiredApprovingReviewCountAboveMaximum() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubProtectedBranchPullRequestReview(null, null, null, true, true, 7, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("requiredApprovingReviewCount");
    }

    @Test
    void constructor_shouldAcceptCount_whenWithinDumpBounds() {
        final GitHubProtectedBranchPullRequestReview review =
                new GitHubProtectedBranchPullRequestReview(null, null, null, true, true, 6, null);

        assertThat(review.requiredApprovingReviewCount()).isEqualTo(6);
    }
}
