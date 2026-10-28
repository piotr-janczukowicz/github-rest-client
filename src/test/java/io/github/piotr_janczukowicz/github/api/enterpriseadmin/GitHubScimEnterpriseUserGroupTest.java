package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseUserGroupTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimEnterpriseUserGroup value = EnterpriseAdminCatalogFixtures.scimEnterpriseUserGroup();

        assertThat(GitHubScimEnterpriseUserGroupBuilder.builder(value).build()).isEqualTo(value);
    }
}
