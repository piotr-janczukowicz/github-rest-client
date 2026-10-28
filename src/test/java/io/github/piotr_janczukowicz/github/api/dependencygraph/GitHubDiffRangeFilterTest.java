package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDiffRangeFilterTest {

    @Test
    void empty_shouldReturnFilter_whenCalled() {
        final GitHubDiffRangeFilter filter = GitHubDiffRangeFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeName_whenFilterSet() {
        final GitHubDiffRangeFilter filter = DependencyGraphCatalogFixtures.diffRangeFilter();

        assertThat(filter.toQueryParameters()).containsEntry("name", "package.json");
    }
}
