package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingPremiumRequestUsageReportOrgTest {

    @Test
    void constructor_shouldThrow_whenTimePeriodNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportOrgBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportOrg())
                .timePeriod(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timePeriod");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportOrgBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportOrg())
                .organization(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organization");
    }

    @Test
    void constructor_shouldThrow_whenUsageItemsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportOrgBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportOrg())
                .usageItems(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("usageItems");
    }
}
