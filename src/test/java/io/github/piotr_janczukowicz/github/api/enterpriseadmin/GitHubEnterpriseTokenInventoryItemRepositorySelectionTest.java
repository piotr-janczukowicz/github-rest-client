package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemRepositorySelectionTest {

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemRepositorySelection.fromWire("all"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemRepositorySelection.ALL);
    }

    @Test
    void fromWire_shouldReturnSubset_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemRepositorySelection.fromWire("subset"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemRepositorySelection.SUBSET);
    }

    @Test
    void fromWire_shouldReturnNone_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemRepositorySelection.fromWire("none"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemRepositorySelection.NONE);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseTokenInventoryItemRepositorySelection.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
