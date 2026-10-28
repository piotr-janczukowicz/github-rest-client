package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserRoleValueTest {

    @Test
    void fromWire_shouldReturnUser_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("user")).isEqualTo(GitHubUserRoleValue.USER);
    }

    @Test
    void fromWire_shouldReturnRole27d9891d_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("27d9891d-2c17-4f45-a262-781a0e55c80a"))
                .isEqualTo(GitHubUserRoleValue.ROLE_27D9891D);
    }

    @Test
    void fromWire_shouldReturnGuestCollaborator_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("guest_collaborator"))
                .isEqualTo(GitHubUserRoleValue.GUEST_COLLABORATOR);
    }

    @Test
    void fromWire_shouldReturnRole1ebc4a02_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("1ebc4a02-e56c-43a6-92a5-02ee09b90824"))
                .isEqualTo(GitHubUserRoleValue.ROLE_1EBC4A02);
    }

    @Test
    void fromWire_shouldReturnEnterpriseOwner_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("enterprise_owner")).isEqualTo(GitHubUserRoleValue.ENTERPRISE_OWNER);
    }

    @Test
    void fromWire_shouldReturnRole981df190_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("981df190-8801-4618-a08a-d91f6206c954"))
                .isEqualTo(GitHubUserRoleValue.ROLE_981DF190);
    }

    @Test
    void fromWire_shouldReturnRoleBa4987ab_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("ba4987ab-a1c3-412a-b58c-360fc407cb10"))
                .isEqualTo(GitHubUserRoleValue.ROLE_BA4987AB);
    }

    @Test
    void fromWire_shouldReturnBillingManager_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("billing_manager")).isEqualTo(GitHubUserRoleValue.BILLING_MANAGER);
    }

    @Test
    void fromWire_shouldReturnRole0e338b8c_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("0e338b8c-cc7f-498a-928d-ea3470d7e7e3"))
                .isEqualTo(GitHubUserRoleValue.ROLE_0E338B8C);
    }

    @Test
    void fromWire_shouldReturnRoleE6be2762_whenKnownValue() {
        assertThat(GitHubUserRoleValue.fromWire("e6be2762-e4ad-4108-b72d-1bbe884a0f91"))
                .isEqualTo(GitHubUserRoleValue.ROLE_E6BE2762);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubUserRoleValue.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
