package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListPreReceiveHooksFilterTest {

    @Test
    void toQueryParameters_shouldBeEmpty_whenNothingSet() {
        final Map<String, String> query =
                GitHubListPreReceiveHooksFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldListSortAndDirection_whenBothSet() {
        final Map<String, String> query =
                EnterpriseAdminCatalogFixtures.listPreReceiveHooksFilter().toQueryParameters();

        assertThat(query).containsExactly(Map.entry("sort", "created"), Map.entry("direction", "desc"));
    }
}
