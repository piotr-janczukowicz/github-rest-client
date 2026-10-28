package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseGroupListSchemaUrnTest {

    @Test
    void fromWire_shouldReturnListResponse_whenKnownValue() {
        assertThat(GitHubScimEnterpriseGroupListSchemaUrn.fromWire(
                        "urn:ietf:params:scim:api:messages:2.0:ListResponse"))
                .isEqualTo(GitHubScimEnterpriseGroupListSchemaUrn.LIST_RESPONSE);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubScimEnterpriseGroupListSchemaUrn.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
