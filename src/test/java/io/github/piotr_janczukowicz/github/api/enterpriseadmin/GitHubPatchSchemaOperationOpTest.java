package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPatchSchemaOperationOpTest {

    @Test
    void fromWire_shouldReturnAdd_whenKnownValue() {
        assertThat(GitHubPatchSchemaOperationOp.fromWire("add")).isEqualTo(GitHubPatchSchemaOperationOp.ADD);
    }

    @Test
    void fromWire_shouldReturnReplace_whenKnownValue() {
        assertThat(GitHubPatchSchemaOperationOp.fromWire("replace")).isEqualTo(GitHubPatchSchemaOperationOp.REPLACE);
    }

    @Test
    void fromWire_shouldReturnRemove_whenKnownValue() {
        assertThat(GitHubPatchSchemaOperationOp.fromWire("remove")).isEqualTo(GitHubPatchSchemaOperationOp.REMOVE);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubPatchSchemaOperationOp.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
