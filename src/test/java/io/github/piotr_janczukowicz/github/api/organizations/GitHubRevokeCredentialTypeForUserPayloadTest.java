package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForUserPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialTypeForUserPayload value =
                OrganizationsCatalogFixtures.revokeCredentialTypeForUserPayload();
        final GitHubRevokeCredentialTypeForUserPayload expected =
                OrganizationsCatalogFixtures.revokeCredentialTypeForUserPayload();

        assertThat(value.credentialType()).isEqualTo(expected.credentialType());
    }

    @Test
    void constructor_shouldThrow_whenCredentialTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRevokeCredentialTypeForUserPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("credentialType");
    }
}
