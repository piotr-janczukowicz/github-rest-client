package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingCostCenterMembershipMembershipCostCenterTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubBillingCostCenterMembershipMembershipCostCenterBuilder builder =
                GitHubBillingCostCenterMembershipMembershipCostCenterBuilder.builder(
                                BillingCatalogFixtures.billingCostCenterMembershipMembershipCostCenter())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubBillingCostCenterMembershipMembershipCostCenterBuilder builder =
                GitHubBillingCostCenterMembershipMembershipCostCenterBuilder.builder(
                                BillingCatalogFixtures.billingCostCenterMembershipMembershipCostCenter())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
