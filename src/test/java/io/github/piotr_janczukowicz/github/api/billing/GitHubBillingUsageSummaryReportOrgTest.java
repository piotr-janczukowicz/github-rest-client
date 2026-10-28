package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingUsageSummaryReportOrgTest {

    @Test
    void constructor_shouldThrow_whenTimePeriodNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportOrgBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportOrg())
                .timePeriod(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timePeriod");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportOrgBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportOrg())
                .organization(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organization");
    }

    @Test
    void constructor_shouldThrow_whenUsageItemsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportOrgBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportOrg())
                .usageItems(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("usageItems");
    }
}
