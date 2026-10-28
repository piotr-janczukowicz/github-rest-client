package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserOperationOpTest {

    @Test
    void fromWire_shouldReturnAdd_whenValueAdd() {
        assertThat(GitHubScimUserOperationOp.fromWire("add")).isEqualTo(GitHubScimUserOperationOp.ADD);
    }

    @Test
    void fromWire_shouldReturnRemove_whenValueRemove() {
        assertThat(GitHubScimUserOperationOp.fromWire("remove")).isEqualTo(GitHubScimUserOperationOp.REMOVE);
    }

    @Test
    void fromWire_shouldReturnReplace_whenValueReplace() {
        assertThat(GitHubScimUserOperationOp.fromWire("replace")).isEqualTo(GitHubScimUserOperationOp.REPLACE);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubScimUserOperationOp decoded = GitHubScimUserOperationOp.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubScimUserOperationOp.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
