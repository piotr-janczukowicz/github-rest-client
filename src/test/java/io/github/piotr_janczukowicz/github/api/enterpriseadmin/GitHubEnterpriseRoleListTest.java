package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRoleListTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubEnterpriseRoleList value = EnterpriseAdminCatalogFixtures.enterpriseRoleList();

        assertThat(GitHubEnterpriseRoleListBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveRolesNull_whenNull() {
        final GitHubEnterpriseRoleList value = GitHubEnterpriseRoleListBuilder.builder(
                        EnterpriseAdminCatalogFixtures.enterpriseRoleList())
                .roles(null)
                .build();

        assertThat(value.roles()).isNull();
    }
}
