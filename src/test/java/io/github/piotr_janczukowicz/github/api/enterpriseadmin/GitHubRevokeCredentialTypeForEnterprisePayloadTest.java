package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForEnterprisePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubRevokeCredentialTypeForEnterprisePayload value =
                EnterpriseAdminCatalogFixtures.revokeCredentialTypeForEnterprisePayload();

        assertThat(value.credentialType()).isEqualTo(GitHubRevokeCredentialTypeForEnterpriseCredentialType.CLASSIC_PAT);
        assertThat(value.revokeCredentials()).isTrue();
    }
}
