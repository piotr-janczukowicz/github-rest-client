package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseTeamPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubCreateEnterpriseTeamPayload payload = EnterpriseTeamsCatalogFixtures.createEnterpriseTeamPayload();

        assertThat(payload.name()).isEqualTo("Justice League");
        assertThat(payload.syncToOrganizations()).isEqualTo(GitHubCreateEnterpriseTeamSyncToOrganizations.DISABLED);
        assertThat(payload.organizationSelectionType())
                .isEqualTo(GitHubCreateEnterpriseTeamOrganizationSelectionType.SELECTED);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final GitHubCreateEnterpriseTeamPayloadBuilder builder = GitHubCreateEnterpriseTeamPayloadBuilder.builder(
                        EnterpriseTeamsCatalogFixtures.createEnterpriseTeamPayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
