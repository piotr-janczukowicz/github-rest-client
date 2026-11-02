package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationTasksFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListRepoAutomationTasksFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeAllFields_whenSet() {
        final GitHubListRepoAutomationTasksFilter filter = GitHubListRepoAutomationTasksFilterBuilder.builder()
                .sort(GitHubListRepoAutomationTasksSort.UPDATED_AT)
                .direction(GitHubListRepoAutomationTasksDirection.ASC)
                .state("completed")
                .isArchived(false)
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("sort", "updated_at")
                .containsEntry("direction", "asc")
                .containsEntry("state", "completed")
                .containsEntry("is_archived", "false");
    }
}
