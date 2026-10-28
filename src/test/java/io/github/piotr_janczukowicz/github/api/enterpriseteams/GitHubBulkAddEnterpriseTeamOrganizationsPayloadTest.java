package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBulkAddEnterpriseTeamOrganizationsPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubBulkAddEnterpriseTeamOrganizationsPayload payload =
                EnterpriseTeamsCatalogFixtures.bulkAddEnterpriseTeamOrganizationsPayload();

        assertThat(payload.organizationSlugs()).containsExactly("github");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenOrganizationSlugsNull() {
        final GitHubBulkAddEnterpriseTeamOrganizationsPayloadBuilder builder =
                GitHubBulkAddEnterpriseTeamOrganizationsPayloadBuilder.builder(
                                EnterpriseTeamsCatalogFixtures.bulkAddEnterpriseTeamOrganizationsPayload())
                        .organizationSlugs(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationSlugs");
    }
}
