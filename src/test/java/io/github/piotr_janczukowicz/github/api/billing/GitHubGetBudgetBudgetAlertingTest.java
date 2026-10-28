package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetBudgetBudgetAlertingTest {

    @Test
    void constructor_shouldLeaveAlertRecipientsNull_whenNull() {
        final GitHubGetBudgetBudgetAlerting value = GitHubGetBudgetBudgetAlertingBuilder.builder(
                        BillingCatalogFixtures.getBudgetBudgetAlerting())
                .alertRecipients(null)
                .build();

        assertThat(value.alertRecipients()).isNull();
    }
}
