package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamAccessRestrictionsPayloadTest {

    @Test
    void constructor_shouldThrow_whenBothBranchesSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubTeamAccessRestrictionsPayload(List.of("team"), List.of("team")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("teams or teamSlugs must have exactly one value present");
    }

    @Test
    void constructor_shouldThrow_whenNeitherBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubTeamAccessRestrictionsPayload(null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("teams or teamSlugs must have exactly one value present");
    }

    @Test
    void constructor_shouldAcceptEmptyTeams_whenTeamsBranch() {
        final GitHubTeamAccessRestrictionsPayload payload = GitHubTeamAccessRestrictionsPayload.withTeams(List.of());

        assertThat(payload.teams()).isEmpty();
    }

    @Test
    void constructor_shouldAcceptEmptyTeamSlugs_whenTeamSlugsBranch() {
        final GitHubTeamAccessRestrictionsPayload payload =
                GitHubTeamAccessRestrictionsPayload.withTeamSlugs(List.of());

        assertThat(payload.teamSlugs()).isEmpty();
    }
}
