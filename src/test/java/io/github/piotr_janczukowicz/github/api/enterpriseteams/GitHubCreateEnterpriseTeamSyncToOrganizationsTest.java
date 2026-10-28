package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseTeamSyncToOrganizationsTest {

    @Test
    void fromWire_shouldReturnAll_whenValueAll() {
        assertThat(GitHubCreateEnterpriseTeamSyncToOrganizations.fromWire("all"))
                .isEqualTo(GitHubCreateEnterpriseTeamSyncToOrganizations.ALL);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenValueDisabled() {
        assertThat(GitHubCreateEnterpriseTeamSyncToOrganizations.fromWire("disabled"))
                .isEqualTo(GitHubCreateEnterpriseTeamSyncToOrganizations.DISABLED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCreateEnterpriseTeamSyncToOrganizations.fromWire("selected"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selected");
    }
}
