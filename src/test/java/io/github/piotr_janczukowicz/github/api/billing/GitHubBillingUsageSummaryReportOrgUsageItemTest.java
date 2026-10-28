package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingUsageSummaryReportOrgUsageItemTest {

    @Test
    void constructor_shouldThrow_whenProductNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportOrgUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportOrgUsageItem())
                .product(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("product");
    }

    @Test
    void constructor_shouldThrow_whenSkuNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportOrgUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportOrgUsageItem())
                .sku(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sku");
    }

    @Test
    void constructor_shouldThrow_whenUnitTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportOrgUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportOrgUsageItem())
                .unitType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("unitType");
    }
}
