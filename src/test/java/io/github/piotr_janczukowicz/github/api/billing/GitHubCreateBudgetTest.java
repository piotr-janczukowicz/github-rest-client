package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateBudgetTest {

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateBudgetBuilder.builder(BillingCatalogFixtures.createBudget())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenBudgetNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateBudgetBuilder.builder(BillingCatalogFixtures.createBudget())
                        .budget(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("budget");
    }
}
