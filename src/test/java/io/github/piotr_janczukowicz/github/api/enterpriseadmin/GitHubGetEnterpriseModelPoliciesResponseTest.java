package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseModelPoliciesResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetEnterpriseModelPoliciesResponse value =
                EnterpriseAdminCatalogFixtures.getEnterpriseModelPoliciesResponse();

        assertThat(value.defaultAvailability()).isNotNull();
        assertThat(value.models()).isNotEmpty();
    }
}
