package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetProvisioningInformationForEnterpriseGroupFilterTest {

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenFilterNotSet() {
        final Map<String, String> query =
                GitHubGetProvisioningInformationForEnterpriseGroupFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldSendEveryQueryParameter_whenFilterSet() {
        final Map<String, String> query =
                EnterpriseAdminCatalogFixtures.getGroupProvisioningFilter().toQueryParameters();

        assertThat(query).containsExactly(Map.entry("excludedAttributes", "members"));
    }
}
