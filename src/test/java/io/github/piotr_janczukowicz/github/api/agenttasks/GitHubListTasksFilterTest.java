package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTasksFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListTasksFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeAllFields_whenSet() {
        final GitHubListTasksFilter filter = GitHubListTasksFilterBuilder.builder()
                .sort(GitHubListTasksSort.UPDATED_AT)
                .direction(GitHubListTasksDirection.DESC)
                .state("queued")
                .isArchived(true)
                .since(Instant.parse("2026-01-01T00:00:00Z"))
                .build();

        assertThat(filter.toQueryParameters())
                .containsEntry("sort", "updated_at")
                .containsEntry("direction", "desc")
                .containsEntry("state", "queued")
                .containsEntry("is_archived", "true")
                .containsEntry("since", "2026-01-01T00:00:00Z");
    }
}
