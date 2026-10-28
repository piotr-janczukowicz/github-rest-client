package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoReviewCommentsFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubListRepoReviewCommentsFilter filter = GitHubListRepoReviewCommentsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSetFields_whenFilterSet() {
        final Instant since = Instant.parse("2024-01-15T12:00:00Z");
        final GitHubListRepoReviewCommentsFilter filter = GitHubListRepoReviewCommentsFilterBuilder.builder()
                .sort(GitHubListRepoReviewCommentsSort.UPDATED)
                .direction(GitHubListPullRequestsDirection.ASC)
                .since(since)
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("sort", "updated")
                .containsEntry("direction", "asc")
                .containsEntry("since", since.toString());
    }
}
