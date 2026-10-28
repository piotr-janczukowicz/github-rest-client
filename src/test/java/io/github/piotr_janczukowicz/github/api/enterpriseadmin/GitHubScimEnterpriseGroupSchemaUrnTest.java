package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseGroupSchemaUrnTest {

    @Test
    void fromWire_shouldReturnGroup_whenKnownValue() {
        assertThat(GitHubScimEnterpriseGroupSchemaUrn.fromWire("urn:ietf:params:scim:schemas:core:2.0:Group"))
                .isEqualTo(GitHubScimEnterpriseGroupSchemaUrn.GROUP);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubScimEnterpriseGroupSchemaUrn.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
