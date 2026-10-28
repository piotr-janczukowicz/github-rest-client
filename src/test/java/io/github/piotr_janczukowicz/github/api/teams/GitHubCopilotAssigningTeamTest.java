package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotAssigningTeamTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnTeam_whenJsonIsFullTeam() {
        final byte[] json = FileUtils.readBytes(GitHubTeam.class, "team.json");

        final GitHubCopilotAssigningTeam decoded = CODEC.decode(json, GitHubCopilotAssigningTeam.class);

        assertThat(decoded).isEqualTo(TeamCatalogFixtures.team());
    }

    @Test
    void decode_shouldReturnEnterpriseTeam_whenJsonIsFullEnterpriseTeam() {
        final byte[] json = FileUtils.readBytes(GitHubEnterpriseTeam.class, "enterprise-team.json");

        final GitHubCopilotAssigningTeam decoded = CODEC.decode(json, GitHubCopilotAssigningTeam.class);

        assertThat(decoded).isEqualTo(TeamCatalogFixtures.enterpriseTeam());
    }

    @Test
    void decode_shouldReturnTeam_whenOnlyTeamRequiredPropertiesPresent() {
        final byte[] json = FileUtils.readBytes(
                GitHubCopilotAssigningTeamTest.class, "copilot-assigning-team-team-required-only.json");

        final GitHubCopilotAssigningTeam decoded = CODEC.decode(json, GitHubCopilotAssigningTeam.class);

        assertThat(decoded).isInstanceOf(GitHubTeam.class);
    }

    @Test
    void decode_shouldReturnEnterpriseTeam_whenOnlyEnterpriseTeamRequiredPropertiesPresent() {
        final byte[] json = FileUtils.readBytes(
                GitHubCopilotAssigningTeamTest.class, "copilot-assigning-team-enterprise-team-required-only.json");

        final GitHubCopilotAssigningTeam decoded = CODEC.decode(json, GitHubCopilotAssigningTeam.class);

        assertThat(decoded).isInstanceOf(GitHubEnterpriseTeam.class);
    }

    @Test
    void decode_shouldThrow_whenShapeMatchesNeitherTeam() {
        final byte[] json =
                FileUtils.readBytes(GitHubCopilotAssigningTeamTest.class, "copilot-assigning-team-unknown-shape.json");

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubCopilotAssigningTeam.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }
}
