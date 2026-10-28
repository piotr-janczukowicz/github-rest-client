package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLicenseUploadFilterTest {

    @Test
    void toQueryParameters_shouldBeEmpty_whenNothingSet() {
        final Map<String, String> query = GitHubLicenseUploadFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldListApply_whenSet() {
        final Map<String, String> query =
                EnterpriseAdminCatalogFixtures.licenseUploadFilter().toQueryParameters();

        assertThat(query).containsExactly(Map.entry("apply", "true"));
    }
}
