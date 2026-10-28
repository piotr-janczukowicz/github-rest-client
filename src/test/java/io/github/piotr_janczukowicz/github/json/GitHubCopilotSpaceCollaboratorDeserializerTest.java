package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.copilotspaces.CopilotSpacesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.copilotspaces.GitHubCopilotSpaceCollaborator;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubCopilotSpaceCollaboratorDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnUserCollaborator_whenActorTypeUser() {
        final byte[] json =
                FileUtils.readBytes(GitHubCopilotSpaceCollaborator.class, "copilot-space-user-collaborator.json");

        final GitHubCopilotSpaceCollaborator decoded = CODEC.decode(json, GitHubCopilotSpaceCollaborator.class);

        assertThat(decoded).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator());
    }

    @Test
    void decode_shouldReturnTeamCollaborator_whenActorTypeTeam() {
        final byte[] json =
                FileUtils.readBytes(GitHubCopilotSpaceCollaborator.class, "copilot-space-team-collaborator.json");

        final GitHubCopilotSpaceCollaborator decoded = CODEC.decode(json, GitHubCopilotSpaceCollaborator.class);

        assertThat(decoded).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator());
    }

    @Test
    void decode_shouldThrow_whenActorTypeUnknown() {
        final byte[] json = "{\"actor_type\":\"Robot\"}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubCopilotSpaceCollaborator.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("Robot");
    }
}
