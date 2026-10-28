package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemAuthorizationStateTest {

    @Test
    void fromWire_shouldReturnCurrentlyAuthorized_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemAuthorizationState.fromWire("currently_authorized"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemAuthorizationState.CURRENTLY_AUTHORIZED);
    }

    @Test
    void fromWire_shouldReturnMemberOwnedOnly_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemAuthorizationState.fromWire("member_owned_only"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemAuthorizationState.MEMBER_OWNED_ONLY);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubEnterpriseTokenInventoryItemAuthorizationState decoded =
                GitHubEnterpriseTokenInventoryItemAuthorizationState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubEnterpriseTokenInventoryItemAuthorizationState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
