package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListInstallationsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        final GitHubListInstallationsFilter filter = GitHubListInstallationsFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeSetFields_whenFilterFilled() {
        final GitHubListInstallationsFilter filter = AppsCatalogFixtures.listInstallationsFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("since", "2024-01-01T00:00:00Z").containsEntry("outdated", "true");
    }
}
