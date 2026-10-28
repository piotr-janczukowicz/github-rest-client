package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBudgetBudgetAlertingTest {

    @Test
    void constructor_shouldThrow_whenAlertRecipientsNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubBudgetBudgetAlertingBuilder.builder(BillingCatalogFixtures.budgetBudgetAlerting())
                        .alertRecipients(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("alertRecipients");
    }
}
