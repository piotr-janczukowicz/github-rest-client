package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGrantCredentialAuthorizationsCredentialTypeTest {

    @Test
    void fromWire_shouldReturnClassicPat_whenKnownValue() {
        assertThat(GitHubGrantCredentialAuthorizationsCredentialType.fromWire("classic_pat"))
                .isEqualTo(GitHubGrantCredentialAuthorizationsCredentialType.CLASSIC_PAT);
    }

    @Test
    void fromWire_shouldReturnSshKey_whenKnownValue() {
        assertThat(GitHubGrantCredentialAuthorizationsCredentialType.fromWire("ssh_key"))
                .isEqualTo(GitHubGrantCredentialAuthorizationsCredentialType.SSH_KEY);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGrantCredentialAuthorizationsCredentialType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
