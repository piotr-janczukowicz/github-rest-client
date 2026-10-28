package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseTokenInventoryExportAuthorizationStateTest {

    @Test
    void fromWire_shouldReturnCurrentlyAuthorized_whenKnownValue() {
        assertThat(GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.fromWire("currently_authorized"))
                .isEqualTo(GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.CURRENTLY_AUTHORIZED);
    }

    @Test
    void fromWire_shouldReturnMemberOwnedOnly_whenKnownValue() {
        assertThat(GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.fromWire("member_owned_only"))
                .isEqualTo(GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.MEMBER_OWNED_ONLY);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
