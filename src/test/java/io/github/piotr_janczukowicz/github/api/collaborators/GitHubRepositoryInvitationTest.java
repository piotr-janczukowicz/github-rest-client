package io.github.piotr_janczukowicz.github.api.collaborators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryInvitationTest {

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final GitHubRepositoryInvitation base = CollaboratorsCatalogFixtures.repositoryInvitation();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryInvitation(
                base.id(),
                null,
                base.invitee(),
                base.inviter(),
                base.permissions(),
                base.createdAt(),
                base.expired(),
                base.url(),
                base.htmlUrl(),
                base.nodeId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final GitHubRepositoryInvitation base = CollaboratorsCatalogFixtures.repositoryInvitation();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryInvitation(
                base.id(),
                base.repository(),
                base.invitee(),
                base.inviter(),
                null,
                base.createdAt(),
                base.expired(),
                base.url(),
                base.htmlUrl(),
                base.nodeId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubRepositoryInvitation base = CollaboratorsCatalogFixtures.repositoryInvitation();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryInvitation(
                base.id(),
                base.repository(),
                base.invitee(),
                base.inviter(),
                base.permissions(),
                null,
                base.expired(),
                base.url(),
                base.htmlUrl(),
                base.nodeId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubRepositoryInvitation base = CollaboratorsCatalogFixtures.repositoryInvitation();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryInvitation(
                base.id(),
                base.repository(),
                base.invitee(),
                base.inviter(),
                base.permissions(),
                base.createdAt(),
                base.expired(),
                null,
                base.htmlUrl(),
                base.nodeId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubRepositoryInvitation base = CollaboratorsCatalogFixtures.repositoryInvitation();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryInvitation(
                base.id(),
                base.repository(),
                base.invitee(),
                base.inviter(),
                base.permissions(),
                base.createdAt(),
                base.expired(),
                base.url(),
                null,
                base.nodeId()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubRepositoryInvitation base = CollaboratorsCatalogFixtures.repositoryInvitation();

        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryInvitation(
                base.id(),
                base.repository(),
                base.invitee(),
                base.inviter(),
                base.permissions(),
                base.createdAt(),
                base.expired(),
                base.url(),
                base.htmlUrl(),
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }
}
