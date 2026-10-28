package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemTypeTest {

    @Test
    void fromWire_shouldReturnCredential_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemType.fromWire("credential"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemType.CREDENTIAL);
    }

    @Test
    void fromWire_shouldReturnTokenIssuerPrincipal_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemType.fromWire("token_issuer_principal"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemType.TOKEN_ISSUER_PRINCIPAL);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubEnterpriseTokenInventoryItemType.fromWire("not-a-known-value"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemType.UNKNOWN);
    }
}
