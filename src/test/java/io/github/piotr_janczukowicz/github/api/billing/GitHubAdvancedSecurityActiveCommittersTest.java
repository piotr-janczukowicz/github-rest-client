package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAdvancedSecurityActiveCommittersTest {

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAdvancedSecurityActiveCommittersBuilder.builder(
                        BillingCatalogFixtures.advancedSecurityActiveCommitters())
                .repositories(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }

    @Test
    void constructor_shouldKeepTotalCount_whenBuilt() {
        final GitHubAdvancedSecurityActiveCommitters sut = BillingCatalogFixtures.advancedSecurityActiveCommitters();

        assertThat(sut.totalCount()).isEqualTo(2);
    }
}
