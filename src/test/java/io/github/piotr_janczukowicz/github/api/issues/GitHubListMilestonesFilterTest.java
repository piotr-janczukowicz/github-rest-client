package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListMilestonesFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListMilestonesFilter filter = GitHubListMilestonesFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubListMilestonesFilter filter = GitHubListMilestonesFilterBuilder.builder()
                .state(GitHubListIssuesState.CLOSED)
                .sort(GitHubListMilestonesSort.DUE_ON)
                .direction(GitHubDirection.ASC)
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("state", "closed");
            softly.assertThat(query).containsEntry("sort", "due_on");
            softly.assertThat(query).containsEntry("direction", "asc");
        });
    }
}
