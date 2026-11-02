package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseFeaturePoliciesPayloadTest {

    @Test
    void constructor_shouldThrow_whenDefaultAvailabilityNull() {
        final GitHubUpdateEnterpriseFeaturePoliciesPayloadBuilder builder =
                GitHubUpdateEnterpriseFeaturePoliciesPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateEnterpriseFeaturePoliciesPayload())
                        .defaultAvailability(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("defaultAvailability");
    }
}
