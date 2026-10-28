package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForUserPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialTypeForUserPayload value =
                EnterpriseAdminCatalogFixtures.revokeCredentialTypeForUserPayload();

        assertThat(value.credentialType()).isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.CLASSIC_PAT);
        assertThat(value.revokeCredentials()).isTrue();
    }
}
