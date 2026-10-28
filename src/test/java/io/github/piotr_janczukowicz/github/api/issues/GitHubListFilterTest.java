package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListFilter filter = GitHubListFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubListFilter filter = GitHubListFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.ASSIGNED)
                .state(GitHubListIssuesState.OPEN)
                .labels("bug,enhancement")
                .sort(GitHubListIssuesSort.CREATED)
                .direction(GitHubDirection.DESC)
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .collab(true)
                .orgs(false)
                .owned(true)
                .pulls(false)
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("filter", "assigned");
            softly.assertThat(query).containsEntry("state", "open");
            softly.assertThat(query).containsEntry("labels", "bug,enhancement");
            softly.assertThat(query).containsEntry("sort", "created");
            softly.assertThat(query).containsEntry("direction", "desc");
            softly.assertThat(query).containsEntry("since", "2020-01-01T00:00:00Z");
            softly.assertThat(query).containsEntry("collab", "true");
            softly.assertThat(query).containsEntry("orgs", "false");
            softly.assertThat(query).containsEntry("owned", "true");
            softly.assertThat(query).containsEntry("pulls", "false");
        });
    }
}
