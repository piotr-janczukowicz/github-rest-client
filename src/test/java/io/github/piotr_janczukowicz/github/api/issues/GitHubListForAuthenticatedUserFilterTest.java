package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListForAuthenticatedUserFilter filter = GitHubListForAuthenticatedUserFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubListForAuthenticatedUserFilter filter = GitHubListForAuthenticatedUserFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.MENTIONED)
                .state(GitHubListIssuesState.CLOSED)
                .labels("bug")
                .sort(GitHubListIssuesSort.COMMENTS)
                .direction(GitHubDirection.DESC)
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("filter", "mentioned");
            softly.assertThat(query).containsEntry("state", "closed");
            softly.assertThat(query).containsEntry("labels", "bug");
            softly.assertThat(query).containsEntry("sort", "comments");
            softly.assertThat(query).containsEntry("direction", "desc");
            softly.assertThat(query).containsEntry("since", "2020-01-01T00:00:00Z");
        });
    }
}
