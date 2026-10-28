package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemCredentialStateTest {

    @Test
    void fromWire_shouldReturnActive_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialState.fromWire("active"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialState.ACTIVE);
    }

    @Test
    void fromWire_shouldReturnExpired_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialState.fromWire("expired"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialState.EXPIRED);
    }

    @Test
    void fromWire_shouldReturnRevoked_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialState.fromWire("revoked"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialState.REVOKED);
    }

    @Test
    void fromWire_shouldReturnDeleted_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialState.fromWire("deleted"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialState.DELETED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialState.fromWire("not-a-known-value"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialState.UNKNOWN);
    }
}
