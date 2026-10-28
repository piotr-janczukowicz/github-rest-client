package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingUsageReportUsageItemTest {

    @Test
    void constructor_shouldThrow_whenDateNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageReportUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageReportUsageItem())
                .date(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("date");
    }

    @Test
    void constructor_shouldThrow_whenProductNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageReportUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageReportUsageItem())
                .product(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("product");
    }

    @Test
    void constructor_shouldThrow_whenSkuNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageReportUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageReportUsageItem())
                .sku(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sku");
    }

    @Test
    void constructor_shouldThrow_whenUnitTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageReportUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageReportUsageItem())
                .unitType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("unitType");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageReportUsageItemBuilder.builder(
                        BillingCatalogFixtures.billingUsageReportUsageItem())
                .organizationName(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationName");
    }
}
