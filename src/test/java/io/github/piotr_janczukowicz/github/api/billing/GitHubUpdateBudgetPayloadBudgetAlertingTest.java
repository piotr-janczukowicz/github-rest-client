package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBudgetPayloadBudgetAlertingTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.updateBudgetPayloadBudgetAlerting()).isNotNull();
    }

    @Test
    void constructor_shouldLeaveAlertRecipientsNull_whenNull() {
        final GitHubUpdateBudgetPayloadBudgetAlerting value = GitHubUpdateBudgetPayloadBudgetAlertingBuilder.builder(
                        BillingCatalogFixtures.updateBudgetPayloadBudgetAlerting())
                .alertRecipients(null)
                .build();

        assertThat(value.alertRecipients()).isNull();
    }
}
