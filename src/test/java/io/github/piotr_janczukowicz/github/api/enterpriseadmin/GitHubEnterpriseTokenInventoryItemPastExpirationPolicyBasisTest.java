package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasisTest {

    @Test
    void fromWire_shouldReturnEnforcedLimit_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.fromWire("enforced_limit"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.ENFORCED_LIMIT);
    }

    @Test
    void fromWire_shouldReturnProposedBaseline_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.fromWire("proposed_baseline"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.PROPOSED_BASELINE);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis decoded =
                GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
