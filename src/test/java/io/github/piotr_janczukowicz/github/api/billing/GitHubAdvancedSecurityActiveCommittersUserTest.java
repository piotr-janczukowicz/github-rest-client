package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAdvancedSecurityActiveCommittersUserTest {

    @Test
    void constructor_shouldThrow_whenUserLoginNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAdvancedSecurityActiveCommittersUserBuilder.builder(
                        BillingCatalogFixtures.advancedSecurityActiveCommittersUser())
                .userLogin(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("userLogin");
    }

    @Test
    void constructor_shouldThrow_whenLastPushedDateNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAdvancedSecurityActiveCommittersUserBuilder.builder(
                        BillingCatalogFixtures.advancedSecurityActiveCommittersUser())
                .lastPushedDate(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("lastPushedDate");
    }

    @Test
    void constructor_shouldThrow_whenLastPushedEmailNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAdvancedSecurityActiveCommittersUserBuilder.builder(
                        BillingCatalogFixtures.advancedSecurityActiveCommittersUser())
                .lastPushedEmail(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("lastPushedEmail");
    }
}
