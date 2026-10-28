package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingUsageReportUserTest {

    @Test
    void constructor_shouldLeaveUsageItemsNull_whenNull() {
        final GitHubBillingUsageReportUser value = GitHubBillingUsageReportUserBuilder.builder(
                        BillingCatalogFixtures.billingUsageReportUser())
                .usageItems(null)
                .build();

        assertThat(value.usageItems()).isNull();
    }
}
