package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAmazonS3OidcConfigAuthenticationTypeTest {

    @Test
    void fromWire_shouldReturnOidc_whenKnownValue() {
        assertThat(GitHubAmazonS3OidcConfigAuthenticationType.fromWire("oidc"))
                .isEqualTo(GitHubAmazonS3OidcConfigAuthenticationType.OIDC);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubAmazonS3OidcConfigAuthenticationType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
