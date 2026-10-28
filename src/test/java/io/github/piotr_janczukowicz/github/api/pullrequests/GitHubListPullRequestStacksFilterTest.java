package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPullRequestStacksFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubListPullRequestStacksFilter filter = GitHubListPullRequestStacksFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludePullRequest_whenFilterSet() {
        final GitHubListPullRequestStacksFilter filter = GitHubListPullRequestStacksFilterBuilder.builder()
                .pullRequest(101)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("pull_request", "101");
    }
}
