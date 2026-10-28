package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateBudgetBudgetTest {

    @Test
    void constructor_shouldThrow_whenBudgetAmountBelowMinimum() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateBudgetBudgetBuilder.builder(BillingCatalogFixtures.createBudgetBudget())
                        .budgetAmount(-1)
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldLeaveBudgetAmountNull_whenNull() {
        final GitHubCreateBudgetBudget value = GitHubCreateBudgetBudgetBuilder.builder(
                        BillingCatalogFixtures.createBudgetBudget())
                .budgetAmount(null)
                .build();

        assertThat(value.budgetAmount()).isNull();
    }
}
