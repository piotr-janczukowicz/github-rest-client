package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForSuiteFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters_whenCalled() {
        final GitHubListForSuiteFilter filter = GitHubListForSuiteFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeCheckNameStatusAndFilter_whenFilterSet() {
        final GitHubListForSuiteFilter filter = ChecksCatalogFixtures.listForSuiteFilter();

        assertThat(filter.toQueryParameters())
                .containsEntry("check_name", "mighty_readme")
                .containsEntry("status", "completed")
                .containsEntry("filter", "latest");
    }
}
