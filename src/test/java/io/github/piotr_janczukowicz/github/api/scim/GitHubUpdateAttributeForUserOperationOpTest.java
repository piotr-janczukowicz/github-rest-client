package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAttributeForUserOperationOpTest {

    @Test
    void fromWire_shouldReturnAdd_whenValueAdd() {
        assertThat(GitHubUpdateAttributeForUserOperationOp.fromWire("add"))
                .isEqualTo(GitHubUpdateAttributeForUserOperationOp.ADD);
    }

    @Test
    void fromWire_shouldReturnRemove_whenValueRemove() {
        assertThat(GitHubUpdateAttributeForUserOperationOp.fromWire("remove"))
                .isEqualTo(GitHubUpdateAttributeForUserOperationOp.REMOVE);
    }

    @Test
    void fromWire_shouldReturnReplace_whenValueReplace() {
        assertThat(GitHubUpdateAttributeForUserOperationOp.fromWire("replace"))
                .isEqualTo(GitHubUpdateAttributeForUserOperationOp.REPLACE);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateAttributeForUserOperationOp.fromWire("copy"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("copy");
    }
}
