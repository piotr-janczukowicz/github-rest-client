package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAdvancedSecurityActiveCommittersRepositoryTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAdvancedSecurityActiveCommittersRepositoryBuilder.builder(
                        BillingCatalogFixtures.advancedSecurityActiveCommittersRepository())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBreakdownNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAdvancedSecurityActiveCommittersRepositoryBuilder.builder(
                        BillingCatalogFixtures.advancedSecurityActiveCommittersRepository())
                .advancedSecurityCommittersBreakdown(null)
                .build());

        assertThat(thrown)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("advancedSecurityCommittersBreakdown");
    }
}
