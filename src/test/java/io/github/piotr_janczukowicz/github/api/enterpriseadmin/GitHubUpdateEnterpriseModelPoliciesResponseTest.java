package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseModelPoliciesResponse value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesResponse();

        assertThat(value.defaultAvailability()).isNotNull();
        assertThat(value.models()).isNotEmpty();
    }
}
