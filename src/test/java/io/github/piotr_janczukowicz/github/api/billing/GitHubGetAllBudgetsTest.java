package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllBudgetsTest {

    @Test
    void constructor_shouldThrow_whenBudgetsNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGetAllBudgetsBuilder.builder(BillingCatalogFixtures.getAllBudgets())
                        .budgets(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("budgets");
    }
}
