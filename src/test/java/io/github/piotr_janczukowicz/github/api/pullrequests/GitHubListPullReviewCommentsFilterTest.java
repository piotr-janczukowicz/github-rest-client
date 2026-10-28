package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPullReviewCommentsFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubListPullReviewCommentsFilter filter = GitHubListPullReviewCommentsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSetFields_whenFilterSet() {
        final Instant since = Instant.parse("2024-01-15T12:00:00Z");
        final GitHubListPullReviewCommentsFilter filter = GitHubListPullReviewCommentsFilterBuilder.builder()
                .sort(GitHubListPullReviewCommentsSort.CREATED)
                .direction(GitHubListPullRequestsDirection.DESC)
                .since(since)
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("sort", "created")
                .containsEntry("direction", "desc")
                .containsEntry("since", since.toString());
    }
}
