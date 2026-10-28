package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpaceTeamCollaboratorTest {

    @Test
    void constructor_shouldThrow_whenActorTypeNull() {
        final GitHubCopilotSpaceTeamCollaboratorBuilder builder = GitHubCopilotSpaceTeamCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator())
                .actorType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("actorType");
    }

    @Test
    void constructor_shouldThrow_whenRoleNull() {
        final GitHubCopilotSpaceTeamCollaboratorBuilder builder = GitHubCopilotSpaceTeamCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator())
                .role(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("role");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubCopilotSpaceTeamCollaboratorBuilder builder = GitHubCopilotSpaceTeamCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCopilotSpaceTeamCollaboratorBuilder builder = GitHubCopilotSpaceTeamCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenSlugNull() {
        final GitHubCopilotSpaceTeamCollaboratorBuilder builder = GitHubCopilotSpaceTeamCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator())
                .slug(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("slug");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubCopilotSpaceTeamCollaboratorBuilder builder = GitHubCopilotSpaceTeamCollaboratorBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator())
                .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldLeaveParentNull_whenNull() {
        final GitHubCopilotSpaceTeamCollaborator sut = CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator();

        assertThat(sut.parent()).isNull();
    }
}
