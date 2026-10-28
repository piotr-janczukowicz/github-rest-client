package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRoleSourceTest {

    @Test
    void fromWire_shouldReturnEnterprise_whenKnownValue() {
        assertThat(GitHubEnterpriseRoleSource.fromWire("Enterprise")).isEqualTo(GitHubEnterpriseRoleSource.ENTERPRISE);
    }

    @Test
    void fromWire_shouldReturnPredefined_whenKnownValue() {
        assertThat(GitHubEnterpriseRoleSource.fromWire("Predefined")).isEqualTo(GitHubEnterpriseRoleSource.PREDEFINED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubEnterpriseRoleSource decoded = GitHubEnterpriseRoleSource.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubEnterpriseRoleSource.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
