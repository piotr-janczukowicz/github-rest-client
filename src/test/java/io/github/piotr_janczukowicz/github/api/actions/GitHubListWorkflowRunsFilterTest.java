package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListWorkflowRunsFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters() {
        final GitHubListWorkflowRunsFilter sut = GitHubListWorkflowRunsFilter.empty();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeStatusAndHeadSha_whenSet() {
        final GitHubListWorkflowRunsFilter sut = new GitHubListWorkflowRunsFilter(
                null, null, null, GitHubWorkflowRunStatus.SUCCESS, null, null, null, "abc123");

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsExactlyInAnyOrderEntriesOf(Map.of("status", "success", "head_sha", "abc123"));
    }

    @Test
    void toQueryParameters_shouldIncludeEveryField_whenAllSet() {
        final GitHubListWorkflowRunsFilter sut = ActionsCatalogFixtures.listWorkflowRunsFilter();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query)
                .containsOnlyKeys(
                        "actor",
                        "branch",
                        "event",
                        "status",
                        "created",
                        "exclude_pull_requests",
                        "check_suite_id",
                        "head_sha");
    }

    @Test
    void toQueryParameters_shouldIncludeActorOnly_whenOnlyActorSet() {
        final GitHubListWorkflowRunsFilter sut =
                GitHubListWorkflowRunsFilterBuilder.builder().actor("octocat").build();

        final Map<String, String> query = sut.toQueryParameters();

        assertThat(query).containsOnlyKeys("actor");
    }
}
