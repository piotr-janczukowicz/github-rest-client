package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseNetworkConfigurationsPageTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubEnterpriseNetworkConfigurationsPage value =
                EnterpriseAdminCatalogFixtures.enterpriseNetworkConfigurationsPage();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.networkConfigurations()).isNotEmpty();
    }
}
