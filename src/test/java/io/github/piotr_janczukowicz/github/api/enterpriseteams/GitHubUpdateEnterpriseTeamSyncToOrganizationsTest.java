package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamSyncToOrganizationsTest {

    @Test
    void fromWire_shouldReturnAll_whenValueAll() {
        assertThat(GitHubUpdateEnterpriseTeamSyncToOrganizations.fromWire("all"))
                .isEqualTo(GitHubUpdateEnterpriseTeamSyncToOrganizations.ALL);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenValueDisabled() {
        assertThat(GitHubUpdateEnterpriseTeamSyncToOrganizations.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamSyncToOrganizations.DISABLED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateEnterpriseTeamSyncToOrganizations.fromWire("selected"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selected");
    }
}
