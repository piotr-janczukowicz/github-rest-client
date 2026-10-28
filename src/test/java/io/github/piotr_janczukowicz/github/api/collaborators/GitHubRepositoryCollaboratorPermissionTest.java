package io.github.piotr_janczukowicz.github.api.collaborators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryCollaboratorPermissionTest {

    @Test
    void constructor_shouldThrow_whenPermissionNull() {
        final GitHubRepositoryCollaboratorPermission base =
                CollaboratorsCatalogFixtures.repositoryCollaboratorPermission();

        final Throwable thrown =
                catchThrowable(() -> new GitHubRepositoryCollaboratorPermission(null, base.roleName(), base.user()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permission");
    }

    @Test
    void constructor_shouldThrow_whenRoleNameNull() {
        final GitHubRepositoryCollaboratorPermission base =
                CollaboratorsCatalogFixtures.repositoryCollaboratorPermission();

        final Throwable thrown =
                catchThrowable(() -> new GitHubRepositoryCollaboratorPermission(base.permission(), null, base.user()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("roleName");
    }
}
