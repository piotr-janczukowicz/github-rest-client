package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetManageUpgradeStatusFilterTest {

    @Test
    void toQueryParameters_shouldBeEmpty_whenNothingSet() {
        final Map<String, String> query =
                GitHubGetManageUpgradeStatusFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldListEverything_whenAllSet() {
        final Map<String, String> query =
                EnterpriseAdminCatalogFixtures.getManageUpgradeStatusFilter().toQueryParameters();

        assertThat(query).containsExactly(Map.entry("uuid", "uuid"), Map.entry("is_verbose", "true"));
    }
}
