package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddCollaboratorForOrgPayloadTest {

    @Test
    void constructor_shouldThrow_whenActorTypeNull() {
        final GitHubAddCollaboratorForOrgPayloadBuilder builder = GitHubAddCollaboratorForOrgPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.addCollaboratorForOrgPayload())
                .actorType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("actorType");
    }

    @Test
    void constructor_shouldThrow_whenActorIdentifierNull() {
        final GitHubAddCollaboratorForOrgPayloadBuilder builder = GitHubAddCollaboratorForOrgPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.addCollaboratorForOrgPayload())
                .actorIdentifier(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("actorIdentifier");
    }

    @Test
    void constructor_shouldThrow_whenRoleNull() {
        final GitHubAddCollaboratorForOrgPayloadBuilder builder = GitHubAddCollaboratorForOrgPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.addCollaboratorForOrgPayload())
                .role(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("role");
    }
}
