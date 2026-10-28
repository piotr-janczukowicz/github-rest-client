package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateSecretForAuthenticatedUserPayloadTest {

    @Test
    void constructor_shouldThrow_whenKeyIdNull() {
        final GitHubCreateOrUpdateSecretForAuthenticatedUserPayloadBuilder builder =
                GitHubCreateOrUpdateSecretForAuthenticatedUserPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload())
                        .keyId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyId");
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenMutableList() {
        final List<String> mutable = new ArrayList<>(List.of("1296269", "1296270"));
        final GitHubCreateOrUpdateSecretForAuthenticatedUserPayload sut =
                GitHubCreateOrUpdateSecretForAuthenticatedUserPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload())
                        .selectedRepositoryIds(mutable)
                        .build();

        mutable.clear();

        assertThat(sut.selectedRepositoryIds()).containsExactly("1296269", "1296270");
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubCreateOrUpdateSecretForAuthenticatedUserPayload sut =
                GitHubCreateOrUpdateSecretForAuthenticatedUserPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload())
                        .selectedRepositoryIds(null)
                        .build();

        assertThat(sut.selectedRepositoryIds()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenEncryptedValueNotBase64() {
        final GitHubCreateOrUpdateSecretForAuthenticatedUserPayloadBuilder builder =
                GitHubCreateOrUpdateSecretForAuthenticatedUserPayloadBuilder.builder(
                                CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload())
                        .encryptedValue("not-valid-base64!");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("encryptedValue");
    }

    @Test
    void toString_shouldRedactEncryptedValue_whenCalled() {
        final GitHubCreateOrUpdateSecretForAuthenticatedUserPayload payload =
                CodespacesCatalogFixtures.createOrUpdateSecretForAuthenticatedUserPayload();

        final String text = payload.toString();

        assertThat(text).contains("<redacted>").doesNotContain(payload.encryptedValue());
    }
}
