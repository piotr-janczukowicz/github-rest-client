package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgMembershipPermissionsTest {

    @Test
    void constructor_shouldStoreCanCreateRepository_whenCalled() {
        final GitHubOrgMembershipPermissions permissions = new GitHubOrgMembershipPermissions(true);

        assertThat(permissions.canCreateRepository()).isTrue();
    }
}
