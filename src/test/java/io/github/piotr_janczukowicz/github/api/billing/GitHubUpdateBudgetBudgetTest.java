package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBudgetBudgetTest {

    @Test
    void constructor_shouldThrow_whenBudgetAmountBelowMinimum() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateBudgetBudgetBuilder.builder(BillingCatalogFixtures.updateBudgetBudget())
                        .budgetAmount(-1)
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldLeaveBudgetAmountNull_whenNull() {
        final GitHubUpdateBudgetBudget value = GitHubUpdateBudgetBudgetBuilder.builder(
                        BillingCatalogFixtures.updateBudgetBudget())
                .budgetAmount(null)
                .build();

        assertThat(value.budgetAmount()).isNull();
    }
}
