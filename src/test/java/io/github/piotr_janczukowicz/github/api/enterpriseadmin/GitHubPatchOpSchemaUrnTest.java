package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPatchOpSchemaUrnTest {

    @Test
    void fromWire_shouldReturnPatchOp_whenKnownValue() {
        assertThat(GitHubPatchOpSchemaUrn.fromWire("urn:ietf:params:scim:api:messages:2.0:PatchOp"))
                .isEqualTo(GitHubPatchOpSchemaUrn.PATCH_OP);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubPatchOpSchemaUrn.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
