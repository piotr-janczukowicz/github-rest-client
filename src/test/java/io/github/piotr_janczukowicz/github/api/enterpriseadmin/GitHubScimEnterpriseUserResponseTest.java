package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseUserResponseTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimEnterpriseUserResponse value = EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse();

        assertThat(GitHubScimEnterpriseUserResponseBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveRolesNull_whenNull() {
        final GitHubScimEnterpriseUserResponse value = GitHubScimEnterpriseUserResponseBuilder.builder(
                        EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse())
                .roles(null)
                .build();

        assertThat(value.roles()).isNull();
    }

    @Test
    void constructor_shouldLeaveGroupsNull_whenNull() {
        final GitHubScimEnterpriseUserResponse value = GitHubScimEnterpriseUserResponseBuilder.builder(
                        EnterpriseAdminCatalogFixtures.scimEnterpriseUserResponse())
                .groups(null)
                .build();

        assertThat(value.groups()).isNull();
    }
}
