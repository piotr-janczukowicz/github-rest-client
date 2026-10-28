package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseUserListSchemaUrnTest {

    @Test
    void fromWire_shouldReturnListResponse_whenKnownValue() {
        assertThat(GitHubScimEnterpriseUserListSchemaUrn.fromWire("urn:ietf:params:scim:api:messages:2.0:ListResponse"))
                .isEqualTo(GitHubScimEnterpriseUserListSchemaUrn.LIST_RESPONSE);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubScimEnterpriseUserListSchemaUrn.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
