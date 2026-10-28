package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSearchIssuesFilterTest {

    @Test
    void constructor_shouldThrow_whenQNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubSearchIssuesFilter(null, null, null, null, null, false));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @Test
    void constructor_shouldThrow_whenQBlank() {
        final Throwable thrown = catchThrowable(() -> new GitHubSearchIssuesFilter(" ", null, null, null, null, false));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("q");
    }

    @Test
    void toQueryParameters_shouldIncludeAllFilters_whenSet() {
        final GitHubSearchIssuesFilter filter = GitHubSearchIssuesFilterBuilder.builder()
                .q("bug")
                .sort(GitHubIssueSearchSort.CREATED)
                .order(GitHubSearchOrder.ASC)
                .advancedSearch("true")
                .searchType(GitHubIssueSearchQueryType.SEMANTIC)
                .build();
        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("q", "bug");
            softly.assertThat(query).containsEntry("sort", "created");
            softly.assertThat(query).containsEntry("order", "asc");
            softly.assertThat(query).containsEntry("advanced_search", "true");
            softly.assertThat(query).containsEntry("search_type", "semantic");
        });
    }

    @Test
    void toQueryParameters_shouldOmitAdvancedSearch_whenAdvancedSearchNull() {
        final GitHubSearchIssuesFilter filter =
                GitHubSearchIssuesFilterBuilder.builder().q("bug").build();
        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactlyEntriesOf(Map.of("q", "bug"));
    }
}
