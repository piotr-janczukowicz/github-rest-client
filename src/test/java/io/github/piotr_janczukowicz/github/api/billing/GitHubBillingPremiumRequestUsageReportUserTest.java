package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingPremiumRequestUsageReportUserTest {

    @Test
    void constructor_shouldThrow_whenTimePeriodNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportUserBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportUser())
                .timePeriod(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timePeriod");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportUserBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportUser())
                .user(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenUsageItemsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingPremiumRequestUsageReportUserBuilder.builder(
                        BillingCatalogFixtures.billingPremiumRequestUsageReportUser())
                .usageItems(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("usageItems");
    }
}
