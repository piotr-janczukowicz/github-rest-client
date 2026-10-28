package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListSuggestionsFilterTest {

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubListSuggestionsFilter filter = GitHubListSuggestionsFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubListSuggestionsFilter filter = GitHubListSuggestionsFilterBuilder.builder()
                .state(GitHubListIssueSuggestionsState.PENDING)
                .action(GitHubIssueSuggestionAction.ADD_LABEL)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("state", "pending").containsEntry("action", "add_label");
    }
}
