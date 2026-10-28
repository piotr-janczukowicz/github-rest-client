package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAdvancedSecurityProductTest {

    @Test
    void fromWire_shouldReturnCodeSecurity_whenKnownValue() {
        assertThat(GitHubAdvancedSecurityProduct.fromWire("code_security"))
                .isEqualTo(GitHubAdvancedSecurityProduct.CODE_SECURITY);
    }

    @Test
    void fromWire_shouldReturnSecretProtection_whenKnownValue() {
        assertThat(GitHubAdvancedSecurityProduct.fromWire("secret_protection"))
                .isEqualTo(GitHubAdvancedSecurityProduct.SECRET_PROTECTION);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubAdvancedSecurityProduct.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
