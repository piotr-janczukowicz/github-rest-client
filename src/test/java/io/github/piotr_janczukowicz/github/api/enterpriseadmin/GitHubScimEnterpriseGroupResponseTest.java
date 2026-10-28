package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimEnterpriseGroupResponseTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimEnterpriseGroupResponse value = EnterpriseAdminCatalogFixtures.scimEnterpriseGroupResponse();

        assertThat(GitHubScimEnterpriseGroupResponseBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveMembersNull_whenNull() {
        final GitHubScimEnterpriseGroupResponse value = GitHubScimEnterpriseGroupResponseBuilder.builder(
                        EnterpriseAdminCatalogFixtures.scimEnterpriseGroupResponse())
                .members(null)
                .build();

        assertThat(value.members()).isNull();
    }
}
