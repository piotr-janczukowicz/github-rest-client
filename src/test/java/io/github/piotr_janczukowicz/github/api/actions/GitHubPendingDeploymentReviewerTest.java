package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubPendingDeploymentReviewerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void ofUser_shouldStoreValue_whenCalled() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final GitHubPendingDeploymentReviewer reviewer = GitHubPendingDeploymentReviewer.ofUser(user);

        assertThat(reviewer).isEqualTo(new GitHubPendingDeploymentReviewer.SimpleUser(user));
    }

    @Test
    void ofUser_shouldThrowNullPointerException_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> GitHubPendingDeploymentReviewer.ofUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void ofTeam_shouldStoreValue_whenCalled() {
        final GitHubTeam team = TeamCatalogFixtures.team();

        final GitHubPendingDeploymentReviewer reviewer = GitHubPendingDeploymentReviewer.ofTeam(team);

        assertThat(reviewer).isEqualTo(new GitHubPendingDeploymentReviewer.Team(team));
    }

    @Test
    void ofTeam_shouldThrowNullPointerException_whenTeamNull() {
        final Throwable thrown = catchThrowable(() -> GitHubPendingDeploymentReviewer.ofTeam(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("team");
    }

    @Test
    void decode_shouldReturnUser_whenJsonHasLogin() {
        final byte[] json = FileUtils.readBytes(GitHubSimpleUser.class, "simple-user-octocat.json");

        final GitHubPendingDeploymentReviewer decoded = CODEC.decode(json, GitHubPendingDeploymentReviewer.class);

        assertThat(decoded).isEqualTo(GitHubPendingDeploymentReviewer.ofUser(UsersCatalogFixtures.octocat()));
    }

    @Test
    void decode_shouldReturnTeam_whenJsonHasSlug() {
        final byte[] json = FileUtils.readBytes(GitHubTeam.class, "team.json");

        final GitHubPendingDeploymentReviewer decoded = CODEC.decode(json, GitHubPendingDeploymentReviewer.class);

        assertThat(decoded).isEqualTo(GitHubPendingDeploymentReviewer.ofTeam(TeamCatalogFixtures.team()));
    }

    @Test
    void decode_shouldReturnTeam_whenJsonHasSlugAndLogin() {
        final String team = new String(FileUtils.readBytes(GitHubTeam.class, "team.json"), StandardCharsets.UTF_8);
        final byte[] json = team.replaceFirst("[{]", "{\"login\":\"octocat\",").getBytes(StandardCharsets.UTF_8);

        final GitHubPendingDeploymentReviewer decoded = CODEC.decode(json, GitHubPendingDeploymentReviewer.class);

        assertThat(decoded).isEqualTo(GitHubPendingDeploymentReviewer.ofTeam(TeamCatalogFixtures.team()));
    }

    @Test
    void decode_shouldThrow_whenShapeUnknown() {
        final byte[] json = "{\"id\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubPendingDeploymentReviewer.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("Unknown GitHub pending deployment reviewer shape");
    }
}
