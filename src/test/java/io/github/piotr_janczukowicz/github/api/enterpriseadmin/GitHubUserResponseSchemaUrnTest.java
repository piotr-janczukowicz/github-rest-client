package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserResponseSchemaUrnTest {

    @Test
    void fromWire_shouldReturnUser_whenKnownValue() {
        assertThat(GitHubUserResponseSchemaUrn.fromWire("urn:ietf:params:scim:schemas:core:2.0:User"))
                .isEqualTo(GitHubUserResponseSchemaUrn.USER);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubUserResponseSchemaUrn.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
