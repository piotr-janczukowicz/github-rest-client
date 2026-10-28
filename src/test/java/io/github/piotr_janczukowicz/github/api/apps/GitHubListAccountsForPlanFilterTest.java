package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAccountsForPlanFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubListAccountsForPlanFilter filter = GitHubListAccountsForPlanFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSetFields_whenFilterFilled() {
        final GitHubListAccountsForPlanFilter filter = AppsCatalogFixtures.listAccountsForPlanFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("sort", "updated").containsEntry("direction", "asc");
    }
}
