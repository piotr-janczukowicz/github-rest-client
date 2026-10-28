package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseRoleTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubEnterpriseRole value = EnterpriseAdminCatalogFixtures.enterpriseRole();

        assertThat(GitHubEnterpriseRoleBuilder.builder(value).build()).isEqualTo(value);
    }
}
