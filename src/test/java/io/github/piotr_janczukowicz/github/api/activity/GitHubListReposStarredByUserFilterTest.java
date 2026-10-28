package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListReposStarredByUserFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubListReposStarredByUserFilter filter = GitHubListReposStarredByUserFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSortAndDirection_whenFilterSet() {
        final GitHubListReposStarredByUserFilter filter = ActivityCatalogFixtures.listReposStarredByUserFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsOnly(Map.entry("sort", "updated"), Map.entry("direction", "asc"));
    }
}
