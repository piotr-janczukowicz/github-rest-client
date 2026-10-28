package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateRepoSecretPayloadTest {

    @Test
    void constructor_shouldThrow_whenEncryptedValueNotBase64() {
        final GitHubCreateOrUpdateRepoSecretPayloadBuilder builder =
                GitHubCreateOrUpdateRepoSecretPayloadBuilder.builder(
                                AgentsCatalogFixtures.createOrUpdateRepoSecretPayload())
                        .encryptedValue("not-valid-base64!");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("encryptedValue");
    }

    @Test
    void constructor_shouldThrow_whenKeyIdNull() {
        final GitHubCreateOrUpdateRepoSecretPayloadBuilder builder =
                GitHubCreateOrUpdateRepoSecretPayloadBuilder.builder(
                                AgentsCatalogFixtures.createOrUpdateRepoSecretPayload())
                        .keyId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyId");
    }

    @Test
    void toString_shouldRedactEncryptedValue_whenCalled() {
        final GitHubCreateOrUpdateRepoSecretPayload payload = AgentsCatalogFixtures.createOrUpdateRepoSecretPayload();

        final String text = payload.toString();

        assertThat(text).contains("<redacted>").doesNotContain(payload.encryptedValue());
    }
}
