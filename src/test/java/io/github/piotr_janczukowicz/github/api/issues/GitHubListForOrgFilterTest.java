package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForOrgFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListForOrgFilter filter = GitHubListForOrgFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubListForOrgFilter filter = GitHubListForOrgFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.CREATED)
                .state(GitHubListIssuesState.OPEN)
                .labels("bug")
                .type("Task")
                .sort(GitHubListIssuesSort.UPDATED)
                .direction(GitHubDirection.ASC)
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("filter", "created");
            softly.assertThat(query).containsEntry("state", "open");
            softly.assertThat(query).containsEntry("labels", "bug");
            softly.assertThat(query).containsEntry("type", "Task");
            softly.assertThat(query).containsEntry("sort", "updated");
            softly.assertThat(query).containsEntry("direction", "asc");
            softly.assertThat(query).containsEntry("since", "2020-01-01T00:00:00Z");
        });
    }
}
