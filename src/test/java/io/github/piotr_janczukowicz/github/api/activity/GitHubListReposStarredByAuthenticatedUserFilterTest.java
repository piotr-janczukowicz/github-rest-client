package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListReposStarredByAuthenticatedUserFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubListReposStarredByAuthenticatedUserFilter filter =
                GitHubListReposStarredByAuthenticatedUserFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSortAndDirection_whenFilterSet() {
        final GitHubListReposStarredByAuthenticatedUserFilter filter =
                ActivityCatalogFixtures.listReposStarredByAuthenticatedUserFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsOnly(Map.entry("sort", "created"), Map.entry("direction", "desc"));
    }
}
