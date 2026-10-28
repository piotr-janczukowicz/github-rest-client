package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpaceUserCollaboratorTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .login(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenAvatarUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .avatarUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("avatarUrl");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenFollowersUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .followersUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("followersUrl");
    }

    @Test
    void constructor_shouldThrow_whenFollowingUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .followingUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("followingUrl");
    }

    @Test
    void constructor_shouldThrow_whenGistsUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .gistsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("gistsUrl");
    }

    @Test
    void constructor_shouldThrow_whenStarredUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .starredUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("starredUrl");
    }

    @Test
    void constructor_shouldThrow_whenSubscriptionsUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .subscriptionsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("subscriptionsUrl");
    }

    @Test
    void constructor_shouldThrow_whenOrganizationsUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .organizationsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("organizationsUrl");
    }

    @Test
    void constructor_shouldThrow_whenReposUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .reposUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reposUrl");
    }

    @Test
    void constructor_shouldThrow_whenEventsUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .eventsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("eventsUrl");
    }

    @Test
    void constructor_shouldThrow_whenReceivedEventsUrlNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .receivedEventsUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("receivedEventsUrl");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenActorTypeNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .actorType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("actorType");
    }

    @Test
    void constructor_shouldThrow_whenRoleNull() {
        final GitHubCopilotSpaceUserCollaboratorBuilder builder = GitHubCopilotSpaceUserCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator())
                .role(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("role");
    }
}
