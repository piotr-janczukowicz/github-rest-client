package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseUserSchemaUrnTest {

    @Test
    void fromWire_shouldReturnUser_whenKnownValue() {
        assertThat(GitHubScimEnterpriseUserSchemaUrn.fromWire("urn:ietf:params:scim:schemas:core:2.0:User"))
                .isEqualTo(GitHubScimEnterpriseUserSchemaUrn.USER);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubScimEnterpriseUserSchemaUrn.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
