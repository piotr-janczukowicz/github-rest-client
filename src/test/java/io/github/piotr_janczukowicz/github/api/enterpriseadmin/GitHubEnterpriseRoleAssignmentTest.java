package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRoleAssignmentTest {

    @Test
    void fromWire_shouldReturnDirect_whenKnownValue() {
        assertThat(GitHubEnterpriseRoleAssignment.fromWire("direct")).isEqualTo(GitHubEnterpriseRoleAssignment.DIRECT);
    }

    @Test
    void fromWire_shouldReturnIndirect_whenKnownValue() {
        assertThat(GitHubEnterpriseRoleAssignment.fromWire("indirect"))
                .isEqualTo(GitHubEnterpriseRoleAssignment.INDIRECT);
    }

    @Test
    void fromWire_shouldReturnMixed_whenKnownValue() {
        assertThat(GitHubEnterpriseRoleAssignment.fromWire("mixed")).isEqualTo(GitHubEnterpriseRoleAssignment.MIXED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubEnterpriseRoleAssignment decoded = GitHubEnterpriseRoleAssignment.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubEnterpriseRoleAssignment.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
