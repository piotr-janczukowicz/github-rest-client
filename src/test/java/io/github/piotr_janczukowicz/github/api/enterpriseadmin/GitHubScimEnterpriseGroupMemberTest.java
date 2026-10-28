package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseGroupMemberTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimEnterpriseGroupMember value = EnterpriseAdminCatalogFixtures.scimEnterpriseGroupMember();

        assertThat(GitHubScimEnterpriseGroupMemberBuilder.builder(value).build())
                .isEqualTo(value);
    }
}
