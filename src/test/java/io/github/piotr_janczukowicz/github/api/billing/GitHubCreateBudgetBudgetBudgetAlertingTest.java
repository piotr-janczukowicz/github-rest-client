package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateBudgetBudgetBudgetAlertingTest {

    @Test
    void constructor_shouldLeaveAlertRecipientsNull_whenNull() {
        final GitHubCreateBudgetBudgetBudgetAlerting value = GitHubCreateBudgetBudgetBudgetAlertingBuilder.builder(
                        BillingCatalogFixtures.createBudgetBudgetBudgetAlerting())
                .alertRecipients(null)
                .build();

        assertThat(value.alertRecipients()).isNull();
    }
}
