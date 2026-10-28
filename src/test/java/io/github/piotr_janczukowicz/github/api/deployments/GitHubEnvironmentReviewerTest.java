package io.github.piotr_janczukowicz.github.api.deployments;

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
class GitHubEnvironmentReviewerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void ofUser_shouldStoreValue_whenCalled() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final GitHubEnvironmentReviewer reviewer = GitHubEnvironmentReviewer.ofUser(user);

        assertThat(reviewer).isEqualTo(new GitHubEnvironmentReviewer.SimpleUser(user));
    }

    @Test
    void ofUser_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> GitHubEnvironmentReviewer.ofUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void ofTeam_shouldStoreValue_whenCalled() {
        final GitHubTeam team = TeamCatalogFixtures.team();

        final GitHubEnvironmentReviewer reviewer = GitHubEnvironmentReviewer.ofTeam(team);

        assertThat(reviewer).isEqualTo(new GitHubEnvironmentReviewer.Team(team));
    }

    @Test
    void ofTeam_shouldThrow_whenTeamNull() {
        final Throwable thrown = catchThrowable(() -> GitHubEnvironmentReviewer.ofTeam(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("team");
    }

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnvironmentReviewer.SimpleUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenTeamNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnvironmentReviewer.Team(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("team");
    }

    @Test
    void decode_shouldReturnUser_whenJsonHasLogin() {
        final byte[] json = FileUtils.readBytes(GitHubSimpleUser.class, "simple-user-octocat.json");

        final GitHubEnvironmentReviewer decoded = CODEC.decode(json, GitHubEnvironmentReviewer.class);

        assertThat(decoded).isEqualTo(GitHubEnvironmentReviewer.ofUser(UsersCatalogFixtures.octocat()));
    }

    @Test
    void decode_shouldReturnTeam_whenJsonHasSlug() {
        final byte[] json = FileUtils.readBytes(GitHubTeam.class, "team.json");

        final GitHubEnvironmentReviewer decoded = CODEC.decode(json, GitHubEnvironmentReviewer.class);

        assertThat(decoded).isEqualTo(GitHubEnvironmentReviewer.ofTeam(TeamCatalogFixtures.team()));
    }

    @Test
    void decode_shouldThrow_whenShapeUnknown() {
        final byte[] json = "{\"id\":1}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubEnvironmentReviewer.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("reviewer shape");
    }
}
