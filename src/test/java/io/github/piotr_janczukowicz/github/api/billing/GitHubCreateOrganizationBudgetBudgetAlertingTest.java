package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrganizationBudgetBudgetAlertingTest {

    @Test
    void constructor_shouldLeaveAlertRecipientsNull_whenNull() {
        final GitHubCreateOrganizationBudgetBudgetAlerting value =
                GitHubCreateOrganizationBudgetBudgetAlertingBuilder.builder(
                                BillingCatalogFixtures.createOrganizationBudgetBudgetAlerting())
                        .alertRecipients(null)
                        .build();

        assertThat(value.alertRecipients()).isNull();
    }
}
