package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListSuitesForRefFilterTest {

    @Test
    void empty_shouldReturnFilterWithoutQueryParameters_whenCalled() {
        final GitHubListSuitesForRefFilter filter = GitHubListSuitesForRefFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeAppIdAndCheckName_whenFilterSet() {
        final GitHubListSuitesForRefFilter filter = ChecksCatalogFixtures.listSuitesForRefFilter();

        assertThat(filter.toQueryParameters())
                .containsEntry("app_id", "1")
                .containsEntry("check_name", "mighty_readme");
    }
}
