package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGrantCredentialAuthorizationsResponseTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGrantCredentialAuthorizationsResponse value =
                EnterpriseAdminCatalogFixtures.grantCredentialAuthorizationsResponse();

        assertThat(value.credentialId()).isEqualTo(1L);
        assertThat(value.credentialType()).isEqualTo(GitHubGrantCredentialAuthorizationsCredentialType.SSH_KEY);
        assertThat(value.fingerprint()).isEqualTo("SHA256:sample");
        assertThat(value.organizations()).containsExactly("octo-org");
    }

    @Test
    void constructor_shouldLeaveFingerprintNull_whenClassicPat() {
        final GitHubGrantCredentialAuthorizationsResponse value =
                GitHubGrantCredentialAuthorizationsResponseBuilder.builder(
                                EnterpriseAdminCatalogFixtures.grantCredentialAuthorizationsClassicPatResponse())
                        .fingerprint(null)
                        .build();

        assertThat(value.fingerprint()).isNull();
    }
}
