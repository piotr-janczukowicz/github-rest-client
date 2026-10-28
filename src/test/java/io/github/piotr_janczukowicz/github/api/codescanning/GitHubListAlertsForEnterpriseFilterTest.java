package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAlertsForEnterpriseFilterTest {

    @Test
    void empty_shouldOmitQueryParameters() {
        final GitHubListAlertsForEnterpriseFilter filter = GitHubListAlertsForEnterpriseFilter.empty();

        assertThat(filter.toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeEveryQuery_whenSet() {
        final GitHubListAlertsForEnterpriseFilter filter = CodeScanningCatalogFixtures.listAlertsForEnterpriseFilter();

        assertThat(filter.toQueryParameters())
                .containsExactly(
                        Map.entry("tool_name", "CodeQL"),
                        Map.entry("tool_guid", "2d1ec4ac-8e38-11ec-8d3d-0242ac130003"),
                        Map.entry("direction", "desc"),
                        Map.entry("state", "open"),
                        Map.entry("sort", "created"),
                        Map.entry("assignees", "octocat"));
    }
}
