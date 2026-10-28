package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetWorkflowRunAttemptFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubGetWorkflowRunAttemptFilter sut = GitHubGetWorkflowRunAttemptFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeExcludePullRequests_whenSet() {
        final GitHubGetWorkflowRunAttemptFilter sut = new GitHubGetWorkflowRunAttemptFilter(false);

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("exclude_pull_requests", "false"));
    }
}
