package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingPremiumRequestUsageReportUserUsageItemTest {

    @Test
    void constructor_shouldThrow_whenProductNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubBillingPremiumRequestUsageReportUserUsageItemBuilder.builder(
                                BillingCatalogFixtures.billingPremiumRequestUsageReportUserUsageItem())
                        .product(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("product");
    }

    @Test
    void constructor_shouldThrow_whenSkuNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubBillingPremiumRequestUsageReportUserUsageItemBuilder.builder(
                                BillingCatalogFixtures.billingPremiumRequestUsageReportUserUsageItem())
                        .sku(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sku");
    }

    @Test
    void constructor_shouldThrow_whenModelNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubBillingPremiumRequestUsageReportUserUsageItemBuilder.builder(
                                BillingCatalogFixtures.billingPremiumRequestUsageReportUserUsageItem())
                        .model(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("model");
    }

    @Test
    void constructor_shouldThrow_whenUnitTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubBillingPremiumRequestUsageReportUserUsageItemBuilder.builder(
                                BillingCatalogFixtures.billingPremiumRequestUsageReportUserUsageItem())
                        .unitType(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("unitType");
    }
}
