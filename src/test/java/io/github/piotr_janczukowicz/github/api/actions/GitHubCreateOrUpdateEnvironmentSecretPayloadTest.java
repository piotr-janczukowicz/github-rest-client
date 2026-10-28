package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateEnvironmentSecretPayloadTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenEncryptedValueDoesNotMatch() {
        final String keyId = "keyId-sample";

        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrUpdateEnvironmentSecretPayload("!not valid!", keyId));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenKeyIdNull() {
        final String encryptedValue = "YWJjZA==";

        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrUpdateEnvironmentSecretPayload(encryptedValue, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyId");
    }

    @Test
    void toString_shouldRedactEncryptedValue() {
        final GitHubCreateOrUpdateEnvironmentSecretPayload payload =
                ActionsCatalogFixtures.createOrUpdateEnvironmentSecretPayload();

        final String text = payload.toString();

        assertThat(text)
                .isEqualTo(
                        "GitHubCreateOrUpdateEnvironmentSecretPayload[keyId=keyId-sample, encryptedValue=<redacted>]");
    }
}
