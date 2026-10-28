package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTasksForRepoFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListTasksForRepoFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeAllFields_whenSet() {
        final GitHubListTasksForRepoFilter filter = GitHubListTasksForRepoFilterBuilder.builder()
                .sort(GitHubListTasksForRepoSort.CREATED_AT)
                .direction(GitHubListTasksForRepoDirection.ASC)
                .state("completed")
                .isArchived(false)
                .since(Instant.parse("2026-01-01T00:00:00Z"))
                .creatorId(List.of(1L, 2L))
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("sort", "created_at")
                .containsEntry("direction", "asc")
                .containsEntry("state", "completed")
                .containsEntry("is_archived", "false")
                .containsEntry("since", "2026-01-01T00:00:00Z")
                .containsEntry("creator_id", "1,2");
    }

    @Test
    void toQueryValues_shouldRepeatCreatorId_whenSeveralValues() {
        final GitHubListTasksForRepoFilter filter = GitHubListTasksForRepoFilterBuilder.builder()
                .state("completed")
                .creatorId(List.of(1L, 2L))
                .build();

        assertThat(filter.toQueryValues())
                .containsEntry("state", List.of("completed"))
                .containsEntry("creator_id", List.of("1", "2"));
    }

    @Test
    void toQueryValues_shouldBeEmpty_whenNothingSet() {
        assertThat(GitHubListTasksForRepoFilter.empty().toQueryValues()).isEmpty();
    }
}
