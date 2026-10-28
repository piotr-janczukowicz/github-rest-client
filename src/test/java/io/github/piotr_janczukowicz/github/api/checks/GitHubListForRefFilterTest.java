package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListForRefFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters_whenCalled() {
        final GitHubListForRefFilter filter = GitHubListForRefFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAllParameters_whenFilterSet() {
        final GitHubListForRefFilter filter = ChecksCatalogFixtures.listForRefFilter();

        assertThat(filter.toQueryParameters())
                .containsEntry("check_name", "mighty_readme")
                .containsEntry("status", "completed")
                .containsEntry("filter", "latest")
                .containsEntry("app_id", "1");
    }
}
