package io.github.piotr_janczukowicz.github.api.codespaces;

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
                                CodespacesCatalogFixtures.createOrUpdateRepoSecretPayload())
                        .encryptedValue("not-valid-base64!");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("encryptedValue");
    }

    @Test
    void constructor_shouldLeaveEncryptedValueNull_whenNull() {
        final GitHubCreateOrUpdateRepoSecretPayloadBuilder builder =
                GitHubCreateOrUpdateRepoSecretPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createOrUpdateRepoSecretPayload())
                        .encryptedValue(null);

        final GitHubCreateOrUpdateRepoSecretPayload sut = builder.build();

        assertThat(sut.encryptedValue()).isNull();
    }

    @Test
    void toString_shouldRedactEncryptedValue_whenCalled() {
        final GitHubCreateOrUpdateRepoSecretPayload payload =
                CodespacesCatalogFixtures.createOrUpdateRepoSecretPayload();

        final String text = payload.toString();

        assertThat(text).contains("<redacted>").doesNotContain(payload.encryptedValue());
    }
}
