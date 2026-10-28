package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemEffectiveAccessStateTest {

    @Test
    void fromWire_shouldReturnEffective_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.fromWire("effective"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.EFFECTIVE);
    }

    @Test
    void fromWire_shouldReturnNotEffective_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.fromWire("not_effective"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.NOT_EFFECTIVE);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.fromWire("unknown"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.UNKNOWN);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.fromWire("not-a-known-value"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.UNKNOWN);
    }
}
