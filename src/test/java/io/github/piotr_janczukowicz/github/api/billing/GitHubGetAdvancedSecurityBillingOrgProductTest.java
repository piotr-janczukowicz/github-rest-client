package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubGetAdvancedSecurityBillingOrgProductTest {

    @ParameterizedTest
    @CsvSource({"code_security,CODE_SECURITY", "secret_protection,SECRET_PROTECTION"})
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubGetAdvancedSecurityBillingOrgProduct value) {
        final GitHubGetAdvancedSecurityBillingOrgProduct decoded =
                GitHubGetAdvancedSecurityBillingOrgProduct.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetAdvancedSecurityBillingOrgProduct.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrow_whenValueNotKnown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGetAdvancedSecurityBillingOrgProduct.fromWire("brand_new_value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
