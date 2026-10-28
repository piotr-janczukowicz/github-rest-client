package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetManageConfigApplyStatusFilterTest {

    @Test
    void toQueryParameters_shouldBeEmpty_whenNothingSet() {
        final Map<String, String> query =
                GitHubGetManageConfigApplyStatusFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldListEverything_whenAllSet() {
        final Map<String, String> query = EnterpriseAdminCatalogFixtures.getManageConfigApplyStatusFilter()
                .toQueryParameters();

        assertThat(query).containsExactly(Map.entry("run_id", "run_id"));
    }
}
