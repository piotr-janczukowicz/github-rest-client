package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForRepoFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListForRepoFilter filter = GitHubListForRepoFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubListForRepoFilter filter = GitHubListForRepoFilterBuilder.builder()
                .milestone("1")
                .state(GitHubListIssuesState.OPEN)
                .assignee("octocat")
                .type("bug")
                .creator("hubot")
                .mentioned("octocat")
                .issueFieldValues("priority:high")
                .labels("bug")
                .sort(GitHubListIssuesSort.CREATED)
                .direction(GitHubDirection.DESC)
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("milestone", "1");
            softly.assertThat(query).containsEntry("state", "open");
            softly.assertThat(query).containsEntry("assignee", "octocat");
            softly.assertThat(query).containsEntry("type", "bug");
            softly.assertThat(query).containsEntry("creator", "hubot");
            softly.assertThat(query).containsEntry("mentioned", "octocat");
            softly.assertThat(query).containsEntry("issue_field_values", "priority:high");
            softly.assertThat(query).containsEntry("labels", "bug");
            softly.assertThat(query).containsEntry("sort", "created");
            softly.assertThat(query).containsEntry("direction", "desc");
            softly.assertThat(query).containsEntry("since", "2020-01-01T00:00:00Z");
        });
    }
}
