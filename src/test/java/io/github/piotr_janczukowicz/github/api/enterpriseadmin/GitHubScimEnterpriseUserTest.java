package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseUserTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimEnterpriseUser value = EnterpriseAdminCatalogFixtures.scimEnterpriseUser();

        assertThat(GitHubScimEnterpriseUserBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveRolesNull_whenNull() {
        final GitHubScimEnterpriseUser value = GitHubScimEnterpriseUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.scimEnterpriseUser())
                .roles(null)
                .build();

        assertThat(value.roles()).isNull();
    }
}
