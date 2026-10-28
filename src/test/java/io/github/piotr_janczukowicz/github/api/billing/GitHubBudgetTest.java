package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBudgetTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBudgetBuilder.builder(BillingCatalogFixtures.budget())
                .id(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenBudgetTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBudgetBuilder.builder(BillingCatalogFixtures.budget())
                .budgetType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("budgetType");
    }

    @Test
    void constructor_shouldThrow_whenBudgetScopeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBudgetBuilder.builder(BillingCatalogFixtures.budget())
                .budgetScope(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("budgetScope");
    }

    @Test
    void constructor_shouldThrow_whenBudgetProductSkuNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBudgetBuilder.builder(BillingCatalogFixtures.budget())
                .budgetProductSku(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("budgetProductSku");
    }

    @Test
    void constructor_shouldThrow_whenBudgetAlertingNull() {
        final Throwable thrown = catchThrowable(() -> GitHubBudgetBuilder.builder(BillingCatalogFixtures.budget())
                .budgetAlerting(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("budgetAlerting");
    }
}
