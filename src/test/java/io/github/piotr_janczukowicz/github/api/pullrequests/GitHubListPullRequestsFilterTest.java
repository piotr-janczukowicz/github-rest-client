package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPullRequestsFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubListPullRequestsFilter filter = GitHubListPullRequestsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSetFields_whenFilterSet() {
        final GitHubListPullRequestsFilter filter = GitHubListPullRequestsFilterBuilder.builder()
                .state(GitHubListPullRequestsState.OPEN)
                .head("octocat:main")
                .base("master")
                .sort(GitHubListPullRequestsSort.UPDATED)
                .direction(GitHubListPullRequestsDirection.DESC)
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("state", "open")
                .containsEntry("head", "octocat:main")
                .containsEntry("base", "master")
                .containsEntry("sort", "updated")
                .containsEntry("direction", "desc");
    }
}
