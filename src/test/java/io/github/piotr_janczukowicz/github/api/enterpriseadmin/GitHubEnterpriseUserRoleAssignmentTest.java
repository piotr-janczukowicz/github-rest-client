package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseUserRoleAssignmentTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubEnterpriseUserRoleAssignment value = EnterpriseAdminCatalogFixtures.enterpriseUserRoleAssignment();

        assertThat(GitHubEnterpriseUserRoleAssignmentBuilder.builder(value).build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveInheritedFromNull_whenNull() {
        final GitHubEnterpriseUserRoleAssignment value = GitHubEnterpriseUserRoleAssignmentBuilder.builder(
                        EnterpriseAdminCatalogFixtures.enterpriseUserRoleAssignment())
                .inheritedFrom(null)
                .build();

        assertThat(value.inheritedFrom()).isNull();
    }
}
