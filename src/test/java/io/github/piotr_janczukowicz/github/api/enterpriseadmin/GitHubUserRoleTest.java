package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserRoleTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUserRole value = EnterpriseAdminCatalogFixtures.userRole();

        assertThat(GitHubUserRoleBuilder.builder(value).build()).isEqualTo(value);
    }
}
