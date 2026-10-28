package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseAppsModelPoliciesResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetEnterpriseAppsModelPoliciesResponse value =
                EnterpriseAdminCatalogFixtures.getEnterpriseAppsModelPoliciesResponse();

        assertThat(value.models()).isNotEmpty();
    }
}
