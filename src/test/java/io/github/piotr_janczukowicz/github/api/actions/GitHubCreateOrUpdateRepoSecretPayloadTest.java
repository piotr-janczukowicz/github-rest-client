package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateRepoSecretPayloadTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenEncryptedValueDoesNotMatch() {
        final String keyId = "keyId-sample";

        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrUpdateRepoSecretPayload("!not valid!", keyId));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenKeyIdNull() {
        final String encryptedValue = "YWJjZA==";

        final Throwable thrown = catchThrowable(() -> new GitHubCreateOrUpdateRepoSecretPayload(encryptedValue, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyId");
    }

    @Test
    void toString_shouldRedactEncryptedValue() {
        final GitHubCreateOrUpdateRepoSecretPayload payload = ActionsCatalogFixtures.createOrUpdateRepoSecretPayload();

        final String text = payload.toString();

        assertThat(text)
                .isEqualTo("GitHubCreateOrUpdateRepoSecretPayload[keyId=keyId-sample, encryptedValue=<redacted>]");
    }
}
