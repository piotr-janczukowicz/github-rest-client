package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBudgetBudgetBudgetAlertingTest {

    @Test
    void constructor_shouldLeaveAlertRecipientsNull_whenNull() {
        final GitHubUpdateBudgetBudgetBudgetAlerting value = GitHubUpdateBudgetBudgetBudgetAlertingBuilder.builder(
                        BillingCatalogFixtures.updateBudgetBudgetBudgetAlerting())
                .alertRecipients(null)
                .build();

        assertThat(value.alertRecipients()).isNull();
    }
}
