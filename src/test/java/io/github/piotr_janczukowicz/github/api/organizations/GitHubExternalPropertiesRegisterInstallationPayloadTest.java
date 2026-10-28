package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalPropertiesRegisterInstallationPayloadTest {

    @Test
    void constructor_shouldThrow_whenDisplayNameNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesRegisterInstallationPayload(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldThrow_whenDisplayNameEmpty() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesRegisterInstallationPayload(null, ""));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldThrow_whenDisplayNameTooLong() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesRegisterInstallationPayload(null, "a".repeat(16)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldThrow_whenDisplayNameHasInvalidCharacters() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubExternalPropertiesRegisterInstallationPayload(null, "Acme Corp"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldAcceptDisplayName_whenAtBounds() {
        assertThat(new GitHubExternalPropertiesRegisterInstallationPayload(null, "a").displayName())
                .isEqualTo("a");
        assertThat(new GitHubExternalPropertiesRegisterInstallationPayload(null, "a".repeat(15)).displayName())
                .hasSize(15);
    }

    @Test
    void constructor_shouldLeaveInstallationIdNull_whenNull() {
        final GitHubExternalPropertiesRegisterInstallationPayload payload =
                new GitHubExternalPropertiesRegisterInstallationPayload(null, "Acme");

        assertThat(payload.installationId()).isNull();
    }
}
