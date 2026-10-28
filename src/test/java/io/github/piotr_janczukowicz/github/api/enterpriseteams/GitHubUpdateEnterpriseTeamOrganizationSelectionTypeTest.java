package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamOrganizationSelectionTypeTest {

    @Test
    void fromWire_shouldReturnDisabled_whenValueDisabled() {
        assertThat(GitHubUpdateEnterpriseTeamOrganizationSelectionType.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamOrganizationSelectionType.DISABLED);
    }

    @Test
    void fromWire_shouldReturnSelected_whenValueSelected() {
        assertThat(GitHubUpdateEnterpriseTeamOrganizationSelectionType.fromWire("selected"))
                .isEqualTo(GitHubUpdateEnterpriseTeamOrganizationSelectionType.SELECTED);
    }

    @Test
    void fromWire_shouldReturnAll_whenValueAll() {
        assertThat(GitHubUpdateEnterpriseTeamOrganizationSelectionType.fromWire("all"))
                .isEqualTo(GitHubUpdateEnterpriseTeamOrganizationSelectionType.ALL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateEnterpriseTeamOrganizationSelectionType.fromWire("none"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("none");
    }
}
