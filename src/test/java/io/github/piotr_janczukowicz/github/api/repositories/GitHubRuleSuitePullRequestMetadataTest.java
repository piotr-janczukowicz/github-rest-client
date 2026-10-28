package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRuleSuitePullRequestMetadataTest {

    @Test
    void constructor_shouldStoreReviews_whenReviewsSet() {
        final GitHubRuleSuiteReview review = RepositoriesCatalogFixtures.ruleSuiteReview();

        final GitHubRuleSuitePullRequestMetadata metadata =
                new GitHubRuleSuitePullRequestMetadata(1L, 2, null, List.of(review), null);

        assertThat(metadata.reviews()).containsExactly(review);
    }

    @Test
    void constructor_shouldLeaveReviewsNull_whenReviewsAbsent() {
        final GitHubRuleSuitePullRequestMetadata metadata =
                new GitHubRuleSuitePullRequestMetadata(1L, 2, null, null, null);

        assertThat(metadata.reviews()).isNull();
    }
}
