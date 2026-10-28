package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListProvisionedIdentitiesEnterpriseFilterTest {

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenFilterNotSet() {
        final Map<String, String> query =
                GitHubListProvisionedIdentitiesEnterpriseFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldSendEveryQueryParameter_whenFilterSet() {
        final Map<String, String> query = EnterpriseAdminCatalogFixtures.listProvisionedIdentitiesEnterpriseFilter()
                .toQueryParameters();

        assertThat(query).containsExactly(Map.entry("filter", "userName eq \"Mona Lisa\""));
    }
}
