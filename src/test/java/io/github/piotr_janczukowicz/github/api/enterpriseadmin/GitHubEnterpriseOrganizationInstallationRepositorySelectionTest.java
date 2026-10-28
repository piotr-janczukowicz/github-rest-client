package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseOrganizationInstallationRepositorySelectionTest {

    @Test
    void fromWire_shouldReturnAll_whenKnownValue() {
        assertThat(GitHubEnterpriseOrganizationInstallationRepositorySelection.fromWire("all"))
                .isEqualTo(GitHubEnterpriseOrganizationInstallationRepositorySelection.ALL);
    }

    @Test
    void fromWire_shouldReturnSelected_whenKnownValue() {
        assertThat(GitHubEnterpriseOrganizationInstallationRepositorySelection.fromWire("selected"))
                .isEqualTo(GitHubEnterpriseOrganizationInstallationRepositorySelection.SELECTED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseOrganizationInstallationRepositorySelection.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
