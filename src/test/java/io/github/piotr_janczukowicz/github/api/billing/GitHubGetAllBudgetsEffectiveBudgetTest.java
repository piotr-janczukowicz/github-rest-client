package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAllBudgetsEffectiveBudgetTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetAllBudgetsEffectiveBudgetBuilder.builder(
                        BillingCatalogFixtures.getAllBudgetsEffectiveBudget())
                .id(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }
}
