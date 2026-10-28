package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingAiCreditUsageReportUserTest {

    @Test
    void constructor_shouldThrow_whenTimePeriodNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingAiCreditUsageReportUserBuilder.builder(
                        BillingCatalogFixtures.billingAiCreditUsageReportUser())
                .timePeriod(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("timePeriod");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingAiCreditUsageReportUserBuilder.builder(
                        BillingCatalogFixtures.billingAiCreditUsageReportUser())
                .user(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenUsageItemsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBillingAiCreditUsageReportUserBuilder.builder(
                        BillingCatalogFixtures.billingAiCreditUsageReportUser())
                .usageItems(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("usageItems");
    }
}
