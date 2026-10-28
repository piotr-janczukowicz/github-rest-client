package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseGroupTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimEnterpriseGroup value = EnterpriseAdminCatalogFixtures.scimEnterpriseGroup();

        assertThat(GitHubScimEnterpriseGroupBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveMembersNull_whenNull() {
        final GitHubScimEnterpriseGroup value = GitHubScimEnterpriseGroupBuilder.builder(
                        EnterpriseAdminCatalogFixtures.scimEnterpriseGroup())
                .members(null)
                .build();

        assertThat(value.members()).isNull();
    }
}
