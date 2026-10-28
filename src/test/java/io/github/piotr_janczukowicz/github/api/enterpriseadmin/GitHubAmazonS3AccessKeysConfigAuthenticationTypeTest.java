package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAmazonS3AccessKeysConfigAuthenticationTypeTest {

    @Test
    void fromWire_shouldReturnAccessKeys_whenKnownValue() {
        assertThat(GitHubAmazonS3AccessKeysConfigAuthenticationType.fromWire("access_keys"))
                .isEqualTo(GitHubAmazonS3AccessKeysConfigAuthenticationType.ACCESS_KEYS);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubAmazonS3AccessKeysConfigAuthenticationType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
