package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListEnterpriseTokenInventoryAuthorizationStateTest {

    @Test
    void fromWire_shouldReturnCurrentlyAuthorized_whenKnownValue() {
        assertThat(GitHubListEnterpriseTokenInventoryAuthorizationState.fromWire("currently_authorized"))
                .isEqualTo(GitHubListEnterpriseTokenInventoryAuthorizationState.CURRENTLY_AUTHORIZED);
    }

    @Test
    void fromWire_shouldReturnMemberOwnedOnly_whenKnownValue() {
        assertThat(GitHubListEnterpriseTokenInventoryAuthorizationState.fromWire("member_owned_only"))
                .isEqualTo(GitHubListEnterpriseTokenInventoryAuthorizationState.MEMBER_OWNED_ONLY);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubListEnterpriseTokenInventoryAuthorizationState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
