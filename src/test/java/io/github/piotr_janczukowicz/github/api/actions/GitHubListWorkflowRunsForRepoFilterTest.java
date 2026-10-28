package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListWorkflowRunsForRepoFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListWorkflowRunsForRepoFilter sut = GitHubListWorkflowRunsForRepoFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFields_whenSet() {
        final GitHubListWorkflowRunsForRepoFilter sut = new GitHubListWorkflowRunsForRepoFilter(
                "octocat",
                "main",
                "push",
                GitHubWorkflowRunStatus.IN_PROGRESS,
                Instant.parse("2024-01-02T03:04:05Z"),
                true,
                42L,
                "abc123");

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query)
                .containsExactlyInAnyOrderEntriesOf(Map.of(
                        "actor", "octocat",
                        "branch", "main",
                        "event", "push",
                        "status", "in_progress",
                        "created", "2024-01-02T03:04:05Z",
                        "exclude_pull_requests", "true",
                        "check_suite_id", "42",
                        "head_sha", "abc123"));
    }
}
