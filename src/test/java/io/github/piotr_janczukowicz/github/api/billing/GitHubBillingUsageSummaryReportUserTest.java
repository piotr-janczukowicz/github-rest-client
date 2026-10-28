package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingUsageSummaryReportUserTest {

    @Test
    void constructor_shouldThrow_whenTimePeriodNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportUserBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportUser())
                .timePeriod(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timePeriod");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportUserBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportUser())
                .user(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenUsageItemsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingUsageSummaryReportUserBuilder.builder(
                        BillingCatalogFixtures.billingUsageSummaryReportUser())
                .usageItems(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("usageItems");
    }
}
