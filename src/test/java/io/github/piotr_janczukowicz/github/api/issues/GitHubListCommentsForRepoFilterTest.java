package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCommentsForRepoFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListCommentsForRepoFilter filter = GitHubListCommentsForRepoFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubListCommentsForRepoFilter filter = GitHubListCommentsForRepoFilterBuilder.builder()
                .sort(GitHubListCommentsForRepoSort.UPDATED)
                .direction(GitHubDirection.ASC)
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("sort", "updated");
            softly.assertThat(query).containsEntry("direction", "asc");
            softly.assertThat(query).containsEntry("since", "2020-01-01T00:00:00Z");
        });
    }
}
