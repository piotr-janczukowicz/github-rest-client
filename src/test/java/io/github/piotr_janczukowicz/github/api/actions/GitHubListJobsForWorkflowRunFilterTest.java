package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListJobsForWorkflowRunFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListJobsForWorkflowRunFilter sut = GitHubListJobsForWorkflowRunFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeFilter_whenSet() {
        final GitHubListJobsForWorkflowRunFilter sut = new GitHubListJobsForWorkflowRunFilter(GitHubJobFilterMode.ALL);

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("filter", "all"));
    }
}
