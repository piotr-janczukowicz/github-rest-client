package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseTeamOrganizationSelectionTypeTest {

    @Test
    void fromWire_shouldReturnDisabled_whenValueDisabled() {
        assertThat(GitHubCreateEnterpriseTeamOrganizationSelectionType.fromWire("disabled"))
                .isEqualTo(GitHubCreateEnterpriseTeamOrganizationSelectionType.DISABLED);
    }

    @Test
    void fromWire_shouldReturnSelected_whenValueSelected() {
        assertThat(GitHubCreateEnterpriseTeamOrganizationSelectionType.fromWire("selected"))
                .isEqualTo(GitHubCreateEnterpriseTeamOrganizationSelectionType.SELECTED);
    }

    @Test
    void fromWire_shouldReturnAll_whenValueAll() {
        assertThat(GitHubCreateEnterpriseTeamOrganizationSelectionType.fromWire("all"))
                .isEqualTo(GitHubCreateEnterpriseTeamOrganizationSelectionType.ALL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateEnterpriseTeamOrganizationSelectionType.fromWire("none"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("none");
    }
}
