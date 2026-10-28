package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailability value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesResponseDefaultAvailability();

        assertThat(value.policyState())
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
    }
}
