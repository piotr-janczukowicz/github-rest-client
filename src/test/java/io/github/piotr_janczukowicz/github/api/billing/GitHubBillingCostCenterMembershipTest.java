package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingCostCenterMembershipTest {

    @Test
    void constructor_shouldThrow_whenResourceNull() {
        final GitHubBillingCostCenterMembershipBuilder builder = GitHubBillingCostCenterMembershipBuilder.builder(
                        BillingCatalogFixtures.billingCostCenterMembership())
                .resource(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("resource");
    }

    @Test
    void constructor_shouldThrow_whenMembershipsNull() {
        final GitHubBillingCostCenterMembershipBuilder builder = GitHubBillingCostCenterMembershipBuilder.builder(
                        BillingCatalogFixtures.billingCostCenterMembership())
                .memberships(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("memberships");
    }
}
