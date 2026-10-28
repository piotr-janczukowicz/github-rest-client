package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingPremiumRequestUsageReportOrgUsageItemTest {

    @Test
    void constructor_shouldThrow_whenProductNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportOrgUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportOrgUsageItem())
                .product(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("product");
    }

    @Test
    void constructor_shouldThrow_whenSkuNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportOrgUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportOrgUsageItem())
                .sku(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sku");
    }

    @Test
    void constructor_shouldThrow_whenModelNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportOrgUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportOrgUsageItem())
                .model(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("model");
    }

    @Test
    void constructor_shouldThrow_whenUnitTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportOrgUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportOrgUsageItem())
                .unitType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("unitType");
    }
}
