package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetEnterpriseModelPoliciesResponseDefaultAvailability value =
                EnterpriseAdminCatalogFixtures.getEnterpriseModelPoliciesResponseDefaultAvailability();

        assertThat(value.policyState())
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
    }
}
